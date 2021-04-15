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
    private int idAgendaTopic;
    private String ProfessionalLicense;

    public Topic(String topicName, String startTime, String finishTime, String ProfessionalLicense){
        this.topicName = topicName;
        this.startTime = startTime;
        this.finishTime = finishTime;
        this.ProfessionalLicense = ProfessionalLicense;
    }
    
    public Topic(int idAgendaTopic, String topicName, String startTime, String finishTime, String ProfessionalLicense){
        this.idAgendaTopic = idAgendaTopic;
        this.topicName = topicName;
        this.startTime = startTime;
        this.finishTime = finishTime;
    }
    
    public int getIdAgendaTopic(){
        return idAgendaTopic;
    }

    public void setIdAgendaTopic(int idAgendaTopic){
        this.idAgendaTopic = idAgendaTopic;
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
        return ProfessionalLicense;
    }

    public void setProfessionalLicense(String ProfessionalLicense){
        this.ProfessionalLicense = ProfessionalLicense;
    }
}
