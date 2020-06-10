package sample;

import java.io.*;

public class Person implements Serializable
{
  private String FirstName;
  private String LastName;
  private static int count;

//Creates a person object with blank first, last and middle names.   
  public Person(){
    FirstName = "";
    LastName = "";
    setCount();
  }
  
  public Person(String fn, String ln){
    FirstName = fn;
    LastName = ln;
    setCount();
  }
  
  //creates new person object from another person
  public Person(Person thisPerson){
    FirstName = thisPerson.getFirstName();
    LastName = thisPerson.getLastName();
  }
  public String getFirstName(){
    return FirstName;
  }

  public String getLastName(){
    return LastName;
  }
//get total person objects created
  public static int getCount(){
    return count;
  }
  
 //set new FirstName
  public void setFirstName(String str){
    FirstName = str;
  }
 //set new last name
  public void setLastName(String str){
    LastName = str;
  }
 //icrement person counter
  public static void setCount(){
    count++;
  }
//return fullname details
  public String toString(){
    return getLastName()+", "+getFirstName();
  }
}