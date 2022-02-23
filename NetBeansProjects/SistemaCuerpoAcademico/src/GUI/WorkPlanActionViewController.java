/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import domain.Member;
import domain.Prerequisite;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * FXML Controller class
 *
 * @author Mariana
 */
public class WorkPlanActionViewController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        @FXML private TableView<Action> tvActions;
        @FXML private TableColumn<Goal,String>  tcGoal;
        @FXML private TableColumn<Action,Member> tcAction;
        @FXML private TableColumn<Action,String> tcDate;
        @FXML private TableColumn<Action,String> tcResponsable;
        @FXML private TableColumn<Action,String> tcResource;
    }    
    
}
