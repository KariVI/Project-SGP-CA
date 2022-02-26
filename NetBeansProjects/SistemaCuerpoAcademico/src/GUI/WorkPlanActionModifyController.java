/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import businessLogic.ActionDAO;
import businessLogic.GoalDao;
import domain.Action;
import domain.Goal;
import domain.Member;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import log.BusinessException;
import log.Log;

/**
 * FXML Controller class
 *
 * @author david
 */
public class WorkPlanActionModifyController implements Initializable {

    @FXML
    private TableView<Action> tvActions;
    @FXML
    private TableColumn<Action, String> tcAction;
    @FXML
    private TableColumn<Action, String> tcDate;
    @FXML
    private TableColumn<Action, String> tcResponsable;
    @FXML
    private TableColumn<Action, String> tcResource;
    @FXML
    private Button btReturn;
    @FXML
    private Button btSave;
    @FXML
    private ComboBox<Goal> cbGoals;
    @FXML
    private TextField tfAction;
    @FXML
    private TextField tfResponsable;
    @FXML
    private TextField tfDate;
    @FXML
    private TextField tfResource;
    @FXML
    private Button btAdd;
    @FXML
    private Button btDelete;
    
    int idWorkPlan;
    int idGoalSelected;
    private Member member;
    ObservableList<Goal> goalList;
    private ObservableList<Action> actions;
    private ObservableList<Action> oldActions;
    @FXML
    private Button btUpdate;
    @FXML
    private Button btFinish;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tcAction.setCellValueFactory(new PropertyValueFactory<Action,String>("description"));
        tcDate.setCellValueFactory(new PropertyValueFactory<Action,String>("dateFinish"));
        tcResponsable.setCellValueFactory(new PropertyValueFactory<Action,String>("memberInCharge"));
        tcResource.setCellValueFactory(new PropertyValueFactory<Action,String>("resource"));
        cbGoals.setOnAction(e -> {
            clicOnGoal();    
        });
    }    
    
    public void setMember(Member member) {
        this.member = member;
    }
    
    public void setWorkPlanId(int idWorkPlan) {
        this.idWorkPlan = idWorkPlan;
        initializeGoals();
    }   
    
    private void initializeGoals(){
        GoalDao goalsDAO = new GoalDao();
        try{
            goalList = FXCollections.observableArrayList(goalsDAO.getGoalsByWorkPlanId(idWorkPlan));
            cbGoals.setItems(goalList);
        }catch(BusinessException ex){
            Log.logException(ex);
        }
    }
    
    private void initializeActions() {
        ActionDAO actionDAO = new ActionDAO();
        try{
            actions = FXCollections.observableArrayList(actionDAO.getActionsByGoalId(idGoalSelected));
            oldActions = FXCollections.observableArrayList();
            oldActions = actions;
            tvActions.setItems(actions);
        }catch(BusinessException ex){
            Log.logException(ex);
        }
    }
    
    private void clicOnGoal() {
        idGoalSelected = cbGoals.getSelectionModel().getSelectedItem().getId();
        initializeActions();
    }

    @FXML
    private void actionReturn(ActionEvent event) {
        Stage stage = (Stage) btReturn.getScene().getWindow();
        stage.close();
        openWorkPlanModify();
    }

    @FXML
    private void actionSave(ActionEvent event) {
        ActionDAO actionDAO = new ActionDAO();
        try{
            for (int i = 0; i < oldActions.size(); i++){
                actionDAO.deletedActionById(oldActions.get(i).getId());
            }
            
            for (int i = 0; i < actions.size(); i++){
                //MANDAR A LLAMAR A MÉTODO PARA GUARDAR ACCIONES DE LA META
            }
            
        }catch (BusinessException ex) {
            Log.logException(ex);
        }
    }

    @FXML
    private void actionAddAction(ActionEvent event) {
        String description = tfAction.getText();
        String responsable = tfResponsable.getText();
        String date = tfDate.getText();
        String resource = tfResource.getText();
        Action action = new Action(description, responsable, date, resource);
        //MANDAR A VALIDAR
        actions.add(action);
        tvActions.refresh();
        cleanFields();
    }

    @FXML
    private void actionDeleteAction(ActionEvent event) {
        Action action = tvActions.getSelectionModel().getSelectedItem();
        if(action != null){
            actions.remove(action);
            tvActions.refresh();
            cleanFields();
        }
    }
    
    @FXML
    private void actionUpdateAction(ActionEvent event) {
        Action action = tvActions.getSelectionModel().getSelectedItem();
        if(action != null){
            int indexAction = actions.indexOf(action);
            String description = tfAction.getText();
            String responsable = tfResponsable.getText();
            String date = tfDate.getText();
            String resource = tfResource.getText();
             //Mandar a validar que no sea repetido y settear
            action.setDescription(description);
            action.setMemberInCharge(responsable);
            action.setDateFinish(date);
            action.setResource(resource);
            actions.set(indexAction, action);
            tvActions.refresh();
            cleanFields();
        }
    }

    
    private void openWorkPlanModify(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("WorkPlanModify.fxml"));
            Parent root = loader.load();
            WorkPlanModifyController workPlanModifyController = loader.getController();
            workPlanModifyController.setWorkPlanId(idWorkPlan);
            workPlanModifyController.setMember(member);
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.showAndWait();
        } catch (IOException ex) {
            Log.logException(ex);
        }
    }

    @FXML
    private void fillActionFields(MouseEvent event) {
        Action action = tvActions.getSelectionModel().getSelectedItem();
        if (action != null){
            tfAction.setText(action.getDescription());
            tfResponsable.setText(action.getMemberInCharge());
            tfDate.setText(action.getDateFinish());
            tfResource.setText(action.getResource());
        }
    }
    
    private void cleanFields(){
        tfAction.setText("");
        tfResponsable.setText("");
        tfDate.setText("");
        tfResource.setText("");
    }

    @FXML
    private void actionFinish(ActionEvent event) {
    }
}
