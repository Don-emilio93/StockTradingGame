package entities;

import java.util.UUID;

public class Stock {
  private final UUID id;
  private final String symbol;
  private final String name;
  private final double currentPrice;
  private final String currentState;

  public Stock(UUID id, String symbol, String name, double currentPrice, String currentState) {
    this.id = id;
    this.symbol = symbol;
    this.name = name;
    this.currentPrice = currentPrice;
    this.currentState = currentState;
  }

  public UUID getId() { return id; }
  public String getSymbol() { return symbol; }
  public String getName() { return name; }
  public double getCurrentPrice() { return currentPrice; }
  public String getCurrentState() { return currentState; }
}