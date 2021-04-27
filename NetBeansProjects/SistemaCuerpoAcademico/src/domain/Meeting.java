
package domain;

import java.util.ArrayList;
import java.util.Objects;

public class Meeting {
    private int key;
    private String subject;
    private String date;
    private String hourStart;
    private String state;
    private ArrayList<Prerequisite> listPrerequisites;
    public ArrayList<Member> assistants;
   
    
    public Meeting (String subject, String date, String hourStart){
      this.subject= subject;
      this.date= date;
      this.hourStart= hourStart;
    }
    public Meeting (int key, String subject, String date, String hourStart, String state){
      this.key=key;
      this.subject= subject;
      this.date= date;
      this.hourStart= hourStart;
      this.state=state;
      listPrerequisites= new ArrayList<Prerequisite> ();
      
    }

    
     public int getKey(){
        return key;
    }
    
    public void setKey(int key){ 
        this.key=key;
    }
    
    public String getSubject(){
        return subject;
    }
    
    public void setSubject(String subject){ 
        this.subject=subject;
    }
    
    public String getDate(){
        return date;
    }
    
    public void setDate(String date){ 
        this.date=date;
    }
    
    public String getHourStart(){
        return hourStart;
    }
    
     public String getState(){
        return state;
    }
    
    public void setState(String state){ 
        this.state=state;
    }
    
    public void setHourStart(String hourStart){ 
        this.hourStart=hourStart;
    }
    
    public Prerequisite getPrerequisite( int index){
        return listPrerequisites.get(index);
    }
    public ArrayList<Prerequisite> getPrerequisites(){
        return listPrerequisites;
    }
    public void addPrerequiste(Prerequisite prerequisite){ 
        listPrerequisites.add(prerequisite);
    }
    
    public Member getAssistant( String licenseProfessional){
        Member assistant=null;
        for (int i=0; i< assistants.size() ; i++  ){
            Member auxiliar=assistants.get(i);
            if (auxiliar.getProfessionalLicense().equals(licenseProfessional)){
               assistant=auxiliar;
            }
        }
        return assistant;
    }
    public ArrayList<Member> getAssistant(){
        return assistants;
    }
    public void addAssistant(Member assistant){ 
        assistants.add(assistant);
    }



    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + this.key;
        hash = 89 * hash + Objects.hashCode(this.subject);
        hash = 89 * hash + Objects.hashCode(this.date);
        hash = 89 * hash + Objects.hashCode(this.hourStart);
        hash = 89 * hash + Objects.hashCode(this.state);
        return hash;
    }
    
    public boolean equals(Object object){
        boolean value=false;
            if (object instanceof Meeting) {
            Meeting meetingCompare = (Meeting) object;
            if( (this.key==meetingCompare.getKey() )&& ( this.subject.equals(meetingCompare.getSubject())) &&
             ( this.hourStart.equals(meetingCompare.getHourStart()) )  && (this.date.equals(meetingCompare.getDate()) )&&
             (this.state.equals(meetingCompare.getState()) ) ) {
                value=true;
            }

        }  
        return value;
    }
    
}
