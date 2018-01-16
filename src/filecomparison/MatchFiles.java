/**
* Represents a file match after a comparison
* @author Giuliano Morais
* @version 1.0
* @date 2008
*/


package filecomparison;

public class MatchFiles {
  private FileCompare f1;
  private FileCompare f2;
  private String key;

  public MatchFiles(FileCompare f1, FileCompare f2) throws MatchFilesException{
    if ((f1 == null) || (f2 == null)) {
      throw new MatchFilesException("Parameter null not allowed");
    }
    this.f1 = f1;
    this.f2 = f2;
    key = f1.getAbsolutePath() + FileCompare.pathSeparator + f2.getAbsolutePath();
  }

  public FileCompare getFile1() {
    return f1;
  }

  public FileCompare getFile2() {
    return f2;
  }

  public String getKey() {
    return key;
  }

}