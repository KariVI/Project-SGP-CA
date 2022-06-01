package GUI;

import businessLogic.MemberDAO;
import domain.Member;
import domain.Topic;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import log.BusinessException;
import log.Log;


public class TopicRegisterController implements Initializable {
    private ObservableList<Member> members;
    private ObservableList<Topic> topics;
    @FXML ComboBox<Member> cbMember;
    @FXML TextField tfTopic;
    @FXML TextField tfStartTime;
    @FXML TextField tfFinishTime;
    @FXML TableColumn<Topic,String>  tcFinishTime;
    @FXML TableColumn<Topic,String>  tcStartTime;
    @FXML TableColumn<Topic,String>  tcMember;
    @FXML TableColumn<Topic,String> tcTopic;
    @FXML TableView<Topic> tvTopic;
    @FXML Button btDelete;
    @FXML Button btAdd;
    @FXML Button btSave;
    @FXML Button btCancel;
    private int idMeeting;
    private String hourMeeting;
    private Member member;
    private int indexTopic;
    private Topic topicSelected;
    private MeetingRegisterController meetingRegisterController;
    private ListChangeListener<Topic> tableTopicListener;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tcTopic.setCellValueFactory(new PropertyValueFactory<Topic,String>("topicName"));
        tcStartTime.setCellValueFactory(new PropertyValueFactory<Topic,String>("startTime"));
        tcFinishTime.setCellValueFactory(new PropertyValueFactory<Topic,String>("finishTime"));
        tcMember.setCellValueFactory(new PropertyValueFactory<Topic,String>("member"));
        topics = FXCollections.observableArrayList();
        tvTopic.setItems(topics);
        members = FXCollections.observableArrayList();
        cbMember.setItems(members);
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
    
    public void initializeMeeting(int idMeeting, MeetingRegisterController meetingRegisterController, String hour){
        this.idMeeting = idMeeting;
        this.meetingRegisterController = meetingRegisterController;
        this.hourMeeting = hour;
    }
    
    public void setMember(Member member){
        this.member = member;
        initializeMembers();
    }
    
    @FXML
    private void add(ActionEvent event){
        String finishTime = "";
        String startTime = "";
        String topicName = "";
        Member memberSelected = cbMember.getSelectionModel().getSelectedItem();
        finishTime = tfFinishTime.getText();
        startTime = tfStartTime.getText();
        topicName = tfTopic.getText();
        Topic topic = new Topic(topicName,startTime,finishTime,memberSelected.getName(),memberSelected.getProfessionalLicense(), idMeeting);
        if(validateTopic(topic)){
            topics.add(topic);
            cleanFields();
        }      
    }
    
    @FXML
    private void delete(ActionEvent event){
        topics.remove(indexTopic);
        tvTopic.refresh();
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
                exceptionShow(ex);
            }
        }
    }
    
    private void initializeMembers() {
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
            exceptionShow(ex);
        }
    }
   
   @FXML
   private void actionSave(){ 
       if(!topics.isEmpty()){   
           meetingRegisterController.setTopics(topics);   
           AlertMessage alertMessage = new AlertMessage();
           alertMessage.showAlertSuccesfulSave("La agenda");
           Stage stage = (Stage)btSave.getScene().getWindow();
           stage.close();
       }else{
           AlertMessage alertMessage = new AlertMessage();
           alertMessage.showAlertValidateFailed("Campos vacios");
       }
       
   }

   @FXML
   private void actionCancel(){
      Stage stage = (Stage)btCancel.getScene().getWindow();
      stage.close(); 
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
            alertMessage.showAlertValidateFailed("La agenda no puede iniciar antes que la reunión programada a las: " + hourMeeting);
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
        if(validation.validateHour(topic.getFinishTime())&&validation.validateCorrectHours(topic.getStartTime(), topic.getFinishTime())){
            value = true;
        }
        return value;
    }
    
    private boolean validateStartTime(Topic topic){
        boolean value = false;
        Validation validation = new Validation();
        if(!validation.validateCorrectHours(hourMeeting, topic.getStartTime())){
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
