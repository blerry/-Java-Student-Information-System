package sample;

import java.io.*;
import java.awt.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.table.*;
import javax.swing.event.*;

public class StudentInformationSystem extends ViewElements
{
  //main
  public static void main(String[] args){
    System.out.println("Loading program...");
    try {
      UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
    } catch (Exception e) {}//*/
    String title = "Student Information System";
    JFrame main = new StudentInformationSystem(title);
    Toolkit thKit = main.getToolkit();
    Dimension windowSize = thKit.getScreenSize();
    main.pack();
    int wd = main.getWidth();
    int ht = main.getHeight();
    int x = (int)((windowSize.getWidth()/2)-(wd/2));
    int y = (int)((windowSize.getHeight()/2)-(ht/2));
    main.setBounds(x,y,wd,ht);
    main.setVisible(true);
    main.setResizable(false);
    main.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
  }
  StudentInformationSystem(String title){
    super(title);
    System.out.println("setting up user interface\ngathering resources.");
    Container cont = getContentPane();
    System.out.println("loading tables");
    setTabs();
    readFiles();    readMisc();
    setStudentTable();
    setSubjectTable();
    setEnrollmentTable();
    setGridTable();
    //setLayouts();
    cont.add(tabs);
    addListeners();
    addWindowListener(new winListener());
  }
  //misc methods
  //sets all tabs
  private void setTabs(){
    String[] titles = {"Student","Courses",
                       "Enroll","Grades","Settings"};
    String[] ttips = {"Create new student.","View classes","Student registration",
                      "Edit/View Grades","Settings"};
    addTabComponents();
    
    tabs.addTab(titles[0],new ImageIcon("") ,tab1,ttips[0]);
    tabs.addTab(titles[1],new ImageIcon(""),tab2,ttips[1]);
    tabs.addTab(titles[2],new ImageIcon(""),tab3,ttips[2]);
    tabs.addTab(titles[3],new ImageIcon(""),tab4,ttips[3]);
    tabs.addTab(titles[4],new ImageIcon(""),tab5,ttips[4]);
    
  }
  //adds all components to each tab
  private void addTabComponents(){
    //tab1
    tab1.add(label1Add);			tab1.add(label1cm);
    tab1.add(label1ID);			tab1.add(textField1ID);
    tab1.add(label1Name);			tab1.add(textField1LastName);
    tab1.add(textField1FirstName);		
    //tab1.add(tf1mi); tab1.add(lb1mn);                          tab1.add(tf1mn);
    tab1.add(button1RNew);			tab1.add(button1Save);
    tab1.add(button1Edit);		tab1.add(button1Delete);
    tab1.add(label1FirstName);			tab1.add(label1LastName);
    textField1ID.setEditable(false);

    
    //tab2
    tab2.add(label2Subject);			
    tab2.add(label2Code);			tab2.add(textField2Code);
    tab2.add(label2Units);			tab2.add(textField2Units);
    tab2.add(label2Desc);		tab2.add(textField2Desc);
    tab2.add(button2RNew);			tab2.add(button2Save);
    tab2.add(button2Delete);			tab2.add(button2Edit);
    tab2.add(label2Message);

    //tab3
    tab3.add(label3Subject);			tab3.add(button3Add);
    tab3.add(button3OpenR);
    button3OpenR.setEnabled(false);
   
    //tab4
    tab4.add(label4Grid);			
    tab4.add(label4ID);			tab4.add(textField4ID);
    tab4.add(label4Name);			tab4.add(textField4LastName);
    tab4.add(textField4FirstName);		
    tab4.add(label4Course);			tab4.add(textField4Course);
    tab4.add(label4Year);			tab4.add(textField4Year);
    tab4.add(button4Grid);
    textField4ID.setEditable(false);
    textField4LastName.setEditable(false);	textField4Course.setEditable(false);
    textField4Year.setEditable(false); textField4FirstName.setEditable(false);
    
    //tab5
    tab5.add(label5Settings);
    tab5.add(label5System);		tab5.add(sr5sy);
    tab5.add(label5Semester);		tab5.add(cb5sem);
    tab5.add(button5Update);
    
    
  }
  //add listeners to all components in tabs
  private void addListeners(){
    //buttons
    button1Save.addActionListener(new ButtonListener());
    button1RNew.addActionListener(new ButtonListener());
    button1Edit.addActionListener(new ButtonListener());
    button1Delete.addActionListener(new ButtonListener());
    button2Save.addActionListener(new ButtonListener());
    button2RNew.addActionListener(new ButtonListener());
    button2Edit.addActionListener(new ButtonListener());
    button2Delete.addActionListener(new ButtonListener());
    button3Add.addActionListener(new ButtonListener());
    button3OpenR.addActionListener(new ButtonListener());
    button4Grid.addActionListener(new ButtonListener());
    button5Update.addActionListener(new ButtonListener());
    
    //tables
    ListSelectionModel tbModel = table1Student.getSelectionModel();
    tbModel.addListSelectionListener(new StudentTableListener());
    tbModel = table2Subject.getSelectionModel();
    tbModel.addListSelectionListener(new SubjectTableListener());
    tbModel = table3Student.getSelectionModel();
    tbModel.addListSelectionListener(new EnrollTableListener());
    tbModel = tab4Grid.getSelectionModel();
    tbModel.addListSelectionListener(new GrdTableListener());
    

  }
  //file handling routines
  //read/write main files
  static void writeFiles(){
    try{
      FileOutputStream outStream;
      ObjectOutputStream ooStream;

      outStream = new FileOutputStream("StudentList.dat");
      ooStream = new ObjectOutputStream(outStream);
      ooStream.writeObject(StudentList);
      ooStream.flush();
      outStream.close();
      outStream = new FileOutputStream("SemesterList.dat");
      ooStream = new ObjectOutputStream(outStream);
      ooStream.writeObject(SemesterList);
      ooStream.flush();
      outStream.close();
      System.out.println("files saved");
    }
    catch (IOException exc){System.out.println("Error writing file!");}
  }
  static void readFiles(){
    try{
      FileInputStream inStream;
      ObjectInputStream oiStream;
      
      try{
        inStream = new FileInputStream("SemesterList.dat");
        oiStream = new ObjectInputStream(inStream);
       
        SemesterList = (SemesterList)oiStream.readObject();
      }
      catch(FileNotFoundException exc){
        System.out.println("SemesterList not found.");
        sr5sy.setValue(new Integer(2005));
        cb5sem.setValue(new Integer(1));
        int sy = Integer.parseInt(""+sr5sy.getValue());
        int sem = Integer.parseInt(""+cb5sem.getValue());
        Semester temp = new Semester(sy,sem);
        currentSemester = temp;
        SemesterList.put(currentSemester.getSemCode(),currentSemester);
      }
      inStream = new FileInputStream("StudentList.dat");
      oiStream = new ObjectInputStream(inStream);
      StudentList = (StudentList)oiStream.readObject();
      System.out.println("files reloaded.");
    }
    catch (FileNotFoundException exc){
      System.out.println("File/s not found!");
      writeMisc();
    }
    catch(IOException exc){
      System.out.println("Error reading file! Creating new database.");
      sr5sy.setValue(new Integer(2005));
      cb5sem.setValue(new Integer(1));
      int semesterYear = Integer.parseInt(""+sr5sy.getValue());
      int semester = Integer.parseInt(""+cb5sem.getValue());
      Semester temp = new Semester(semesterYear,semester);
      currentSemester = temp;
      SemesterList.put(currentSemester.getSemCode(),currentSemester);
      writeMisc();
    }
    catch(ClassNotFoundException exc){
      System.out.println("Class not found.");
    }
  }
  //read/write miscellaneous files
  static void writeMisc(){
    String settings = Student.getCount()+" ";
    String semset = currentSemester.getSemCode()+" ";
    try{
      FileWriter outStream = new FileWriter("misc.inf");
      outStream.write(settings+"\n");
      outStream.write(semset);
      outStream.close();
      System.out.println("miscellaneous files saved.");
    }
    catch(IOException exc){System.out.println("error writing misc file.");}
  }
  static void readMisc(){
    try{
      BufferedReader inStream = new BufferedReader(new FileReader("misc.inf"));
      
      int setThis = Integer.parseInt(inStream.readLine().trim());
      Student.setInitialCount(setThis);
      
      String semCode = inStream.readLine().trim();
      currentSemester = (Semester)SemesterList.get(semCode);
      sr5sy.setValue(new Integer(currentSemester.getSchoolYear()));
      cb5sem.setValue(new Integer(currentSemester.getSem()));
      System.out.println("miscellaneous files reloaded.");
    }
    catch(IOException exc){
      System.out.println("Error reading misc file");
      writeMisc();
    }
  }
  
