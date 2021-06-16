
package GUI;

import domain.Member;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import log.Log;


public class MissingGroupAcademicController implements Initializable {
        @FXML private Button btAddGroupAcademic;
        private Member member;
        @FXML private Button btCancel;
        
    public void setMember(Member member) {
        this.member = member;
    }
    
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }   
    
     @FXML
    private void actionCancel(ActionEvent actionEvent){
        Stage stage = (Stage) btCancel.getScene().getWindow();
        stage.close();
        openLogin();
    }
    
    private void  openLogin(){   
        Stage primaryStage =  new Stage();
        try{
            
            URL url = new File("src/GUI/Login.fxml").toURI().toURL();
            try{
                FXMLLoader loader = new FXMLLoader(url);
                loader.setLocation(url);
                loader.load();
                LoginController login = loader.getController();
                Parent root = loader.getRoot();
                Scene scene = new Scene(root);
                primaryStage.setScene(scene);
                
            } catch (IOException ex) {
                Log.logException(ex);
            }
            primaryStage.show();
            
        } catch (MalformedURLException ex) {
                Log.logException(ex);
        }
    }
    
    @FXML
    private void actionAddGroupAcademic(ActionEvent actionEvent){ 
        Stage stage = (Stage) btAddGroupAcademic.getScene().getWindow();
        stage.close();
        
        try{ 
            Stage primaryStage= new Stage();
            URL url = new File("src/GUI/groupAcademicRegister.fxml").toURI().toURL();
           try{
              FXMLLoader loader = new FXMLLoader(url);
              loader.setLocation(url);
              loader.load();
              GroupAcademicRegisterController groupAcademicRegisterController =loader.getController(); 
              groupAcademicRegisterController.setMember(member);
              Parent root = loader.getRoot();
              Scene scene = new Scene(root);
              primaryStage.setScene(scene);       
            } catch (IOException ex) {
                    Log.logException(ex);
            }
            primaryStage.show();
       } catch (MalformedURLException ex) {
           Log.logException(ex);
       }
    
    }
    
}
