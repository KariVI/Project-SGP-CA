
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
import javafx.stage.Stage;
import log.BusinessException;
import log.Log;

public class MeetingListController implements Initializable {
    @FXML private ListView lvMeetings;
    @FXML private Button btAddMeeting;
    @FXML private Button btReturn;
    private ObservableList<Meeting> meetings ;
    private Member member;
    
    
    private void  disableButtonRegister(){  
        if(member.getRole().equals("Integrante")){
            btAddMeeting.setOpacity(0);
            btAddMeeting.setDisable(true);
        }
    }
    
    @FXML 
    public void actionAddMeeting(ActionEvent actionEvent){  
        Stage stage = (Stage) btAddMeeting.getScene().getWindow();
        stage.close();
         try{ 
            Stage primaryStage= new Stage();
            URL url = new File("src/GUI/MeetingRegister.fxml").toURI().toURL();
           try{
              FXMLLoader loader = new FXMLLoader(url);
              loader.setLocation(url);
              loader.load();
              MeetingRegisterController meetingRegisterController =loader.getController();
              meetingRegisterController.setMember(member);
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
        disableButtonRegister();
        getMeetings(keyGroupAcademic);  
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
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      meetings = FXCollections.observableArrayList();         
      lvMeetings.setItems(meetings);
      lvMeetings.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Meeting>() {
        @Override
        public void changed(ObservableValue<? extends Meeting> observaleValue, Meeting oldValue, Meeting newValue) {
            Meeting selectedMeeting = (Meeting) lvMeetings.getSelectionModel().getSelectedItem();   
            Stage stage = (Stage) lvMeetings.getScene().getWindow();
            stage.close();
            openMeetingShow(selectedMeeting);
        }
      }); 
    }    
    
    private void openMeetingShow(Meeting meeting){ 
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MeetingShow.fxml"));
            Parent root = loader.load();
            MeetingShowController meetingShowController = loader.getController();
            meetingShowController.setMember(member);
            meetingShowController.setMeeting(meeting); 
            try {
                meetingShowController.initializeMeeting();
            } catch (BusinessException | NullPointerException ex) {
                Log.logException(ex);
                openLogin();
            }
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.showAndWait();
        } catch (IOException ex) {
           Log.logException(ex);
        }    
    }
    
    private void getMeetings( String keyGroupAcademic) {   
        MeetingDAO meetingDAO = new MeetingDAO();
        ArrayList<Meeting> meetingList = null;  
          try {
                meetingList = meetingDAO.getMeetings(keyGroupAcademic,  member.getProfessionalLicense());
            } catch (BusinessException | NullPointerException ex) {
                Log.logException(ex);
                openLogin();
            }
        for(int i=0; i< meetingList.size(); i++){
            meetings.add(meetingList.get(i));
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
