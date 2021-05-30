
package GUI;

import domain.Member;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class MemberViewController implements Initializable {
    @FXML Button btReturn = new Button();
    @FXML Button btUpdate = new Button();
    @FXML Label lName = new Label();
    @FXML Label lRole = new Label();
    @FXML Label lProfessionalLicense = new Label();
    @FXML Label lDegree = new Label();
    @FXML Label lNameDegree = new Label();
    @FXML Label lDegreeYear = new Label();
    @FXML Label lUniversityName = new Label();
    private Member member ;

    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }

    
    public void initializeMember(Member member) {
         this.member = member;
         lName.setText(member.getName());
         lRole.setText(member.getRole());
         lProfessionalLicense.setText((member.getProfessionalLicense()));
         lDegree.setText(member.getDegree());
         lNameDegree.setText(member.getNameDegree());
         lDegreeYear.setText(Integer.toString(member.getDegreeYear()));
         lUniversityName.setText(member.getUniversityName());
    }

    
    @FXML
    public void actionReturn(){
        try {
            Stage primaryStage= new Stage();
            URL url = new File("src/GUI/memberList.fxml").toURI().toURL();
            FXMLLoader loader = new FXMLLoader(url);
            loader.setLocation(url);
            loader.load();
            MemberListController memberListController =loader.getController();
            Parent root = loader.getRoot();
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            Stage stage = (Stage) btReturn.getScene().getWindow();
            stage.close();
            primaryStage.show();
        } catch (IOException ex) {
            Logger.getLogger(MemberViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
 
    public void actionUpdate(){
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
            Logger.getLogger(MemberViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    

}
