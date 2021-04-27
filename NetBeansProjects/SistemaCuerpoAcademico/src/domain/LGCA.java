
package domain;

public class LGCA {
   private String name;
   private String description;
  
   
    public LGCA(String name, String description){  
        this.name=name;
        this.description=description;
    }
   
    public String getName(){
        return name;
    }
    
    public void setName(String name){ 
        this.name=name;
    }
    
    public String getDescription(){
        return description;
    }
    
    public void setDescription(String description){ 
        this.description=description;
    }
    
    public boolean equals(Object object){
        boolean value=false;
            if (object instanceof LGCA) {
            LGCA lgacCompare = (LGCA) object;
            if( (this.name.equals(lgacCompare.getName()) ) && (this.description.equals(lgacCompare.getDescription())) ) {
                value=true;
            }
        }  
        return value;
    } 
}
