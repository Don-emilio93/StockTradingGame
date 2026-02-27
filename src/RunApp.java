package app;

import persistence.fileimplementation.*;
import persistence.interfaces.*;
import shared.logging.*;
import entities.*;

import java.time.LocalDateTime;
import java.util.UUID;

public class RunApp {

  public static void main(String[] args) {

    Logger logger = Logger.getInstance();
    logger.log(LogLevel.INFO, "Starting application...");

    // File-based persistence
    FileUnitOfWork uow = new FileUnitOfWork("data");

    // DAO instances
    StockDAO stockDAO = new StockFileDAO(uow);
    PortfolioDAO portfolioDAO = new PortfolioFileDAO(uow);
    OwnedStockDAO ownedStockDAO = new OwnedStockFileDAO(uow);
    TransactionDAO transactionDAO = new TransactionFileDAO(uow);
    StockPriceHistoryDAO historyDAO = new StockPriceHistoryFileDAO(uow);

    // Start a unit of work
    uow.begin();

    // Create a portfolio
    Portfolio p = new Portfolio(UUID.randomUUID(), 10000.0);
    portfolioDAO.create(p);

    // Create a stock
    Stock s = new Stock(UUID.randomUUID(), "AAPL", "Apple Inc.", 150.0, "STABLE");
    stockDAO.create(s);

    // Create owned stock
    OwnedStock os = new OwnedStock(UUID.randomUUID(), p.getId(), "AAPL", 10);
    ownedStockDAO.create(os);

    // Create a transaction
    Transaction t = new Transaction(
        UUID.randomUUID(),
        p.getId(),
        "AAPL",
        TransactionType.BUY,
        10,
        150.0,
        1500.0,
        1.0,
        LocalDateTime.now()
    );
    transactionDAO.append(t);

    // Create stock price history
    StockPriceHistory h = new StockPriceHistory(
        UUID.randomUUID(),
        "AAPL",
        150.0,
        LocalDateTime.now()
    );
    historyDAO.append(h);

    // Commit everything
    uow.commit();

    logger.log(LogLevel.INFO, "Data saved successfully.");

    // Read back data
    System.out.println("=== Portfolios ===");
    for (Portfolio pf : portfolioDAO.getAll()) {
      System.out.println("Portfolio: " + pf.getId() + " | Balance: " + pf.getCurrentBalance());
    }

    System.out.println("=== Stocks ===");
    for (Stock st : stockDAO.getAll()) {
      System.out.println(st.getSymbol() + " - " + st.getName());
    }

    System.out.println("=== Owned Stocks ===");
    for (OwnedStock owned : ownedStockDAO.getAll()) {
      System.out.println("Owned: " + owned.getStockSymbol() + " | Shares: " + owned.getNumberOfShares());
    }

    System.out.println("=== Transactions ===");
    for (Transaction tr : transactionDAO.getAll()) {
      System.out.println(tr.getType() + " " + tr.getStockSymbol() + " | Amount: " + tr.getTotalAmount());
    }

    System.out.println("=== Price History ===");
    for (StockPriceHistory sh : historyDAO.getAll()) {
      System.out.println(sh.getStockSymbol() + " | Price: " + sh.getPrice());
    }

    logger.log(LogLevel.INFO, "Application finished.");
  }
}