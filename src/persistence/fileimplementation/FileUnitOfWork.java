package persistence.fileimplementation;

import entities.*;
import persistence.interfaces.UnitOfWork;

import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.util.*;

public class FileUnitOfWork implements UnitOfWork {

  private final String directoryPath;

  private List<OwnedStock> ownedStocks;
  private List<Portfolio> portfolios;
  private List<Stock> stocks;
  private List<StockPriceHistory> stockPriceHistories;
  private List<Transaction> transactions;

  // Append-only buffers
  private final List<StockPriceHistory> stockPriceHistoryBuffer = new ArrayList<>();
  private final List<Transaction> transactionBuffer = new ArrayList<>();

  public FileUnitOfWork(String directoryPath) {
    this.directoryPath = directoryPath;
    ensureFilesExist();
  }

  // ---------------------------
  // File initialization
  // ---------------------------

  private void ensureFilesExist() {
    try {
      Files.createDirectories(Paths.get(directoryPath));
    } catch (IOException e) {
      throw new RuntimeException("Failed to create directory: " + directoryPath, e);
    }

    createFileIfMissing(getOwnedStockFilePath());
    createFileIfMissing(getPortfolioFilePath());
    createFileIfMissing(getStockFilePath());
    createFileIfMissing(getStockPriceHistoryFilePath());
    createFileIfMissing(getTransactionFilePath());
  }

  private void createFileIfMissing(String path) {
    try {
      Files.createFile(Paths.get(path));
    } catch (IOException ignored) {
      // File already exists → ignore
    }
  }

  // ---------------------------
  // File paths
  // ---------------------------

  private String getOwnedStockFilePath() {
    return directoryPath + "/ownedstocks.txt";
  }

  private String getPortfolioFilePath() {
    return directoryPath + "/portfolios.txt";
  }

  private String getStockFilePath() {
    return directoryPath + "/stocks.txt";
  }

  private String getStockPriceHistoryFilePath() {
    return directoryPath + "/stockpricehistory.txt";
  }

  private String getTransactionFilePath() {
    return directoryPath + "/transactions.txt";
  }

  // ---------------------------
  // Helpers
  // ---------------------------

  private List<String[]> readAndParseLinesFromFile(String filePath) {
    List<String[]> result = new ArrayList<>();
    try {
      List<String> lines = Files.readAllLines(Paths.get(filePath));
      for (String line : lines) {
        if (!line.isBlank()) {
          result.add(line.split("\\|"));
        }
      }
      return result;
    } catch (IOException e) {
      throw new RuntimeException("Failed to read file: " + filePath, e);
    }
  }

  private void writeLines(String filePath, List<String> lines) {
    try {
      Files.write(Paths.get(filePath), lines);
    } catch (IOException e) {
      throw new RuntimeException("Failed to write file: " + filePath, e);
    }
  }

  private void appendLines(String filePath, List<String> lines) {
    try {
      Files.write(Paths.get(filePath), lines, StandardOpenOption.APPEND);
    } catch (IOException e) {
      throw new RuntimeException("Failed to append file: " + filePath, e);
    }
  }

  // ---------------------------
  // Lazy loading
  // ---------------------------

  @Override
  public List<OwnedStock> getOwnedStocks() {
    if (ownedStocks == null) {
      ownedStocks = new ArrayList<>();
      for (String[] p : readAndParseLinesFromFile(getOwnedStockFilePath())) {
        ownedStocks.add(new OwnedStock(
            UUID.fromString(p[0]),
            UUID.fromString(p[1]),
            p[2],
            Integer.parseInt(p[3])
        ));
      }
    }
    return ownedStocks;
  }

  @Override
  public List<Portfolio> getPortfolios() {
    if (portfolios == null) {
      portfolios = new ArrayList<>();
      for (String[] p : readAndParseLinesFromFile(getPortfolioFilePath())) {
        portfolios.add(new Portfolio(
            UUID.fromString(p[0]),
            Double.parseDouble(p[1])
        ));
      }
    }
    return portfolios;
  }

  @Override
  public List<Stock> getStocks() {
    if (stocks == null) {
      stocks = new ArrayList<>();
      for (String[] p : readAndParseLinesFromFile(getStockFilePath())) {
        stocks.add(new Stock(
            UUID.fromString(p[0]),
            p[1],
            p[2],
            Double.parseDouble(p[3]),
            p[4]
        ));
      }
    }
    return stocks;
  }

