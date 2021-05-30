package domain;

import java.sql.Blob;

public class LoginCredential {
    private String user;
    private Blob password;
    private String professionalLicense;
    
    public LoginCredential(String user, Blob password, String professionalLicense){
        this.user = user;
        this.password = password;
        this.professionalLicense = professionalLicense;
    }
    
    
    public LoginCredential(String user, Blob password ){
       this.user = user;
       this.password = password;
    }
    
    public void setUser(String user){
        this.user = user;
    }
    
    public String getUser(){
        return user;
    }
    
    public void setPassword(Blob password){
        this.password = password;
    }
    
    public Blob getPassword(){
        return password;
    }
    
    public void setProfessionalLicense(String professionalLicense){
        this.professionalLicense = professionalLicense;
    }
    
    public String getProfessionalLicense(){
        return professionalLicense;
    }
}
