
package GUI;

import domain.Goal;
import domain.Member;
import domain.WorkPlan;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import log.Log;


public class WorkPlanRegisterController implements Initializable {
    @FXML Button btNext = new Button();
    @FXML Button btReturn = new Button();
    @FXML Button btAdd = new Button();
    @FXML Button btDelete = new Button();
    @FXML private TextArea taObjetive;
    @FXML private TextField tfDescription;
    @FXML private ComboBox cbMonths;
    private ObservableList<Goal> goals;
    private ObservableList<String> months;
    private ObservableList<String> years;
    @FXML private ComboBox cbYears; 
   @FXML private TableView<Goal> tvGoals;
    private ListChangeListener<Goal> tableGoalListener;
    @FXML private TableColumn tcGoal;
    private String keyGroupAcademic;
    private int indexGoal;
   
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tcGoal.setCellValueFactory(new PropertyValueFactory<Goal,String>("description"));
        goals= FXCollections.observableArrayList();
        months=FXCollections.observableArrayList();
        years= FXCollections.observableArrayList();
        months.add("Febrero-Julio");
        months.add("Agosto-Enero");
        getYears(); 
        tvGoals.setItems(goals);
        cbMonths.setItems(months);
        cbYears.setItems(years);
        cbMonths.getSelectionModel().selectFirst();

         tvGoals.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                 setSelectedGoal();
             }
            }
        );

        tableGoalListener = new ListChangeListener<Goal>(){
            @Override
            public void onChanged(ListChangeListener.Change<? extends Goal> goal) {
                setSelectedGoal();
            }
        };
    }
    
    
    
    private Goal getSelectedGoal(){
        Goal goal = null;
        int tamTable = 1;
        if(tvGoals != null){
            List<Goal> goalTable = tvGoals.getSelectionModel().getSelectedItems();
            if(goalTable.size() == tamTable){
                goal = goalTable.get(0);
            }
        }
        return goal;
    }
    
    private void setSelectedGoal(){
        Goal goal = getSelectedGoal();
        indexGoal = goals.indexOf(goal);
        if(goal != null){
            tfDescription.setText(goal.getDescription());
        
        }
    }
    
    private void getYears(){
       Date date=new Date();
        int year=date.getYear()+1900;
        int yearAuxiliar=year;
        
        while(year < (yearAuxiliar + 20)){
            years.add(year +"-" + (year+2));
            year= year +2;
        }
    
    }
    public String getKeyGroupAcademic() {
        return keyGroupAcademic;
    }

    public void setKeyGroupAcademic(String keyGroupAcademic) {
        this.keyGroupAcademic = keyGroupAcademic;
      
    }
   
    @FXML
    private void actionReturn(){
        Stage stage = (Stage) btReturn.getScene().getWindow();
        stage.close();
    }
    
    @FXML 
    private void addGoal(ActionEvent actionEvent){    
        String description="";
        description = tfDescription.getText();      
        Goal goal = new Goal(description);  
        if(validateGoal(goal)){
           goals.add(goal);
        }
        cleanFields();
    }
    
    private void cleanFields(){
        tfDescription.setText("");
    }
    
     private boolean validateGoal(Goal goal){ 
        boolean value=true;
        Validation validation = new Validation();
        AlertMessage alertMessage = new AlertMessage();
            if(goal.getDescription().isEmpty()){  
                value=false;
                alertMessage.showAlertValidateFailed("Campos vacios");
            }
            
            if(validation.findInvalidField(goal.getDescription())){   
                value=false;
               alertMessage.showAlertValidateFailed("Campos invalidos");
            } 
            
            if(repeatedGoal(goal)){ 
               value=false;
               alertMessage.showAlertValidateFailed("Meta repetida");
            }
        return value;
    }
     
      public boolean repeatedGoal(Goal goal){
        Boolean value = false;
        int i = 0;
        while(!value && i<goals.size()){
            if(goals.get(i).equals(goal)){
                value = true;
            }
            i++;
        }
       return value;
    }
    
    @FXML
    private void actionDelete(ActionEvent event){
        goals.remove(indexGoal);
        cleanFields();
    }
    @FXML
    private void actionNext(){
        try {
           
             if(!validateFieldEmpty() && validateInformationField()){
                WorkPlan workPlan = new WorkPlan();
                String objetive= taObjetive.getText();
                workPlan.setObjetiveGeneral(objetive);
                workPlan.setGoals(saveGoals());
                Stage primaryStage= new Stage();
                URL url = new File("src/GUI/ActionRegister.fxml").toURI().toURL();
                FXMLLoader loader = new FXMLLoader(url);
                loader.setLocation(url);
                loader.load();
                Parent root = loader.getRoot();
                Scene scene = new Scene(root);
                primaryStage.setScene(scene);
                Stage stage = (Stage) btNext.getScene().getWindow();
                stage.close();
                primaryStage.show();        
             }
          
        } catch (IOException ex) {
            Log.logException(ex);
        }
    }
    
    private Goal[] saveGoals(){
        Goal[] goalsAuxiliar = new Goal[goals.size()];    
        for(int i=0; i< goals.size(); i++){
            goalsAuxiliar[i] = goals.get(i);
        }    
        return goalsAuxiliar;
    }
    
   
    private boolean validateFieldEmpty(){ 
          boolean value=false;
          if(taObjetive.getText().isEmpty() ){
              value=true;
          }
          return value;
    }
    
    private boolean validateInformationField(){ 
        boolean value=true;
        Validation validation=new Validation();
        if(!validation.findInvalidField(taObjetive.getText())){   
            value=false;
        }  
        return value;
    }
    
   

}
