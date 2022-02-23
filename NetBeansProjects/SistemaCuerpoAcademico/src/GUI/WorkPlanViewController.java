/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import businessLogic.GoalDao;
import businessLogic.WorkPlanDAO;
import domain.Goal;
import domain.WorkPlan;
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
import log.BusinessException;
import log.Log;

/**
 * FXML Controller class
 *
 * @author gustavor
 */
public class WorkPlanViewController implements Initializable {

    @FXML
    private Label lbTimePeriod;
    @FXML
    private Label lbObjective;
    @FXML
    private TableView<Goal> tvGoals;
    @FXML
    private Button btModify;
    @FXML
    private Button btBack;

    int idWorkPlan;
    WorkPlan workPlan;
    ObservableList<Goal> goalList;
    @FXML
    private TableColumn<Goal, String> tcGoals;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tcGoals.setCellValueFactory(new PropertyValueFactory<Goal, String>("description"));
        setWorkPlanId(1);

    }

    public void setWorkPlanId(int idWorkPlan) {
        this.idWorkPlan = idWorkPlan;
        initializeWorkPlan();
        initializeGoals();
    }

    @FXML
    private void actionReturn(ActionEvent event) {
    }

    @FXML
    private void actionModify(ActionEvent event) {
    }

    private void initializeWorkPlan() {
        WorkPlanDAO workPlanDAO = new WorkPlanDAO();
        try {
            workPlan = workPlanDAO.getWorkPlan(idWorkPlan);
            lbTimePeriod.setText(workPlan.getTimePeriod());
            lbObjective.setText(workPlan.getObjetiveGeneral());
        } catch (BusinessException ex) {
            Log.logException(ex);
        }
    }

    private void initializeGoals() {
        GoalDao goalsDAO = new GoalDao();
        try {
            goalList = FXCollections.observableArrayList(goalsDAO.getGoalsByWorkPlanId(idWorkPlan));
            tvGoals.setItems(goalList);
        } catch (BusinessException ex) {
            Log.logException(ex);
        }
        
    }

}
