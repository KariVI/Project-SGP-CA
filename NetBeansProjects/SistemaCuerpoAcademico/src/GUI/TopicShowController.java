package GUI;

import businessLogic.TopicDAO;
import domain.Meeting;
import domain.Member;
import domain.Topic;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import log.BusinessException;
import log.Log;

public class TopicShowController implements Initializable {
    private ObservableList<Topic> topics;
    @FXML TableColumn<Topic,String>  tcFinishTime;
    @FXML TableColumn<Topic,String>  tcStartTime;
    @FXML TableColumn<Topic,String> tcMember;
    @FXML TableColumn<Topic,String> tcTopic;
    @FXML TableView<Topic> tvTopic;
    @FXML Button btDelete;
    @FXML Button btAdd;
    @FXML Button btUpdate;
    @FXML Button btReturn; 
    private Meeting meeting;
    private Member member;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tcTopic.setCellValueFactory(new PropertyValueFactory<>("topicName"));
        tcStartTime.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        tcFinishTime.setCellValueFactory(new PropertyValueFactory<>("finishTime"));
        tcMember.setCellValueFactory(new PropertyValueFactory<>("member"));
        topics = FXCollections.observableArrayList();      
        tvTopic.setItems(topics);        
    }  
    
    private void  disableButtonModify(){  
        Date currentDay = getCurrentDay();
        Date meetingDay = getMeetingDay();
        if(currentDay.after(meetingDay)){
            btUpdate.setOpacity(0);
            btUpdate.setDisable(true);
        }
    }
    
    private Date getMeetingDay(){
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date meetingDay = null;
        System.out.println(meeting.getDate());
        try {
            meetingDay = format.parse(meeting.getDate());
        } catch (ParseException ex) {
            Log.logException(ex);
        }
        return meetingDay;
    }
    
    private Date getCurrentDay(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        return date;
    }
    
    public void setMeeting(Meeting meeting){
        this.meeting = meeting;
        initializeTopics();
        disableButtonModify();
    }
    
    public void setMember(Member member){
        this.member = member;
    }
    
    private void initializeTopics(){
        TopicDAO topicDAO = new TopicDAO();
        try {
            ArrayList<Topic> topicList = new ArrayList<Topic>();
            topicList = topicDAO.getAgendaTopics(meeting.getKey());
            for(int i = 0; i < topicList.size(); i++){
                topics.add(topicList.get(i));   
            }   
        } catch (BusinessException ex) {
            Log.logException(ex);
        }       
    }  
    
    @FXML
    private void actionReturn(ActionEvent actionEvent){   
        Stage stage = (Stage) btReturn.getScene().getWindow();
        stage.close();
        openShowMeeting();
    }
    
    private void openShowMeeting(){   
        Stage primaryStage = new Stage();
        try{
              URL url = new File("src/GUI/MeetingShow.fxml").toURI().toURL();
              FXMLLoader loader = new FXMLLoader(url);
              loader.setLocation(url);
              loader.load();
              MeetingShowController meetingShowController  = loader.getController();
              meetingShowController.setMember(member);
              meetingShowController.setMeeting(meeting);
            try {
                meetingShowController.initializeMeeting();
            } catch (BusinessException ex) {
                Log.logException(ex);
            }
              Parent root = loader.getRoot();
              Scene scene = new Scene(root);
              primaryStage.setScene(scene);
              primaryStage.show();
            }catch (IOException ex) {
                Log.logException(ex);
            } 
    }
    
    @FXML
    private void actionUpdate(){
        try {
            Stage primaryStage= new Stage();
            URL url = new File("src/GUI/topicModify.fxml").toURI().toURL();
            FXMLLoader loader = new FXMLLoader(url);
            loader.setLocation(url);
            loader.load();
            TopicModifyController topicModifyController = loader.getController();
            topicModifyController.setMember(member);
            topicModifyController.setMeeting(meeting);
            Parent root = loader.getRoot();
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            Stage stage = (Stage) btUpdate.getScene().getWindow();
            stage.close();
            primaryStage.show();
        } catch (IOException ex) {
            Log.logException(ex);
        }
    }
}
