
package domain;

public class Action {
    private int id;
    private String description;
    private String dateEnd;
    private String memberInCharge;
    private String resource;
    private Goal goal;

    public Action(String description, String member, String resource) {
        this.description = description;
        this.memberInCharge = memberInCharge;
        this.resource = resource;
    }

    public Goal getGoal() {
        return goal;
    }

    public void setGoal(Goal goal) {
        this.goal = goal;
    }

    public Action(int id, String description, String dateEnd, String memberInCharge, String resource) {
        this.id = id;
        this.description = description;
        this.dateEnd = dateEnd;
        this.memberInCharge = memberInCharge;
        this.resource = resource;
    }
   
      public Action(String description, String dateEnd, String memberInCharge, String resource) {
        this.description = description;
        this.dateEnd = dateEnd;
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

    public String getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(String dateEnd) {
        this.dateEnd = dateEnd;
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
            if( (this.description.equals(actionCompare.getDescription()))&& this.memberInCharge == actionCompare.getMemberInCharge()
            && this.dateEnd == actionCompare.getDateEnd()) {
                value=true;
            }

        }
        return value;
    }
    
}
