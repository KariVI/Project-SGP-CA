
package GUI;

import businessLogic.GroupAcademicDAO;
import domain.GroupAcademic;
import domain.Member;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import log.BusinessException;
import log.Log;


public class MenuController implements Initializable {

    @FXML private AnchorPane anchorPaneMenu;
    @FXML private Button btConsult;
    @FXML private ListView lvOptions;
    @FXML private Button btExit;
    private GroupAcademic groupAcademic;
    private ObservableList<String> options ;
    private Member member;
    
    
    
  
    
    @FXML
    private void actionExit(ActionEvent actionEvent){
        Stage stage = (Stage) btExit.getScene().getWindow();
        stage.close();
        openLogin();
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
    
    @FXML 
    private void actionQuery(ActionEvent actionEvent) throws BusinessException{  
        Stage stage = (Stage) btConsult.getScene().getWindow();
              stage.close(); 
        try{ 
            Stage primaryStage= new Stage();
            URL url = new File("src/GUI/groupAcademicShow.fxml").toURI().toURL();
           try{
              FXMLLoader loader = new FXMLLoader(url);
              loader.setLocation(url);
              loader.load();
                GroupAcademicShowController groupAcademicShowController =loader.getController();
                GroupAcademicDAO groupAcademicDAO= new GroupAcademicDAO();
                 GroupAcademic groupAcademic= groupAcademicDAO.getGroupAcademicById(member.getKeyGroupAcademic());
                groupAcademicShowController.setGroupAcademic(groupAcademic);
                groupAcademicShowController.setMember(member);
                groupAcademicShowController.initializeGroupAcademic();
                Parent root = loader.getRoot();
                Scene scene = new Scene(root);
                primaryStage.setScene(scene);
           }catch (IOException ex) {
                    Log.logException(ex);
            }
            primaryStage.show();
       } catch (MalformedURLException ex) {
           Log.logException(ex);
       }
    }
         
    private void fillOptions() {   
        options.add("Anteproyectos");
        options.add("Miembros");
         options.add("Trabajos recepcionales");
        options.add("Proyectos");
        options.add("Reuniones");
       
    }
    
    public void initializeMenu(Member member){
        this.member = member;
        fillOptions();
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        options = FXCollections.observableArrayList();     
      lvOptions.setItems(options);
      lvOptions.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
         
          public void changed(ObservableValue<? extends String> observaleValue, String oldValue, String newValue) {
             String selectedOption = (String) lvOptions.getSelectionModel().getSelectedItem();
               Stage stage = (Stage) lvOptions.getScene().getWindow();
               stage.close();
               showViewOption(selectedOption);

          }
      });
    
    }  
    
 
    
    private void showViewOption(String option){  
        FXMLLoader loader;
        Parent root;
        switch(option){ 
            case "Anteproyectos": ;
                try {
               loader = new FXMLLoader(getClass().getResource("PreliminarProjectList.fxml"));
               root = loader.load();
               PreliminarProjectListController preliminarProjectListController = loader.getController();
                preliminarProjectListController.setMember(member);
               String keyGroupAcademic = member.getKeyGroupAcademic();
               preliminarProjectListController.setKeyGroupAcademic(keyGroupAcademic);
              
               Scene scene = new Scene(root);
               Stage stage = new Stage();
               stage.setScene(scene);
               stage.initModality(Modality.APPLICATION_MODAL);
               stage.showAndWait();
               } catch (IOException ex) {
                   Log.logException(ex);
               }
            break;
            
            case "Miembros":;
                   try {
               loader = new FXMLLoader(getClass().getResource("MemberList.fxml"));
               root = loader.load();
               MemberListController memberListController = loader.getController();
               memberListController.setMember(member);
               Scene scene = new Scene(root);
               Stage stage = new Stage();
               stage.setScene(scene);
               stage.initModality(Modality.APPLICATION_MODAL);
               stage.showAndWait();
               } catch (IOException ex) {
                   Log.logException(ex);
               }
            break;
            
            case "Reuniones":;
              try {
               loader = new FXMLLoader(getClass().getResource("MeetingList.fxml"));
               root = loader.load();
               MeetingListController meetingListController = loader.getController();
               meetingListController.setMember(member);
               Scene scene = new Scene(root);
               Stage stage = new Stage();
               stage.setScene(scene);
               stage.initModality(Modality.APPLICATION_MODAL);
               stage.showAndWait();
               } catch (IOException ex) {
                   Log.logException(ex);
               }
            break;
            
            case "Trabajos recepcionales":; 
               try {
               loader = new FXMLLoader(getClass().getResource("ReceptionWorkList.fxml"));
               root = loader.load();
               ReceptionWorkListController receptionWorkListController = loader.getController();
               receptionWorkListController.setMember(member); 
               String keyGroupAcademic = member.getKeyGroupAcademic();
               receptionWorkListController.setKeyGroupAcademic(keyGroupAcademic);
               
               Scene scene = new Scene(root);
               Stage stage = new Stage();
               stage.setScene(scene);
               stage.initModality(Modality.APPLICATION_MODAL);
               stage.showAndWait();
               } catch (IOException ex) {
                   Log.logException(ex);
               }
            break;
            
           case "Proyectos":;
                try {
               loader = new FXMLLoader(getClass().getResource("ProjectList.fxml"));
               root = loader.load();
               ProjectListController projectListController = loader.getController();
               String keyGroupAcademic = member.getKeyGroupAcademic();
               projectListController.setMember(member);
               Scene scene = new Scene(root);
               Stage stage = new Stage();
               stage.setScene(scene);
               stage.initModality(Modality.APPLICATION_MODAL);
               stage.showAndWait();
               } catch (IOException ex) {
                   Log.logException(ex);
               }
            break;
        
        }
    
    }
    
}
