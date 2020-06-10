package sample;

import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.awt.event.*;
import javax.swing.table.*;
import javax.swing.event.*;

public class EnrollDialog extends JDialog {
  
  //model 
  Student selected;
  //view
  JTable       tableNStudent;
  JScrollPane  scrollPaneNStudent;
  JLabel       labelNAdd  = new JLabel("STUDENT ENROLLMENT",JLabel.CENTER);
  JLabel       LabelNCourse  = new JLabel("Course");
  JLabel       labelNYear  = new JLabel("Year");
  SpinnerModel mod    = new SpinnerNumberModel(1,1,4,1);
  JSpinner     spinnerYear   = new JSpinner(mod);
  String[]     course     = {"BS-CS","BS-IT","BS-IM"};
  JComboBox    comboBoxNCourse  = new JComboBox(course);
  JButton      buttonEnroll   = new JButton("Enroll Student");
  JButton      buttonCancel  = new JButton("Cancel");
  
  EnrollDialog(JFrame parent) {
    super(parent, true);
    setTitle("Student Enrollment");
    buttonEnroll.setEnabled(false);
    setStudTable();
    Container cont = getContentPane();
    GridLayout layout = new GridLayout();
    cont.setLayout(layout);
    
    cont.add(labelNAdd);
    cont.add(labelNYear);		cont.add(spinnerYear);
    cont.add(LabelNCourse);		cont.add(comboBoxNCourse);
    cont.add(buttonEnroll);		cont.add(buttonCancel);
    cont.add(scrollPaneNStudent);
    
    layout.setConstraints(labelNYear,2,1,1,1);
    layout.setConstraints(spinnerYear,2,2,1,1);
    layout.setConstraints(LabelNCourse,3,1,1,1);
    layout.setConstraints(comboBoxNCourse,3,2,1,1);
    layout.setConstraints(scrollPaneNStudent,4,1,3,1);
    layout.defineOwnConstraints(labelNAdd,1,1,3,1, 0,0, 0,10, 0,0,0,0);
    layout.defineOwnConstraints(buttonEnroll,5,3,1,1, 0,0, 0,13, 5,0,0,0);
    layout.defineOwnConstraints(buttonCancel,6,3,1,1, 0,0, 0,13, 5,0,0,0);
    
    buttonEnroll.addActionListener(new ButtonListener());
    buttonCancel.addActionListener(new ButtonListener());

    ListSelectionModel tbModel = tableNStudent.getSelectionModel();
    tbModel.addListSelectionListener(new StudTableListener());
    
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
  
  //set-up table
  public void setStudTable(){
    System.out.println("model initialized");
    tableNStudent = new JTable(new StudentInformationSystem.StudentModel());
    scrollPaneNStudent = new JScrollPane(tableNStudent);
    tableNStudent.setPreferredScrollableViewportSize(new Dimension(400,150));
    tableNStudent.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
  }

  //controller
  private class ButtonListener implements ActionListener {
    public void actionPerformed(ActionEvent ev) {
      if(ev.getSource()== buttonEnroll){
        selected.setCourse("" + comboBoxNCourse.getSelectedItem());
        selected.setYear(Integer.parseInt(""+spinnerYear.getValue()));
        ViewElements.currentSemester.enrollStudent(selected);
        ViewElements.SemesterList.put(ViewElements.currentSemester.getSemCode(),ViewElements.currentSemester);
        ViewElements.table3Student.setModel(new StudentInformationSystem.EnrModel());
        ViewElements.tab4Grid.setModel(new StudentInformationSystem.EnrModel());
        StudentInformationSystem.writeFiles();		StudentInformationSystem.readFiles();
        System.out.println("student enrolled");
        dispose();
        JFrame tmpfm = new JFrame();
        RegistryDialog regdlg = new RegistryDialog(tmpfm,selected);
        regdlg.show();
        
      }
      if(ev.getSource()==buttonCancel){
        dispose();
      }
    }
  }
  private class StudTableListener implements ListSelectionListener {
    public void valueChanged(ListSelectionEvent e) {
      if(e.getValueIsAdjusting()) return;
      ListSelectionModel lsm = (ListSelectionModel)e.getSource();
      if(lsm.isSelectionEmpty()) {
        buttonEnroll.setEnabled(false);
      }
      else {
        buttonEnroll.setEnabled(true);
     
       int selectedRow = lsm.getMinSelectionIndex();
       AbstractTableModel tempMod = new StudentInformationSystem.StudentModel();
       String id = "" +tempMod.getValueAt(selectedRow,0);
       selected = (Student)ViewElements.StudentList.get(id);
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