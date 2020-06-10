package sample;

import java.io.*;
import java.util.*;

public class Semester implements Serializable{
  private int schoolYear;
  private int semNum;
  private String semCode;
  private Hashtable studEnrolled = new Hashtable();
  private Hashtable subjOpen = new Hashtable();
  
  
  //Creates new semester with null school year
  public Semester(){
  	schoolYear = 0;
  	semNum = 1;
  	semCode = "";
  }

  public Semester(int sy, int sem){
    schoolYear = sy;
    semNum = sem;
    setSemCode();
  }
//enrolls and adds student to semester
  public void       enrollStudent(Student thisStudent){
    studEnrolled.put(thisStudent.getIdNumber(),thisStudent);
  }
  //returns school year semester is set
  public int        getSchoolYear(){
    return schoolYear;
  }
  //returns generated semester code
  public String     getSemCode(){
    return semCode;
  }
  //returns semester number
  public int        getSem(){
    return semNum;
  }
  
  //Returns the collection of students currently enrolled in semester.
  public Hashtable  getStudentsEnrolled(){
    return studEnrolled;
  }
  //Returns the collection of subjects currently opened for the semester.
  
  public Hashtable  getSubjectsOpen(){
    return subjOpen;
  }
  //opens new subject in semester
  public void       openSubject(Subject thisSubject){
    subjOpen.put(thisSubject.getCode(),thisSubject);
  }
  
   //Closes and deletes a subject from the semester.
   
  public void       closeSubject(String code){
    subjOpen.remove(code);
  }
 
   //Sets new school year for which the semester is to be classified and automatically
   //sets new semester code for the modified semester
   
  public void       setSchoolYear(int newSy){
    schoolYear=newSy;
    setSemCode();
  }

   //Sets new semester number for the semester and automatically
   //sets new semester code for the modified semester.
  
  public void       setSem(int newSem){
   semNum = newSem;
   setSemCode();
  }
  
   //Sets and generates a new Semester code for the semester based on the school
   //year to which it bellongs and its semester number
   
  public void       setSemCode(){
    String sy = ""+schoolYear;
    String sem = ""+semNum;
    String temp = sy.trim()+"-"+sem.trim();
    semCode = temp.trim();
  }
  
   //Returns the objects details
  public String     toString(){
    return semCode+"\n students enrolled: "+studEnrolled.size()
                  +"\n subjects open: "+subjOpen.size();
  }
  
}