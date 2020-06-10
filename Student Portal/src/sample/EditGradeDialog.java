package sample;

import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.awt.event.*;
import javax.swing.table.*;
import javax.swing.event.*;

public class EditGradeDialog extends JDialog {
  //model
  Subject selectDSubject;
  //view
  JLabel      lbrhd = new JLabel("ENTER NEW GRADE FOR");
  JLabel      lbrsb = new JLabel();
  JTextField  tfrsb = new JTextField(7);
  JButton     btrok = new JButton("ACCEPT");
  JButton     btrcn = new JButton("CANCEL");
  
  
  EditGradeDialog(JFrame parent,Subject selected) {
    super(parent, true);
    setTitle("Edit Grade");
    selectDSubject = selected;
    
    Container cont = getContentPane();
    GridLayout layout = new GridLayout();
    
    cont.add(lbrhd);			cont.add(lbrsb);
    cont.add(tfrsb);			cont.add(btrok);
    cont.add(btrcn);			cont.setLayout(layout);
    
    lbrsb.setText(""+selectDSubject);
    tfrsb.setText(""+selectDSubject.getGrade());
    
    layout.defineOwnConstraints(lbrsb,2,1,3,1, 0,0, 0,10, 10,5,5,5);
    layout.defineOwnConstraints(btrok,4,1,1,1, 0,0, 0,10, 5,5,5,5);
    layout.defineOwnConstraints(btrcn,4,3,1,1, 0,0, 0,10, 5,5,5,5);
    layout.defineOwnConstraints(tfrsb,3,2,1,1, 0,0, 0,10, 0,0,0,0);
    layout.defineOwnConstraints(lbrhd,1,1,3,1, 0,0, 0,10, 7,7,7,7);

    btrok.addActionListener(new ButtonListener());
    btrcn.addActionListener(new ButtonListener());
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
  
  //controller
  private class ButtonListener implements ActionListener {
    public void actionPerformed(ActionEvent ev) {
      if(ev.getSource()==btrok){
        GradesDialog.flag = true;
        try{
          double thisGrade = Double.parseDouble(tfrsb.getText());
          Student temp = GradesDialog.selected;
          Subject temp2 = GradesDialog.selectedSubject;
          temp2.setGrade(thisGrade);
          temp.addSubject(temp2);
          ViewElements.currentSemester.enrollStudent(temp);
          ViewElements.SemesterList.put(ViewElements.currentSemester.getSemCode(),ViewElements.currentSemester);
          StudentInformationSystem.writeFiles();	StudentInformationSystem.readFiles();
          GradesDialog.tableGSubject.setModel(new GradesDialog.GrdModel());
          dispose();
        }
        catch(NumberFormatException exc){}
      }
      if(ev.getSource()==btrcn){
        GradesDialog.flag = false;
        dispose();
      }
    }
  }
  private class MyWindowAdapter extends WindowAdapter {
    public void windowClosing(WindowEvent we) {
      dispose();
    }
  }
}