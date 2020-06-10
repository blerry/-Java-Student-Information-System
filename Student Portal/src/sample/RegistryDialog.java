package sample;

import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.awt.event.*;
import javax.swing.table.*;
import javax.swing.event.*;

public class RegistryDialog extends JDialog {
  
  //model
  static Student selected;
  Vector addedSubject = new Vector();
  Subject selectedSubject;
  
  //view
  static JTable tableRSubject;
  JScrollPane   scrollPaneRSubject;
  JLabel        labelRID   = new JLabel("id here");
  JLabel        labelRName  = new JLabel("name here");
  JLabel        labelRCourse  = new JLabel("Course");
  JLabel        labelRYear   = new JLabel("Year");
  SpinnerModel  mod     = new SpinnerNumberModel(1,1,4,1);
  JSpinner      spinnerRYear   = new JSpinner(mod);
  String[]      course     = {"BS-CS","BS-IT","BS-IM"};
  JComboBox     comboBoxRCourse  = new JComboBox(course);
  JButton       buttonRSave   = new JButton("Save");
  JButton       buttonRCancel  = new JButton("Close");
  JButton       buttonRAdd  = new JButton("Add Subject");
  JButton       buttonRDrop  = new JButton("Drop Subject");
  
  RegistryDialog(JFrame parent, Student selectedStud) {
    super(parent, true);
    buttonRDrop.setEnabled(true);
    selected = selectedStud;
    setSbjTable();
    setTitle("Student Registration Information");
    Container cont = getContentPane();
    GridLayout layout = new GridLayout();
    cont.setLayout(layout);

    cont.add(labelRID);		cont.add(labelRName);
    cont.add(buttonRSave);		cont.add(buttonRCancel);
    cont.add(labelRYear);		cont.add(spinnerRYear);
    cont.add(labelRCourse);		cont.add(comboBoxRCourse);
    cont.add(scrollPaneRSubject);		cont.add(buttonRAdd);
    cont.add(buttonRDrop);
    
    layout.setConstraints(labelRID,1,1,2,1);
    layout.setConstraints(labelRName,2,1,2,1);
    layout.setConstraints(labelRCourse,3,1,1,1);
    layout.setConstraints(labelRYear,4,1,1,1);
    layout.setConstraints(scrollPaneRSubject,5,1,3,1);
    layout.defineOwnConstraints(comboBoxRCourse,3,2,1,1, 0,0, 0,17, 0,0,0,0);
    layout.defineOwnConstraints(spinnerRYear,4,2,1,1, 0,0, 0,17, 0,0,0,0);
    layout.defineOwnConstraints(buttonRAdd,4,2,2,1, 0,0, 0,13, 0,0,0,0);
    layout.defineOwnConstraints(buttonRDrop,6,1,2,1, 0,0, 0,17, 0,0,0,0);
    layout.defineOwnConstraints(buttonRSave,6,3,1,1, 0,0, 0,13, 5,0,0,0);
    layout.defineOwnConstraints(buttonRCancel,7,3,1,1, 0,0, 0,13, 5,0,0,0);
    fillFormWith(selected);
    
    buttonRSave.addActionListener(new ButtonListener());
    buttonRCancel.addActionListener(new ButtonListener());
    buttonRAdd.addActionListener(new ButtonListener());
    buttonRDrop.addActionListener(new ButtonListener());
    tableRSubject.setModel(new SubjectModel());
    ListSelectionModel tbModel = tableRSubject.getSelectionModel();
    tbModel.addListSelectionListener(new SubjTableListener());
    
    Toolkit thKit = getToolkit();
    Dimension windowSize = thKit.getScreenSize();
    pack();
    
    int wd = getWidth();
    int ht = getHeight();
    int x  = (int)((windowSize.getWidth()/2)-(wd/2));
    int y  = (int)((windowSize.getHeight()/2)-(ht/2));
    setBounds(x,y,wd,ht);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }
  //fill up for with necessary information.
  private void fillFormWith(Student thisStudent){
    Person temp = new Person(thisStudent.getFirstName(),
                             thisStudent.getLastName());

    labelRName.setText("Full Name:     "+temp);
    labelRID.setText("ID Number:     "+thisStudent.getIdNumber());
    String stdCourse = thisStudent.getCourse();
    int index = -1;
    if(stdCourse.trim().equals("BS-CS"))
      index = 0;
    else if(stdCourse.trim().equals("BS-IT"))
      index = 1;
    else if(stdCourse.trim().equals("BS-IM"))
      index = 2;
    comboBoxRCourse.setSelectedIndex(index);
    spinnerRYear.setValue(new Integer(thisStudent.getYear()));
  }
  private String getSCode(String thisToString){
    StringTokenizer str = new StringTokenizer(thisToString," -");
    String thisCode = str.nextToken().trim();
    return thisCode;
  }
  //setup table and table model
  public void setSbjTable(){
    tableRSubject = new JTable(new SubjectModel());
    scrollPaneRSubject = new JScrollPane(tableRSubject);
    tableRSubject.setPreferredScrollableViewportSize(new Dimension(550,150));
    tableRSubject.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
  }
  static class SubjectModel extends AbstractTableModel {
    private String[] columnNames = {"SUBJECT","UNITS","MAJOR?"};
    private Object[][] data = storeObject();
    
