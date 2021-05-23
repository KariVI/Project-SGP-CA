
package domain;

public class LGAC {
   private String name;
   private String description;
  
   
    public LGAC(String name, String description){  
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
            if (object instanceof LGAC) {
            LGAC lgacCompare = (LGAC) object;
            if( (this.name.equals(lgacCompare.getName()) )  ) {
                value=true;
            }
        }  
        return value;
    } 
}
