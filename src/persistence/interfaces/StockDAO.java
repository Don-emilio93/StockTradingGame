package persistence.interfaces;

import entities.Stock;
import java.util.List;
import java.util.UUID;

public interface StockDAO {
  Stock getById(UUID id);
  Stock getBySymbol(String symbol);
  List<Stock> getAll();
  void create(Stock stock);
  void update(Stock stock);
  void delete(UUID id);
}