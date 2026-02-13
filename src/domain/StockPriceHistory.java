package domain;

import java.time.LocalDateTime;

public class StockPriceHistory
{

  private final int id;
  private final String stockSymbol;
  private final double price;
  private final LocalDateTime timestamp;

  public StockPriceHistory(int id, String stockSymbol, double price, LocalDateTime timestamp)
  {
    this.id = id;
    this.stockSymbol = stockSymbol;
    this.price = price;
    this.timestamp = timestamp;
  }

  public int getId()
  {
    return id;
  }

  public String getStockSymbol()
  {
    return stockSymbol;
  }

  public double getPrice()
  {
    return price;
  }

  public LocalDateTime getTimestamp()
  {
    return timestamp;
  }
}