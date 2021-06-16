
package GUI;

import businessLogic.PreliminarProjectDAO;
import businessLogic.ReceptionWorkDAO;
import domain.Member;
import domain.PreliminarProject;
import domain.ReceptionWork;
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

public class ReceptionWorkListController implements Initializable {
     @FXML private ListView lvReceptionWorks;
     @FXML private Button btRegisterReceptionWork;
     @FXML private Button btReturn;
     private ObservableList<ReceptionWork> receptionWorks ;
     private ObservableList <PreliminarProject> preliminarProjectsAssigned ;
     private ObservableList <PreliminarProject> preliminarProjectsUnassigned ;
     private String keyGroupAcademic;
     private Member member;

    public void setMember(Member member) {
        this.member = member;
    }
     
     
    public void setKeyGroupAcademic(String keyGroupAcademic) {
        this.keyGroupAcademic = keyGroupAcademic;
        getReceptionWorks();   
        fillPreliminarProjectsAssigned();
        fillPreliminarProjectsUnassigned();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        receptionWorks  = FXCollections.observableArrayList();
        preliminarProjectsAssigned= FXCollections.observableArrayList();
        preliminarProjectsUnassigned= FXCollections.observableArrayList();
      lvReceptionWorks.setItems(receptionWorks);
      lvReceptionWorks.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ReceptionWork>() {
          public void changed(ObservableValue<? extends ReceptionWork> observaleValue, ReceptionWork oldValue, ReceptionWork newValue) {
             ReceptionWork selectedReceptionWork = (ReceptionWork) lvReceptionWorks.getSelectionModel().getSelectedItem();
             Stage stage = (Stage) lvReceptionWorks.getScene().getWindow();
             stage.close();
             
             openReceptionWork(selectedReceptionWork);
       }
      });
    }    
    
    
    private void openReceptionWork(ReceptionWork receptionWork){    
         try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("ReceptionWorkShow.fxml"));
                 Parent root = loader.load();
                 ReceptionWorkShowController receptionWorkShowController = loader.getController();
                 receptionWorkShowController.setReceptionWork(receptionWork);
                 receptionWorkShowController.setMember(member);
                 receptionWorkShowController.setKeyGroupAcademic(keyGroupAcademic);   
                 receptionWorkShowController.setPreliminarProjectsUnassigned(preliminarProjectsUnassigned);
                 receptionWorkShowController.initializeReceptionWork(); 
                 Scene scene = new Scene(root);
                 Stage stage = new Stage();
                 stage.setScene(scene);
                 stage.initModality(Modality.APPLICATION_MODAL);
                 stage.showAndWait();
        } catch (IOException ex) {
            Log.logException(ex);
        }
    
    }
    
       private void getReceptionWorks() {   
        ReceptionWorkDAO preliminarProjectDAO = new ReceptionWorkDAO();
        ArrayList<ReceptionWork> receptionWorkList;  
        try {
            receptionWorkList = preliminarProjectDAO.getReceptionWorks(keyGroupAcademic);
            for(int i=0; i< receptionWorkList.size(); i++){
            receptionWorks.add(receptionWorkList.get(i));
           }
            
           
        } catch (BusinessException ex) {
            Log.logException(ex);
        }
        
    }
       
    private void fillPreliminarProjectsAssigned(){   
         for(int i=0; i< receptionWorks.size(); i++){
            PreliminarProject preliminarProject = receptionWorks.get(i).getPreliminarProject();
            preliminarProjectsAssigned.add(preliminarProject);
           
           }
    }
    
     private void fillPreliminarProjectsUnassigned() {   
         PreliminarProjectDAO preliminarProjectDAO = new PreliminarProjectDAO();
         ArrayList<PreliminarProject> preliminarProjects = null;
         try {

             preliminarProjects = preliminarProjectDAO.getPreliminarProjects(keyGroupAcademic);

         } catch (BusinessException ex) {
             Log.logException(ex);
         }
         for(int i=0; i< preliminarProjects.size(); i++){
            PreliminarProject preliminarProject = preliminarProjects.get(i);
                if(!repeatedPreliminarProject(preliminarProject)){
                    preliminarProjectsUnassigned.add(preliminarProject);
                }
           }
    }
    
    private boolean repeatedPreliminarProject(PreliminarProject preliminarProject){ 
        int id = preliminarProject.getKey();
        int i=0;
        boolean value=false;
        while((value==false) &&  i< preliminarProjectsAssigned.size()){ 
            int idAuxiliar = preliminarProjectsAssigned.get(i).getKey();
            if(id==idAuxiliar){
             value=true;
            }           
            i++;
        }        
        return value;     
    }
    
    @FXML 
    public void actionRegisterReceptionWork(ActionEvent actionEvent){    
          Stage stage = (Stage) btRegisterReceptionWork.getScene().getWindow();
        stage.close();     
        try{ 
             
            Stage primaryStage= new Stage();
            URL url = new File("src/GUI/ReceptionWorkRegister.fxml").toURI().toURL();
           try{
              FXMLLoader loader = new FXMLLoader(url);
              loader.setLocation(url);
              loader.load();
              ReceptionWorkRegisterController receptionWorkController =loader.getController();  
              receptionWorkController.setPreliminarProjects(preliminarProjectsUnassigned);
              receptionWorkController.setKeyGroupAcademic(keyGroupAcademic);
              receptionWorkController.setMember(member);
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
}
