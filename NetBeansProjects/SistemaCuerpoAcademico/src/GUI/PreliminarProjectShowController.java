/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import businessLogic.PreliminarProjectDAO;
import domain.Member;
import domain.PreliminarProject;
import domain.Student;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import log.BusinessException;
import log.Log;

/**
 * FXML Controller class
 *
 * @author kari
 */
public class PreliminarProjectShowController implements Initializable {
    @FXML private Label lbTitle;
    @FXML private  Label lbDirector;
    @FXML private TextArea taDescription;
    @FXML private TextArea taCodirectors;
    @FXML Label lbStartDate;
    @FXML  Label lbEndDate;
    @FXML Button btUpdate;
    @FXML Pane paneStudents;
    private String codirectors="";  
    private PreliminarProject preliminarProject;
    
    
     public void setPreliminarProject(PreliminarProject preliminarProject){   
        this.preliminarProject= preliminarProject;
    }
     
    
    public void initializePreliminarProject(){
       lbTitle.setText(preliminarProject.getTitle());
       lbStartDate.setText(preliminarProject.getDateStart());
       lbEndDate.setText(preliminarProject.getDateEnd());
       taDescription.setText("Descripción: " + preliminarProject.getDescription());
       recoverColaborators();
        try {
            getStudents();
        } catch (BusinessException ex) {
            Log.logException(ex);
        }
    }
    
    public void recoverColaborators (){ 
        PreliminarProjectDAO preliminarProjectDAO = new PreliminarProjectDAO ();
        ArrayList<Member> colaborators ;
        try {
             colaborators=preliminarProjectDAO.getColaborators(preliminarProject.getKey());
             for(int i=0; i< colaborators.size(); i++){ 
                 if(colaborators.get(i).getRole().equals("Director")){  
                     lbDirector.setText(colaborators.get(i).getProfessionalLicense());
                 }else{ 
                     codirectors= codirectors + colaborators.get(i).getProfessionalLicense();
                     codirectors= codirectors + ",";
                 }
             }
        } catch (BusinessException ex) {
            Log.logException(ex);
        }
        taCodirectors.setText("Codirectores: "+ codirectors);
    }
    
    private void getStudents() throws BusinessException{
        PreliminarProjectDAO preliminarProjectDAO =new PreliminarProjectDAO();
         preliminarProject.setStudents(preliminarProjectDAO.getStudents(preliminarProject.getKey()));
         ArrayList<Student> students= preliminarProject.getStudents();
         int i=0;
        int numberStudent=0;
        int numberRows=2;
        GridPane gridPane= new GridPane();
        gridPane.setHgap (5);
        gridPane.setVgap (5);
        while (i < ( students.size() * numberRows)){ 
                Label lbEnrollmentStudent = new Label("Matricula: "+ students.get(numberStudent).getEnrollment());
                Label lbNameStudent = new Label("Nombre: "+ students.get(numberStudent).getName());
                gridPane.add(lbEnrollmentStudent,1,i);
                gridPane.add(lbNameStudent,1, (i + 1));
                i=i+2;
                numberStudent++;
           }
            paneStudents.getChildren().add(gridPane);
    }
  
    
    @FXML 
    private void actionUpdate(ActionEvent actionEvent){ 
         Stage primaryStage= new Stage();
        URL url=null;
        try{ 
             url = new File("src/GUI/PreliminarProjectModify.fxml").toURI().toURL();
        } catch (MalformedURLException ex) {
            Log.logException(ex);
        }
        
        try{
              FXMLLoader loader = new FXMLLoader(url);
              loader.setLocation(url);
              loader.load();
              PreliminarProjectModifyController preliminarProjectModifyController =loader.getController();      
               preliminarProjectModifyController.setPreliminarProject(preliminarProject);
               preliminarProjectModifyController.initializePreliminarProject();
              Parent root = loader.getRoot();
              Scene scene = new Scene(root);
              primaryStage.setScene(scene);
              Stage stage = (Stage) btUpdate.getScene().getWindow();
              stage.close();
            } catch (IOException ex) {
                    Log.logException(ex);
            }
            primaryStage.show();
    
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
}
