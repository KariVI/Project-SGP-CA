/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import businessLogic.ActionDAO;
import businessLogic.GoalDao;
import businessLogic.WorkPlanDAO;
import domain.Action;
import domain.Goal;
import domain.Member;
import domain.WorkPlan;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import log.BusinessException;
import log.Log;

/**
 * FXML Controller class
 *
 * @author david
 */
public class WorkPlanModifyController implements Initializable {

    @FXML
    private TableView<Goal> tvGoals;
    @FXML
    private TableColumn<Goal, String> tcGoals;
    @FXML
    private Button btCancel;
    @FXML
    private Button btNext;
    @FXML
    private TextField tfGoal;
    @FXML
    private Button btAdd;
    @FXML
    private Button btDelete;
    
    int idWorkPlan;
    WorkPlan workPlan;
    Goal goalSelected;
    ObservableList<Goal> goalList;
    ObservableList<Goal> newGoals;
    ObservableList<Goal> updateGoals;
    ObservableList<Goal> deleteGoals;
    private Member member;
    @FXML
    private Button btUpdate;
    @FXML
    private ComboBox<String> cbMonths;
    @FXML
    private ComboBox<String> cbYears;
    @FXML
    private TextArea taObjetive;
    
    private ObservableList<String> months;
    private ObservableList<String> years;
    private String currentMonth;
    private String currentYear;
    @FXML
    private Button btFinish;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tcGoals.setCellValueFactory(new PropertyValueFactory<Goal, String>("description")); 
        months=FXCollections.observableArrayList();
        years= FXCollections.observableArrayList();
        months.add("Febrero-Julio");
        months.add("Agosto-Enero");
        getYears(); 
        cbMonths.setItems(months);
        cbYears.setItems(years);
        newGoals = FXCollections.observableArrayList();
        updateGoals = FXCollections.observableArrayList();
        deleteGoals = FXCollections.observableArrayList();
    } 
    
    public void setMember(Member member) {
        this.member = member;
    }
    
    public void setWorkPlanId(int idWorkPlan) {
        this.idWorkPlan = idWorkPlan;
        initializeWorkPlan();
        initializeGoals();
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
    
    private void initializeWorkPlan() {
        WorkPlanDAO workPlanDAO = new WorkPlanDAO();
        try {
            workPlan = workPlanDAO.getWorkPlan(idWorkPlan);
            separatePeriod();
            cbMonths.getSelectionModel().select(currentMonth);
            cbYears.getSelectionModel().select(currentYear);
            taObjetive.setText(workPlan.getObjetiveGeneral());
        } catch (BusinessException ex) {
            Log.logException(ex);
        }
    }
    
    private void separatePeriod(){
        String period = workPlan.getTimePeriod();
        String parts[] = period.split(" ");
        currentMonth = parts[0];
        currentYear = parts[1];
    }
    
    private void initializeGoals() {
        GoalDao goalsDAO = new GoalDao();
        try {
            goalList = FXCollections.observableArrayList(goalsDAO.getGoalsByWorkPlanId(idWorkPlan));
            tvGoals.setItems(goalList);
        } catch (BusinessException ex) {
            Log.logException(ex);
        }

    }

    @FXML
    private void actionCancel(ActionEvent event) {
        Stage stage = (Stage) btCancel.getScene().getWindow();
        stage.close();
        openWorkPlan();
    }
    
    @FXML
    private void actionFinish(ActionEvent event) {
        if(updateWorkPlan()){
            AlertMessage alertMessage = new AlertMessage();
            alertMessage.showAlertSuccesfulSave("El plan de trabajo  ");
            Stage stage = (Stage) btFinish.getScene().getWindow();
            stage.close();
            openMenu();
        }
    }

    @FXML
    private void actionNextWindow(ActionEvent event) {
        if(updateWorkPlan()){
            goalSelected = tvGoals.getSelectionModel().getSelectedItem();
            if(goalSelected != null){
                Stage stage = (Stage) btNext.getScene().getWindow();
                stage.close();
                openWorkPlanActionModify();
            }else{
                AlertMessage alertMessage = new AlertMessage();
                alertMessage.showAlertValidateFailed("Selecciona una meta para modificar sus acciones");
            }          
        }       
    }

    @FXML
    private void actionAddGoal(ActionEvent event) {
        String description = tfGoal.getText();
        Goal goal = new Goal(description);
        if(validateGoal(goal)){
            goalList.add(goal);
            newGoals.add(goal);
            tvGoals.refresh();
            cleanFields();
        }
    }
    
    @FXML
    private void actionUpdateGoal(ActionEvent event) {
        Goal goal = tvGoals.getSelectionModel().getSelectedItem();
        if(goal != null){
           int indexGoal = goalList.indexOf(goal);
           String description = tfGoal.getText();
           Goal newGoal = new Goal(goal.getId(), description);
           if(validateGoal(newGoal)){               
                if(existsInRecentlyAdded(goal)){
                    goalList.set(indexGoal, newGoal);
                    int indexUpdateGoal = newGoals.indexOf(goal);
                    newGoals.set(indexUpdateGoal, newGoal);
                }else{
                    goalList.set(indexGoal, newGoal);
                    System.out.println(newGoal.getId());
                    updateGoals.add(newGoal);
                }
                tvGoals.refresh();
                cleanFields();
           }
        }else{
            AlertMessage alertMessage = new AlertMessage();
            alertMessage.showAlertValidateFailed("Selección de meta faltante");
        }
    }

    @FXML
    private void actionDeleteGoal(ActionEvent event) {
        Goal goal = tvGoals.getSelectionModel().getSelectedItem();
        if(goal != null){
            if(existsInRecentlyAdded(goal)){
               goalList.remove(goal); 
               newGoals.remove(goal);
            }else{
                goalList.remove(goal);
                deleteGoals.add(goal);
            }
            tvGoals.refresh();
            cleanFields();
        }else{
            AlertMessage alertMessage = new AlertMessage();
            alertMessage.showAlertValidateFailed("Selección de meta faltante");
        }
    }
    
    private void openWorkPlan() {        
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("WorkPlanView.fxml"));
            Parent root = loader.load();
            WorkPlanViewController workPlanViewController = loader.getController();
            workPlanViewController.setWorkPlanId(this.idWorkPlan);
            workPlanViewController.setMember(member);
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.showAndWait();
        } catch (IOException ex) {
            Log.logException(ex);
        }       
    }
    
    private void openWorkPlanActionModify(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("WorkPlanActionModify.fxml"));
            Parent root = loader.load();
            WorkPlanActionModifyController workPlanActionModifyController = loader.getController();
            workPlanActionModifyController.setWorkPlanId(this.idWorkPlan);
            workPlanActionModifyController.setMember(member);
            workPlanActionModifyController.setGoalOfActions(goalSelected);
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException ex) {
            Log.logException(ex);
        }       
    }
    
    private void openMenu(){
        Stage primaryStage= new Stage();
            try{
                URL url = new File("src/GUI/Menu.fxml").toURI().toURL();
                FXMLLoader loader = new FXMLLoader(url);
                loader.setLocation(url);
                loader.load();
                MenuController menu = loader.getController();
                menu.initializeMenu(member);
                Parent root = loader.getRoot();
                Scene scene = new Scene(root);
                primaryStage.setScene(scene);
                primaryStage.show();
            }catch (IOException ex) {
                Log.logException(ex);
            }
    }

    @FXML
    private void fillGoalField(MouseEvent event) {
        Goal goal = tvGoals.getSelectionModel().getSelectedItem();
        if(goal != null){
            tfGoal.setText(goal.getDescription());
        }
    }
    
    private void cleanFields(){
        tfGoal.setText("");
    }
    
    private boolean updateWorkPlan(){
        boolean isSaved = false;
        if(!validateFieldEmpty() && validateInformationField()){
            String objetive= taObjetive.getText();
            String timePeriod = cbMonths.getSelectionModel().getSelectedItem() + " " +  cbYears.getSelectionModel().getSelectedItem();
            WorkPlan newWorkPlan = new WorkPlan(idWorkPlan, member.getKeyGroupAcademic(), objetive, timePeriod);
            WorkPlanDAO workPlanDAO = new WorkPlanDAO();
            isSaved = true;
            try {
                workPlanDAO.updateWorkPlan(newWorkPlan);
            } catch (BusinessException ex) {
                Log.logException(ex);
            }
            updateGoals(); 
        }
        return isSaved;
    }
    
    private void updateGoals(){
        GoalDao goalDAO = new GoalDao();
        try{
            for (int i = 0; i < updateGoals.size(); i++){
                goalDAO.updatedGoalById(idWorkPlan, updateGoals.get(i));
                System.out.println(updateGoals.get(i).getId());
            }
            
            for (int i = 0; i < newGoals.size(); i++){
                goalDAO.saveSuccesful(newGoals.get(i), idWorkPlan);
            }
            
            ActionDAO actionDAO = new ActionDAO();
            for (int i = 0; i < deleteGoals.size(); i++){
                ArrayList<Action> actions = actionDAO.getActionsByGoalId(deleteGoals.get(i).getId());
                if(actions != null){
                   actionDAO.deleteAllActionByIdGoal(deleteGoals.get(i).getId());   
                }
                goalDAO.deletedGoalById(deleteGoals.get(i).getId());
            }    
        }catch (BusinessException ex) {
            Log.logException(ex);
         }
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
        while(!value && i<goalList.size()){
            if(goalList.get(i).equals(goal)){
                value = true;
            }
            i++;
        }
       return value;
    }
    
    private boolean existsInRecentlyAdded(Goal goal){
        Boolean value = false;
        int i = 0;
        while(!value && i<newGoals.size()){
            if(newGoals.get(i).equals(goal)){
                value = true;
            }
            i++;
        }
       return value;
    }
    
    private boolean emptyField(String field){
        boolean value = false;
        
        if(field.trim().length()==0){
            value=true;
        }
        return value;
    
    }
    
    private boolean validateFieldEmpty(){ 
          boolean value=false;
          if(taObjetive.getText().isEmpty() ){
              value=true;
          }else if(emptyField(taObjetive.getText())){
              value=true;
          }
          return value;
    }
    
    private boolean validateInformationField(){ 
        boolean value=true;
        Validation validation=new Validation();
        if(validation.findInvalidField(taObjetive.getText())){   
            value=false;
        }  
        return value;
    }
}
