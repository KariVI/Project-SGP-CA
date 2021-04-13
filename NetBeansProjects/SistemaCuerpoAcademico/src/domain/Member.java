package domain;

import java.util.Objects;

public class Member {
    private String professionalLicense;
    private String name;
    private String role;
<<<<<<< HEAD
    private int year;
    private String degree;
    private String nameDegree;
    private String universityName;

    public Member(String professionalLicense, String name, String role ) {
        this.professionalLicense = professionalLicense;
        this.name = name;
        this.role = role;
    }

    public Member(String professionalLicense, String name, String role, String degree, String nameDegree, String universityName) {
        this.professionalLicense = professionalLicense;
        this.name = name;
        this.role = role;
        this.nameDegree = nameDegree;
        this.degree = degree;
        this.universityName = universityName;
=======


    public Member(String professionalLicense, String name, String role) {
        this.professionalLicense = professionalLicense;
        this.name = name;
        this.role = role;
 
    }
       public Member(String professionalLicense, String name) {
        this.professionalLicense = professionalLicense;
        this.name = name;
        this.role="";
>>>>>>> KarinaChanges
    }
    
    @Override
    public boolean equals(Object object){
        boolean value=false;
            if (object instanceof Member) {
            Member memberCompare = (Member) object;
<<<<<<< HEAD
            if( (this.professionalLicense.equals(memberCompare.getProfessionalLicense()) && this.name.equals(memberCompare.getName())) &&
            this.role.equals(memberCompare.getRole())){
            } else {
=======
            if( (this.professionalLicense.equals(memberCompare.getProfessionalLicense()))&&( this.name.equals(memberCompare.getName())) &&
            (this.role.equals(memberCompare.getRole()) )){
>>>>>>> KarinaChanges
                value=true;
            }
        }  
        return value;
    } 

    @Override
    public int hashCode() {
<<<<<<< HEAD
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.professionalLicense);
        hash = 67 * hash + Objects.hashCode(this.name);
        hash = 67 * hash + Objects.hashCode(this.role);
        hash = 67 * hash + this.year;
        hash = 67 * hash + Objects.hashCode(this.degree);
        hash = 67 * hash + Objects.hashCode(this.nameDegree);
        hash = 67 * hash + Objects.hashCode(this.universityName);
=======
        int hash = 5;
        hash = 47 * hash + Objects.hashCode(this.professionalLicense);
        hash = 47 * hash + Objects.hashCode(this.name);
        hash = 47 * hash + Objects.hashCode(this.role);

>>>>>>> KarinaChanges
        return hash;
    }

    public void setProfessionalLicense(String professionalLicense) {
        this.professionalLicense = professionalLicense;
    }
    
    public String getProfessionalLicense() {
        return professionalLicense;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }

    public void setRole(String role) {
        this.role = role;
    }
    
    public String getRole() {
        return role;
    }
<<<<<<< HEAD
    
    public void setDegree(String degree){
        this.degree = degree;
    }
    
    public String getDegree(){
        return  degree;
    }
    
    public void setNameDegree(String nameDegree){
        this.nameDegree = nameDegree;
=======

    public void setProfessionalLicense(String professionalLicense) {
        this.professionalLicense = professionalLicense;
>>>>>>> KarinaChanges
    }
    
    public String getNameDegree(){
        return nameDegree;
    }
    
    public void setUniversityName(String universityName){
        this.universityName = universityName;
    }
<<<<<<< HEAD
    
    public String getUniversityName(){
        return universityName;
    }
=======

>>>>>>> KarinaChanges

}
