package persistence.interfaces;

import entities.Portfolio;
import java.util.List;
import java.util.UUID;

public interface PortfolioDAO {
  Portfolio getById(UUID id);
  List<Portfolio> getAll();
  void create(Portfolio portfolio);
  void update(Portfolio portfolio);
  void delete(UUID id);
}