package entities;

import java.util.UUID;

public class Portfolio {
  private final UUID id;
  private final double currentBalance;

  public Portfolio(UUID id, double currentBalance) {
    this.id = id;
    this.currentBalance = currentBalance;
  }

  public UUID getId() { return id; }
  public double getCurrentBalance() { return currentBalance; }
}