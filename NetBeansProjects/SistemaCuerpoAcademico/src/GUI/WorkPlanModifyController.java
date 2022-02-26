/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import businessLogic.GoalDao;
import businessLogic.WorkPlanDAO;
import domain.Goal;
import domain.Member;
import domain.WorkPlan;
import java.io.IOException;
import java.net.URL;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
    private TextField tfTimePeriod;
    @FXML
    private TextField tfObjective;
    @FXML
    private Button btDelete;
    
    int idWorkPlan;
    WorkPlan workPlan;
    ObservableList<Goal> goalList;
    ObservableList<Goal> oldGoals;
    private Member member;
    @FXML
    private Button btUpdate;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tcGoals.setCellValueFactory(new PropertyValueFactory<Goal, String>("description")); 
    } 
    
    public void setMember(Member member) {
        this.member = member;
    }
    
    public void setWorkPlanId(int idWorkPlan) {
        this.idWorkPlan = idWorkPlan;
        initializeWorkPlan();
        initializeGoals();
    }
    
    private void initializeWorkPlan() {
        WorkPlanDAO workPlanDAO = new WorkPlanDAO();
        try {
            workPlan = workPlanDAO.getWorkPlan(idWorkPlan);
            tfTimePeriod.setText(workPlan.getTimePeriod());
            tfObjective.setText(workPlan.getObjetiveGeneral());
        } catch (BusinessException ex) {
            Log.logException(ex);
        }
    }
    
    private void initializeGoals() {
        GoalDao goalsDAO = new GoalDao();
        try {
            goalList = FXCollections.observableArrayList(goalsDAO.getGoalsByWorkPlanId(idWorkPlan));
            oldGoals = FXCollections.observableArrayList();
            oldGoals = goalList;
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
    private void actionNextWindow(ActionEvent event) {
        Stage stage = (Stage) btNext.getScene().getWindow();
        stage.close();
        //Llamar a método para guardar lo del plan y las metas
        openWorkPlanActionModify();
    }

    @FXML
    private void actionAddGoal(ActionEvent event) {
        String description = tfGoal.getText();
        Goal goal = new Goal(description);
        //Validar que no sea repetido y caracteres y después guardar en lista de goals
        goalList.add(goal);
        tvGoals.refresh();
        cleanFields();
    }
    
    @FXML
    private void actionUpdateGoal(ActionEvent event) {
        Goal goal = tvGoals.getSelectionModel().getSelectedItem();
        if(goal != null){
           int indexGoal = goalList.indexOf(goal);
           String description = tfGoal.getText();
           //Mandar a validar que no sea repetido y settear
           goal.setDescription(description);
           goalList.set(indexGoal, goal);  
           tvGoals.refresh();
           cleanFields();
        }
    }

    @FXML
    private void actionDeleteGoal(ActionEvent event) {
        Goal goal = tvGoals.getSelectionModel().getSelectedItem();
        if(goal != null){
            goalList.remove(goal);
            tvGoals.refresh();
            cleanFields();
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
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException ex) {
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
    
    private void updateGoals(){
        GoalDao goalDAO = new GoalDao();
        try{
            for (int i = 0; i < oldGoals.size(); i++){
                goalDAO.deletedGoalById(oldGoals.get(i).getId());
            }
            
            for (int i = 0; i < goalList.size(); i++){
                //MANDAR A LLAMAR A MÉTODO PARA GUARDAR GOALS
            }
                      
        }catch (BusinessException ex) {
            Log.logException(ex);
         }
    }
}
