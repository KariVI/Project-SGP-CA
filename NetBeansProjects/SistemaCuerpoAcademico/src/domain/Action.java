
package domain;

public class Action {
    private int id;
    private String description;
    private String dateFinish;
    private String memberInCharge;
    private String resource;

    public Action(int id, String description, String dateFinish, String memberInCharge, String resource) {
        this.id = id;
        this.description = description;
        this.dateFinish = dateFinish;
        this.memberInCharge = memberInCharge;
        this.resource = resource;
    }

    
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDateFinish() {
        return dateFinish;
    }

    public void setDateFinish(String dateFinish) {
        this.dateFinish = dateFinish;
    }

    public String getMemberInCharge() {
        return memberInCharge;
    }

    public void setMemberInCharge(String memberInCharge) {
        this.memberInCharge = memberInCharge;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }
    
    @Override
    public boolean equals(Object object){
        boolean value = false;
            if (object instanceof Action) {
            Action actionCompare = (Action) object;
            if( (this.description.equals(actionCompare.getDescription()))&& this.id == actionCompare.getId()) {
                value=true;
            }

        }
        return value;
    }
    
}
