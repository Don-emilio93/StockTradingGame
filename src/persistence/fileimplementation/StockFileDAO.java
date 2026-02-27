package persistence.fileimplementation;

import persistence.interfaces.StockDAO;
import entities.Stock;

import java.util.List;
import java.util.UUID;

public class StockFileDAO implements StockDAO {

  private final FileUnitOfWork uow;

  public StockFileDAO(FileUnitOfWork uow) {
    this.uow = uow;
  }

  @Override
  public Stock getById(UUID id) {
    return uow.getStocks().stream()
        .filter(s -> s.getId().equals(id))
        .findFirst()
        .orElse(null);
  }

  @Override
  public Stock getBySymbol(String symbol) {
    return uow.getStocks().stream()
        .filter(s -> s.getSymbol().equals(symbol))
        .findFirst()
        .orElse(null);
  }

  @Override
  public List<Stock> getAll() {
    return uow.getStocks();
  }

  @Override
  public void create(Stock stock) {
    Stock newStock = new Stock(
        UUID.randomUUID(),
        stock.getSymbol(),
        stock.getName(),
        stock.getCurrentPrice(),
        stock.getCurrentState()
    );

    uow.getStocks().add(newStock);
  }

  @Override
  public void update(Stock stock) {
    List<Stock> list = uow.getStocks();
    for (int i = 0; i < list.size(); i++) {
      if (list.get(i).getId().equals(stock.getId())) {
        list.set(i, stock);
        return;
      }
    }
  }

  @Override
  public void delete(UUID id) {
    uow.getStocks().removeIf(s -> s.getId().equals(id));
  }
}