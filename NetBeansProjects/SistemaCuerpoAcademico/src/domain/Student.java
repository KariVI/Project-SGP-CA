
package domain;

public class Student {
    private String enrolment;
    private String name;
    
     public String getEnrolmet(){
        return enrolment;
    }
     
    public Student(String enrolment, String name){
        this.enrolment=enrolment;
        this.name=name;

    }
    
    public void setEnrolment(String enrolment){ 
        this.enrolment=enrolment;
    }
    
    public String getName(){
        return name;
    }
    
    public void setName(String name){ 
        this.name=name;
    }
}
