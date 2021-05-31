/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import businessLogic.MinuteDAO;
import businessLogic.TopicDAO;
import domain.Meeting;
import domain.Topic;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import log.BusinessException;
import log.Log;

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
    @FXML Button btDelete;
    @FXML Button btAdd;
    @FXML Button btUpdate;
    private int idMeeting = 0;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tcTopic.setCellValueFactory(new PropertyValueFactory<Topic,String>("topicName"));
        tcStartTime.setCellValueFactory(new PropertyValueFactory<Topic,String>("startTime"));
        tcFinishTime.setCellValueFactory(new PropertyValueFactory<Topic,String>("finishTime"));
        tcMember.setCellValueFactory(new PropertyValueFactory<Topic,String>("professionalLicense"));
        topics = FXCollections.observableArrayList();      
        tvTopic.setItems(topics);
        
    }  
    public void initializeMeeting(Meeting meeting){
        idMeeting = meeting.getKey();
        initializeTopics();
    }
    
    public void initializeTopics(){
        TopicDAO topicDAO = new TopicDAO();
        try {
            ArrayList<Topic> topicList = new ArrayList<Topic>();
            System.out.println(idMeeting);
            topicList = topicDAO.getAgendaTopics(idMeeting);
            for(int i = 0; i < topicList.size(); i++){
                topics.add(topicList.get(i));
   
            }
        } catch (BusinessException ex) {
            Logger.getLogger(TopicShowController.class.getName()).log(Level.SEVERE, null, ex);
        }       
    }
     
    public void actionUpdate(){
          try {
            Stage primaryStage= new Stage();
            URL url = new File("src/GUI/topicModify.fxml").toURI().toURL();
            FXMLLoader loader = new FXMLLoader(url);
            loader.setLocation(url);
            loader.load();
            TopicModifyController topicModifyController = loader.getController();
            topicModifyController.initializeMeeting(idMeeting);
            Parent root = loader.getRoot();
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            Stage stage = (Stage) btUpdate.getScene().getWindow();
            stage.close();
            primaryStage.show();
        } catch (IOException ex) {
            Logger.getLogger(MemberViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
