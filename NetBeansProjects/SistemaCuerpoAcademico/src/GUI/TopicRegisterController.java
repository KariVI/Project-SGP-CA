package GUI;

import businessLogic.MemberDAO;
import domain.Member;
import domain.Topic;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import log.BusinessException;


public class TopicRegisterController implements Initializable {
    private ObservableList<Member> members;
    private ObservableList<Topic> topics;
    @FXML ComboBox<Member> cbMember;
    @FXML TextField tfTopic;
     @FXML TextField tfStartTime;
      @FXML TextField tfFinishTime;
    @FXML TableColumn tcFinishTime;
    @FXML TableColumn tcStartTime;
    @FXML TableColumn tcMember;
    @FXML TableColumn<Topic,String> tcTopic;
    @FXML TableView<Topic> tvTopic;
    @FXML Button btDelete;
    @FXML Button btAdd;
    private int idMeeting = 1;
    private int indexTopic;
    private ListChangeListener<Topic> tableTopicListener;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tcTopic.setCellValueFactory(new PropertyValueFactory<Topic,String>("topicName"));
        tcStartTime.setCellValueFactory(new PropertyValueFactory<Topic,String>("startTime"));
        tcFinishTime.setCellValueFactory(new PropertyValueFactory<Topic,String>("finishTime"));
        tcMember.setCellValueFactory(new PropertyValueFactory<Topic,String>("professionalLicense"));
        topics = FXCollections.observableArrayList();
        tvTopic.setItems(topics);
        members = FXCollections.observableArrayList();
        initializeMembers();
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
    
    @FXML
    private void add(ActionEvent event){
        String finishTime = "", startTime = "", topicName = "";
        Member member = cbMember.getSelectionModel().getSelectedItem();
        finishTime = tfFinishTime.getText();
        startTime = tfFinishTime.getText();
        topicName = tfTopic.getText();
        Topic topic = new Topic(topicName,startTime,finishTime,member.getProfessionalLicense(),idMeeting);
        topics.add(topic);
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
                Logger.getLogger(TopicRegisterController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    void initializeMembers() {
        try {
            MemberDAO memberDAO = new MemberDAO();
            ArrayList <Member> memberList = new ArrayList<Member>();
            memberList = memberDAO.getMembers();
            for( int i = 0; i<memberList.size(); i++) {
                members.add(memberList.get(i));
            }
        } catch (BusinessException ex) {
            Logger.getLogger(MemberListController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
   
}
