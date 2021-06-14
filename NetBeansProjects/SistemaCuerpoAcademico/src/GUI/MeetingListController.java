
package GUI;

import businessLogic.MeetingDAO;
import domain.Meeting;
import domain.Member;
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

public class MeetingListController implements Initializable {
    @FXML private ListView lvMeetings;
    @FXML private Button btAddMeeting;
    @FXML private Button btReturn;
    private ObservableList<Meeting> meetings ;
    private Member member;
    
     @FXML 
    public void actionAddMeeting(ActionEvent actionEvent){    
         try{ 
            Stage primaryStage= new Stage();
            URL url = new File("src/GUI/MeetingRegister.fxml").toURI().toURL();
           try{
              FXMLLoader loader = new FXMLLoader(url);
              loader.setLocation(url);
              loader.load();
              MeetingRegisterController meetingRegisterController =loader.getController();  
              meetingRegisterController.setKeyGroupAcademic(member.getKeyGroupAcademic());
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
    
    public void setMember(Member member){
        this.member = member;
        String keyGroupAcademic = member.getKeyGroupAcademic();
         getMeetings(keyGroupAcademic);  
    }
    
    @FXML 
    private void actionReturn(ActionEvent actionEvent){   
        Stage stage = (Stage) btReturn.getScene().getWindow();
        stage.close();
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        meetings = FXCollections.observableArrayList();
          
      lvMeetings.setItems(meetings);
      lvMeetings.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Meeting>() {
          @Override
          public void changed(ObservableValue<? extends Meeting> observaleValue, Meeting oldValue, Meeting newValue) {
             Meeting selectedMeeting = (Meeting) lvMeetings.getSelectionModel().getSelectedItem();
            try {
           FXMLLoader loader = new FXMLLoader(getClass().getResource("MeetingShow.fxml"));
            Parent root = loader.load();
            MeetingShowController meetingShowController = loader.getController();
            meetingShowController.setMeeting(selectedMeeting);
            meetingShowController.setMember(member);
                 try {
                     meetingShowController.initializeMeeting();
                 } catch (BusinessException ex) {
                    Log.logException(ex);
                 }
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
    
    
    private void getMeetings( String keyGroupAcademic) {   
        MeetingDAO meetingDAO = new MeetingDAO();
        ArrayList<Meeting> meetingList;  
        meetingList = meetingDAO.getMeetings(keyGroupAcademic);
        for(int i=0; i< meetingList.size(); i++){
            meetings.add(meetingList.get(i));
        }
        
    }
}
