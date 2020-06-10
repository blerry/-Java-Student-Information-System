package sample;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class EditSubjectDialog extends JDialog {
  //model
  Subject selected;
  
  //view
  JLabel      labelSStudent  = new JLabel("EDIT SUBJECT");
  JLabel      labelSCode   = new JLabel("Subject Code");
  JLabel      labelSUnits  = new JLabel("Units:");
  JTextField  textFieldSUnits  = new JTextField(2);
  JTextField  textFieldSCode   = new JTextField(15);
  JLabel      labelSDesc   = new JLabel("Subject Description");
  JTextField  textFieldSDesc   = new JTextField(30);
  JButton     buttonSave   = new JButton("SAVE");
  JButton     buttonCancel  = new JButton("CANCEL");
  
  EditSubjectDialog(JFrame parent, Subject selected) {
    super(parent, true);
    this.selected = selected;
    setTitle("Edit Subject");

    Container cont = getContentPane();
    GridLayout layout = new GridLayout();

    cont.setLayout(layout);	cont.add(labelSStudent);
    cont.add(labelSCode);				cont.add(textFieldSCode);
    cont.add(labelSUnits);       cont.add(textFieldSUnits);
    cont.add(labelSDesc);				cont.add(textFieldSDesc);
    cont.add(buttonCancel);				cont.add(buttonSave);

    layout.setConstraints(labelSStudent,1,1,4,1);
    layout.setConstraints(labelSCode,2,1,1,1);
    layout.setConstraints(labelSUnits,2,3,1,1);
    layout.setConstraints(textFieldSUnits,2,4,1,1);
    layout.setConstraints(textFieldSCode,2,2,1,1);
    layout.setConstraints(labelSDesc,3,1,2,1);
    layout.setConstraints(buttonSave,5,3,2,1);
    layout.setConstraints(buttonCancel,5,5,1,1);
    layout.defineOwnConstraints(textFieldSDesc,4,1,5,1, 0,0, 0,10, 0,0,0,0);
    
    
    fillFormWith(selected);
    
    buttonCancel.addActionListener(new ButtonListener());
    buttonSave.addActionListener(new ButtonListener());
    
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
  
  //fill up frame fields
  private void fillFormWith(Subject This){
    textFieldSCode.setText(This.getCode());
    textFieldSDesc.setText(This.getDescription());
    textFieldSUnits.setText(""+This.getUnits());
  }
  
  //controller
  private class ButtonListener implements ActionListener {
    public void actionPerformed(ActionEvent event) {
      if(event.getSource() == buttonSave ) {
        try{
          ViewElements.currentSemester.closeSubject(selected.getCode());
          String code = textFieldSCode.getText().trim().toUpperCase();
          String desc = textFieldSDesc.getText().trim().toUpperCase();
          double units   = Double.parseDouble(textFieldSUnits.getText().trim());
          if(units>1){
            Subject temp = new Subject(code,desc);
            
            temp.setUnits(units);
            ViewElements.currentSemester.openSubject(temp);
            ViewElements.SemesterList.put(ViewElements.currentSemester.getSemCode(),ViewElements.currentSemester);
            StudentInformationSystem.writeFiles();		StudentInformationSystem.readFiles();
            ViewElements.table2Subject.setModel(new StudentInformationSystem.SubjectModel());
            System.out.println("subject editted");
            dispose();
          }
        }
        catch(NumberFormatException exc){}
      }
      
      if(event.getSource() == buttonCancel) {
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