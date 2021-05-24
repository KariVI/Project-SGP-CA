
package domain;

import java.util.ArrayList;

public class GroupAcademic {
    private String key;
    private String name;
    private String consolidationGrade;
    private String objetive;
    private String mission;
    private String vision;
    private ArrayList<LGAC> lgacs;
    
    
    public GroupAcademic(String key, String name, String objetive, String consolidationGrade, String mission, String vision) {
      this.key= key;
      this.name= name;
      this.objetive=objetive;
      this.consolidationGrade= consolidationGrade;
      this.mission= mission;
      this.vision= vision;
    }

    public GroupAcademic() {
        this.key= "";
      this.name= "";
      this.objetive="";
      this.consolidationGrade= "";
      this.mission= "";
      this.vision= "";
    }
    
    public String getKey(){
        return key;
    }
    
    public void setKey(String key){ 
        this.key=key;
    }
    
    public String getName(){
        return name;
    }
    
    public void setName(String name){ 
        this.name=name;
    }
    
    public String getObjetive(){
        return objetive;
    }
    
    public void setObjetive(String objetive){ 
        this.objetive= objetive;
    }
    
    public String getConsolidationGrade(){
        return consolidationGrade;
    }
    
    public void setConsolidationGrade(String consolidationGrade){ 
        this.consolidationGrade=consolidationGrade;
    }
    
    public String getMission(){
        return mission;
    }
    
    public void setMission(String mission){ 
        this.mission=mission;
    }
    
    public String getVision(){
        return vision;
    }
    
    public void setVision(String vision ){ 
        this.vision=vision;
    }
    

    public ArrayList<LGAC> getLGACs(){

     return lgacs;
    }
    
    public void setLGACs(ArrayList<LGAC> lgacs){ 
        this.lgacs=lgacs;
    }
    
    public void addLGAC(LGAC lgac){ 
        lgacs.add(lgac);
    }
    
    public boolean equals(Object object){
        boolean value=false;
            if (object instanceof GroupAcademic) {
            GroupAcademic groupCompare = (GroupAcademic) object;
            if( (this.name.equals(groupCompare.getName()) ) && (this.key.equals(groupCompare.getKey()) )&&
                    ( this.consolidationGrade.equals(groupCompare.getConsolidationGrade()) ) ) {
                value=true;
            }

        }  
        return value;
    }
    
    @Override
    public String toString(){
        return name;
    }
}
