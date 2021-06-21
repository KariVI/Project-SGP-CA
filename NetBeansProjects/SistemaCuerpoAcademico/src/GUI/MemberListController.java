
package GUI;

import businessLogic.MemberDAO;
import domain.Member;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import log.BusinessException;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import log.Log;


public class MemberListController implements  Initializable {
   
    @FXML private Button btClose;
    @FXML private Button btRegisterMember;
    @FXML private ListView<Member> lvMembers = new ListView<Member>();
    private ObservableList <Member> members ;
    private Member loginMember;
    
    public void setMember(Member member){
        this.loginMember = member;
        initializeMembers();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      members = FXCollections.observableArrayList();   
      lvMembers.setItems(members);
      lvMembers.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Member>() {
          @Override
          public void changed(ObservableValue<? extends Member> observaleValue, Member oldValue, Member newValue) {
            Member selectedMember = lvMembers.getSelectionModel().getSelectedItem();
            Stage stage = (Stage) lvMembers.getScene().getWindow();
            stage.close();
            openMemberShow(selectedMember);
          }
      });
      
    }
    
    public void openMemberShow(Member selectedMember){
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
            primaryStage.show();
        } catch (IOException ex) {
            Log.logException(ex);
        }       
    }

    @FXML
    void initializeMembers() {
        try {
            MemberDAO memberDAO = new MemberDAO();
            ArrayList <Member> memberList = new ArrayList<Member>();
            memberList = memberDAO.getMembers(loginMember.getKeyGroupAcademic());
            for( int i = 0; i<memberList.size(); i++) {
                    members.add(memberList.get(i));     
            }
            
        } catch (BusinessException ex) {
            Log.logException(ex);
        }      
    }
    
    @FXML
    public void actionClose() {
        Stage stage = (Stage)btClose.getScene().getWindow();
        stage.close();
        openViewMenu();
    }
    
    private void openViewMenu(){   
        Stage primaryStage = new Stage();
        try{
              URL url = new File("src/GUI/Menu.fxml").toURI().toURL();
              FXMLLoader loader = new FXMLLoader(url);
              loader.setLocation(url);
              loader.load();
              MenuController menu = loader.getController();
              menu.initializeMenu(loginMember);
              Parent root = loader.getRoot();
              Scene scene = new Scene(root);
              primaryStage.setScene(scene);
              primaryStage.show();
            }catch (IOException ex) {
                Log.logException(ex);
            }
    }
    
    @FXML 
    public void actionRegister(){
        try {
            Stage primaryStage= new Stage();
            URL url = new File("src/GUI/memberRegister.fxml").toURI().toURL();
            FXMLLoader loader = new FXMLLoader(url);
            loader.setLocation(url);
            loader.load();
            MemberRegisterController memberRegisterController = loader.getController();
            memberRegisterController.setMember(loginMember);
            Parent root = loader.getRoot();
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            Stage stage = (Stage) btRegisterMember.getScene().getWindow();
            stage.close();
            primaryStage.show();
        } catch (IOException ex) {
            Log.logException(ex);
        }
    }
    
}
