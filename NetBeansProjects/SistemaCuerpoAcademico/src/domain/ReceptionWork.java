
package domain;

import java.util.ArrayList;


public class ReceptionWork {
    
    private int key;
    private String title;
    private String type;
    private String description;
    private String dateStart;
    private String dateEnd;
    private String actualState;
    private PreliminarProject preliminarProject;
    private ArrayList<Student> students;
    private ArrayList<Member> members;
    private ArrayList<LGCA> lgacs;
    
    
    public ReceptionWork(String title, String type, String description, String dateStart, String dateEnd, String actualState){
        this.title=title;
        this.type=type;
        this.description=description;
        this.dateStart=dateStart;
        this.dateEnd=dateEnd;
        this.actualState=actualState;
        students=new ArrayList<Student>();
        members=new ArrayList<Member>();
        lgacs=new ArrayList<LGCA>();
         
    }
    
    public int getKey(){
        return key;
    }
    
    public void setKey(int key){ 
        this.key=key;
    }
    
    public String getTitle(){
        return title;
    }
    
    public void setTitle(String title){ 
        this.title=title;
    }
    
    public String getType(){
        return type;
    }
    
    public void setType(String type){ 
        this.type=type;
    }
    
    public String getDescription(){
        return title;
    }
    
    public void setDescription(String description){ 
        this.description=description;
    }
    
       public String getDateStart(){
        return dateStart;
    }
    
    public void setDateStart(String dateStart){ 
        this.dateStart=dateStart;
    }
    
    public String getDateEnd(){
        return dateEnd;
    }
    
    public void setDateEnd(String dateEnd){ 
        this.dateEnd=dateEnd;
    }
    
    public String getActualState(){
        return actualState;
    }
    
    public void setActualState(String actualState){ 
        this.type=type;
    }
    
    public PreliminarProject getPreliminarProject(){
        return preliminarProject;
    }
    
    public void setPreliminarProject(PreliminarProject preliminarProject){ 
        this.preliminarProject=preliminarProject;
    }
    
     public ArrayList<Member> getMembers(){
        return  members;
    }
    
    public void addMember(Member assistant){ 
         members.add(assistant);
    }
    
     public void setLGAC(LGCA lgac){
        lgacs.add(lgac);
    }
    
    public ArrayList<LGCA> getLGACs(){
        return lgacs;
    }
    
    public void addLGAC(LGCA lgac){ 
         lgacs.add(lgac);
    }
    
    public Student getStudent( String enrollment){
        Student student=null;
        for (int i=0; i<  students.size() ; i++  ){
            Student auxiliar= students.get(i);
            if (auxiliar.getEnrollment().equals(enrollment)){
               student=auxiliar;
            }
        }
        return student;
    }
    
    public ArrayList<Student> getStudents(){
        return  students;
    }
    
    public void addStudent(Student student){ 
         students.add(student);
    }

    
    @Override
    public boolean equals(Object object){
        boolean value=false;
            if (object instanceof ReceptionWork) {
            ReceptionWork receptionWorkCompare = (ReceptionWork) object;
            if( (this.key==receptionWorkCompare.getKey() )
               && ( this.title.equals(receptionWorkCompare.getTitle())) 
               && ( this.dateStart.equals(receptionWorkCompare.getDateStart()) )  
               && (this.dateEnd.equals(receptionWorkCompare.getDateEnd()) )
               &&(this.actualState.equals(receptionWorkCompare.getActualState()) ) 
               && (this.type.equals(receptionWorkCompare.getType()) )) {
                value=true;
            }

        }  
        return value;
    }
}

