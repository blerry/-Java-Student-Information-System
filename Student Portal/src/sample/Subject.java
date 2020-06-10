package sample;

import java.io.*;

public class Subject implements Serializable{
  private String code;
  private String description;
  private double units;
  private double grade;
   //Creates subject with blank code and description, units of 1, grade of 65
  Subject(){
    code="";
    description="";
    units=1;
    grade=65;

  }
   //Creates a subject with a subject code but no description Also sets number
   //of units to 1, grade of 65

  Subject(String code){
    this.code=code.toUpperCase();
    description="";
    units=1;
    grade=65;
  }
  Subject(Subject newSbj){
  	code = newSbj.getCode();
  	description = newSbj.getDescription();
  	units = newSbj.getUnits();
  	grade = newSbj.getGrade();

  }
  
  //Create ubject with a subject code and description, units of 1, grade of 65
  
  Subject(String code, String subjectDesc){
    this.code=code.toUpperCase();
    description=subjectDesc.toUpperCase();
    units=1;
    grade=65;

  }

//create subject
  Subject(String code, String SD, double grade, double units){
    this.code = code.toUpperCase();
    description = SD.toUpperCase();
    this.grade = grade;
    this.units = units;

  }
  public double getGrade(){
    return grade;
  }
 
  public String getCode(){
    return code;
  }
 
  public String getDescription(){
    return description;
  }
 
  public double getUnits(){
    return units;
  }
 
  public void setGrade(double grade){
    this.grade = grade;
  }
  
   //Sets new subject code for this subject in upper case
   
  public void setCode(String thisCode){
    code = thisCode.toUpperCase();
  }
  
  public void setDescription(String SD){
    description= SD.toUpperCase();
  }

  public void setUnits(double units){
    this.units = units;
  }
 
  public String toString(){
    return code+" - "+description;
  }
}