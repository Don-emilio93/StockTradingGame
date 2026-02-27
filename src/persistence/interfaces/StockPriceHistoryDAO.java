package persistence.interfaces;

import entities.StockPriceHistory;
import java.util.List;
import java.util.UUID;

public interface StockPriceHistoryDAO {
  StockPriceHistory getById(UUID id);
  List<StockPriceHistory> getAll();
  void append(StockPriceHistory history);
}