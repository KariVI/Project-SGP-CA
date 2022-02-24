
package domain;


public class Goal {
    private int id;
    private String description;
    private Action[] actions;

    public Goal(int id, String description) {
        this.id = id;
        this.description = description;
    }

    public Goal() {}
    
    public Action[] getActions() {
        return actions;
    }

    public void setActions(Action[] actions) {
        this.actions = actions;
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
    
       public boolean equals(Object object){
        boolean value = false;
            if (object instanceof Goal) {
            Goal goalCompare = (Goal) object;
            if( (this.description.equals(goalCompare.getDescription()))&& this.id == goalCompare.getId()) {
                value=true;
            }
        }
        return value;
    }
}
