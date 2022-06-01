
package GUI;

import businessLogic.MinuteDAO;
import domain.Member;
import domain.Minute;
import domain.MinuteComment;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import log.BusinessException;
import log.Log;

public class MinuteCommentController implements Initializable {

    private Member member;
    private int idMinute = 0;
    @FXML TextArea taComment;
    @FXML Button btSave;
    @FXML Button btCancel;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    } 
    
    public void setMinute(Minute minute){
        this.idMinute = minute.getIdMinute();
    }
    
    @FXML
    private void actionSave(){
        try {
            String comment = taComment.getText();
            MinuteDAO minuteDAO = new MinuteDAO();
            MinuteComment minuteComment = new MinuteComment(comment,member.getProfessionalLicense(),idMinute);
            minuteDAO.disapproveMinute(minuteComment);
            AlertMessage alertMessage= new AlertMessage();
            alertMessage.showAlertSuccesfulSave("El comentario");
            Stage stage = (Stage) btSave.getScene().getWindow();
            stage.close();
        } catch (BusinessException ex) {
            Log.logException(ex);
                 AlertMessage alertMessage  = new AlertMessage();
                alertMessage.showAlertValidateFailed("Error en la conexión con la base de datos");
                Stage stage = (Stage) btCancel.getScene().getWindow();
                stage.close();
                openLogin();
        }
    }
    
    @FXML
    private void actionCancel(){
         Stage stage = (Stage) btCancel.getScene().getWindow();
         stage.close();
        
    }
    
    public void setMember(Member member){
        this.member = member;
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

    
}
