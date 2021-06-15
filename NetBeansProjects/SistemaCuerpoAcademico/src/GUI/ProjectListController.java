package GUI;

import businessLogic.ProjectDAO;
import domain.Project;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import log.BusinessException;
import log.Log;

public class ProjectListController implements Initializable {

    @FXML private ListView<Project> lvProjects = new ListView<Project>();
    @FXML private Button btClose;
    @FXML private Button btRegisterProject;
    private ObservableList <Project> projects ;
    private String groupAcademicKey;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
         projects = FXCollections.observableArrayList();
         lvProjects.setItems(projects);
         lvProjects.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Project>() {
          @Override
          public void changed(ObservableValue<? extends Project> observaleValue, Project oldValue, Project newValue) {
             Project selectedProject = lvProjects.getSelectionModel().getSelectedItem();
            try {
            Stage primaryStage= new Stage();
            URL url = new File("src/GUI/ProjectShow.fxml").toURI().toURL();
            FXMLLoader loader = new FXMLLoader(url);
              loader.setLocation(url);
              loader.load();
              ProjectShowController projectShowController =loader.getController();      
              projectShowController.setProject(selectedProject);
              Parent root = loader.getRoot();
              Scene scene = new Scene(root);
              primaryStage.setScene(scene);
              Stage stage = (Stage) lvProjects.getScene().getWindow();
              stage.close();
              primaryStage.show();
        } catch (IOException ex) {
            Log.logException(ex);
        }
          }
      });
    }
    
    public void setGroupAcademicKey(String groupAcademicKey){
        this.groupAcademicKey = groupAcademicKey;
        initializeProjects();
    }
    
    private void initializeProjects(){
       try {
            ProjectDAO projectDAO = new ProjectDAO();
            ArrayList <Project> projectList = new ArrayList<Project>();
            projectList = projectDAO.getProjects();
            for( int i = 0; i<projectList.size(); i++) {
                if(projectList.get(i).getGroupAcademicKey().equals(groupAcademicKey)){
                    projects.add(projectList.get(i));
                }         
            }
        } catch (BusinessException ex) {
            Logger.getLogger(ProjectListController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    private void actionClose(ActionEvent event) {
    }

    @FXML
    private void actionRegister(ActionEvent event) {
        try {
            URL url = new File("src/GUI/ProjectRegister.fxml").toURI().toURL();
            FXMLLoader loader = new FXMLLoader(url);
            loader.setLocation(url);
            try {
                loader.load();
            } catch (IOException ex) {
                Logger.getLogger(ProjectListController.class.getName()).log(Level.SEVERE, null, ex);
            }
            ProjectRegisterController  projectRegisterController = loader.getController();
            projectRegisterController.setGroupAcademic(groupAcademicKey);
            Parent root = loader.getRoot();
            Scene scene = new Scene(root);
            Stage primaryStage= new Stage();
            primaryStage.setScene(scene);
            Stage stage = (Stage) btRegisterProject.getScene().getWindow();
            stage.close();
            primaryStage.show();
        } catch (MalformedURLException ex) {
            Logger.getLogger(ProjectListController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
