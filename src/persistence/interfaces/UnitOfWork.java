package persistence.interfaces;

import java.util.List;
import entities.*;

public interface UnitOfWork {

  void begin();
  void commit();
  void rollback();

  List<Stock> getStocks();
  List<Portfolio> getPortfolios();
  List<OwnedStock> getOwnedStocks();
  List<Transaction> getTransactions();
  List<StockPriceHistory> getStockPriceHistories();

  List<StockPriceHistory> getStockPriceHistoryAppendOnly();
  List<Transaction> getTransactionAppendOnly();
}