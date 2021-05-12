/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import domain.Member;
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
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Mariana
 */
public class MemberViewController implements Initializable,LoadData {
    @FXML Button btClose = new Button();
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

    @Override
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
    public void close() {
        Stage stage = (Stage)btClose.getScene().getWindow();
        stage.close();
    }
    
 
    public void goUpdate(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MemberModify.fxml"));
            Parent root = loader.load();
            MemberModifyController controller = loader.getController();
            controller.initializeMember(member);
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(MemberViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
