/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import businessLogic.MemberDAO;
import domain.Member;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import log.BusinessException;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;

/**
 * FXML Controller class
 *
 * @author Mariana
 */
public class MemberListController implements  Initializable {
    @FXML private GridPane gpMembers = new GridPane();
    @FXML private Button btClose;
    @FXML private ListView<Member> lvMembers = new ListView<Member>();
    private ObservableList <Member> members ;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      members = FXCollections.observableArrayList();
      initializeMembers();
      lvMembers.setItems(members);
      lvMembers.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Member>() {
          @Override
          public void changed(ObservableValue<? extends Member> observaleValue, Member oldValue, Member newValue) {
              Member selectedMember = lvMembers.getSelectionModel().getSelectedItem();
            try {
            Stage primaryStage= new Stage();
            URL url = new File("src/GUI/memberView.fxml").toURI().toURL();
            FXMLLoader loader = new FXMLLoader(url);
              loader.setLocation(url);
              loader.load();
              MemberViewController memberViewController =loader.getController();      
              memberViewController.initializeMember(selectedMember);
              Parent root = loader.getRoot();
              Scene scene = new Scene(root);
              primaryStage.setScene(scene);
              Stage stage = (Stage) lvMembers.getScene().getWindow();
              stage.close();
              primaryStage.show();
        } catch (IOException ex) {
            Logger.getLogger(Launcher.class.getName()).log(Level.SEVERE, null, ex);
        }
          }
      });
    }  

    @FXML
    void initializeMembers() {
        try {
            MemberDAO md = new MemberDAO();
            ArrayList <Member> memberList = new ArrayList<Member>();
            memberList = md.getMembers();
            for( int i = 0; i<memberList.size(); i++) {
                members.add(memberList.get(i));
            }
        } catch (BusinessException ex) {
            Logger.getLogger(MemberListController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    public void close() {
        Stage stage = (Stage)btClose.getScene().getWindow();
        stage.close();
    }


}
