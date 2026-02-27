package persistence.fileimplementation;

import persistence.interfaces.TransactionDAO;
import entities.Transaction;
import entities.TransactionType;

import java.util.List;
import java.util.UUID;

public class TransactionFileDAO implements TransactionDAO {

  private final FileUnitOfWork uow;

  public TransactionFileDAO(FileUnitOfWork uow) {
    this.uow = uow;
  }

  @Override
  public Transaction getById(UUID id) {
    return uow.getTransactions().stream()
        .filter(t -> t.getId().equals(id))
        .findFirst()
        .orElse(null);
  }

  @Override
  public List<Transaction> getAll() {
    return uow.getTransactions();
  }

  @Override
  public void append(Transaction transaction) {
    Transaction newTransaction = new Transaction(
        UUID.randomUUID(),
        transaction.getPortfolioId(),
        transaction.getStockSymbol(),
        transaction.getType(),
        transaction.getQuantity(),
        transaction.getPricePerShare(),
        transaction.getTotalAmount(),
        transaction.getFee(),
        transaction.getTimestamp()
    );

    uow.getTransactionAppendOnly().add(newTransaction);
  }
}