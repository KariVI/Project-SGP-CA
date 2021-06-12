
package GUI;

import businessLogic.PreliminarProjectDAO;
import domain.PreliminarProject;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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


public class PreliminarProjectListController implements Initializable {
    @FXML private ListView lvPreliminarProjects;
    @FXML private Button btAddPreliminarProject;
    @FXML private Button btReturn;
    private ObservableList<PreliminarProject> preliminarProjects ;
    private String keyGroupAcademic="JDOEIJ804";

    public void setKeyGroupAcademic(String keyGroupAcademic) {
        this.keyGroupAcademic = keyGroupAcademic;
    }
            
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       preliminarProjects = FXCollections.observableArrayList();
       getPreliminarProjects();      
      lvPreliminarProjects.setItems(preliminarProjects);
      lvPreliminarProjects.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<PreliminarProject>() {
          @Override
          public void changed(ObservableValue<? extends PreliminarProject> observaleValue, PreliminarProject oldValue, PreliminarProject newValue) {
             PreliminarProject selectedPreliminarProject = (PreliminarProject) lvPreliminarProjects.getSelectionModel().getSelectedItem();
            try {
           FXMLLoader loader = new FXMLLoader(getClass().getResource("PreliminarProjectShow.fxml"));
            Parent root = loader.load();
            PreliminarProjectShowController preliminarProjectShowController = loader.getController();
            preliminarProjectShowController.setPreliminarProject(selectedPreliminarProject);
            preliminarProjectShowController.initializePreliminarProject();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException ex) {
            Log.logException(ex);
        }
          }
      });
    
    }    
    
    private void getPreliminarProjects() {   
        PreliminarProjectDAO preliminarProjectDAO = new PreliminarProjectDAO();
        ArrayList<PreliminarProject> preliminarProjectList;  
        try {
            preliminarProjectList = preliminarProjectDAO.getPreliminarProjects(keyGroupAcademic);
            for(int i=0; i< preliminarProjectList.size(); i++){
            preliminarProjects.add(preliminarProjectList.get(i));
           }
        } catch (BusinessException ex) {
            Log.logException(ex);
        }
        
    }
    
    @FXML 
    public void actionAddPreliminarProject(ActionEvent actionEvent){    
         try{ 
            Stage primaryStage= new Stage();
            URL url = new File("src/GUI/PreliminarProjectRegister.fxml").toURI().toURL();
           try{
              FXMLLoader loader = new FXMLLoader(url);
              loader.setLocation(url);
              loader.load();
              PreliminarProjectRegisterController preliminarProjectRegisterController =loader.getController();      
              preliminarProjectRegisterController.setKeyGroupAcademic(keyGroupAcademic);
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
    }
    
}
