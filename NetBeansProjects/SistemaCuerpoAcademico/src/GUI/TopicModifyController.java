package GUI;

import businessLogic.MemberDAO;
import businessLogic.TopicDAO;
import domain.Meeting;
import domain.Member;
import domain.Topic;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import log.BusinessException;
import log.Log;


public class TopicModifyController implements Initializable {
    private ObservableList<Member> members;
    private ObservableList<Topic> topics;
    private ObservableList<Topic> oldTopics;
    @FXML private ComboBox<Member> cbMember;
    @FXML private TextField tfTopic;
    @FXML private TextField tfStartTime;
    @FXML private TextField tfFinishTime;
    @FXML private TableColumn tcFinishTime;
    @FXML private TableColumn tcStartTime;
    @FXML private TableColumn tcMember;
    @FXML private TableColumn<Topic,String> tcTopic;
    @FXML private TableView<Topic> tvTopic;
    @FXML private Button btDelete;
    @FXML private Button btAdd;
    @FXML private Button btSave;
    @FXML private Button btCancel;
    @FXML private Button btUpdate;
    private Meeting meeting;
    private Member member;
    private int indexTopic;
    private ListChangeListener<Topic> tableTopicListener;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tcTopic.setCellValueFactory(new PropertyValueFactory<Topic,String>("topicName"));
        tcStartTime.setCellValueFactory(new PropertyValueFactory<Topic,String>("startTime"));
        tcFinishTime.setCellValueFactory(new PropertyValueFactory<Topic,String>("finishTime"));
        tcMember.setCellValueFactory(new PropertyValueFactory<Topic,String>("member"));
        topics = FXCollections.observableArrayList();
        oldTopics = FXCollections.observableArrayList();
        tvTopic.setItems(topics);
       