  //table models for each table on tabs
  public void setStudentTable(){
    table1Student = new JTable(new StudentModel());
    scrollPane1Student = new JScrollPane(table1Student);
    tab1.add(scrollPane1Student);
    table1Student.setPreferredScrollableViewportSize(new Dimension(600,200));
    table1Student.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    button1Edit.setEnabled(false);
    button1Delete.setEnabled(false);
    button2Edit.setEnabled(false);
    button2Delete.setEnabled(false);
    button4Grid.setEnabled(false);
  }
  public void setSubjectTable(){
    table2Subject = new JTable(new SubjectModel());
    scrollPane2Subject = new JScrollPane(table2Subject);
    tab2.add(scrollPane2Subject);
    table2Subject.setPreferredScrollableViewportSize(new Dimension(600,220));
    table2Subject.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
  }
  public void setEnrollmentTable(){
    table3Student = new JTable(new EnrModel());
    scrollPane3Student = new JScrollPane(table3Student);
    tab3.add(scrollPane3Student);
    table3Student.setPreferredScrollableViewportSize(new Dimension(755,300));
    table3Student.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
  }
  public void setGridTable(){
    tab4Grid = new JTable(new EnrModel());
    spinner4Grid = new JScrollPane(tab4Grid);
    tab4.add(spinner4Grid);
    tab4Grid.setPreferredScrollableViewportSize(new Dimension(600,200));
    tab4Grid.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
  }
  static class StudentModel extends AbstractTableModel {
    private String[] columnNames = {"ID NUMBER","LAST NAME","FIRST NAME"};
    private Object[][] data = storeObject();
    
