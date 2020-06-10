package sample;

import java.io.*;
import java.util.*;

public class Student extends Person implements Serializable
{
  private int         year;
  private Hashtable   subjects = new Hashtable();
  private static int  count;
  private String      IdNumber;
  private String      course;
  
  public Student(){
    super();
    addCount();
  }
  
  public Student(Person thisPerson){
    super(thisPerson);
    addCount();
  }

  public Student(String first,String last){
    super(first,last);
    addCount();
  }
//creates student object
  
  public Student(String first,String last,int yr,String crs){
    super(first,last);
    year = yr;
    course = crs;
    addCount();
  }
  //Increments automatically everytime a student object has been created
  private void        addCount(){
    count++;
    setIdNumber();
  }
  //Add or enrolls student to Subject
  
  public  void        addSubject(Subject thisSubj){
    subjects.put(thisSubj.getCode(),thisSubj);
  }
  
  //drops student from a subject
  public  void        dropSubject(String thisCode){
    subjects.remove(thisCode);
  }
  //gets total number of student objects created
  public  static int  getCount(){
    return count;
  }
  //gets course that object is currently enrolled to
  public  String      getCourse(){
    return course;
  }
//gets student id number
  public  String      getIdNumber(){
    return IdNumber;

  }
  //Returns hashtable of students enrolled
  public  Hashtable   getSubjects(){
    return subjects;
  }

 //returns student year level
  public  int         getYear(){
    return year;
  }
 //sets a new course for the student
  public  void        setCourse(String course){
    this.course = course;
  }
  //method used in addCount() to generate student ID number  
  private void        setIdNumber(){
    if(count<10)
      IdNumber="0000"+count;
    else if(count>9&&count<100)
      IdNumber="000"+count;
    else if(count>99&&count<1000)
      IdNumber="00"+count;
    else if(count>999&&count<10000)
      IdNumber="0"+count;
    else if(count>9999&&count<100000)
      IdNumber=""+count;
  }
  //Sets initial value of how many Students have been created before
  //newCount is new inital count
  
  public  static void setInitialCount(int newCount){
    count = newCount;
  }
  //Sets new ID number for student created, thisNew is new initialized number

  public  void        setNewId(String thisNew){
    IdNumber = thisNew;
  }
  //sets the Students year level
  public  void        setYear(int year){
    this.year = year;
  }
 //Returns student ID and full name
  public  String      toString(){
    return getIdNumber()+" "+super.toString();
  }
}