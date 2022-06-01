/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import businessLogic.ReceptionWorkDAO;
import businessLogic.WorkPlanDAO;
import domain.Member;
import domain.ReceptionWork;
import domain.WorkPlan;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import log.BusinessException;
import log.Log;

/**
 * FXML Controller class
 *
 * @author Mariana
 */
public class WorkPlanListController implements Initializable {
     @FXML private ListView lvPlanWork;
     @FXML private Button btRegisterPlanWork;
     @FXML private Button btReturn;
     private ObservableList<WorkPlan> planWorks ;
     private Member member;
     private String keyGroupAcademic;
      
    public void setMember(Member member) {
        this.member = member;
        getWorkPlans();
    }
     
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        planWorks  = FXCollections.observableArrayList();
        lvPlanWork.setItems(planWorks);
        lvPlanWork.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<WorkPlan>() {
          public void changed(ObservableValue<? extends WorkPlan> observaleValue, WorkPlan oldValue, WorkPlan newValue) {
             WorkPlan selectedWorkPlan = (WorkPlan) lvPlanWork.getSelectionModel().getSelectedItem();
             Stage stage = (Stage) lvPlanWork.getScene().getWindow();
             stage.close();
             
             openWorkPlan(selectedWorkPlan);
       }
      });
    }    
    
    private void getWorkPlans() {   
        WorkPlanDAO workPlanDAO = new WorkPlanDAO();
        ArrayList<WorkPlan> WorkPlanList;  
        try {
            WorkPlanList = workPlanDAO.getWorkPlans();
            for(int i=0; i< WorkPlanList.size(); i++){
            planWorks.add(WorkPlanList.get(i));
         }
            
           
       } catch (BusinessException | NullPointerException ex) {
            Log.logException(ex);
                 AlertMessage alertMessage  = new AlertMessage();
                alertMessage.showAlertValidateFailed("Error en la conexión con la base de datos");
                Stage stage = (Stage)  btReturn.getScene().getWindow();
                stage.close();
                openLogin();
       }
        
    }
     private void openWorkPlan(WorkPlan selectedWorkPlan){    
         try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("WorkPlanView.fxml"));
                Parent root = loader.load();
                WorkPlanViewController workPlanViewController = loader.getController();
                workPlanViewController.setWorkPlanId(selectedWorkPlan.getId());
                workPlanViewController.setMember(member);
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
    private void actionReturn(ActionEvent actionEvent){   
        Stage stage = (Stage) btReturn.getScene().getWindow();
        stage.close();
        openViewMenu();
    }
      private void openViewMenu(){   
        Stage primaryStage = new Stage();
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
    public void actionRegisterWorkPlan(ActionEvent actionEvent){    
        Stage stage = (Stage) btRegisterPlanWork.getScene().getWindow();
        stage.close();     
        try{ 
             
            Stage primaryStage= new Stage();
            URL url = new File("src/GUI/WorkPlanRegister.fxml").toURI().toURL();
           try{
              FXMLLoader loader = new FXMLLoader(url);
              loader.setLocation(url);
              loader.load();
              WorkPlanRegisterController workPlanRegisterController =loader.getController();  
              workPlanRegisterController.setMember(member); 
              workPlanRegisterController.setKeyGroupAcademic(member.getKeyGroupAcademic());
              Parent root = loader.getRoot();
              Scene scene = new Scene(root);
              primaryStage.setScene(scene);       
            } catch (IOException ex) {
                    Log.logException(ex);
            }
            primaryStage.show();
       } catch (MalformedURLException ex) {
           Log.logException(ex);
       } 
    }
    
             private void  openLogin(){   
        Stage primaryStage =  new Stage();
        try{
            
            URL url = new File("src/GUI/Login.fxml").toURI().toURL();
            try{
                FXMLLoader loader = new FXMLLoader(url);
                loader.setLocation(url);
                loader.load();
                LoginController login = loader.getController();
                Parent root = loader.getRoot();
                Scene scene = new Scene(root);
                primaryStage.setScene(scene);
                
            } catch (IOException ex) {
                Log.logException(ex);
            }
            primaryStage.show();
            
        } catch (MalformedURLException ex) {
                Log.logException(ex);
        }
    }
}
