package shared.logging;

public class Logger
{
  private static Logger instance;
  private LogOutput output;

  private Logger()
  {
    this.output = new ConsoleLogOutput();
  }

  public static Logger getInstance()
  {
    if (instance == null)
    {
      synchronized (Logger.class)
      {
        if (instance == null)
        {
          instance = new Logger();
        }
      }
    }
    return instance;
  }

  public synchronized void setOutput(LogOutput output)
  {
    this.output = output;
  }

  public synchronized void log(LogLevel logLevel, String message)
  {
    output.log(logLevel, message);
  }

}