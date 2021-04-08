package domain;

import java.util.Objects;

public class Member {
    private String professionalLicense;
    private String name;
    private String role;
    private String maxDegreeStudy;
    private String email;

    public Member(String professionalLicense, String name, String role, String maxDegreeStudy, String email) {
        this.professionalLicense = professionalLicense;
        this.name = name;
        this.role = role;
        this.maxDegreeStudy = maxDegreeStudy;
        this.email = email;
    }
    
    @Override
    public boolean equals(Object object){
        boolean value=false;
            if (object instanceof Member) {
            Member memberCompare = (Member) object;
            if( (this.professionalLicense == memberCompare.getProfessionalLicense())&&( this.name.equals(memberCompare.getName())) &&
            (this.role.equals(memberCompare.getRole()) )  && (this.maxDegreeStudy.equals(memberCompare.getMaxDegreeStudy())) &&
            (this.email.equals(memberCompare.getEmail()))){
                value=true;
            }
        }  
        return value;
    } 

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 47 * hash + Objects.hashCode(this.professionalLicense);
        hash = 47 * hash + Objects.hashCode(this.name);
        hash = 47 * hash + Objects.hashCode(this.role);
        hash = 47 * hash + Objects.hashCode(this.maxDegreeStudy);
        hash = 47 * hash + Objects.hashCode(this.email);
        return hash;
    }
    
    public String getProfessionalLicense() {
        return professionalLicense;
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }

    public String getMaxDegreeStudy() {
        return maxDegreeStudy;
    }

    public String getEmail() {
        return email;
    }

    public void setProfessionalLicense(String professionalLicense) {
        this.professionalLicense = professionalLicense;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setMaxDegreeStudy(String maxDegreeStudy) {
        this.maxDegreeStudy = maxDegreeStudy;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
