package sample;

import java.io.*;
import java.awt.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.table.*;

public class ViewElements extends JFrame
{
	
  ViewElements(String title){ super(title); }
  //other methods
  //resets and clears tab1
  public void clearTab1(){
    textField1ID.setText("");
    textField1LastName.setText("");
    textField1FirstName.setText("");
  }
  //resets and clears tab2
  public void clearTab2(){
    textField2Code.setText("");
    textField2Desc.setText("");
    textField2Units.setText("");
  }
  //resets and clears tab4
  public void clearTab4(){
    textField4ID.setText("");
    textField4LastName.setText("");
    textField4FirstName.setText("");
    textField4Course.setText("");
    textField4Year.setText("");
  }

  //main
  JTabbedPane tabs = new JTabbedPane();
  JPanel mainPanel = new JPanel();

  static Student selectedStudent;
  static Subject selectedSubject;
  static Semester currentSemester;

  //tab1
  static JTable  table1Student;
  JScrollPane    scrollPane1Student;
  JPanel         tab1     = new JPanel();
  JLabel         label1Add   = new JLabel("CREATE NEW STUDENT REGISTRATION",JLabel.CENTER);
  JLabel         label1ID    = new JLabel("ID Number:");
  JTextField     textField1ID    = new JTextField(5);
  JLabel         label1Name   = new JLabel("Student Name ");
  JTextField     textField1LastName    = new JTextField(14);
  JLabel         label1cm    = new JLabel(",",JLabel.LEFT);
  JTextField     textField1FirstName    = new JTextField(14);

  JButton        button1Save    = new JButton("SAVE");
  JButton        button1RNew   = new JButton("NEW");
  JButton        button1Delete   = new JButton("DELETE");
  JButton        button1Edit  = new JButton("EDIT");
  JLabel         label1FirstName    = new JLabel("First Name");
  JLabel         label1LastName    = new JLabel("Last Name"); 

  //edit student
  EditStudentDialog studentDialog;
  JFrame         frameStudent   = new JFrame("Edit Student");

  //tab2
  static JTable  table2Subject;
  JScrollPane    scrollPane2Subject;
  JPanel         tab2     = new JPanel();
  JLabel         label2Subject   = new JLabel("OPEN NEW SUBJECTS");
  JLabel         label2Code    = new JLabel("SUBJECT CODE:");
  JTextField     textField2Code    = new JTextField(10);
  JLabel         label2Units   = new JLabel("UNITS:");
  JTextField     textField2Units   = new JTextField(2);
  JLabel         label2Desc  = new JLabel("SUBJECT DESCRIPTION:");
  JTextField     textField2Desc  = new JTextField(45);
  JButton        button2Save    = new JButton("SAVE");
  JButton        button2RNew   = new JButton("NEW");
  JButton        button2Delete   = new JButton("DELETE");
  JButton        button2Edit  = new JButton("EDIT");
  JLabel         label2Message   = new JLabel();
  
  //edit subject
  EditSubjectDialog subjectDialog;
  JFrame            frameSubject   = new JFrame("Edit Subject");
  
  //tab3
  static JTable  table3Student;
  JScrollPane    scrollPane3Student;
  JPanel         tab3     = new JPanel();
  JLabel         label3Subject   = new JLabel("ENROLLMENT",JLabel.CENTER);
  JButton        button3Add   = new JButton("Add new enrollee");
  JButton        button3OpenR    = new JButton("Open Student Registration");
  
  //enroll student
  EnrollDialog   enrollDialog;
  JFrame         fmnenr   = new JFrame("Student Enrollment");
  
  //student registry
  RegistryDialog registerDialog;
  JFrame         frameRRegister   = new JFrame();
  
  //tab4
  static JTable  tab4Grid;
  JScrollPane    spinner4Grid;
  JPanel         tab4     = new JPanel();
  JLabel         label4Grid   = new JLabel("STUDENT REGISTRY",JLabel.CENTER);
  JLabel         label4ID    = new JLabel("ID Number");
  JTextField     textField4ID    = new JTextField(15);
  JLabel         label4Name   = new JLabel("Full Name");
  JTextField     textField4LastName    = new JTextField(3);
  JTextField     textField4FirstName    = new JTextField(25);
  JLabel         label4Course   = new JLabel("Course");
  JTextField     textField4Course   = new JTextField(10);
  JLabel         label4Year    = new JLabel("Year");
  JTextField     textField4Year    = new JTextField(2);
  JButton        button4Grid  = new JButton("EDIT/VIEW GRADES");
  
  //grades
  GradesDialog   gridDDialog;
  JFrame         frameGGRid   = new JFrame();
  
  //tab5
  JPanel         tab5     = new JPanel();
  JLabel         label5Settings    = new JLabel("SETTINGS");
  JLabel         label5System    = new JLabel("School Year");
  JLabel         label5Semester   = new JLabel("Semester");
  JButton        button5Update    = new JButton("UPDATE");

  static JSpinner     sr5sy  = new JSpinner();
  static SpinnerModel mod1   = new SpinnerNumberModel(1,1,3,1);
  static JSpinner     cb5sem = new JSpinner(mod1);

  //models and lists
  static StudentList StudentList = new StudentList();
  static SemesterList SemesterList = new SemesterList();
}