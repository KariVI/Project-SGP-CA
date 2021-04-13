
package domain;

public class Prerequisite {
    private int key;
    private String description;
    public Member mandated;

    
    
    public Prerequisite(String description){
        this.description=description;
    }
    
    public int getKey(){
        return key;
    }
    
    public void setKey(int key){ 
        this.key=key;
    }
    
    public String getDescription(){
        return description;
    }
    
    public void setDescription(String description){ 
        this.description=description;
    }
    
    public Member getMandated(){
        return mandated;
    }
    
    public void setMandated(Member mandated){ 
        this.mandated=mandated;
    }
    public boolean equals(Object object){
        return false;
    }

    public Member getMandated() {
        return this.mandated;
    }
    
    public void setMandated(Member mandated){
        this.mandated=mandated;
        
    }
    

}