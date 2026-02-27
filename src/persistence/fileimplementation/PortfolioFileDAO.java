package persistence.fileimplementation;

import persistence.interfaces.PortfolioDAO;
import entities.Portfolio;

import java.util.List;
import java.util.UUID;

public class PortfolioFileDAO implements PortfolioDAO {

  private final FileUnitOfWork uow;

  public PortfolioFileDAO(FileUnitOfWork uow) {
    this.uow = uow;
  }

  @Override
  public Portfolio getById(UUID id) {
    return uow.getPortfolios().stream()
        .filter(p -> p.getId().equals(id))
        .findFirst()
        .orElse(null);
  }

  @Override
  public List<Portfolio> getAll() {
    return uow.getPortfolios();
  }

  @Override
  public void create(Portfolio portfolio) {
    Portfolio newPortfolio = new Portfolio(
        UUID.randomUUID(),
        portfolio.getCurrentBalance()
    );

    uow.getPortfolios().add(newPortfolio);
  }

  @Override
  public void update(Portfolio portfolio) {
    List<Portfolio> list = uow.getPortfolios();
    for (int i = 0; i < list.size(); i++) {
      if (list.get(i).getId().equals(portfolio.getId())) {
        list.set(i, portfolio);
        return;
      }
    }
  }

  @Override
  public void delete(UUID id) {
    uow.getPortfolios().removeIf(p -> p.getId().equals(id));
  }
}