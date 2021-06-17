
package GUI;

import businessLogic.MeetingDAO;
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
    
    @FXML private Label lbSubject;
    @FXML  private Label lbDate;
    @FXML  private Label lbHour;
    @FXML  private Button btShowSchedule;
    @FXML  private Button btReturn;
    @FXML private TableView tvAssistants;
    @FXML private Button btMeetingStart;
    @FXML private Button btUpdate;
    @FXML private Button btShowMinute;
     @FXML private  Button btRegisterMinute;
    @FXML private TableView<Prerequisite> tvPrerequisites;
    @FXML private TableColumn<Prerequisite,String>  tcDescription;
    @FXML private TableColumn<Prerequisite,Member> tcMandated;
    @FXML private TableColumn<Member,String> tcRole;
    @FXML private TableColumn<Member,String> tcMember;
    private ListChangeListener<Prerequisite> tablePrerequisiteListener;
    private ListChangeListener tableAssistantsListener;
    private ObservableList<Prerequisite> prerequisites;
    private ObservableList<Member> assistants;
    private Meeting meeting= new Meeting();
    private Member member;
    
    public void setMeeting(Meeting meeting){
        this.meeting.setKey(meeting.getKey());
        this.meeting.setDate(meeting.getDate());
        this.meeting.setHourStart(meeting.getHourStart());
        this.meeting.setState(meeting.getState());
        this.meeting.setSubject(meeting.getSubject());
    
    } 
    
    @FXML
    private void actionReturn(ActionEvent actionEvent){   
        Stage stage = (Stage) btReturn.getScene().getWindow();
        stage.close();
        openMeetingList();
    }
    
    private void openMeetingList(){ 
        try{ 
            Stage primaryStage= new Stage();
            URL url = new File("src/GUI/meetingList.fxml").toURI().toURL();
           try{
              FXMLLoader loader = new FXMLLoader(url);
              loader.setLocation(url);
              loader.load();
              MeetingListController meetingListController =loader.getController();   
              meetingListController.setMember(member);
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
        disableButtonUpdate();

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
              topicShowController.setMember(member);
              topicShowController.setMeeting(meeting);
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
               meetingModifyController.setMember(member);
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
      }else if(dateMeeting.equals(dateCurrently)){    
          meeting.setState("Proxima");
          meetingDAO.changedStateSucessful(meeting);
          activateMeetingStartButton();
      }
    }
    
    private void disableButtonUpdate(){   
        if(member.getRole().equals("Integrante")){  
            btUpdate.setOpacity(0);
            btUpdate.setDisable(true);
        
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
    
    public void setMember(Member member){
       this.member = member;
   }
    
    @FXML
    private void actionMinute(){
        if(btMeetingStart.getText().equals("Ver minuta")){
            showMinute();
        }else{
            registerMinute();
        }
    }
    
    private void registerMinute(){  
       
         try{ 
            Stage primaryStage= new Stage();
            URL url = new File("src/GUI/minuteRegister.fxml").toURI().toURL();
           try{
              FXMLLoader loader = new FXMLLoader(url);
              loader.setLocation(url);
              loader.load();
              MinuteRegisterController minuteRegisterController =loader.getController(); 
              minuteRegisterController.setMember(member);
              minuteRegisterController.setMeeting(meeting);
              Parent root = loader.getRoot();
              Scene scene = new Scene(root);
              primaryStage.setScene(scene);
              Stage stage = (Stage) btMeetingStart.getScene().getWindow();
              stage.close();
            } catch (IOException ex) {
                    Log.logException(ex);
            }
            primaryStage.show();
       } catch (MalformedURLException ex) {
           Log.logException(ex);
       }
    }
    
    private void showMinute(){  
       
         try{ 
            Stage primaryStage= new Stage();
            URL url = new File("src/GUI/minuteShow.fxml").toURI().toURL();
           try{
              FXMLLoader loader = new FXMLLoader(url);
              loader.setLocation(url);
              loader.load();
              MinuteShowController minuteShowController =loader.getController(); 
               minuteShowController.setMember(member);
               minuteShowController.initializeMinute(meeting); 
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
