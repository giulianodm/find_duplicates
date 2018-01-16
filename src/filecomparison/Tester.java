package filecomparison;


import java.util.*;

public class Tester {

  public Tester() {
  }
  public static void main(String[] args) throws Exception {

    FileCompare f4 = new FileCompare("C:\\temp");
    Hashtable ht = f4.compareFilesInCurrentFolder();
    System.out.println(ht.size());
    Enumeration e = ht.elements();
    while (e.hasMoreElements()) {
     System.out.println("MatchFiles:"+((MatchFiles)e.nextElement()).getKey());
    }
  }
}