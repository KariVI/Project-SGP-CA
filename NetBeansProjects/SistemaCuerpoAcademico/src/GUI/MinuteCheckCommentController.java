/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import businessLogic.MinuteDAO;
import domain.Member;
import domain.Minute;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import log.BusinessException;

/**
 * FXML Controller class
 *
 * @author Mariana
 */
public class MinuteCheckCommentController implements Initializable {

    @FXML private AnchorPane paneMembers;
    Minute minute;
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }  
    
    public void setMinute(Minute minute){
        this.minute = minute;
        setMembers();
    }
    
    private void setMembers(){
        try {
            GridPane gridPane= new GridPane();
            MinuteDAO minuteDAO = new MinuteDAO();
            ArrayList<Member> memberListApprove = minuteDAO.getMembersApprove(minute);
            for(int i = 0; i < memberListApprove.size(); i++){
                System.out.println(memberListApprove.get(i).getName());
                Label label = new Label(memberListApprove.get(i).getName());
                gridPane.add(label,1,i);
            }
            paneMembers.getChildren().add(gridPane);
        } catch (BusinessException ex) {
            Logger.getLogger(MinuteCheckCommentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
