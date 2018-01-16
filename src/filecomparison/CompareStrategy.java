/**
* Compare interface based on Strategy Pattern
* @author Giuliano Morais
* @version 1.0
* @date 2008
*/

package filecomparison;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public interface CompareStrategy {
    public boolean compareFile(File f1, File f2) throws FileNotFoundException, IOException;
}