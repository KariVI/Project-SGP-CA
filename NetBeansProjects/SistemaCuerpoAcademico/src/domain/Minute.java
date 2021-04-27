package domain;

/**
 *
 * @author Mariana
 */
public class Minute {
    private String note;
    private String state;
    private String due;
    private int idMinute;
    private int idMeeting;

    public Minute(int idMinute, String note, String state, String due, int idMeeting ){
        this.idMinute = idMinute;
        this.note = note;
        this.state = state;
        this.due = due;
        this.idMeeting = idMeeting;
    }
    
    public Minute(String note, String state, String due, int idMeeting ){
        this.note = note;
        this.state = state;
        this.due = due;
        this.idMeeting = idMeeting;
    }
    
    public int getIdMinute(){
        return idMinute;
    }

    public void setIdMinute(int idMinute){
        this.idMinute = idMinute;
    }

    public String getNote(){
        return note;
    }

    public void setNote(String note){
        this.note = note;
    }

    public String getSate(){
        return state;
    }
    
    public void setState(String state){
        this.state = state;
    }
    public String getDue() {
        return due;
    }
    
    public void setDue(String due){
        this.due = due;
    }
    
    public int getIdMeeting(){
        return idMeeting;
    }
    
    public void setIdMeeting(int idMeeting){
        this.idMeeting = idMeeting;
    }
}
