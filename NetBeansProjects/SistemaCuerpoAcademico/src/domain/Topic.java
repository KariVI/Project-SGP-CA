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
public class Topic {
    private String topicName;
    private String startTime;
    private String finishTime;
    private int idTopic;
    private String professionalLicense;
    private int idMeeting;

    public Topic(String topicName, String startTime, String finishTime, String ProfessionalLicense, int idMeeting){
        this.topicName = topicName;
        this.startTime = startTime;
        this.finishTime = finishTime;
        this.professionalLicense = ProfessionalLicense;
        this.idMeeting = idMeeting;
    }
    
    public Topic(int idTopic, String topicName, String startTime, String finishTime, String professionalLicense, int idMeeting){
        this.idTopic = idTopic;
        this.topicName = topicName;
        this.startTime = startTime;
        this.finishTime = finishTime;
        this.idMeeting = idMeeting;
        this.professionalLicense = professionalLicense;
    }
    
    public int getIdTopic(){
        return idTopic;
    }

    public void setIdTopic(int idTopic){
        this.idTopic = idTopic;
    }

    public String getTopicName(){
        return topicName;
    }

    public void setTopicName(String topicName){
        this.topicName = topicName;
    }

    public String getStartTime(){
        return startTime;
    }

    public void setStartTime(String startTime){
        this.startTime = startTime;
    }

    public String getFinishTime(){
        return finishTime;
    }

    public void setFinishTime(String finishTime){
        this.finishTime = finishTime;
    }
    
    public String getProfessionalLicense(){
        return professionalLicense;
    }

    public void setProfessionalLicense(String ProfessionalLicense){
        this.professionalLicense = ProfessionalLicense;
    }
    
    public int getIdMeeting(){
        return idMeeting;
    }
    
    public void setIdMeeting(int idMeeting){
        this.idMeeting = idMeeting;
    }
}
