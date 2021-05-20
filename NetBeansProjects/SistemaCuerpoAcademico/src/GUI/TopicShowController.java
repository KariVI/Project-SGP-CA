/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import businessLogic.TopicDAO;
import domain.Member;
import domain.Topic;
import java.net.URL;
import java.util.ArrayList;
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

/**
 * FXML Controller class
 *
 * @author Mariana
 */
public class TopicShowController implements Initializable {
    private ObservableList<Topic> topics;
    TextField tfTopic;
    TextField tfStartTime;
    TextField tfFinishTime;
    @FXML TableColumn<Topic,String>  tcFinishTime;
    @FXML TableColumn<Topic,String>  tcStartTime;
    @FXML TableColumn<Topic,String> tcMember;
    @FXML TableColumn<Topic,String> tcTopic;
    @FXML TableView<Topic> tvTopic;
    Button btDelete;
    Button btAdd;
    private int idMeeting = 1;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tcTopic.setCellValueFactory(new PropertyValueFactory<Topic,String>("topicName"));
        tcStartTime.setCellValueFactory(new PropertyValueFactory<Topic,String>("startTime"));
        tcFinishTime.setCellValueFactory(new PropertyValueFactory<Topic,String>("finishTime"));
        tcMember.setCellValueFactory(new PropertyValueFactory<Topic,String>("professionalLicense"));
        topics = FXCollections.observableArrayList();
        initializeTopics();
        tvTopic.setItems(topics);
    }  
    
    public void initializeTopics(){
        TopicDAO topicDAO = new TopicDAO();
        try {
            ArrayList<Topic> topicList = new ArrayList<Topic>();
            topicList = topicDAO.getAgendaTopics(idMeeting);
            for(int i = 0; i < topicList.size(); i++){
                topics.add(topicList.get(i));
                System.out.println(topicList.get(i).getProfessionalLicense());
            }
        } catch (BusinessException ex) {
            Logger.getLogger(TopicShowController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
