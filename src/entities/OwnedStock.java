package entities;

import java.util.UUID;

public class OwnedStock {
  private final UUID id;
  private final UUID portfolioId;
  private final String stockSymbol;
  private final int numberOfShares;

  public OwnedStock(UUID id, UUID portfolioId, String stockSymbol, int numberOfShares) {
    this.id = id;
    this.portfolioId = portfolioId;
    this.stockSymbol = stockSymbol;
    this.numberOfShares = numberOfShares;
  }

  public UUID getId() { return id; }
  public UUID getPortfolioId() { return portfolioId; }
  public String getStockSymbol() { return stockSymbol; }
  public int getNumberOfShares() { return numberOfShares; }
}