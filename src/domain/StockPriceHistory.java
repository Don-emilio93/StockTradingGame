package domain;

import java.time.LocalDateTime;
import java.util.UUID;

public class StockPriceHistory
{

  private final UUID id;
  private final String stockSymbol;
  private final double price;
  private final LocalDateTime timestamp;

  public StockPriceHistory(UUID id, String stockSymbol, double price, LocalDateTime timestamp)
  {
    this.id = id;
    this.stockSymbol = stockSymbol;
    this.price = price;
    this.timestamp = timestamp;
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