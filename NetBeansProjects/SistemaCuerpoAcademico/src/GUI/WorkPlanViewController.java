/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import businessLogic.WorkPlanDAO;
import domain.Goal;
import domain.WorkPlan;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setWorkPlanId(1);

    }

    public void setWorkPlanId(int idWorkPlan) {
        this.idWorkPlan = idWorkPlan;
        initializeWorkPlan();
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

}
