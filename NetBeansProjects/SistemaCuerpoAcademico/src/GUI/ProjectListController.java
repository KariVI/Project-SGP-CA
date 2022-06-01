package GUI;

import businessLogic.ProjectDAO;
import domain.Member;
import domain.Project;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
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
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import log.BusinessException;
import log.Log;

public class ProjectListController implements Initializable {

    @FXML private ListView<Project> lvProjects = new ListView<Project>();
    @FXML private Button btReturn;
    @FXML private Button btRegisterProject;
    private ObservableList <Project> projects ;
    Member member;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
         projects = FXCollections.observableArrayList();
         lvProjects.setItems(projects);
         lvProjects.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Project>() {
          @Override
          public void changed(ObservableValue<? extends Project> observaleValue, Project oldValue, Project newValue) {
             Project selectedProject = lvProjects.getSelectionModel().getSelectedItem();
             Stage stage = (Stage) lvProjects.getScene().getWindow();
             stage.close();
             openShowProject(selectedProject);
          }
      });
    }
    
    public void setMember(Member member){
        this.member = member;
        initializeProjects();
    }
    
    private void openShowProject(Project selectedProject){
        try {
          Stage primaryStage= new Stage();
          URL url = new File("src/GUI/ProjectShow.fxml").toURI().toURL();
          FXMLLoader loader = new FXMLLoader(url);
          loader.setLocation(url);
          loader.load();
          ProjectShowController projectShowController =loader.getController(); 
          projectShowController.setMember(member);
          projectShowController.setProject(selectedProject);
          Parent root = loader.getRoot();
          Scene scene = new Scene(root);
          primaryStage.setScene(scene);
          primaryStage.show();
        } catch (IOException ex) {
          Log.logException(ex);
        }
    }
    
    private void initializeProjects(){
       try {
            ProjectDAO projectDAO = new ProjectDAO();
            ArrayList <Project> projectList = new ArrayList<Project>();
            projectList = projectDAO.getProjects();
            for( int i = 0; i<projectList.size(); i++) {
                if(projectList.get(i).getGroupAcademicKey().equals(member.getKeyGroupAcademic())){
                    projects.add(projectList.get(i));
                } 
                
            }
            
        } catch (BusinessException | NullPointerException ex) {
            Log.logException(ex);
                 AlertMessage alertMessage  = new AlertMessage();
                alertMessage.showAlertValidateFailed("Error en la conexión con la base de datos");
                Stage stage = (Stage) btReturn.getScene().getWindow();
                stage.close();
                openLogin();
        }
       
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
                Log.logException(ex);
            }
            
            ProjectRegisterController  projectRegisterController = loader.getController();
            projectRegisterController.setMember(member);
            Parent root = loader.getRoot();
            Scene scene = new Scene(root);
            Stage primaryStage= new Stage();
            primaryStage.setScene(scene);
            Stage stage = (Stage) btRegisterProject.getScene().getWindow();
            stage.close();
            primaryStage.show();
        } catch (MalformedURLException ex) {
            Log.logException(ex);
        }
        
    }
    
    @FXML
    private void actionReturn(ActionEvent actionEvent){   
        Stage stage = (Stage) btReturn.getScene().getWindow();
        stage.close();
        openViewMenu();   
    }
    
    private void openViewMenu(){   
        Stage primaryStage = new Stage();
        try{
              URL url = new File("src/GUI/Menu.fxml").toURI().toURL();
              FXMLLoader loader = new FXMLLoader(url);
              loader.setLocation(url);
              loader.load();
              MenuController menu = loader.getController();
              menu.initializeMenu(member);
              Parent root = loader.getRoot();
              Scene scene = new Scene(root);
              primaryStage.setScene(scene);
              primaryStage.show();
            }catch (IOException ex) {
                Log.logException(ex);
            }
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
