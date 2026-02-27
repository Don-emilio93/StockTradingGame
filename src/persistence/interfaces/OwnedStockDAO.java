package persistence.interfaces;

import entities.OwnedStock;
import java.util.List;
import java.util.UUID;

public interface OwnedStockDAO {
  OwnedStock getById(UUID id);
  List<OwnedStock> getAll();
  void create(OwnedStock ownedStock);
  void update(OwnedStock ownedStock);
  void delete(UUID id);
}