
package domain;

import java.util.Objects;

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

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + this.key;
        hash = 79 * hash + Objects.hashCode(this.description);
        return hash;
    }
    public boolean equals(Object object){
        return false;
    }

}