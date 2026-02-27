package shared.configuration;

public class AppConfig {

  private static AppConfig instance;

  private int startingBalance = 10000;
  private double transactionFee = 1.0;
  private int updateFrequencyInMs = 1000;
  private double stockResetValue = 100.0;

  private AppConfig() {}

  public static AppConfig getInstance() {
    if (instance == null) {
      instance = new AppConfig();
    }
    return instance;
  }

  public int getStartingBalance() { return startingBalance; }
  public double getTransactionFee() { return transactionFee; }
  public int getUpdateFrequencyInMs() { return updateFrequencyInMs; }
  public double getStockResetValue() { return stockResetValue; }
}