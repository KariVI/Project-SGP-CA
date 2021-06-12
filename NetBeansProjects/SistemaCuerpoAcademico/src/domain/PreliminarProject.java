
package domain;

import java.util.ArrayList;
import java.util.Objects;

public class PreliminarProject {
    private int key;
    private String title;
    private String description;
    private String dateStart;
    private String dateEnd;
    private ArrayList<Student> students;
    private ArrayList<Member> members;
    private ArrayList<LGAC> lgacs;
    private String keyGroupAcademic;

    public String getKeyGroupAcademic() {
        return keyGroupAcademic;
    }

    public void setKeyGroupAcademic(String keyGroupAcademic) {
        this.keyGroupAcademic = keyGroupAcademic;
    }

    
    public PreliminarProject(String title, String description, String dateStart, String dateEnd, String keyGroupAcademic){
        this.title=title;
        this.description=description;
        this.dateStart=dateStart;
        this.dateEnd=dateEnd;
        students=new ArrayList<Student>();
        members=new ArrayList<Member>();
        lgacs=new ArrayList<LGAC>();
        this.keyGroupAcademic = keyGroupAcademic;
        
         
    }

    public PreliminarProject() {
        this.title="";
        this.description="";
        this.dateStart="";
        this.dateEnd="";
        students=new ArrayList<Student>();
        members=new ArrayList<Member>();
        lgacs=new ArrayList<LGAC>();
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
    
   
    
    public Member getMember( String licenseProfessional){
        Member assistant=null;
        for (int i=0; i<  members.size() ; i++  ){
            Member auxiliar= members.get(i);
            if (auxiliar.getProfessionalLicense().equals(licenseProfessional)){
               assistant=auxiliar;
            }
        }
        return assistant;
    }
    
    public ArrayList<Member> getMembers(){
        return  members;
    }
    
    public void setMembers(ArrayList<Member> members){  
        this.members=members;
    }
    
     public void setStudents(ArrayList<Student> students){  
        this.students=students;
    }
    public void addMember(Member member){ 
         members.add(member);
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
         
    public void setLGAC(LGAC lgac){
        lgacs.add(lgac);
    }
    
    public ArrayList<LGAC> getLGACs(){
        return lgacs;
    }
    
    public void addLGAC(LGAC lgac){ 
         lgacs.add(lgac);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + this.key;
        hash = 71 * hash + Objects.hashCode(this.title);
        hash = 71 * hash + Objects.hashCode(this.dateStart);
        hash = 71 * hash + Objects.hashCode(this.dateEnd);
        return hash;
    }
    
     public boolean equals(Object object){
        boolean value=false;
            if (object instanceof PreliminarProject) {
            PreliminarProject preliminarProjectCompare = (PreliminarProject) object;
            if( (this.key==preliminarProjectCompare.getKey() )
               && ( this.title.equals(preliminarProjectCompare.getTitle())) 
               && ( this.dateStart.equals(preliminarProjectCompare.getDateStart()) )  
               && (this.dateEnd.equals(preliminarProjectCompare.getDateEnd()) )) {
                value=true;
            }

        }  
        return value;
    }
     
    public String toString(){
        return title;
    }
}
