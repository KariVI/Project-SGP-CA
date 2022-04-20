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
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.converter.LocalDateStringConverter;
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
    private Button btSave;
    @FXML
    private TextField tfAction;
    @FXML
    private TextField tfResponsable;
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
    private DatePicker dpDateEnd;
    @FXML
    private Button btCancel;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tcAction.setCellValueFactory(new PropertyValueFactory<Action,String>("description"));
        tcDate.setCellValueFactory(new PropertyValueFactory<Action,String>("dateEnd"));
        tcResponsable.setCellValueFactory(new PropertyValueFactory<Action,String>("memberInCharge"));
        tcResource.setCellValueFactory(new PropertyValueFactory<Action,String>("resource"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        dpDateEnd.setConverter(new LocalDateStringConverter(formatter, null));
    }    
    
    public void setMember(Member member) {
        this.member = member;
    }
    
    public void setWorkPlanId(int idWorkPlan) {
        this.idWorkPlan = idWorkPlan;       
    }   
    
    public void setGoalOfActions(Goal goal){
        try {
            GoalDao goalDAO = new GoalDao();
            idGoalSelected = goalDAO.getId(goal, idWorkPlan);
            initializeActions();
        } catch (BusinessException ex) {
            Log.logException(ex);
        }
    }
     
    private void initializeActions() {
        ActionDAO actionDAO = new ActionDAO();
        try{
            actions = FXCollections.observableArrayList(actionDAO.getActionsByGoalId(idGoalSelected));  
            oldActions = FXCollections.observableArrayList(actionDAO.getActionsByGoalId(idGoalSelected));  
            if(actions != null){
                tvActions.setItems(actions);   
            }
        }catch(BusinessException ex){
            Log.logException(ex);
        }
    }

    @FXML
    private void actionSave(ActionEvent event) {
        ActionDAO actionDAO = new ActionDAO();
        try{
            if(oldActions != null){
                for (int i = 0; i < oldActions.size(); i++){
                    actionDAO.deletedActionById(oldActions.get(i).getId());
                }
            }
            
            if(actions != null){
                for (int i = 0; i < actions.size(); i++){
                    actionDAO.saveSuccesful(actions.get(i), idGoalSelected);
                }     
            } 
            AlertMessage alertMessage = new AlertMessage();
            alertMessage.showAlertSuccesfulSave("Las acciones  ");
            openWorkPlanModify();
        }catch (BusinessException ex) {
            Log.logException(ex);
        }
        
    }

    @FXML
    private void actionAddAction(ActionEvent event) {       
        String description = tfAction.getText();
        String responsable = tfResponsable.getText();
        String resource = tfResource.getText();
        Action action = new Action(description, responsable,resource);
        if(validateAction(action)){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String date= dpDateEnd.getValue().format(formatter);
            action.setDateEnd(date);
            actions.add(action);
            tvActions.refresh();
            cleanFields();
        }
    }

    @FXML
    private void actionDeleteAction(ActionEvent event) {
        Action action = tvActions.getSelectionModel().getSelectedItem();
        if(action != null){
            actions.remove(action);
            tvActions.refresh();
            cleanFields();
        }else{
            AlertMessage alertMessage = new AlertMessage();
            alertMessage.showAlertValidateFailed("Selección de acción faltante");
        }
    }
    
    @FXML
    private void actionUpdateAction(ActionEvent event) {
        Action action = tvActions.getSelectionModel().getSelectedItem();
        if(action != null){
            int indexAction = actions.indexOf(action);
            String description = tfAction.getText();
            String responsable = tfResponsable.getText();
            String resource = tfResource.getText();
            Action newAction = new Action(description, responsable, resource);
            if(validateAction(newAction)){
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                String date= dpDateEnd.getValue().format(formatter);
                newAction.setDateEnd(date);
                actions.set(indexAction, newAction);
                tvActions.refresh();
                cleanFields();
            }
        }else{
            AlertMessage alertMessage = new AlertMessage();
            alertMessage.showAlertValidateFailed("Selección de acción faltante");
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
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate date = LocalDate.parse(action.getDateEnd(), formatter);
            dpDateEnd.setValue(date);
            tfResource.setText(action.getResource());
        }
    }
    
    private void cleanFields(){
        tfAction.setText("");
        tfResponsable.setText("");
        dpDateEnd.setValue(null);
        tfResource.setText("");
    }
    
    @FXML
    private void actionCancel(ActionEvent event) {  
        Stage stage = (Stage) btCancel.getScene().getWindow();
        stage.close();
        openWorkPlanModify();
    }
    
    private boolean validateAction(Action action){ 
        boolean value=true;
        Validation validation = new Validation();
        AlertMessage alertMessage = new AlertMessage();
            if(action.getDescription().isEmpty() ||  action.getMemberInCharge().isEmpty() || action.getResource().isEmpty() || dpDateEnd.getValue()==null ){  
                value=false;
                alertMessage.showAlertValidateFailed("Campos vacios");
            } else if (validation.findInvalidField(action.getDescription())
              || validation.findInvalidField(action.getResource())
              || validation.findInvalidField(action.getMemberInCharge())){   
                value=false;
               alertMessage.showAlertValidateFailed("Campos invalidos");
            } 
            
            if(repeatedAction(action)){ 
               value=false;
               alertMessage.showAlertValidateFailed("Acción repetida");
            }
        return value;
    }
    
    public boolean repeatedAction(Action action){
        Boolean value = false;
        int i = 0;
        while(!value && i<actions.size()){
            if(actions.get(i).equals(action)){
                value = true;
            }
            i++;
        }
       return value;
    }
}
