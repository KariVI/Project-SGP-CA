/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import businessLogic.GoalDao;
import businessLogic.WorkPlanDAO;
import domain.Goal;
import domain.Meeting;
import domain.WorkPlan;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
        tvGoals.setOnMouseClicked(e -> {
            clicOnGoal();
        });
    }

    public void setWorkPlanId(int idWorkPlan) {
        this.idWorkPlan = idWorkPlan;
        initializeWorkPlan();
        initializeGoals();
    }

    @FXML
    private void actionReturn(ActionEvent event) {
        Stage stage = (Stage) btBack.getScene().getWindow();
        stage.close();
        openWorkPlanList();
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

    private void clicOnGoal() {
        Goal goal = tvGoals.getSelectionModel().getSelectedItem();
        Stage stage = (Stage) tvGoals.getScene().getWindow();
        openWorkActionView(goal);
    }

    private void openWorkPlanList() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("WorkPlanList.fxml"));
            Parent root = loader.load();
            WorkPlanActionViewController workPlanListController = loader.getController();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.showAndWait();
        } catch (IOException ex) {
            Log.logException(ex);
        }
    }

    private void openWorkPlanModify() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("WorkPlanModify.fxml"));
            Parent root = loader.load();
            WorkPlanActionViewController workPlanActionViewController = loader.getController();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.showAndWait();
        } catch (IOException ex) {
            Log.logException(ex);
        }
    }

    private void openWorkActionView(Goal goal) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("WorkPlanActionView.fxml"));
            Parent root = loader.load();
            WorkPlanActionViewController workPlanActionViewController = loader.getController();
            workPlanActionViewController.setGoal(goal);
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.showAndWait();
        } catch (IOException ex) {
            Log.logException(ex);
        }
    }

}