    private Object[][] storeObject(){
      Vector vect = StudentList.sort();
      Object[][] temp = new Object[vect.size()][3];
      
      for(int a=0;a<vect.size();a++){
        Student temp2 = (Student)vect.elementAt(a);
        temp[a][0] = temp2.getIdNumber();
        temp[a][1] = temp2.getLastName();
        temp[a][2] = temp2.getFirstName();
      }
      return temp;
    }
    public Object[][] getData(){
      return data;
    }
    public int getColumnCount() { return columnNames.length; }
    public int getRowCount() { return data.length; }
    public String getColumnName(int col) { return columnNames[col]; }
    public Object getValueAt(int row, int col) { return data[row][col]; }
    public boolean isCellEditable(int row, int col) { return false; }
    
  }
  static class SubjectModel extends AbstractTableModel {
    private String[] columnNames = {"SUBJECT CODE","SUBJECT DESCRIPTION","UNITS",""};
    private Object[][] data = storeObject();
    
    private Object[][] storeObject(){
      Hashtable subjects = currentSemester.getSubjectsOpen();
      Set sKeys = subjects.keySet();
      Vector keys = new Vector(sKeys);
      Collections.sort(keys);
      Object[][] temp = new Object[keys.size()][4];
      int a=0;
      for(Iterator i=keys.iterator();i.hasNext();a++){
        String thisKey = ""+i.next();
        Subject temp2 = (Subject)subjects.get(thisKey);
        
        temp[a][0] = temp2.getCode();
        temp[a][1] = temp2.getDescription();
        temp[a][2] = ""+temp2.getUnits();
       
      }
      return temp;
    }
    public int getColumnCount() { return columnNames.length; }
    public int getRowCount() { return data.length; }
    public String getColumnName(int col) { return columnNames[col]; }
    public Object getValueAt(int row, int col) { return data[row][col]; }
    public boolean isCellEditable(int row, int col) { return false; }
    
  }
  static class EnrModel extends AbstractTableModel {
    private String[] columnNames = {"ID NUMBER","STUDENT NAME","YEAR","COURSE"};
    private Object[][] data = storeObject();
        
    private Object[][] storeObject(){
      Hashtable students = currentSemester.getStudentsEnrolled();
      Set sKeys = students.keySet();
      Vector keys = new Vector(sKeys);
      Collections.sort(keys);
      Object[][] temp = new Object[keys.size()][4];
      
      int a=0;
      for(Iterator i=keys.iterator();i.hasNext();a++){
        String thisKey = ""+i.next();
        Student temp2 = (Student)students.get(thisKey);
        Person temp3 = new Person(temp2.getFirstName(),
                                  temp2.getLastName());
        temp[a][0] = temp2.getIdNumber();
        temp[a][1] = temp3;
        temp[a][2] = ""+temp2.getYear();
        temp[a][3] = temp2.getCourse();
      }
      return temp;
    }
    
