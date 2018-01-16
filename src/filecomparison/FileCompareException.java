package filecomparison;


public class FileCompareException extends RuntimeException {
  Exception outsideException;
  public FileCompareException(Exception e) {
    super("FileCompareException:"+e.getMessage());
    outsideException = e;
  }
}