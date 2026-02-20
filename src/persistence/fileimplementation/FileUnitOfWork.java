package persistence.fileimplementation;

import entities.OwnedStock;

import java.util.List;

public class FileUnitOfWork
{
  private List<OwnedStock




  public FileUnitOfWork(String directoryPath)
  {
    this.directoryPath = directoryPath;
    ensureFilesExist();
  }
}