    public int getColumnCount() { return columnNames.length; }
    public int getRowCount() { return data.length; }
    public String getColumnName(int col) { return columnNames[col]; }
    public Object getValueAt(int row, int col) { return data[row][col]; }
    public boolean isCellEditable(int row, int col) { return false; }
    
  }
  //controller/
  //component listeners
  private class ButtonListener implements ActionListener{
    public void actionPerformed(ActionEvent e){
      //Tab1
      if(e.getSource()==button1Save){
        String fn = textField1FirstName.getText().trim().toUpperCase();
        String ln = textField1LastName.getText().trim().toUpperCase();
        
        Student temp = new Student(fn,ln);
        StudentList.put(temp.getIdNumber(),temp);
        textField1ID.setText(temp.getIdNumber());
        writeFiles();  readFiles();
        writeMisc();   readMisc();
        table1Student.setModel(new StudentModel());
        System.out.println("new student added to list");
      }
      if(e.getSource()==button1RNew){
        textField1FirstName.setEditable(true);
        textField1LastName.setEditable(true);
        button1Save.setEnabled(true);
        clearTab1();
      }
      if(e.getSource()==button1Edit){
        studentDialog = new EditStudentDialog(frameStudent,selectedStudent);
        studentDialog.show();
      }
      if(e.getSource()==button1Delete){
        StudentList.remove(selectedStudent.getIdNumber());
        writeFiles();  readFiles();
        table1Student.setModel(new StudentModel());
        System.out.println("student deleted from ist.");
      }
      
      //Tab2
      if(e.getSource()==button2Save){
        label2Message.setText("");
        try{
          String cd = textField2Code.getText().trim().toUpperCase();
          String desc = textField2Desc.getText().trim().toUpperCase();
          int units = Integer.parseInt(textField2Units.getText().trim());
          if(units>0){
            Subject temp = new Subject(cd,desc);
            
            temp.setUnits(units);
            currentSemester.openSubject(temp);
            SemesterList.put(currentSemester.getSemCode(),currentSemester);
            writeFiles();  readFiles();
            table2Subject.setModel(new SubjectModel());
            System.out.println("new subject opened");
            clearTab2();
          }
        }
        catch(NumberFormatException exc){
          label2Message.setText("Please Enter Correct Number of Units.");
        }
      }
      if(e.getSource()==button2RNew){
        table2Subject.clearSelection();
        textField2Code.setEditable(true);
        textField2Desc.setEditable(true);
        button2Save.setEnabled(true);
        textField2Units.setEditable(true);
        clearTab2();
      }
      if(e.getSource()==button2Edit){
        subjectDialog = new EditSubjectDialog(frameSubject,selectedSubject);
        subjectDialog.show();
      }
      if(e.getSource()==button2Delete){
        currentSemester.closeSubject(selectedSubject.getCode());
        SemesterList.put(currentSemester.getSemCode(),currentSemester);
        writeFiles();  readFiles();
        table2Subject.setModel(new SubjectModel());
        System.out.println("subject deleted.");
      }
      //Tab3
      if(e.getSource()==button3Add){
        enrollDialog = new EnrollDialog(fmnenr);
        enrollDialog.show();
      }
      if(e.getSource()==button3OpenR){
        registerDialog = new RegistryDialog(frameRRegister,selectedStudent);
        registerDialog.show();
      }
      //Tab4
      if(e.getSource()==button4Grid){
        gridDDialog = new GradesDialog(frameGGRid,selectedStudent);
        gridDDialog.show();
      }
      //Tab5
      if(e.getSource()==button5Update){
        writeFiles();	readFiles();
        int sy  = Integer.parseInt(""+sr5sy.getValue());
        int sem = Integer.parseInt(""+cb5sem.getValue());
        Semester temp = new Semester(sy,sem);
        System.out.println(SemesterList.containsKey(temp.getSemCode()));
        if(SemesterList.containsKey(temp.getSemCode())){
          System.out.println("changing semester");
          currentSemester = (Semester)SemesterList.get(temp.getSemCode());
        }
        else{
          System.out.println("opening new semester");
          currentSemester = temp;
          SemesterList.put(temp.getSemCode(),temp);
        }
        table2Subject.setModel(new SubjectModel());
        table3Student.setModel(new EnrModel());
        tab4Grid.setModel(new EnrModel());
        writeFiles();	readFiles();
        writeMisc();	readMisc();
        tabs.setSelectedIndex(2);
        System.out.println("semester updated");
      }
    }
  }
  private class StudentTableListener implements ListSelectionListener {
    public void valueChanged(ListSelectionEvent e) {

     if(e.getValueIsAdjusting()) return;
     ListSelectionModel lsm = (ListSelectionModel)e.getSource();
     if(lsm.isSelectionEmpty()) {
       selectedStudent=null;
       button1Edit.setEnabled(false);
       button1Delete.setEnabled(false);
     }
     else {
       button1Edit.setEnabled(true);
       button1Delete.setEnabled(true);
       int selectedRow = lsm.getMinSelectionIndex();
       StudentModel tempMod = new StudentModel();
       String id = ""+tempMod.getValueAt(selectedRow,0);
       selectedStudent = (Student)StudentList.get(id);
       textField1ID.setText(id);
       textField1FirstName.setText(selectedStudent.getFirstName());
       textField1LastName.setText(selectedStudent.getLastName());
       textField1FirstName.setEditable(false);
       textField1LastName.setEditable(false);
       
       button1Save.setEnabled(false);
     }
    }
  }
  private class SubjectTableListener implements ListSelectionListener {
    public void valueChanged(ListSelectionEvent e) {
     if(e.getValueIsAdjusting()) return;
     
     ListSelectionModel lsm = (ListSelectionModel)e.getSource();
     if(lsm.isSelectionEmpty()) {
       selectedSubject=null;
       clearTab2();
       button2Edit.setEnabled(false);
       button2Delete.setEnabled(false);
       textField2Units.setEditable(false);
     }
     else {
       button2Edit.setEnabled(true);
       button2Delete.setEnabled(true);
       textField2Units.setEditable(true);
       int selectedRow = lsm.getMinSelectionIndex();
       SubjectModel tempMod = new SubjectModel();
       String code = ""+tempMod.getValueAt(selectedRow,0);
       Hashtable SbjList = currentSemester.getSubjectsOpen();
       selectedSubject = (Subject)SbjList.get(code);
       textField2Code.setText(code);
       textField2Desc.setText(selectedSubject.getDescription());
       textField2Units.setText(""+selectedSubject.getUnits());

       textField2Code.setEditable(false);
       textField2Desc.setEditable(false);
       button2Save.setEnabled(false);
       textField2Units.setEditable(false);
     }
    }
  }
  private class EnrollTableListener implements ListSelectionListener {
    public void valueChanged(ListSelectionEvent e) {
      if(e.getValueIsAdjusting()) return;
     
      ListSelectionModel lsm = (ListSelectionModel)e.getSource();
      if(lsm.isSelectionEmpty()) {
        button3OpenR.setEnabled(false);
      }
      else {
        button3OpenR.setEnabled(true);
        int selectedRow = lsm.getMinSelectionIndex();
        EnrModel tempMod = new EnrModel();  
        String id = ""+tempMod.getValueAt(selectedRow,0);
        Hashtable StudList = currentSemester.getStudentsEnrolled();
        Student temp = (Student)StudList.get(id);
        selectedStudent = temp;
      }
    }
  }
  private class GrdTableListener implements ListSelectionListener {
    public void valueChanged(ListSelectionEvent e) {
      if(e.getValueIsAdjusting()) return;
     
      ListSelectionModel lsm = (ListSelectionModel)e.getSource();
      if(lsm.isSelectionEmpty()) {
        button4Grid.setEnabled(false);
        clearTab4();
      }
      else {
        button4Grid.setEnabled(true);
        int selectedRow = lsm.getMinSelectionIndex();
        EnrModel tempMod = new EnrModel();
        String id = ""+tempMod.getValueAt(selectedRow,0);
        Hashtable ht = currentSemester.getStudentsEnrolled();
        Student stud = (Student)ht.get(id);
        textField4ID.setText(stud.getIdNumber());
        textField4LastName.setText(stud.getLastName());
        textField4FirstName.setText(stud.getFirstName());
        textField4Course.setText(stud.getCourse());
        textField4Year.setText(""+stud.getYear());
        selectedStudent = stud;
      }
    }
  }
  private class winListener extends WindowAdapter {
    public void windowClosing(WindowEvent we) {
      Object[] options = { "YES", "NO" };
      int option = JOptionPane.showOptionDialog(null, "Are you sure you want to exit?","EXIT",
                   JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
                   null, options, options[0]);
      if (option == JOptionPane.OK_OPTION){
        System.out.println("Done Loading");
        System.exit(0);
      }
    }
  }
}
