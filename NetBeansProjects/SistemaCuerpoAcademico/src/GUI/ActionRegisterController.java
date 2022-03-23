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
import domain.Action;
import domain.Goal;
import domain.Member;
import domain.WorkPlan;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.converter.LocalDateStringConverter;
import log.BusinessException;
import log.Log;

/**
 *
 * @author kari
 */
public class ActionRegisterController implements Initializable {

    
    private WorkPlan workPlan;
    @FXML private ComboBox cbGoals;
    private ObservableList<Goal> goals;
     private ObservableList<Action> actions;
    @FXML private TextField tfAction;
    @FXML private TextField tfMemberInCharge;
     @FXML private TextField tfResource;
    @FXML Button btAdd;
    @FXML Button btDelete;
    @FXML Button btSave;
    @FXML Button btReturn;
    @FXML private TableView<Action> tvActions;
    private ListChangeListener<Action> tableActionListener;
    @FXML private TableColumn tcGoal;
    @FXML private TableColumn tcAction;
    @FXML private TableColumn tcMember;
    @FXML private TableColumn tcDateEnd;
    @FXML private TableColumn tcResource;
    @FXML private DatePicker dpDateEnd  = new DatePicker();;
    private String keyGroupAcademic;
    private WorkPlanRegisterController workPlanController;
    private Member member;

    public void setMember(Member member) {
        this.member = member;
    }

    public void setWorkPlanController(WorkPlanRegisterController workPlanController) {
        this.workPlanController = workPlanController;
    }
    
    private int indexAction;
    private Action lastAction;
    
    public void setWorkPlan(WorkPlan workPlan) {
        this.workPlan = workPlan;
        initializeGoals(); 
    }
    
    
    private void initializeGoals(){
        Goal[] auxiliar = this.workPlan.getGoals();
    
        for(int i=0; i<auxiliar.length; i++){
            goals.add(auxiliar[i]);
        }
        cbGoals.setItems(goals);
        cbGoals.getSelectionModel().selectFirst();
    }
    
    @FXML
    private void delete(ActionEvent event){
        actions.remove(lastAction);
        cleanFields();
    }
    
    
    
    @FXML
    private void addAction(ActionEvent actionEvent){    
        String description="";
        description = tfAction.getText(); 
        String member = tfMemberInCharge.getText();
        String resource = tfResource.getText();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        
        
        Action action = new Action(description, member, resource); 
         Goal goal = (Goal) cbGoals.getSelectionModel().getSelectedItem();
         action.setGoal(goal);
        if(validateAction(action)){
            String date= dpDateEnd.getValue().format(formatter);
            action.setDateEnd(date);
           actions.add(action);
           lastAction=action;
        }
        cleanFields();
    }
    

    private void cleanFields(){
        tfAction.setText(""); 
        tfMemberInCharge.setText("");
        tfResource.setText("");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        
    }
    
    private boolean validateAction(Action action){ 
        boolean value=true;
        Validation validation = new Validation();
        AlertMessage alertMessage = new AlertMessage();
            if(action.getDescription().isEmpty() 
              ||  action.getMemberInCharge().isEmpty() || action.getResource().isEmpty()  || searchEmptyFields() ){  
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
     
    private boolean searchEmptyFields(){
    
        return emptyField(tfResource.getText()) || emptyField(tfMemberInCharge.getText()) || emptyField(tfAction.getText());
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
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        goals= FXCollections.observableArrayList();
        actions =FXCollections.observableArrayList();
        tvActions.setItems(actions);
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        dpDateEnd.setConverter(new LocalDateStringConverter(formatter, null));
        dpDateEnd.setValue(LocalDate.now());
        tcGoal.setCellValueFactory(new PropertyValueFactory<Action,Goal>("goal"));
        tcAction.setCellValueFactory(new PropertyValueFactory<Action,String>("description"));
        tcDateEnd.setCellValueFactory(new PropertyValueFactory<Action,String>("dateEnd"));
        tcMember.setCellValueFactory(new PropertyValueFactory<Action,String>("memberInCharge"));
        tcResource.setCellValueFactory(new PropertyValueFactory<Action,String>("resource"));
        tvActions.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                 setSelectedAction();
             }
            }
        );

