package domain;

import java.time.LocalDateTime;

public class Transaction
{

  private final int id;
  private final int portfolioId;
  private final String stockSymbol;
  private final String type;
  private final int quantity;
  private final double pricePerShare;
  private final double totalAmount;
  private final double fee;
  private final LocalDateTime timestamp;

  public Transaction(int id, int portfolioId, String stockSymbol, String type,
      int quantity, double pricePerShare, double totalAmount,
      double fee, LocalDateTime timestamp)
  {

    this.id = id;
    this.portfolioId = portfolioId;
    this.stockSymbol = stockSymbol;
    this.type = type;
    this.quantity = quantity;
    this.pricePerShare = pricePerShare;
    this.totalAmount = totalAmount;
    this.fee = fee;
    this.timestamp = timestamp;
  }

  public int getId()
  {
    return id;
  }

  public int getPortfolioId()
  {
    return portfolioId;
  }

  public String getStockSymbol()
  {
    return stockSymbol;
  }

  public String getType()
  {
    return type;
  }

  public int getQuantity()
  {
    return quantity;
  }

  public double getPricePerShare()
  {
    return pricePerShare;
  }

  public double getTotalAmount()
  {
    return totalAmount;
  }

  public double getFee()
  {
    return fee;
  }

  public LocalDateTime getTimestamp()
  {
    return timestamp;
  }
}