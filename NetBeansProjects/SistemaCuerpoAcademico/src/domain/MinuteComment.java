/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

/**
 *
 * @author Mariana
 */
public class MinuteComment {
    private String comment;
    private String state;
    private String professionalLicense;
    private int idMinute;
    
    public MinuteComment(String comment, String state, String professionalLicense, int idMinute){
        this.comment = comment;
        this.state = state;
        this.professionalLicense = professionalLicense;
        this.idMinute = idMinute;
    }
    
    public MinuteComment(String comment, String professionalLicense, int idMinute){
        this.comment = comment;
        this.professionalLicense = professionalLicense;
        this.idMinute = idMinute;
    }
    
    public boolean equals(Object object){
        boolean value = false;
            if (object instanceof MinuteComment) {
             MinuteComment minuteComment = (MinuteComment) object;

            if (this.idMinute == minuteComment.getIdMinute() && this.professionalLicense.equals(minuteComment.getProfessionalLicense())){
                value = true;
            }

        }
      return value;
    }
    public void setComment(String comment){
        this.comment = comment;
    }
    
    public String getComment(){
        return comment;
    }
    
    public void setState(String state){
        this.state = state;
    }
    
    public String getState(){
        return state;
    }
    
    public void setProfessionalLicense(String professionalLicense){
        this.professionalLicense = professionalLicense;
    }
    
    public String getProfessionalLicense(){
        return professionalLicense;
    }
    
    public void setIdMinute(int idMinute){
        this.idMinute = idMinute;
    }
    
    public int getIdMinute(){
        return idMinute;
    }
    
}