  @Override
  public List<StockPriceHistory> getStockPriceHistories() {
    if (stockPriceHistories == null) {
      stockPriceHistories = new ArrayList<>();
      for (String[] p : readAndParseLinesFromFile(getStockPriceHistoryFilePath())) {
        stockPriceHistories.add(new StockPriceHistory(
            UUID.fromString(p[0]),
            p[1],
            Double.parseDouble(p[2]),
            LocalDateTime.parse(p[3])
        ));
      }
    }
    return stockPriceHistories;
  }

  @Override
  public List<Transaction> getTransactions() {
    if (transactions == null) {
      transactions = new ArrayList<>();
      for (String[] p : readAndParseLinesFromFile(getTransactionFilePath())) {
        transactions.add(new Transaction(
            UUID.fromString(p[0]),
            UUID.fromString(p[1]),
            p[2],
            TransactionType.valueOf(p[3]),
            Integer.parseInt(p[4]),
            Double.parseDouble(p[5]),
            Double.parseDouble(p[6]),
            Double.parseDouble(p[7]),
            LocalDateTime.parse(p[8])
        ));
      }
    }
    return transactions;
  }

  // ---------------------------
  // Append-only buffers
  // ---------------------------

  @Override
  public List<StockPriceHistory> getStockPriceHistoryAppendOnly() {
    return stockPriceHistoryBuffer;
  }

  @Override
  public List<Transaction> getTransactionAppendOnly() {
    return transactionBuffer;
  }

  // ---------------------------
  // Unit of Work lifecycle
  // ---------------------------

  @Override
  public void begin() {
    resetLists();
  }

  @Override
  public void commit() {

    if (ownedStocks != null)
      writeLines(getOwnedStockFilePath(), ownedStocks.stream().map(this::toPSV).toList());

    if (portfolios != null)
      writeLines(getPortfolioFilePath(), portfolios.stream().map(this::toPSV).toList());

    if (stocks != null)
      writeLines(getStockFilePath(), stocks.stream().map(this::toPSV).toList());

    if (stockPriceHistories != null)
      writeLines(getStockPriceHistoryFilePath(), stockPriceHistories.stream().map(this::toPSV).toList());

    if (transactions != null)
      writeLines(getTransactionFilePath(), transactions.stream().map(this::toPSV).toList());

    // Append-only
    if (!stockPriceHistoryBuffer.isEmpty())
      appendLines(getStockPriceHistoryFilePath(), stockPriceHistoryBuffer.stream().map(this::toPSV).toList());

    if (!transactionBuffer.isEmpty())
      appendLines(getTransactionFilePath(), transactionBuffer.stream().map(this::toPSV).toList());

    resetLists();
    stockPriceHistoryBuffer.clear();
    transactionBuffer.clear();
  }

  @Override
  public void rollback() {
    resetLists();
    stockPriceHistoryBuffer.clear();
    transactionBuffer.clear();
  }

  private void resetLists() {
    ownedStocks = null;
    portfolios = null;
    stocks = null;
    stockPriceHistories = null;
    transactions = null;
  }

  // ---------------------------
  // PSV converters
  // ---------------------------

  private String toPSV(OwnedStock o) {
    return o.getId() + "|" + o.getPortfolioId() + "|" + o.getStockSymbol() + "|" + o.getNumberOfShares();
  }

  private String toPSV(Portfolio p) {
    return p.getId() + "|" + p.getCurrentBalance();
  }

  private String toPSV(Stock s) {
    return s.getId() + "|" + s.getSymbol() + "|" + s.getName() + "|" + s.getCurrentPrice() + "|" + s.getCurrentState();
  }

  private String toPSV(StockPriceHistory h) {
    return h.getId() + "|" + h.getStockSymbol() + "|" + h.getPrice() + "|" + h.getTimestamp();
  }

  private String toPSV(Transaction t) {
    return t.getId() + "|" + t.getPortfolioId() + "|" + t.getStockSymbol() + "|" + t.getType() + "|" +
        t.getQuantity() + "|" + t.getPricePerShare() + "|" + t.getTotalAmount() + "|" + t.getFee() + "|" + t.getTimestamp();
  }
}