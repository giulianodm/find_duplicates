/**
* Comparator class that selects a comparison algorithm to use
* @author Giuliano Morais
* @version 1.0
* @date 2008
*/

package filecomparison;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Vector;
import java.util.Hashtable;

public class FileCompare extends File{
  CompareStrategy comparator = new ByteSequencialFileComparison();

  public FileCompare(String pathname) {
    super(pathname);
  }

  public FileCompare(String parent, String child) {
    super(parent, child);
  }

  public FileCompare(File parent, String child) {
    super(parent, child);
  }

  public boolean equals(Object obj) {
    boolean comparisonstate;
    if (obj == null) {
      return false;
    }

    if (! (obj instanceof FileCompare)) {
      return false;
    }

    try {
      return comparator.compareFile(this, (FileCompare)obj);
    }
    catch(FileNotFoundException fnfe) {
      throw new FileCompareException(fnfe);
    }
    catch(IOException ioe) {
      throw new FileCompareException(ioe);
    }
  }

  public Hashtable compareFilesInCurrentFolder() {
    String filesnames[] = this.list();
    FileCompare f1 = null, f2 = null;
    Hashtable matchFiles = new Hashtable();

    for (int i=0;i<filesnames.length;i++){
      f1 = new FileCompare(this.getAbsolutePath() + this.separator +filesnames[i]);
      if (f1.isDirectory()) {
        continue;
      }
      for (int j=i+1;j<filesnames.length;j++) {
        f2 = new FileCompare(this.getAbsolutePath() + this.separator + filesnames[j]);
          if (f1.equals(f2)) {
              MatchFiles match = new MatchFiles(f1,f2);
              matchFiles.put(match.getKey(), match);
//              System.out.println(this.getAbsolutePath() + this.separator + filesnames[i] + "=" + this.getAbsolutePath() + this.separator + filesnames[j] + " *");
          }
      }
    }
    return matchFiles;
  }


  public Hashtable compareFilesInFolders(FileCompare folder1, FileCompare folder2) {
    String filesnames1[] = folder1.list();
    String filesnames2[] = folder2.list();
    FileCompare f1 = null, f2 = null;
    Vector folders2 = new Vector();
    Hashtable matchFiles = new Hashtable();

    if (!(folder1.isDirectory() && folder2.isDirectory())) {
      throw new FileCompareException(new Exception("Invalid Parameter: Not a folder"));
    }


    for (int i=0; i<filesnames1.length; i++) {
      f1 = new FileCompare(folder1.getAbsolutePath() + folder1.separator + filesnames1[i]);
      if (f1.isDirectory()) {
        continue;
      }
      for (int j=0;j<filesnames2.length;j++) {
        f2 = new FileCompare(folder2.getAbsolutePath() + folder2.separator + filesnames2[j]);
        if (f2.isDirectory()) {
          continue;
        }
        if (f1.equals(f2)) {
          MatchFiles match = new MatchFiles(f1,f2);
          matchFiles.put(match.getKey(), match);
//          System.out.println("-" + folder1.getAbsolutePath() + folder1.separator + filesnames1[i] +  " = " +
//                                   folder2.getAbsolutePath() + folder2.separator + filesnames2[j]);
        }
      }
    }
    return matchFiles;
  }

  public Vector getTree(FileCompare basefolder) {
      String filesnames1[] = basefolder.list();
      FileCompare f1 = null;
      Vector folders = new Vector();
      Vector internalfolders = new Vector();

      if (!basefolder.isDirectory()) {
        return null;
      }

      for (int i=0; i<filesnames1.length; i++) {
        f1 = new FileCompare(basefolder.getAbsolutePath() + this.separator + filesnames1[i]);
        if (f1.isDirectory()) {
          folders.add(f1);
        }
      }

      Vector folderaux = (Vector)folders.clone();
      for (int z=0; z < folderaux.size(); z++) {
          internalfolders = getTree((FileCompare)folderaux.elementAt(z));
          mergeVectors(folders,internalfolders);
      }

      return folders;
  }


  public Hashtable compareFilesInFolders() {
    Vector inside = this.getTree(this);
    Hashtable matchFiles = new Hashtable();
    matchFiles = compareFilesInCurrentFolder();
    for (int i=0; i< inside.size(); i++) {
      matchFiles.putAll(this.compareFilesInFolders(this, (FileCompare)inside.elementAt(i)));
    }
    return matchFiles;
  }


  private void mergeVectors(Vector a, Vector b) {
    a.addAll(b);
  }


}