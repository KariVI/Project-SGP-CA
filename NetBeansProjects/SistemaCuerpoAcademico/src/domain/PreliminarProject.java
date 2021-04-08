
package domain;

public class PreliminarProject {
      private int key;
    private String title;
    private String description;
    private String dateStart;
    private String dateEnd;

    
    
    public PreliminarProject(String title, String description, String dateStart, String dateEnd){
        this.title=title;
        this.description=description;
        this.dateStart=dateStart;
        this.dateEnd=dateEnd;
         
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
      
}
