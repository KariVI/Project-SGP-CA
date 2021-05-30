
package GUI;

import businessLogic.ReceptionWorkDAO;
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
     @FXML ListView lvReceptionWorks;
     @FXML Button btRegisterReceptionWork;
     @FXML  Button btReturn;
     private ObservableList<ReceptionWork> receptionWorks ;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        receptionWorks  = FXCollections.observableArrayList();
       getReceptionWorks();      
      lvReceptionWorks.setItems(receptionWorks);
      lvReceptionWorks.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ReceptionWork>() {
          public void changed(ObservableValue<? extends ReceptionWork> observaleValue, ReceptionWork oldValue, ReceptionWork newValue) {
             ReceptionWork selectedReceptionWork = (ReceptionWork) lvReceptionWorks.getSelectionModel().getSelectedItem();
            try {
           FXMLLoader loader = new FXMLLoader(getClass().getResource("ReceptionWorkShow.fxml"));
            Parent root = loader.load();
            ReceptionWorkShowController receptionWorkShowController = loader.getController();
            receptionWorkShowController.setReceptionWork(selectedReceptionWork);
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
      });
    }    
    
    
       private void getReceptionWorks() {   
        ReceptionWorkDAO preliminarProjectDAO = new ReceptionWorkDAO();
        ArrayList<ReceptionWork> receptionWorkList;  
        try {
            receptionWorkList = preliminarProjectDAO.getReceptionWorks();
            for(int i=0; i< receptionWorkList.size(); i++){
            receptionWorks.add(receptionWorkList.get(i));
           }
        } catch (BusinessException ex) {
            Log.logException(ex);
        }
        
    }
    
    @FXML 
    public void actionRegisterReceptionWork(ActionEvent actionEvent){    
         try{ 
            Stage primaryStage= new Stage();
            URL url = new File("src/GUI/ReceptionWorkRegister.fxml").toURI().toURL();
           try{
              FXMLLoader loader = new FXMLLoader(url);
              loader.setLocation(url);
              loader.load();
              ReceptionWorkRegisterController receptionWorkController =loader.getController();      
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
