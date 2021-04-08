
package domain;


public class ReceptionWork {
    
    private int key;
    private String title;
    private String type;
    private String description;
    private String dateStart;
    private String dateEnd;
    private String actualState;
    
    
    public ReceptionWork(String title, String type, String description, String dateStart, String dateEnd){
        this.title=title;
        this.type=type;
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
    
    
}

