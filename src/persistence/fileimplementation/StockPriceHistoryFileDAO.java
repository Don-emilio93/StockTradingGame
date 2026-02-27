package persistence.fileimplementation;

import persistence.interfaces.StockPriceHistoryDAO;
import entities.StockPriceHistory;

import java.util.List;
import java.util.UUID;

public class StockPriceHistoryFileDAO implements StockPriceHistoryDAO {

  private final FileUnitOfWork uow;

  public StockPriceHistoryFileDAO(FileUnitOfWork uow) {
    this.uow = uow;
  }

  @Override
  public StockPriceHistory getById(UUID id) {
    return uow.getStockPriceHistories().stream()
        .filter(h -> h.getId().equals(id))
        .findFirst()
        .orElse(null);
  }

  @Override
  public List<StockPriceHistory> getAll() {
    return uow.getStockPriceHistories();
  }

  @Override
  public void append(StockPriceHistory history) {
    StockPriceHistory newHistory = new StockPriceHistory(
        UUID.randomUUID(),
        history.getStockSymbol(),
        history.getPrice(),
        history.getTimestamp()
    );

    uow.getStockPriceHistoryAppendOnly().add(newHistory);
  }
}