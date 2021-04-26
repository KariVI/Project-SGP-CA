
package domain;

public class Student {
    private String enrollment;
    private String name;
    
     public String getEnrollment(){
        return enrollment;
    }
     
    public Student(String enrollment, String name){
        this.enrollment=enrollment;
        this.name=name;

    }
    
    public void setEnrollment(String enrollment){ 
        this.enrollment=enrollment;
    }
    
    public String getName(){
        return name;
    }
    
    public void setName(String name){ 
        this.name=name;
    }
    
     public boolean equals(Object object){
        boolean value=false;
            if (object instanceof Student) {
            Student studentCompare = (Student) object;
            if( (this.enrollment.equals(studentCompare.getEnrollment()) )
               && ( this.name.equals(studentCompare.getName()) ) ) {
                value=true;
            }

        }  
        return value;
    }
}
