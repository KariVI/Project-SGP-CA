/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import businessLogic.MinuteDAO;
import domain.Member;
import domain.Minute;
import domain.MinuteComment;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import log.BusinessException;
import log.Log;

/**
 * FXML Controller class
 *
 * @author Mariana
 */
public class MinuteCommentController implements Initializable {

    private Member member;
    private int idMinute = 0;
    @FXML TextArea taComment;
    @FXML Button btSave;
    @FXML Button btCancel;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    } 
    public void setMinute(Minute minute){
        this.idMinute = minute.getIdMinute();
    }
    
    @FXML
    public void actionSave(){
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
        }
    }
    
    
    public void actionCancel(){
         Stage stage = (Stage) btCancel.getScene().getWindow();
         stage.close();
        
    }
    
    public void setMember(Member member){
        this.member = member;
    }
}
