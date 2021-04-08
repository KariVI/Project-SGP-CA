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

    public Minute(String note, String state, String due ){
        this.note = note;
        this.state = state;
        this.due = due;
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
}
