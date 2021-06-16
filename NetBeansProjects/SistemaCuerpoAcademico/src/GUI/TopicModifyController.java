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
        tcMember.setCellValueFactory(new PropertyValueFactory<Topic,String>("professionalLicense"));
        topics = FXCollections.observableArrayList();
        oldTopics = FXCollections.observableArrayList();
        tvTopic.setItems(topics);
       
        members = FXCollections.observableArrayList();
        initializeMembers();
        cbMember.setItems(members);
        cbMember.getSelectionModel().selectFirst();
        tvTopic.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                 setSelectedTopic();
             }
            }
        );

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
    }
    
    public void initializeTopics(){
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
        Member member = cbMember.getSelectionModel().getSelectedItem();
        finishTime = tfFinishTime.getText();
        startTime = tfFinishTime.getText();
        topicName = tfTopic.getText();
        Topic topic = new Topic(topicName,startTime,finishTime,member.getProfessionalLicense(),meeting.getKey());
        if(validateTopic(topic)){
            topics.add(topic);
        }
        cleanFields();
    }
    
    @FXML
    private void delete(ActionEvent event){
        topics.remove(indexTopic);
        cleanFields();
    }

    @FXML
    private void cleanFields(){
        tfTopic.setText("");
        tfFinishTime.setText("");
        tfStartTime.setText("");
    }
    
    private Topic getSelectedTopic(){
        Topic topic = null;
        int tamTable = 1;
        if(tvTopic != null){
            List<Topic> topicTable = tvTopic.getSelectionModel().getSelectedItems();
            if(topicTable.size() == tamTable){
                topic = topicTable.get(0);
            }
        }
        return topic;
    }
    
    private void setSelectedTopic(){
        Topic topic = getSelectedTopic();
        indexTopic = topics.indexOf(topic);
        if(topic != null){
            try {
                MemberDAO memberDAO = new MemberDAO();
                Member member = memberDAO.getMemberByLicense(topic.getProfessionalLicense());
                tfTopic.setText(topic.getTopicName());
                tfFinishTime.setText(topic.getFinishTime());
                tfStartTime.setText(topic.getStartTime());
                cbMember.getSelectionModel().select(member);
                
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
        } catch (BusinessException ex) {
          Log.logException(ex);
        }
    }
    
   public void actionSave(){
       TopicDAO topicDAO = new TopicDAO();
        try {
          for(int i = 0; i < oldTopics.size(); i++){
               topicDAO.delete(oldTopics.get(i));
           }  
          
           for(int i = 0; i < topics.size(); i++){
               topicDAO.save(topics.get(i));
           }
           
           AlertMessage alertMessage = new AlertMessage();
           alertMessage.showAlertSuccesfulSave("Los temas fueron registrados con éxito");
           
         } catch (BusinessException ex) {
             Log.logException(ex);
         }
        
        Stage stage = (Stage)btSave.getScene().getWindow();
        stage.close();
        openTopicShow();
    }
   
   public void actionCancel(){
      Stage stage = (Stage)btCancel.getScene().getWindow();
      stage.close(); 
      openTopicShow();
   }
   
   public void actionUpdate(){
        String finishTime = "", startTime = "", topicName = "";
        Member member = cbMember.getSelectionModel().getSelectedItem();
        finishTime = tfFinishTime.getText();
        startTime = tfFinishTime.getText();
        topicName = tfTopic.getText();
        Topic topic = new Topic(topics.get(indexTopic).getIdTopic(),topicName,startTime,finishTime,member.getProfessionalLicense(),meeting.getKey());
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
    
    public boolean validateTopic(Topic topic){
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
            alertMessage.showAlertValidateFailed("Ingresa la hora en formato HH:MM ");
        }
        
        if(repeatedTopic(topic)){
            value = false;
            alertMessage.showAlertValidateFailed("Tema repetido");
        }
        return value;
    }
    
    public boolean isEmptyFields(Topic topic){
        boolean value = false;
        if(topic.getStartTime().isEmpty()||topic.getFinishTime().isEmpty()||topic.getTopicName().isEmpty()){
            value = true;
        }
        return value;
    }
    
    public boolean invalidFields(Topic topic){
        boolean value = false;
        Validation validation = new Validation();
        if(validation.findInvalidField(topic.getTopicName())){
           value = true;
        }
        return value;
    }
    
    public boolean validateHours(Topic topic){
        boolean value = false;
        Validation validation = new Validation();
        if(validation.validateHour(topic.getFinishTime())){
            value = true;
        }
        return value;
    }
    
    public boolean repeatedTopic(Topic topic){
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
    
}
