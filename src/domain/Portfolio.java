package domain;

import java.util.UUID;

public class Portfolio
{

  private final UUID id;
  private double currentBalance;

  public Portfolio(UUID id, double currentBalance)
  {
    if (id == null)
    this.id = UUID.randomUUID();
    else
      this.id = id;
    this.currentBalance = currentBalance;
  }

  public UUID getId()
  {
    return id;
  }

  public double getCurrentBalance()
  {
    return currentBalance;
  }

  public void setCurrentBalance(double currentBalance)
  {
    this.currentBalance = currentBalance;
  }
}