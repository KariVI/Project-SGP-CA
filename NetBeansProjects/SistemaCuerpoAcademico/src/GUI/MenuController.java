
package GUI;

import businessLogic.GroupAcademicDAO;
import domain.GroupAcademic;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
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


public class MenuController implements Initializable {

    @FXML Button btRegister;
    @FXML Button btConsult;
    @FXML ListView lvOptions;
    GroupAcademic groupAcademic;
    private ObservableList<String> options ;

    
    
    
    @FXML
    private void actionRegister(ActionEvent actionEvent){   
        try{ 
            Stage primaryStage= new Stage();
            URL url = new File("src/GUI/groupAcademicRegister.fxml").toURI().toURL();
           try{
              FXMLLoader loader = new FXMLLoader(url);
              loader.setLocation(url);
              loader.load();
              GroupAcademicRegisterController groupAcademicRegisterController =loader.getController();      
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
    private void actionQuery(ActionEvent actionEvent) throws BusinessException{  
         try{ 
            Stage primaryStage= new Stage();
            URL url = new File("src/GUI/groupAcademicShow.fxml").toURI().toURL();
           try{
              FXMLLoader loader = new FXMLLoader(url);
              loader.setLocation(url);
              loader.load();
                GroupAcademicShowController groupAcademicShowController =loader.getController();
                GroupAcademicDAO groupAcademicDAO= new GroupAcademicDAO();
                 GroupAcademic groupAcademic= groupAcademicDAO.getGroupAcademicById("JDOEIJ804");
                groupAcademicShowController.setGroupAcademic(groupAcademic);
                groupAcademicShowController.initializeGroupAcademic();
                Parent root = loader.getRoot();
                Scene scene = new Scene(root);
                primaryStage.setScene(scene);
           }catch (IOException ex) {
                    Log.logException(ex);
            }
            primaryStage.show();
       } catch (MalformedURLException ex) {
           Log.logException(ex);
       }
    }
         
    private void fillOptions() {   
        options.add("Anteproyectos");
        options.add("Proyectos");
        options.add("Reuniones");
        options.add("Miembros");
    }
        
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        options = FXCollections.observableArrayList();
       fillOptions();      
      lvOptions.setItems(options);
      lvOptions.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
          public void changed(ObservableValue<? extends String> observaleValue, String oldValue, String newValue) {
             String selectedOption = (String) lvOptions.getSelectionModel().getSelectedItem();
             showViewOption(selectedOption);
          }
      });
    
    }  
    
    private void showViewOption(String option){  
        FXMLLoader loader;
        switch(option){ 
            case "Anteproyectos": ;
                try {
               loader = new FXMLLoader(getClass().getResource("PreliminarProjectList.fxml"));
               Parent root = loader.load();
               PreliminarProjectListController PreliminarProjectListController = loader.getController();
               Scene scene = new Scene(root);
               Stage stage = new Stage();
               stage.setScene(scene);
               stage.initModality(Modality.APPLICATION_MODAL);
               stage.showAndWait();
               } catch (IOException ex) {
                   Log.logException(ex);
               }
            break;
            
            case "Miembros":;
                   try {
               loader = new FXMLLoader(getClass().getResource("MemberList.fxml"));
               Parent root = loader.load();
               MemberListController MemberListController = loader.getController();
               Scene scene = new Scene(root);
               Stage stage = new Stage();
               stage.setScene(scene);
               stage.initModality(Modality.APPLICATION_MODAL);
               stage.showAndWait();
               } catch (IOException ex) {
                   Log.logException(ex);
               }
            break;
            
            case "Reuniones":;
              try {
               loader = new FXMLLoader(getClass().getResource("MeetingList.fxml"));
               Parent root = loader.load();
               MeetingListController MeetingListController = loader.getController();
               Scene scene = new Scene(root);
               Stage stage = new Stage();
               stage.setScene(scene);
               stage.initModality(Modality.APPLICATION_MODAL);
               stage.showAndWait();
               } catch (IOException ex) {
                   Log.logException(ex);
               }
            break;
        
        }
    
    }
    
}
