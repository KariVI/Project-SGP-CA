
package GUI;

import domain.Member;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import log.Log;


public class MemberViewController implements Initializable {
    @FXML Button btReturn = new Button();
    @FXML Button btUpdate = new Button();
    @FXML Label lbName = new Label();
    @FXML Label lbRole = new Label();
    @FXML Label lbProfessionalLicense = new Label();
    @FXML Label lbDegree = new Label();
    @FXML Label lbNameDegree = new Label();
    @FXML Label lbDegreeYear = new Label();
    @FXML Label lbUniversityName = new Label();
    private Member member ;
  
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }
    
    public void initializeMember(Member member) {
         this.member = member;
         lbName.setText(member.getName());
         lbRole.setText(member.getRole());
         lbProfessionalLicense.setText((member.getProfessionalLicense()));
         lbDegree.setText(member.getDegree());
         lbNameDegree.setText(member.getNameDegree());
         lbDegreeYear.setText(Integer.toString(member.getDegreeYear()));
         lbUniversityName.setText(member.getUniversityName());
    }
   
    @FXML
    private void actionReturn(){
        Stage stage = (Stage) btReturn.getScene().getWindow();
        stage.close();
        openListMember();
    }
 
    private void actionUpdate(){
        try {
            Stage primaryStage= new Stage();
            URL url = new File("src/GUI/memberModify.fxml").toURI().toURL();
            FXMLLoader loader = new FXMLLoader(url);
            loader.setLocation(url);
            loader.load();
            MemberModifyController memberModifyController = loader.getController();
            memberModifyController.initializeMember(member);
            Parent root = loader.getRoot();
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            Stage stage = (Stage) btReturn.getScene().getWindow();
            stage.close();
            primaryStage.show();
        } catch (IOException ex) {
            Log.logException(ex);
        }
    }
    
    private void openListMember(){   
      Stage primaryStage = new Stage();
      try{
            URL url = new File("src/GUI/MemberList.fxml").toURI().toURL();
            FXMLLoader loader = new FXMLLoader(url);
            loader.setLocation(url);
            loader.load();
            MemberListController memberListController  = loader.getController();
            memberListController.setMember(member);
            Parent root = loader.getRoot();
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
     }catch (IOException ex) {
        Log.logException(ex);
     }
   }  

}