    private Object[][] storeObject(){
    	Hashtable subj = selected.getSubjects();
    	Set sKeys = subj.keySet();
      Vector keys = new Vector(sKeys);
      Collections.sort(keys);
      Object[][] temp = new Object[keys.size()][3];
      
      int a=0;
      for(Iterator i=keys.iterator();i.hasNext();a++){
        String thisKey = ""+i.next();
        Subject temp2 = (Subject)subj.get(thisKey);
        temp[a][0] = ""+temp2;
        temp[a][1] = ""+temp2.getUnits();
        
      }
      return temp;
    }
    public Object[][] getData(){
      return data;
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
  
  //controller
  private class ButtonListener implements ActionListener {
    public void actionPerformed(ActionEvent ev) {
      if(ev.getSource()==buttonRSave){
      	String course = ""+comboBoxRCourse.getSelectedItem();
      	int year = Integer.parseInt(""+spinnerRYear.getValue());
      	selected.setCourse(course);
      	selected.setYear(year);
      	ViewElements.tab4Grid.setModel(new StudentInformationSystem.EnrModel());
      	ViewElements.table3Student.setModel(new StudentInformationSystem.EnrModel());
      	StudentInformationSystem.writeFiles();		StudentInformationSystem.readFiles();
        dispose();
      }
      if(ev.getSource()==buttonRCancel){
        dispose();
      }
      if(ev.getSource()==buttonRAdd){
      	AddSubjectDialog asbj = new AddSubjectDialog(new JFrame(),selected);
      	asbj.show();
      }
      if(ev.getSource()==buttonRDrop){
        selected.dropSubject( ""+selectedSubject.getCode());
        ViewElements.currentSemester.enrollStudent(selected);
        StudentInformationSystem.writeFiles();		StudentInformationSystem.readFiles();
        tableRSubject.setModel(new SubjectModel());
      	System.out.println("subject dropped.");
      }
    }
  }
  private class SubjTableListener implements ListSelectionListener {
    public void valueChanged(ListSelectionEvent e) {
      if(e.getValueIsAdjusting()) return;
      ListSelectionModel lsm = (ListSelectionModel)e.getSource();
      if(lsm.isSelectionEmpty()) {
        buttonRDrop.setEnabled(false);
      }
      else {
        buttonRDrop.setEnabled(true);
        int selectedRow = lsm.getMinSelectionIndex();
        SubjectModel tempMod = new SubjectModel();
        String subj = ""+tempMod.getValueAt(selectedRow,0);
        String code = getSCode(subj);
        Hashtable ht = ViewElements.currentSemester.getSubjectsOpen();
        selectedSubject = (Subject)ht.get(code);
     }
    }
  }
  //might use
  private class MyWindowAdapter extends WindowAdapter {
    public void windowClosing(WindowEvent we) {
      dispose();
    }
  }
}