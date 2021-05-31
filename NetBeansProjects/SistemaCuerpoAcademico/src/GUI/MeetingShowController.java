
package GUI;

import businessLogic.MeetingDAO;
import businessLogic.MemberDAO;
import businessLogic.PrerequisiteDAO;
import domain.Meeting;
import domain.Member;
import domain.Prerequisite;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import log.BusinessException;
import log.Log;


public class MeetingShowController implements Initializable {
    
    @FXML Label lbSubject;
    @FXML  Label lbDate;
    @FXML  Label lbHour;
    @FXML  Button btShowSchedule;
    @FXML TableView tvAssistants;
    @FXML Button btMeetingStart;
    @FXML Button btUpdate;
    @FXML TableView<Prerequisite> tvPrerequisites;
    private ListChangeListener<Prerequisite> tablePrerequisiteListener;
    private ListChangeListener tableAssistantsListener;
    @FXML TableColumn<Prerequisite,String>  tcDescription;
    @FXML TableColumn<Prerequisite,Member> tcMandated;
    @FXML TableColumn<Member,String> tcRole;
    @FXML TableColumn<Member,String> tcMember;
    private ObservableList<Prerequisite> prerequisites;
    private ObservableList<Member> assistants;
    private Meeting meeting= new Meeting();
    
    
    public void setMeeting(Meeting meeting){
        this.meeting.setKey(meeting.getKey());
        this.meeting.setDate(meeting.getDate());
        this.meeting.setHourStart(meeting.getHourStart());
        this.meeting.setState(meeting.getState());
        this.meeting.setSubject(meeting.getSubject());
    
    }   
    
    public void initializeMeeting() throws BusinessException{    
        String subject= "Asunto : " + meeting.getSubject();
        String date= "Fecha: "+ meeting.getDate();
        String hour = "Hora: "+meeting.getHourStart();
        lbSubject.setText(subject);
        lbDate.setText(date);
        lbHour.setText(hour);
        assistants = FXCollections.observableArrayList();
        initializeAssistants();
        tvAssistants.setItems(assistants);
        prerequisites =FXCollections.observableArrayList();
        initializePrerequisites();
        tvPrerequisites.setItems(prerequisites);
        disableMeetingStartButton();
        evaluateDate();

    }
    
    @FXML 
    private void actionShowSchedule(ActionEvent ActionEvent){  
            try{ 
            Stage primaryStage= new Stage();
            URL url = new File("src/GUI/topicShow.fxml").toURI().toURL();
           try{
              FXMLLoader loader = new FXMLLoader(url);
              loader.setLocation(url);
              loader.load();
              TopicShowController topicShowController =loader.getController(); 
              topicShowController.initializeMeeting(meeting);
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
    private void actionUpdate(ActionEvent actionEvent) throws BusinessException{ 
         Stage primaryStage= new Stage();
        URL url=null;
        try{ 
             url = new File("src/GUI/MeetingModify.fxml").toURI().toURL();
        } catch (MalformedURLException ex) {
            Log.logException(ex);
        }
        try{
              FXMLLoader loader = new FXMLLoader(url);
              loader.setLocation(url);
              loader.load();
              MeetingModifyController meetingModifyController =loader.getController();      
               meetingModifyController.setMeeting(meeting);
               meetingModifyController.initializeMeeting();
              Parent root = loader.getRoot();
              Scene scene = new Scene(root);
              primaryStage.setScene(scene);
              Stage stage = (Stage) btUpdate.getScene().getWindow();
              stage.close();
            } catch (IOException ex) {
                    Log.logException(ex);
            }
            primaryStage.show();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tcDescription.setCellValueFactory(new PropertyValueFactory<Prerequisite,String>("description"));
        tcMandated.setCellValueFactory(new PropertyValueFactory<Prerequisite,Member>("mandated"));
        tcRole.setCellValueFactory(new PropertyValueFactory<Member,String>("role"));
        tcMember.setCellValueFactory(new PropertyValueFactory<Member,String>("name"));       
    }  
    
    private void evaluateDate() throws BusinessException{    
      LocalDate dateMeeting= LocalDate.parse(meeting.getDate(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
      LocalDate dateCurrently = LocalDate.now();
      MeetingDAO meetingDAO = new MeetingDAO();
      if(meeting.getState().equals("Concluida")){   
          disableModifyButton();
      }
      
      if(dateMeeting.equals(dateCurrently)){    
          meeting.setState("Proxima");
          meetingDAO.changedStateSucessful(meeting);
          activateMeetingStartButton();
      }
    }
    
    private void disableMeetingStartButton(){ 
        btMeetingStart.setOpacity(0);
        btMeetingStart.setDisable(true);
    }
    
    private void activateMeetingStartButton(){ 
        btMeetingStart.setOpacity(1);
        btMeetingStart.setDisable(false);
    }
    
        private void disableModifyButton(){ 
        btUpdate.setOpacity(0);
        btUpdate.setDisable(true);
        btMeetingStart.setOpacity(1);
        btMeetingStart.setDisable(false);
        btMeetingStart.setText("Ver minuta");
    }
    
    
    private void initializeAssistants(){
        MeetingDAO meetingDAO = new MeetingDAO();
        try{
            ArrayList<Member> assistantsList = meetingDAO.getAssistants(meeting.getKey());
            for(int i = 0; i < assistantsList.size(); i++){
                assistants.add(assistantsList.get(i));
            }
        } catch (BusinessException ex) {
            Log.logException(ex);
        }
    }
    
    private void initializePrerequisites(){
        PrerequisiteDAO prerequisiteDAO = new PrerequisiteDAO();
        try{
            ArrayList<Prerequisite> prerequisitesList = prerequisiteDAO.getPrerequisites(meeting.getKey());
            for(int i = 0; i < prerequisitesList.size(); i++){    
                prerequisites.add(prerequisitesList.get(i));
            }
        } catch (BusinessException ex) {
            Log.logException(ex);
        }
    }
    
}
