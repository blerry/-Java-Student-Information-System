package sample;

import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.awt.event.*;
import javax.swing.table.*;
import javax.swing.event.*;

public class AddSubjectDialog extends JDialog {
  //model
  Student selected;
  Subject selectedSubject;
  //view
  JTable       tableDSubject;
  JScrollPane  scrollPaneDSubject;
  JLabel       labelAdd  = new JLabel("SELECT SUBJECT FROM LIST");
  JButton      buttonDOk   = new JButton("Add this Subject");
  JButton      buttonDClose   = new JButton("Close");
  
  AddSubjectDialog(JFrame parent, Student thisStudent) {
    super(parent, true);
    setTitle("Add Subject");

    Container cont = getContentPane();
    GridLayout layout = new GridLayout();
    cont.setLayout(layout);
    
    setSubjectTable();
    
    cont.add(labelAdd);
    cont.add(buttonDOk);
    cont.add(buttonDClose);
    
    layout.setConstraints(labelAdd,1,1,1,1);
    layout.setConstraints(scrollPaneDSubject,2,1,1,1);
    layout.setConstraints(buttonDOk,3,1,1,1);
    layout.setConstraints(buttonDClose,4,1,1,1);
    
    buttonDOk.addActionListener(new ButtonListener());
    buttonDClose.addActionListener(new ButtonListener());
    
    ListSelectionModel tbModel = tableDSubject.getSelectionModel();
    tbModel.addListSelectionListener(new SubjectTableListener());
    
    buttonDOk.setEnabled(false);
    Toolkit thKit = getToolkit();
    Dimension wndSze = thKit.getScreenSize();
    pack();
    int wd = getWidth();
    int ht = getHeight();
    int x = (int)((wndSze.getWidth()/2)-(wd/2));
    int y = (int)((wndSze.getHeight()/2)-(ht/2));
    setBounds(x,y,wd,ht);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }
  //set table
  public void setSubjectTable(){
    tableDSubject = new JTable(new StudentInformationSystem.SubjectModel());
    scrollPaneDSubject = new JScrollPane(tableDSubject);
    this.getContentPane().add(scrollPaneDSubject);
    tableDSubject.setPreferredScrollableViewportSize(new Dimension(400,200));
    tableDSubject.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
  }
  //controller
  private class ButtonListener implements ActionListener {
    public void actionPerformed(ActionEvent ev) {
      if(ev.getSource()==buttonDOk){
      	RegistryDialog.selected.addSubject(selectedSubject);
      	ViewElements.currentSemester.enrollStudent(RegistryDialog.selected);
        ViewElements.SemesterList.put(ViewElements.currentSemester.getSemCode(),ViewElements.currentSemester);
        RegistryDialog.tableRSubject.setModel(new RegistryDialog.SubjectModel());
        StudentInformationSystem.writeFiles();		StudentInformationSystem.readFiles();
        System.out.println("subject added");
        dispose();
      }
      if(ev.getSource()==buttonDClose){
        System.out.println("closed");
        dispose();
      }
    }
  }
  private class SubjectTableListener implements ListSelectionListener {
    public void valueChanged(ListSelectionEvent e) {
      if(e.getValueIsAdjusting()) return;
      ListSelectionModel lsm = (ListSelectionModel)e.getSource();
      if(lsm.isSelectionEmpty()) {
        buttonDOk.setEnabled(false);
      }
      else {
        buttonDOk.setEnabled(true);
        int selectedRow = lsm.getMinSelectionIndex();
        StudentInformationSystem.SubjectModel tempMod = new StudentInformationSystem.SubjectModel();
        String code = "" +tempMod.getValueAt(selectedRow,0);
        Hashtable ht = ViewElements.currentSemester.getSubjectsOpen();
        selectedSubject = new Subject((Subject)ht.get(code));
     }
    }
  }
  private class MyWindowAdapter extends WindowAdapter {
    public void windowClosing(WindowEvent we) {
      dispose();
    }
  }
}