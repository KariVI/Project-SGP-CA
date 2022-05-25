/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import businessLogic.ActionDAO;
import businessLogic.GoalDao;
import domain.Action;
import domain.Goal;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import log.BusinessException;
import log.Log;

/**
 * FXML Controller class
 *
 * @author Mariana
 */
public class WorkPlanActionViewController implements Initializable {
        @FXML private  TableView<Action> tvActions;

        @FXML private TableColumn<Action,String> tcAction;
        @FXML private TableColumn<Action,String> tcDate;
        @FXML private TableColumn<Action,String> tcResponsable;
        @FXML private TableColumn<Action,String> tcResource;
        @FXML private  Button btReturn;
        @FXML private  Label lbGoal;
        private Goal goal;
        private ObservableList<Action> actions;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tcAction.setCellValueFactory(new PropertyValueFactory<Action,String>("description"));
        tcDate.setCellValueFactory(new PropertyValueFactory<Action,String>("dateEnd"));
        tcResponsable.setCellValueFactory(new PropertyValueFactory<Action,String>("memberInCharge"));
        tcResource.setCellValueFactory(new PropertyValueFactory<Action,String>("resource"));
        initializeActionTable();
    } 
    
    public void initializeActionTable(){
        actions =FXCollections.observableArrayList();
       
        tvActions.setItems(actions);
    }
     public void setGoal(Goal goal){
        this.goal = goal;
        lbGoal.setText(goal.getDescription());
         initializeActions();
    }
     
    @FXML
    private void actionReturn(ActionEvent actionEvent){   
        Stage stage = (Stage) btReturn.getScene().getWindow();
        stage.close();
    }

    private void initializeActions() {
             ActionDAO goalDAO = new ActionDAO();
        try{
            ArrayList<Action> actionList = goalDAO.getActionsByGoalId(goal.getId());
            for(int i = 0; i < actionList.size(); i++){    
                actions.add(actionList.get(i));
            }
        } catch (BusinessException ex) {
            Log.logException(ex);
        }
    } 
}
