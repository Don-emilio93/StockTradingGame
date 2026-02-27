package persistence.interfaces;

import entities.Transaction;
import java.util.List;
import java.util.UUID;

public interface TransactionDAO {
  Transaction getById(UUID id);
  List<Transaction> getAll();
  void append(Transaction transaction);
}