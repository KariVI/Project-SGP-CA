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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import log.BusinessException;
import log.Log;


public class TopicRegisterController implements Initializable {
    private ObservableList<Member> members;
    private ObservableList<Topic> topics;
    @FXML ComboBox<Member> cbMember;
    @FXML TextFieldLimited tfTopic;
    @FXML TextFieldLimited tfStartTime;
    @FXML TextFieldLimited tfFinishTime;
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
    private Member member;
    private int indexTopic;
    private MeetingRegisterController meetingRegisterController;
    private ListChangeListener<Topic> tableTopicListener;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tfTopic.setMaxlength(200);
        tfStartTime.setMaxlength(5);
        tfFinishTime.setMaxlength(5);
        tcTopic.setCellValueFactory(new PropertyValueFactory<Topic,String>("topicName"));
        tcStartTime.setCellValueFactory(new PropertyValueFactory<Topic,String>("startTime"));
        tcFinishTime.setCellValueFactory(new PropertyValueFactory<Topic,String>("finishTime"));
        tcMember.setCellValueFactory(new PropertyValueFactory<Topic,String>("professionalLicense"));
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
    
    public void initializeMeeting(int idMeeting, MeetingRegisterController meetingRegisterController){
        this.idMeeting = idMeeting;
        this.meetingRegisterController = meetingRegisterController;
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
        Topic topic = new Topic(topicName,startTime,finishTime,memberSelected.getProfessionalLicense(), idMeeting);
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
    
    @FXML
    private void cleanFields(){
        tfTopic.setText("");
        tfFinishTime.setText("");
        tfStartTime.setText("");
    }
    
    private Topic getSelectedTopic(){
        Topic topic = null;
        int tableSize = 1;
        if(tvTopic != null){
            List<Topic> topicTable = tvTopic.getSelectionModel().getSelectedItems();
            if(topicTable.size() == tableSize){
                topic = topicTable.get(0);
            }
            
        }
        
        return topic;
    }
    
    private void setSelectedTopic(){
        Topic topic = getSelectedTopic();
        if(topic != null){
            indexTopic = topics.indexOf(topic);
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
        }
    }
   
   @FXML
   private void actionSave(){ 
       if(!topics.isEmpty()){
        meetingRegisterController.setTopics(topics);       
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
    
}
