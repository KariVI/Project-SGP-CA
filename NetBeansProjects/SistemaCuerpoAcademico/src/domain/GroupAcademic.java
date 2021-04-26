
package domain;

public class GroupAcademic {
    private String key;
    private String name;
    private String consolidationGrade;
    private String objetive;
    private String mission;
    private String vision;
    private LGCA lgacs[];
    public static int indexLgacs=0;

    public GroupAcademic(String key, String name, String objetive, String consolidationGrade, String mission, String vision) {
      this.key= key;
      this.name= name;
      this.objetive=objetive;
      this.consolidationGrade= consolidationGrade;
      this.mission= mission;
      this.vision= vision;
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
    
    public LGCA[] getLGAC(){
     return lgacs;
    }
    
    public boolean equals(Object object){
        boolean value=false;
            if (object instanceof GroupAcademic) {
            GroupAcademic groupCompare = (GroupAcademic) object;
            if( (this.name.equals(groupCompare.getName()) ) && (this.key.equals(groupCompare.getKey()) )&& ( this.objetive.equals(groupCompare.getObjetive())) &&
                    ( this.consolidationGrade.equals(groupCompare.getConsolidationGrade()) )  && (this.mission.equals(groupCompare.getMission()) )&&
                    (this.vision.equals(groupCompare.getVision()) ) ) {
                value=true;
            }

        }  
        return value;
    }
}
