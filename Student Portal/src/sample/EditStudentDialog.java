package sample;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
public class EditStudentDialog extends JDialog {
  
  //view
  JLabel labelEStudent = new JLabel("EDIT STUDENT INFORMATION");
  JLabel labelEID = new JLabel("ID Number");
  JTextField textFieldEID = new JTextField(5);
  JLabel labelEName = new JLabel("Student Name");
  JTextField textFieldELastName = new JTextField(15);
  JLabel labelEComma = new JLabel(",");
  JTextField textFieldEFirstName = new JTextField(15);
  JLabel labelELastName = new JLabel("Last Name");
  JLabel labelEFirstName = new JLabel("First Name");
  JButton buttonEok = new JButton("SAVE");
  JButton buttonECancel = new JButton("CANCEL");
  
  EditStudentDialog(JFrame parent, Student selected) {
    super(parent, true);
    setTitle("Edit Student");

    Container cont = getContentPane();
    GridLayout layout = new GridLayout();
    cont.setLayout(layout);
    cont.setLayout(layout);
    cont.add(labelEStudent);
    cont.add(labelEID);		cont.add(textFieldEID);
    cont.add(labelEName);		cont.add(labelEComma);
    cont.add(textFieldELastName);		cont.add(textFieldEFirstName);	
    cont.add(labelELastName);		cont.add(labelEFirstName);		
    cont.add(buttonEok);	cont.add(buttonECancel);
    
    textFieldEID.setEditable(false);
    
    layout.setConstraints(labelEStudent,1,1,7,1);
    layout.setConstraints(labelEID,2,1,1,1);
    layout.setConstraints(textFieldEID,2,2,1,1);
    layout.setConstraints(labelEName,3,1,1,1);
    layout.setConstraints(textFieldELastName,3,2,1,1);
    layout.setConstraints(labelEComma,3,3,1,1);
    layout.setConstraints(textFieldEFirstName,3,4,1,1);
    layout.setConstraints(labelELastName,4,2,1,1);
    layout.setConstraints(labelEFirstName,4,4,1,1);
    layout.setConstraints(buttonECancel,6,5,1,1);
    layout.setConstraints(buttonEok,6,4,2,1);
    fillFormWith(selected);

    buttonECancel.addActionListener(new ButtonListener());
    buttonEok.addActionListener(new ButtonListener());
    
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
  private void fillFormWith(Student This){
    textFieldEID.setText(This.getIdNumber());
    textFieldEFirstName.setText(This.getFirstName());
    textFieldELastName.setText(This.getLastName());
  }
  //controller
  private class ButtonListener implements ActionListener {
    public void actionPerformed(ActionEvent event) {
      if(event.getSource() == buttonEok ) {
        String id = textFieldEID.getText();
        Student temp = (Student)ViewElements.StudentList.get(id);
        temp.setFirstName(textFieldEFirstName.getText().trim().toUpperCase());
        temp.setLastName(textFieldELastName.getText().trim().toUpperCase());
        
        StudentInformationSystem.writeFiles();		StudentInformationSystem.readFiles();
        ViewElements.table1Student.setModel(new StudentInformationSystem.StudentModel());
        System.out.println("student editted");
        dispose();
      }
      
      if(event.getSource() == buttonECancel) {
        dispose();
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