
package domain;

import java.util.Objects;

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

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.enrollment);
        hash = 67 * hash + Objects.hashCode(this.name);
        return hash;
    }
    
     public boolean equals(Object object){
        boolean value=false;
            if (object instanceof Student) {
            Student studentCompare = (Student) object;
            if( (this.enrollment.equals(studentCompare.getEnrollment()) )
                ) {
                value=true;
            }

        }  
        return value;
    }
}
