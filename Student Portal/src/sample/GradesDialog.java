package sample;

import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.awt.event.*;
import javax.swing.table.*;
import javax.swing.event.*;
import java.text.NumberFormat;

public class GradesDialog extends JDialog {
  
  //model
  static Student selected;
  static Subject selectedSubject;
  
  static JTable  tableGSubject;
  JScrollPane    scrollPaneSSubject;
  static boolean flag;
  
  
  //view
  JLabel        labelGID  = new JLabel("id");
  JLabel        labelGName = new JLabel("fullname");
  JLabel        labelGCourse = new JLabel("crs");
  JLabel        labelGYear  = new JLabel("year");
  JLabel        labelGTextt = new JLabel(); 
  JButton       buttonGClose  = new JButton("CLOSE");
  JButton       buttonGGrade  = new JButton("CHANGE GRADE");
  
  GradesDialog(JFrame parent, Student thisStudent) {
    super(parent, true);
    buttonGGrade.setEnabled(false);
    selected = thisStudent;
    setGrdTable();
    scrollPaneSSubject = new JScrollPane(tableGSubject);
    
    setTitle("Student Academic Information");
    Container cont = getContentPane();
    GridLayout layout = new GridLayout();
    cont.setLayout(layout);
    cont.add(labelGID);
    cont.add(labelGName);
    cont.add(labelGCourse);
    cont.add(labelGYear);
    cont.add(scrollPaneSSubject);
    cont.add(labelGTextt);
    cont.add(buttonGClose);  cont.add(buttonGGrade);

    
    
    layout.setConstraints(labelGTextt,7,1,6,1);
    layout.setConstraints(labelGID,1,1,6,1);
    layout.setConstraints(labelGName,2,1,6,1);
    layout.setConstraints(labelGCourse,3,1,6,1);
    layout.setConstraints(labelGYear,4,1,6,1);
    layout.setConstraints(scrollPaneSSubject,5,1,6,1);
    layout.setConstraints(buttonGClose,7,6,1,1);
    layout.setConstraints(buttonGGrade,6,6,1,1);
    fillFormWith();
    
    buttonGClose.addActionListener(new ButtonListener());
    buttonGGrade.addActionListener(new ButtonListener());
    
    labelGTextt.setFont(new Font("Verdana", 3, 12));
    labelGTextt.setForeground(new Color(190,0,0));
    
    NumberFormat dec = NumberFormat.getInstance();
    dec.setMaximumFractionDigits(2);
    dec.setMinimumFractionDigits(2);
     
    ListSelectionModel tbModel = tableGSubject.getSelectionModel();
    tbModel.addListSelectionListener(new SubjTableListener());
    
    Toolkit thKit = getToolkit();
    Dimension wndSze = thKit.getScreenSize();
    pack();
    
    int wd = getWidth();
    int ht = getHeight();
    int x  = (int)((wndSze.getWidth()/2)-(wd/2));
    int y  = (int)((wndSze.getHeight()/2)-(ht/2));
    setBounds(x,y,wd,ht);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }
  
  //tokenize and get code on tostring of subject
  private String getSCode(String thisToString){
    StringTokenizer str = new StringTokenizer(thisToString," -");
    String thisCode = str.nextToken().trim();
    return thisCode;
  }
  
  //set-up table and model
  public void setGrdTable(){
    tableGSubject = new JTable(new GrdModel());
    scrollPaneSSubject = new JScrollPane(tableGSubject);
    this.getContentPane().add(scrollPaneSSubject);
    tableGSubject.setPreferredScrollableViewportSize(new Dimension(500,150));
    tableGSubject.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
  }
  static class GrdModel extends AbstractTableModel {
    private String[] columnNames = {"SUBJECT","GRADE"};
    private Object[][] data = storeObject();
    
    private Object[][] storeObject(){
      Hashtable studs = ViewElements.currentSemester.getStudentsEnrolled();
      Student thisStud = (Student)studs.get(selected.getIdNumber());
      Hashtable subjects = thisStud.getSubjects();
      
      Set sKeys = subjects.keySet();
      Vector keys = new Vector(sKeys);
      Collections.sort(keys);
      Object[][] temp = new Object[keys.size()][2];
      int a=0;
      for(Iterator i=keys.iterator();i.hasNext();a++){
        String thisKey = ""+i.next();
        Subject temp2 = (Subject)subjects.get(thisKey);
        temp[a][0] = ""+temp2;
        temp[a][1] = ""+temp2.getGrade();
      }
      return temp;
    }
    public int getColumnCount() {
      return columnNames.length;
    }
    public int getRowCount() {
      return data.length;
    }
    public String getColumnName(int col) {
      return columnNames[col];
    }
    public Object getValueAt(int row, int col) {
      return data[row][col];
    }
    public boolean isCellEditable(int row, int col) {
      return false;
    }
  }
  private void fillFormWith(){
  	Person temp = new Person(selected.getFirstName(),selected.getLastName());
    labelGID.setText("ID NUMBER:   "+selected.getIdNumber());
    labelGName.setText("FULL NAME:   "+temp);
    labelGCourse.setText("COURSE:  "+selected.getCourse());
    labelGYear.setText("YEAR:  "+selected.getYear());
  }
  
  //controller
  private class ButtonListener implements ActionListener {
    public void actionPerformed(ActionEvent ev) {
      if(ev.getSource()==buttonGClose){
        dispose();
      }
      if(ev.getSource()==buttonGGrade){
        JFrame temp = new JFrame();
        EditGradeDialog editdlg = new EditGradeDialog(temp,selectedSubject);
        editdlg.show();
        }
      }
    }
    public void compute(){
      NumberFormat dec = NumberFormat.getInstance();
      dec.setMaximumFractionDigits(2);
      dec.setMinimumFractionDigits(2);
      
      int maj = 0;		int min = 0;
      int majf = 0;		int minf = 0;
      Hashtable ht = selected.getSubjects();
      Set kset = ht.keySet();
      Vector keys = new Vector(kset);
      for(Iterator i=keys.iterator();i.hasNext();){
        String code = ""+i.next();
        Subject temp = (Subject)ht.get(code);
        
      }
        ViewElements.currentSemester.enrollStudent(selected);
        ViewElements.SemesterList.put(ViewElements.currentSemester.getSemCode(),ViewElements.currentSemester);
        StudentInformationSystem.writeFiles();	StudentInformationSystem.readFiles();
          
      }

  private class SubjTableListener implements ListSelectionListener {
    public void valueChanged(ListSelectionEvent e) {
      if(e.getValueIsAdjusting()) return;
      ListSelectionModel lsm = (ListSelectionModel)e.getSource();
      if(lsm.isSelectionEmpty()) {
        buttonGGrade.setEnabled(false);
      }
      else {
        buttonGGrade.setEnabled(true);
        int selectedRow = lsm.getMinSelectionIndex();
        GrdModel tempMod = new GrdModel();
        String code = ""+tempMod.getValueAt(selectedRow,0);
        Hashtable ht = selected.getSubjects();
        selectedSubject = (Subject)ht.get(getSCode(code));
     }
    }
  }
  private class MyWindowAdapter extends WindowAdapter {
    public void windowClosing(WindowEvent we) {
      dispose();
    }
  }
}