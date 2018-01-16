/**
* Simple algorithm to check if two files are identical comparing each byte
* @author Giuliano Morais
* @version 1.0
* @date 2008
*/

package filecomparison;

import java.io.File;
import java.io.FileInputStream;
import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ByteSequencialFileComparison implements CompareStrategy {

  /**
   * Compare two files reading each byte from each one of them until
   * the first diferrence is found
   */
  public boolean compareFile(File f1, File f2) throws FileNotFoundException, IOException {
    FileInputStream finp1 = null;
    FileInputStream finp2 = null;
    BufferedInputStream bufinp1 = null;
    BufferedInputStream bufinp2 = null;
    int b1, b2;
    boolean equal = true;

    /*
     * Check if one of File Object is a directory
     */
    if (f1.isDirectory() || f2.isDirectory())
      return false;

    /*
     *  Check if the Files have differents sizes
     */
    if (f1.length() != f2.length()) {
      return false;
    }

    /*
     * Compare byte to byte between the Files
     */
    try {
      finp1 = new FileInputStream(f1);
      finp2 = new FileInputStream(f2);
      bufinp1 = new BufferedInputStream(finp1);
      bufinp2 = new BufferedInputStream(finp2);

      while ((b1 = bufinp1.read()) != -1) {
        b2 = bufinp2.read();
        if (b1 != b2) {
          equal = false;
          break;
        }
      }
    }
    finally {
      if (bufinp1 != null) {
        bufinp1.close();
      }

      if (bufinp2 != null) {
        bufinp2.close();
      }

      if (finp1 != null) {
        finp1.close();
      }

      if (finp2 != null) {
        finp2.close();
      }

    }

    return equal;
  }

}