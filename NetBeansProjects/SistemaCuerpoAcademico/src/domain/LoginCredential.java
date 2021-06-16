package domain;

public class LoginCredential {
    private String user;
    private String password;
    private String professionalLicense;
    
    public LoginCredential(String user, String password, String professionalLicense){
        this.user = user;
        this.password = password;
        this.professionalLicense = professionalLicense;
    }
    
    public LoginCredential(String user, String password ){
       this.user = user;
       this.password = password;
    }
    
    public String getUser(){
        return user;
    }  
    
    public void setUser(String user){
        this.user = user;
    }
     
    public String getPassword(){
        return password;
    }   

    public void setPassword(String password){
        this.password = password;
    }
    
    public String getProfessionalLicense(){
        return professionalLicense;
    }

    public void setProfessionalLicense(String professionalLicense){
        this.professionalLicense = professionalLicense;
    }
    
}
