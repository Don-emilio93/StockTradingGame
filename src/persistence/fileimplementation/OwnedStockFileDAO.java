package persistence.fileimplementation;

import persistence.interfaces.OwnedStockDAO;
import entities.OwnedStock;

import java.util.List;
import java.util.UUID;

public class OwnedStockFileDAO implements OwnedStockDAO {

  private final FileUnitOfWork uow;

  public OwnedStockFileDAO(FileUnitOfWork uow) {
    this.uow = uow;
  }

  @Override
  public OwnedStock getById(UUID id) {
    return uow.getOwnedStocks().stream()
        .filter(o -> o.getId().equals(id))
        .findFirst()
        .orElse(null);
  }

  @Override
  public List<OwnedStock> getAll() {
    return uow.getOwnedStocks();
  }

  @Override
  public void create(OwnedStock ownedStock) {
    OwnedStock newOwnedStock = new OwnedStock(
        UUID.randomUUID(),
        ownedStock.getPortfolioId(),
        ownedStock.getStockSymbol(),
        ownedStock.getNumberOfShares()
    );

    uow.getOwnedStocks().add(newOwnedStock);
  }

  @Override
  public void update(OwnedStock ownedStock) {
    List<OwnedStock> list = uow.getOwnedStocks();
    for (int i = 0; i < list.size(); i++) {
      if (list.get(i).getId().equals(ownedStock.getId())) {
        list.set(i, ownedStock);
        return;
      }
    }
  }

  @Override
  public void delete(UUID id) {
    uow.getOwnedStocks().removeIf(o -> o.getId().equals(id));
  }
}