/**
* Tool User Interface.
* @author Giuliano Morais
* @version 1.0
* @date 2008
*/

package filecomparison.gui;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;
import javax.swing.JFileChooser;
import javax.swing.table.TableModel;
import javax.swing.table.AbstractTableModel;
import filecomparison.*;
import java.util.*;

public class FileComparisonGUI extends JFrame {
    private JPanel searchTypePanel;
    private JRadioButton oneFolder, oneFolderRecursive, twoFolders;
    private JButton startbtn = new JButton("Start");
    private JButton stopbtn = new JButton("Stop");
    private JButton resetbtn = new JButton("Reset");
    private JTextField folder1txt, folder2txt;
    private JTable filetable = new JTable(1, 2);
    private JButton folder1btn, folder2btn;
    private JFileChooser filechooser;
    private static final int MODE_ONE_FOLDER = 0;
    private static final int MODE_ONE_FOLDER_RECURSIVE = 1;
    private static final int MODE_TWO_FOLDERS = 2;
    private int currentType = FileComparisonGUI.MODE_ONE_FOLDER;


  public FileComparisonGUI() {
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }
  public static void main(String[] args) {
    FileComparisonGUI fileComparisonGUI = new FileComparisonGUI();
  }
  private void jbInit() throws Exception {
    this.setSize(640,480);
    Container con = this.getContentPane();
    this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    this.setTitle("FileComparison");
    this.prepareSearchTypePanel();
    con.add(searchTypePanel, BorderLayout.NORTH);
    con.add(filetable, BorderLayout.CENTER);
    this.setVisible(true);
    this.addWindowListener(
      new WindowAdapter() {
        public void windowClosed(WindowEvent e) {
          System.exit(0);
        }
      }
    );
  }

  private void prepareSearchTypePanel() {
    searchTypePanel = new JPanel(new BorderLayout());
    searchTypePanel.setBorder(new BevelBorder(BevelBorder.RAISED));

    JPanel radioPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    radioPanel.setBorder(new BevelBorder(BevelBorder.RAISED));
    oneFolder = new JRadioButton("One Folder", true);
    oneFolderRecursive = new JRadioButton("Recursive One Folder", false);
    twoFolders = new JRadioButton("Two Folders", false);
    radioPanel.add(oneFolder);
    radioPanel.add(oneFolderRecursive);
    radioPanel.add(twoFolders);
    ButtonGroup radioGroup = new ButtonGroup();
    radioGroup.add(oneFolder);
    radioGroup.add(oneFolderRecursive);
    radioGroup.add(twoFolders);
    RadioButtonHandler radioHandler = new RadioButtonHandler();
    oneFolder.addItemListener(radioHandler);
    oneFolderRecursive.addItemListener(radioHandler);
    twoFolders.addItemListener(radioHandler);
    searchTypePanel.add(radioPanel, BorderLayout.NORTH);

    JPanel folderPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    folderPanel.setBorder(new BevelBorder(BevelBorder.RAISED));
    JLabel folder1lbl = new JLabel("Folder 1:");
    JLabel folder2lbl = new JLabel("Folder 2:");
    folder1txt = new JTextField(18);
    folder2txt = new JTextField(18);
    folder2txt.setEnabled(false);
    ButtonHandler buttonHandler = new ButtonHandler();
    folder1btn = new JButton("...");
    folder1btn.addActionListener(buttonHandler);
    folder2btn = new JButton("...");
    folder2btn.addActionListener(buttonHandler);
    folder2btn.setEnabled(false);
    folderPanel.add(folder1lbl);
    folderPanel.add(folder1txt);
    folderPanel.add(folder1btn);
    folderPanel.add(folder2lbl);
    folderPanel.add(folder2txt);
    folderPanel.add(folder2btn);
    searchTypePanel.add(folderPanel, BorderLayout.CENTER);

    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
    startbtn = new JButton("Start");
    startbtn.addActionListener(buttonHandler);
    startbtn.setPreferredSize(new Dimension(180,25));
    stopbtn = new JButton("Stop");
    stopbtn.setPreferredSize(new Dimension(180,25));
    resetbtn = new JButton("Reset");
    resetbtn.setPreferredSize(new Dimension(180,25));
    buttonPanel.add(startbtn);
    buttonPanel.add(stopbtn);
    buttonPanel.add(resetbtn);
    searchTypePanel.add(buttonPanel, BorderLayout.SOUTH);
  }

  private class ButtonHandler implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      int retornodialogo;
      if (e.getSource() == startbtn) {
        if (currentType == FileComparisonGUI.MODE_ONE_FOLDER) {
          final Hashtable result = new FileCompare(folder1txt.getText().trim()).compareFilesInCurrentFolder();
          TableModel datamodel = new AbstractTableModel() {
            public int getColumnCount() { return 2; }
            public int getRowCount() { return 30/*result.size()*/;}
            public Object getValueAt(int row, int col) { return "VAZIO"; }
          };
          filetable = new JTable(datamodel);
          filetable.doLayout();
          System.out.println("Matches" + result.size());
        }


      }

      if (e.getSource() == folder1btn) {
        if (!folder1txt.getText().trim().equals("")) {
          filechooser = new JFileChooser(folder1txt.getText().trim());
        }
        else {
          filechooser = new JFileChooser();
        }
        filechooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        retornodialogo = filechooser.showOpenDialog(FileComparisonGUI.this);
        if (retornodialogo == JFileChooser.APPROVE_OPTION) {
          folder1txt.setText(filechooser.getSelectedFile().getAbsolutePath());
        }
      }

      if (e.getSource() == folder2btn) {
        if (!folder2txt.getText().trim().equals("")) {
          filechooser = new JFileChooser(folder2txt.getText().trim());
        }
        else {
          filechooser = new JFileChooser();
        }
        filechooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        retornodialogo = filechooser.showOpenDialog(FileComparisonGUI.this);
        if (retornodialogo == JFileChooser.APPROVE_OPTION) {
          folder2txt.setText(filechooser.getSelectedFile().getAbsolutePath());
        }
      }
    }
  }


  private class RadioButtonHandler implements ItemListener {
    public void itemStateChanged(ItemEvent e) {
      folder2txt.setEnabled(false);
      folder2btn.setEnabled(false);

      if (e.getSource() == oneFolder) {
        currentType = FileComparisonGUI.MODE_ONE_FOLDER;
      }

      if (e.getSource() == oneFolderRecursive) {
        currentType = FileComparisonGUI.MODE_ONE_FOLDER;
      }

      if (e.getSource() == twoFolders) {
        currentType = FileComparisonGUI.MODE_TWO_FOLDERS;
        folder2txt.setEnabled(true);
        folder2btn.setEnabled(true);
      }

    }
  }

}