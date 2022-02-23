/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import domain.Action;
import domain.Goal;
import domain.Member;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Mariana
 */
public class WorkPlanActionViewController implements Initializable {
        @FXML private  TableView<Action> tvActions;
        @FXML private TableColumn<Goal,String>  tcGoal;
        @FXML private TableColumn<Action,Member> tcAction;
        @FXML private TableColumn<Action,String> tcDate;
        @FXML private TableColumn<Action,String> tcResponsable;
        @FXML private TableColumn<Action,String> tcResource;
         @FXML private  Button btReturn;
         private Goal goal;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
     
    }    
     public void setGoal(Goal goal){
        this.goal = goal;
    }
     
    @FXML
    private void actionReturn(ActionEvent actionEvent){   
        Stage stage = (Stage) btReturn.getScene().getWindow();
        stage.close();
    }
}
