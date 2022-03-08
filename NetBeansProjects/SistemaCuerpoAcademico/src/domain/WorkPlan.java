package domain;

public class WorkPlan {

    private int id;
    private String groupAcademicKey;
    private String objetiveGeneral;
    private String timePeriod;
    private Goal[] goals;

    public WorkPlan(String objetiveGeneral,String groupAcademicKey, String timePeriod, Goal[] goals) {
        this.objetiveGeneral = objetiveGeneral;
        this.timePeriod = timePeriod;
        this.goals = goals;
        this.groupAcademicKey = groupAcademicKey;
    }

    public WorkPlan(int id, String groupAcademicKey, String objetiveGeneral, String timePeriod) {
       this.id = id;
       this.objetiveGeneral = objetiveGeneral;
       this.timePeriod = timePeriod;
       this.groupAcademicKey = groupAcademicKey;
    }

    public WorkPlan() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public void setGroupAcademicKey(String key) {
        this.groupAcademicKey = key;
    }

    public String getObjetiveGeneral() {
        return objetiveGeneral;
    }

    public void setObjetiveGeneral(String objetiveGeneral) {
        this.objetiveGeneral = objetiveGeneral;
    }

    public String getTimePeriod() {
        return timePeriod;
    }

    public void setTimePeriod(String timePeriod) {
        this.timePeriod = timePeriod;
    }

    public Goal[] getGoals() {
        return goals;
    }
    
    public String getGroupAcademicKey(){
        return groupAcademicKey;
    }

    public void setGoals(Goal[] goals) {
        this.goals = goals;
    }

    public boolean equals(Object object) {
        boolean value = false;
        if (object instanceof WorkPlan) {
            WorkPlan workPlan = (WorkPlan) object;
            if ((this.timePeriod.equals(workPlan.getTimePeriod()))
                    && this.id == workPlan.getId()) {
                value = true;
            }

        }
        return value;
    }
    
    @Override
      public String toString(){
          return timePeriod;
      }
}
