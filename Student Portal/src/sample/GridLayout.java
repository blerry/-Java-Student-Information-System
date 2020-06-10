package sample;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;

public class GridLayout extends GridBagLayout {
	
  public void setConstraints(Component c, int row, int col, int width, int height) {
    if(c instanceof JLabel)
      finishSet(c,row,col, width, height, 0,0, GridBagConstraints.NONE, GridBagConstraints.WEST);
    else if(c instanceof JButton)
      finishSet(c,row,col, width, height, 0,0, GridBagConstraints.NONE, GridBagConstraints.CENTER);
    else if(c instanceof JTextField)
      finishSet(c,row,col, width, height, 50,0, GridBagConstraints.NONE, GridBagConstraints.NORTHWEST);
    else if(c instanceof JPasswordField)
      finishSet(c,row,col, width, height, 100,0, GridBagConstraints.HORIZONTAL, GridBagConstraints.NORTHWEST);
    else if(c instanceof JScrollPane)
      finishSet(c,row,col, width, height, 0,0, GridBagConstraints.BOTH, GridBagConstraints.NORTHWEST);
    else if(c instanceof JTextArea)
      finishSet(c,row,col, width, height, 100,100, GridBagConstraints.NONE, GridBagConstraints.WEST);
    else if(c instanceof JScrollPane)
      finishSet(c,row,col, width, height, 100,100, GridBagConstraints.BOTH, GridBagConstraints.WEST);
    else if(c instanceof JList)
      finishSet(c,row,col, width, height, 100,100, GridBagConstraints.NONE, GridBagConstraints.NORTHWEST);
    else if(c instanceof JCheckBox)
      finishSet(c,row,col, width, height, 0,0, GridBagConstraints.HORIZONTAL, GridBagConstraints.WEST);
    else if(c instanceof JRadioButton)
      finishSet(c,row,col, width, height, 0,0, GridBagConstraints.HORIZONTAL, GridBagConstraints.NORTHWEST);
    else if(c instanceof JPanel)
      finishSet(c,row,col, width, height, 100,100, GridBagConstraints.BOTH, GridBagConstraints.NORTHWEST);
    else if(c instanceof JDesktopPane)
      finishSet(c,row,col, width, height, 100,100, GridBagConstraints.BOTH, GridBagConstraints.CENTER);
    else if(c instanceof JTabbedPane)
      finishSet(c,row,col, width, height, 100,100, GridBagConstraints.BOTH, GridBagConstraints.CENTER);
    else if(c instanceof JSpinner)
      finishSet(c,row,col, width, height, 0,0, GridBagConstraints.HORIZONTAL, GridBagConstraints.CENTER);
    else if(c instanceof JComboBox)
      finishSet(c,row,col, width, height, 0,0, GridBagConstraints.HORIZONTAL, GridBagConstraints.CENTER);
  }
  //default constraint settings
  private void finishSet(Component c, int y, int x, int w, int h, 
                  int weightx, int weighty, int fill, int anchor) {
    GridBagConstraints constraints = new GridBagConstraints();
    constraints.insets.bottom = 3;
    constraints.insets.left = 3;
    constraints.insets.right = 3;
    constraints.insets.top = 3;
    constraints.weightx = weightx;
    constraints.weighty = weighty;
    constraints.fill = fill;
    constraints.anchor = anchor;
    constraints.gridx = x-1;
    constraints.gridy = y-1;
    constraints.gridwidth = w;
    constraints.gridheight = h;
    
    setConstraints(c,constraints);
  }
  
  
  public void defineOwnConstraints(Component c, int y, int x, int w, int h,
              int weightx, int weighty, int fill, int anchor,int bottomInsets,
              int leftInsets, int rightInsets, int topInsets){
    GridBagConstraints constraints = new GridBagConstraints();
    constraints.insets.bottom = bottomInsets;
    constraints.insets.left = leftInsets;
    constraints.insets.right = rightInsets;
    constraints.insets.top = topInsets;
    constraints.weightx = weightx;
    constraints.weighty = weighty;
    constraints.fill = fill;
    constraints.anchor = anchor;
    constraints.gridx = x-1;
    constraints.gridy = y-1;
    constraints.gridwidth = w;
    constraints.gridheight = h;
    
    setConstraints(c,constraints);
  }
  
  public void Center(Component c, int y, int x, int w, int h){
  	defineOwnConstraints(c,y,x,w,h,1,1,GridBagConstraints.NONE,
                                GridBagConstraints.CENTER,1,1,1,1);
  }
  public void West(Component c,int y, int x, int w, int h){
  	defineOwnConstraints(c,y,x,w,h,1,1,GridBagConstraints.NONE,
                                GridBagConstraints.WEST,1,1,1,1);
  }
  public void WestHorizontal(Component c,int y, int x, int w, int h){
  	defineOwnConstraints(c,y,x,w,h,1,1,GridBagConstraints.HORIZONTAL,
                                GridBagConstraints.WEST,1,1,1,1);
  }
  public void East(Component c,int y, int x, int w, int h){
  	defineOwnConstraints(c,y,x,w,h,1,1,GridBagConstraints.NONE,
  				GridBagConstraints.EAST,1,1,1,1);
  }
  public void CenterBoth(Component c,int y, int x, int w, int h){
  	defineOwnConstraints(c,y,x,w,h,1,1,GridBagConstraints.BOTH,
  				GridBagConstraints.CENTER,1,1,1,1);
  }
  public void CenterVertical(Component c,int y, int x, int w, int h){
  	defineOwnConstraints(c,y,x,w,h,1,1,GridBagConstraints.VERTICAL,
  				GridBagConstraints.CENTER,1,1,1,1);
  }
  public void CenterHorizontal(Component c,int y, int x, int w, int h){
  	defineOwnConstraints(c,y,x,w,h,1,1,GridBagConstraints.HORIZONTAL,
  				GridBagConstraints.CENTER,1,1,1,1);
  }
   
}