        members = FXCollections.observableArrayList();       
        cbMember.setItems(members);
        tvTopic.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                setSelectedTopic();
            }
        });

        tableTopicListener = new ListChangeListener<Topic>(){
            @Override
            public void onChanged(ListChangeListener.Change<? extends Topic> topic) {
                setSelectedTopic();
            }
        };
    }
    
    public void setMeeting(Meeting meeting){
        this.meeting = meeting;
        initializeTopics();
    }
    
    public void setMember(Member member){
        this.member = member;
        initializeMembers();
    }
    
    private void initializeTopics(){
        TopicDAO topicDAO = new TopicDAO();
        try {
            ArrayList<Topic> topicList = new ArrayList<Topic>();
            topicList = topicDAO.getAgendaTopics(meeting.getKey());
            for(int i = 0; i < topicList.size(); i++){
                topics.add(topicList.get(i));
                oldTopics.add(topicList.get(i));
            }
        } catch (BusinessException ex) {
            Log.logException(ex);
        }
        
    }
    
    @FXML
    private void add(ActionEvent event){
        String finishTime = "";
        String startTime = "";
        String topicName = "";
        Member selectedMemmber = cbMember.getSelectionModel().getSelectedItem();
        finishTime = tfFinishTime.getText();
        startTime = tfStartTime.getText();
        topicName = tfTopic.getText();
        Topic topic = new Topic(topicName,startTime,finishTime,selectedMemmber.getName(),selectedMemmber.getProfessionalLicense(),meeting.getKey());
        if(validateTopic(topic)){
            topics.add(topic);
            cleanFields();
        }
    }
    
    @FXML
    private void delete(ActionEvent event){
        topics.remove(indexTopic);
        cleanFields();
    }
 
    private void cleanFields(){
        tfTopic.setText("");
        tfFinishTime.setText("");
        tfStartTime.setText("");
    }
    
    private Topic getSelectedTopic(){
       Topic topic = null;
        topic = tvTopic.getSelectionModel().getSelectedItem();
        getIndexTopic(topic);
        return topic;
    }
    
    private void getIndexTopic(Topic topicSelected){
        
        for(int i = 0; i < topics.size(); i++){
            topics.get(i).getTopicName();
            topicSelected.getTopicName();
            if(topics.get(i).equals(topicSelected)){
                indexTopic = i;
            }
        }
    }
    
    private void setSelectedTopic(){
        Topic topic = getSelectedTopic();
        indexTopic = topics.indexOf(topic);
        if(topic != null){
            try {
                MemberDAO memberDAO = new MemberDAO();
                Member newMember = memberDAO.getMemberByLicense(topic.getProfessionalLicense());
                tfTopic.setText(topic.getTopicName());
                tfFinishTime.setText(topic.getFinishTime());
                tfStartTime.setText(topic.getStartTime());
                cbMember.getSelectionModel().select(newMember);       
            } catch (BusinessException ex) {
                Log.logException(ex);
            }
        }
    }
    
    void initializeMembers() {
        try {
            MemberDAO memberDAO = new MemberDAO();
            ArrayList <Member> memberList = new ArrayList<Member>();
            memberList = memberDAO.getMembers(member.getKeyGroupAcademic());
            for( int i = 0; i<memberList.size(); i++) {
                members.add(memberList.get(i));
            }
            cbMember.getSelectionModel().selectFirst();
        } catch (BusinessException ex) {
            Log.logException(ex);
        }
    }
    
   @FXML 
   private void actionSave(){
       TopicDAO topicDAO = new TopicDAO();
        try {
          for(int i = 0; i < oldTopics.size(); i++){
               topicDAO.deletedSucessful(oldTopics.get(i));
           }  
          
           for(int i = 0; i < topics.size(); i++){
               topicDAO.savedSucessful(topics.get(i));
           }
           
           AlertMessage alertMessage = new AlertMessage();
           alertMessage.showAlertSuccesfulSave("La agenda");
      
        } catch (BusinessException ex) {
            Log.logException(ex);
            exceptionShow(ex);
        }
        
        Stage stage = (Stage)btSave.getScene().getWindow();
        stage.close();
        openTopicShow();
    }
   
  @FXML
   private void actionCancel(){
       Stage stage = (Stage)btCancel.getScene().getWindow();
       stage.close(); 
       openTopicShow();
   }
   
  @FXML
   private void actionUpdate(){
        String finishTime = "";
        String startTime = "";
        String topicName = "";
        Member newMember = cbMember.getSelectionModel().getSelectedItem();
        finishTime = tfFinishTime.getText();
        startTime = tfStartTime.getText();
        topicName = tfTopic.getText();
        Topic topic = new Topic(topics.get(indexTopic).getIdTopic(),topicName,startTime,finishTime,newMember.getName(),newMember.getProfessionalLicense(),meeting.getKey());
        if(validateTopic(topic)){    
            topics.set(indexTopic,topic);
        }
        cleanFields();
   }
   
       
    private void openTopicShow(){   
        Stage primaryStage = new Stage();
        try{
            URL url = new File("src/GUI/topicShow.fxml").toURI().toURL();
            FXMLLoader loader = new FXMLLoader(url);
            loader.setLocation(url);
            loader.load();
            TopicShowController topicShowController  = loader.getController();
            topicShowController.setMember(member);
            topicShowController.setMeeting(meeting);
            Parent root = loader.getRoot();
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
        }catch (IOException ex) {
            Log.logException(ex);
        }
    }
    
    private boolean validateTopic(Topic topic){
        boolean value = true;
        AlertMessage alertMessage = new AlertMessage();
        if(isEmptyFields(topic)){
            value = false;
            alertMessage.showAlertValidateFailed("Campos vacios");
        }
        
        if(invalidFields(topic)){
            value = false;
            alertMessage.showAlertValidateFailed("Campos inválidos");
        }
        
        if(!validateHours(topic)){
            value = false;
            alertMessage.showAlertValidateFailed("Hora inválida");
        }else if(validateStartTime(topic)){
            value = false;
            alertMessage.showAlertValidateFailed("La agenda no puede iniciar antes que la reunión programada a las: " + meeting.getHourStart());
        }
        
        if(repeatedTopic(topic)){
            value = false;
            alertMessage.showAlertValidateFailed("Tema repetido");
        }
        
        return value;
    }
    
    private boolean isEmptyFields(Topic topic){
        boolean value = false;
        if(topic.getStartTime().isEmpty()||topic.getFinishTime().isEmpty()||topic.getTopicName().isEmpty()){
            value = true;
        }
        
        return value;
    }
    
    private boolean invalidFields(Topic topic){
        boolean value = false;
        Validation validation = new Validation();
        if(validation.findInvalidField(topic.getTopicName())){
           value = true;
        }
        
        return value;
    }
    
    private boolean validateHours(Topic topic){
        boolean value = false;
        Validation validation = new Validation();
        if(validation.validateHour(topic.getFinishTime())&&validation.validateCorrectHours(topic.getStartTime(),topic.getFinishTime())){
            value = true;
        }
        
        return value;
    }
    
    private boolean validateStartTime(Topic topic){
        boolean value = false;
        Validation validation = new Validation();
        if(!validation.validateCorrectHours(meeting.getHourStart(), topic.getStartTime())){
            value = true;
        }
        return value;
    }
    
    private boolean repeatedTopic(Topic topic){
        Boolean value = false;
        int i = 0;
        while(!value && i<topics.size()){
            if(topics.get(i).equals(topic)){
                value = true;
            }
            
            i++;
        }
        
        return value;
    }
        
    private void exceptionShow(BusinessException ex){ 
         if(ex.getMessage().equals("DataBase connection failed ")){
                AlertMessage alertMessage = new AlertMessage();
                alertMessage.showAlertValidateFailed("Error en la conexion con la base de datos");
            }else{  
                Log.logException(ex);
            }
    }
    
    
}
