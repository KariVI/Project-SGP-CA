package domain;

/**
 *
 * @author Mariana
 */
public class AgendaTopic {
    private String topicName;
    private String startTime;
    private String finishTime;
    private int idAgendaTopic;
    private String ProfessionalLicense;

    public AgendaTopic(String topicName, String startTime, String finishTime, String ProfessionalLicense){
        this.topicName = topicName;
        this.startTime = startTime;
        this.finishTime = finishTime;
        this.ProfessionalLicense = ProfessionalLicense;
    }
    
    public AgendaTopic(int idAgendaTopic, String topicName, String startTime, String finishTime, String ProfessionalLicense){
        this.idAgendaTopic = idAgendaTopic;
        this.topicName = topicName;
        this.startTime = startTime;
        this.finishTime = finishTime;
    }
    
    public int getIdAgendaTopic(){
        return idAgendaTopic;
    }

    public void setIdAgendaTopic(){
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
