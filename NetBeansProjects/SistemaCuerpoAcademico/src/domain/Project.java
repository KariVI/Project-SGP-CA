package domain;

import java.util.ArrayList;
import java.util.Objects;

public class Project {
    private String title;
    private String description;
    private String startDate;
    private String finishDate;
    private String groupAcademicKey;
    private int idProject;
    private ArrayList<Student> students;
    private ArrayList<Member> members;
    private ArrayList<LGAC> lgacs;
    private ArrayList<ReceptionWork> receptionWorks;
    
    public Project(String title, String description, String startDate, String finishDate, String groupAcademicKey){
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.finishDate = finishDate;
        this.groupAcademicKey = groupAcademicKey;
        students = new ArrayList();
        members = new ArrayList();
        lgacs = new ArrayList();
        receptionWorks = new ArrayList();
    }
    
    public Project(int idProject, String title, String description, String startDate, String finishDate, String groupAcademicKey){
        this.idProject = idProject;
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.finishDate = finishDate;
        this.groupAcademicKey = groupAcademicKey;
        students = new ArrayList();
        members = new ArrayList();
        lgacs = new ArrayList();
        receptionWorks = new ArrayList();
    }    
    
    public int getIdProject(){
        return idProject;
    }
    
    public void setIdProject(int idProject){
        this.idProject = idProject;
    }
    
    public String getTitle(){
        return title;
    }
    
    public void setTitle(String title){
        this.title = title;
    }
    
    public String getDescription(){
        return description;
    }
    
    public void setDescription(String description){
        this.description =  description;
    }
    
    public String getStartDate(){
        return startDate;
    }
    
    public void setStartDate(String startDate){
        this.startDate = startDate;
    }
        
    public String getFinishDate(){
        return finishDate;
    }
    
    public void setFinishDate(String finishDate){
        this.finishDate = finishDate;
    }
    
    public ArrayList<Student> getStudents(){
        return students;
    }
    
    public void setStudent(Student student){
        students.add(student);
    }
    
    public ArrayList<Member> getMembers(){
        return members;
    }  

    public void setMember(Member member){
        members.add(member);
    }
    
    public ArrayList<LGAC> getLGACs(){
        return lgacs;
    }
    
    public void setLGAC(LGAC lgac){
        lgacs.add(lgac);
    }
    
    public ArrayList<ReceptionWork> getReceptionWorks(){
        return receptionWorks;
    }
    
    public void setReceptionWork(ReceptionWork receptionWork){
        receptionWorks.add(receptionWork);
    }  
    
    public String getGroupAcademicKey(){
        return groupAcademicKey;
    } 
    
    public void setGroupAcademciKey(String groupAcademicKey){
        this.groupAcademicKey = groupAcademicKey;
    }
    
    @Override
    public boolean equals(Object object){
        boolean value = false;
            if (object instanceof Project) {
            Project projectCompare = (Project) object;
            if( (this.idProject == projectCompare.getIdProject())&& ( this.title.equals(projectCompare.getTitle())) &&
             ( this.startDate.equals(projectCompare.getStartDate()) )  && (this.finishDate.equals(projectCompare.getFinishDate()) )){
                value=true;
            }

        }
        return value;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + Objects.hashCode(this.title);
        hash = 79 * hash + Objects.hashCode(this.description);
        hash = 79 * hash + Objects.hashCode(this.startDate);
        hash = 79 * hash + Objects.hashCode(this.finishDate);
        hash = 79 * hash + this.idProject;
        return hash;
    }
    
    @Override
    public String toString(){
        return title;
    }
    
}
