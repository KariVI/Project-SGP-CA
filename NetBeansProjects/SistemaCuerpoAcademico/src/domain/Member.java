package domain;

import java.util.Objects;

public class Member {
    private String professionalLicense;
    private String name;
    private String role;
    private int degreeYear;
    private String degree;
    private String nameDegree;
    private String universityName;
    private String keyGroupAcademic;
    private String state;

  public Member(String professionalLicense, String name, String role, String degree, String nameDegree, String universityName, int degreeYear,String state, String keyGroupAcademic) {
        this.professionalLicense = professionalLicense;
        this.name = name;
        this.role = role;
        this.nameDegree = nameDegree;
        this.degree = degree;
        this.universityName = universityName;
        this.degreeYear = degreeYear;
        this.state = state;
        this.keyGroupAcademic = keyGroupAcademic;
    }

    public Member(String professionalLicense, String name, String role, String degree, String nameDegree, String universityName, int degreeYear,String keyGroupAcademic) {
        this.professionalLicense = professionalLicense;
        this.name = name;
        this.role = role;
        this.nameDegree = nameDegree;
        this.degree = degree;
        this.universityName = universityName;
        this.degreeYear = degreeYear;
        this.keyGroupAcademic = keyGroupAcademic;
    }
    
    public Member(String professionalLicense, String name, String role, String degree, String nameDegree, String universityName, int degreeYear) {
        this.professionalLicense = professionalLicense;
        this.name = name;
        this.role = role;
        this.nameDegree = nameDegree;
        this.degree = degree;
        this.universityName = universityName;
        this.degreeYear = degreeYear;
    }

    public Member(String professionalLicense, String name) {
        this.professionalLicense = professionalLicense;
        this.name = name;
        this.role="";

    }
        public Member(String professionalLicense, String name, String role) {
        this.professionalLicense = professionalLicense;
        this.name = name;
        this.role=role;

    }
    
    public boolean equals(Object object){
        boolean value=false;
            if (object instanceof Member) {
            Member memberCompare = (Member) object;

            if ((this.professionalLicense.equals(memberCompare.getProfessionalLicense())) && (this.name.equals(memberCompare.getName())) &&
            (this.role.equals(memberCompare.getRole())) && (this.degree.equals(memberCompare.getDegree())) && (this.nameDegree.equals(memberCompare.getNameDegree())) &&
            (this.universityName.equals(memberCompare.getUniversityName())) && (this.degreeYear == memberCompare.getDegreeYear())){

                value=true;
            }
        }
      return value;
    }

    @Override
    public int hashCode() {

        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.professionalLicense);
        hash = 67 * hash + Objects.hashCode(this.name);
        hash = 67 * hash + Objects.hashCode(this.role);
        hash = 67 * hash + this.degreeYear;
        hash = 67 * hash + Objects.hashCode(this.degree);
        hash = 67 * hash + Objects.hashCode(this.nameDegree);
        hash = 67 * hash + Objects.hashCode(this.universityName);

        return hash;

       
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

    
    public void setDegree(String degree){
        this.degree = degree;
    }
    
    public String getDegree(){
        return  degree;
    }
    
    public void setNameDegree(String nameDegree){
        this.nameDegree = nameDegree;

    }

    
    public void setProfessionalLicense(String professionalLicense) {
        this.professionalLicense = professionalLicense;
    }
 
    

    public String getNameDegree(){
        return nameDegree;
    }
    
    public void setUniversityName(String universityName){
        this.universityName = universityName;
    }

    
    public String getUniversityName(){
        return universityName;
    }



    public String getProfessionalLicense() {
        return professionalLicense;
    }



    
    public void setState(String state){
        this.state = state;
    }
    
    public String getState(){
        return state;
    }
    
    public void setKeyGroupAcademic(String keyGroupAcademic){
        this.keyGroupAcademic = keyGroupAcademic;
    }
    
    public String getKeyGroupAcademic(){
        return keyGroupAcademic;
    }
    
    public void setDegreeYear(int degreeYear){
        this.degreeYear = degreeYear;
    }
    
    public int getDegreeYear(){
        return degreeYear;
    }
}