        tableActionListener = new ListChangeListener<Action>(){
            @Override
            public void onChanged(ListChangeListener.Change<? extends Action> action) {
                setSelectedAction();
            }
        };
    }
    
    
    private boolean optionSave(){
     boolean value = false;
     if(workPlan.getGoals().length>0){
        value = saveGoals(); 
     }else{
         value= savePlan();
     }
     return value;
    }
    
    private Action getSelectedAction(){
        Action action= null;
        int tamTable = 1;
        if(tvActions != null){
            List<Action> goalTable = tvActions.getSelectionModel().getSelectedItems();
            if(goalTable.size() == tamTable){
                action= goalTable.get(0);
            }
        }
        return action;
    }
    
    
    @FXML 
    private void saveWorkPlan(){
        boolean saveSucessful = false;
        AlertMessage alertMessage = new AlertMessage();
        if(actions.size()>0){
            saveSucessful= saveActions();
        }else{  
            saveSucessful= optionSave();
        }
        if(saveSucessful){               
            alertMessage.showAlertSuccesfulSave("El Plan de Trabajo ");
            openWorkPlanList();
         }else{
           alertMessage.showAlertValidateFailed("Error en la base de datos");
        }
    
    }
    
    @FXML 
    private void returnWorkPlan(){    
        
        try{ 

              Stage primaryStage= new Stage();
              URL url = new File("src/GUI/WorkPlanRegister.fxml").toURI().toURL();
             try{
                FXMLLoader loader = new FXMLLoader(url);
                loader.setLocation(url);
                loader.load();
                WorkPlanRegisterController workPlanRegisterController =loader.getController();  
                workPlanRegisterController.setWorkPlan(workPlan) ;
                workPlanRegisterController.setMember(member);
                Parent root = loader.getRoot();
                Scene scene = new Scene(root);
                primaryStage.setScene(scene);   
                Stage stage = (Stage) btReturn.getScene().getWindow();
                stage.close();
              } catch (IOException ex) {
                      Log.logException(ex);
              }
              primaryStage.show();
         } catch (MalformedURLException ex) {
             Log.logException(ex);
         } 
    
    }
    
      private void openWorkPlanList(){ 
         try{ 
            Stage primaryStage= new Stage();
            URL url = new File("src/GUI/WorkPlanList.fxml").toURI().toURL();
           try{
              FXMLLoader loader = new FXMLLoader(url);
              loader.setLocation(url);
              loader.load();
              WorkPlanListController workPlanListController =loader.getController();   
              workPlanListController.setMember(member);
              Parent root = loader.getRoot();
              Scene scene = new Scene(root);        
              primaryStage.setScene(scene);
              Stage stage = (Stage) btSave.getScene().getWindow();
              stage.close();
              primaryStage.show();      
            } catch (IOException ex) {
                    Log.logException(ex);
            }
            primaryStage.show();
       } catch (MalformedURLException ex) {
           Log.logException(ex);
       } 
    }
      
    private boolean savePlan(){
        boolean value = false;
        WorkPlanDAO workPlanDAO = new WorkPlanDAO();   
        
        try {
            value= workPlanDAO.saveSuccesful(workPlan);
        } catch (BusinessException ex) {
            Log.logException(ex);
        }
         return value;
    }
    
    private boolean saveGoals(){    
        boolean value = false;
        GoalDao goalDAO = new GoalDao();
        WorkPlanDAO workPlanDAO = new WorkPlanDAO();
        
        if(savePlan()){ 
            try {
                int idWorkPlan = workPlanDAO.getId(workPlan);
                workPlan.setId(idWorkPlan);
                for(int i=0; i< workPlan.getGoals().length; i++){             
                    goalDAO.saveSuccesful(workPlan.getGoals()[i], idWorkPlan);
                }
                 value= true;    
            } catch (BusinessException ex) {
                Log.logException(ex);
            }
        }
        return value;
    }
    
    private boolean saveActions(){  
        boolean value = false;
        GoalDao goalDAO = new GoalDao();
        ActionDAO actionDAO = new ActionDAO();
        if(saveGoals()){    
            for(int i=0; i< actions.size(); i++){   
                try {
                    int idGoal = goalDAO.getId(actions.get(i).getGoal(), workPlan.getId());
                    actionDAO.saveSuccesful(actions.get(i), idGoal);
                    value=true;
                } catch (BusinessException ex) {
                    Log.logException(ex);
                }
            }
        
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
    
    private void setSelectedAction(){
        Action action= getSelectedAction();
        indexAction = actions.indexOf(action);
        if(action!= null){
            actions.add(action);
        
        }
    }
    
}
