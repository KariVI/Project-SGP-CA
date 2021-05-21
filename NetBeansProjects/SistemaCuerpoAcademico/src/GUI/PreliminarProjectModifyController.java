/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import businessLogic.PreliminarProjectDAO;
import domain.PreliminarProject;
import domain.Student;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.converter.LocalDateStringConverter;
import log.BusinessException;
import log.Log;

/**
 * FXML Controller class
 *
 * @author kari
 */
public class PreliminarProjectModifyController implements Initializable {

   @FXML private TextField tfTitle;
    @FXML private TextField tfDirector;
    @FXML private TextArea taDescription;
    @FXML private TextArea taCodirectors;
    @FXML private Button btSave;
    @FXML private Button btExit;
    @FXML private Pane paneStudent;
    @FXML DatePicker dpStartDate;
    @FXML DatePicker dpEndDate;
    private String[] codirectorsParts;
    private PreliminarProject preliminarProjectRecover = new PreliminarProject();
    private PreliminarProject preliminarProjectNew = new PreliminarProject();
    
    
    
    
    @FXML
    private void actionSave(ActionEvent actionEvent){   
        String title =tfTitle.getText();
        String description= taDescription.getText();
        String codirectors = taCodirectors.getText();
        String director= tfDirector.getText();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String startDate;
        String endDate;
        System.out.println("a");

        /*if(divisionCodirectorsSucessful(codirectors)){  
            System.out.println("uy se pudo");
        }else{  
           System.out.println("uy no se pudo");

        }
        //if(!validateFieldEmpty() && validateInformationField() ){
            startDate = dpStartDate.getValue().format(formatter);
            endDate = dpEndDate.getValue().format(formatter);
            preliminarProject.setTitle(title);
            preliminarProject.setDescription(description);
            preliminarProject.setDateStart(startDate);
            preliminarProject.setDateEnd(endDate);
            if(!searchRepeatePreliminarProject ()){    
                savePreliminarProject ();
            }else{  
                sendAlert();
            }
        
            
        }else{  
            sendAlert();
        }*/
    }
    
    @FXML 
    private void actionExit(ActionEvent actionEvent){   
        Stage stage = (Stage) btExit.getScene().getWindow();
        stage.close();
    }
    
    
    public void setPreliminarProject(PreliminarProject preliminarProject){   
        preliminarProjectRecover.setKey(preliminarProject.getKey());
        preliminarProjectRecover.setTitle(preliminarProject.getTitle());
        preliminarProjectRecover.setDescription(preliminarProject.getDescription());
        preliminarProjectRecover.setDateStart(preliminarProject.getDateStart());
        preliminarProjectRecover.setDateEnd(preliminarProject.getDateEnd());
    
    }
    
    public void initializePreliminarProject(){  
        tfTitle.setText(preliminarProjectRecover.getTitle());
        taDescription.setText(preliminarProjectRecover.getDescription());
        LocalDate localStartDate = LocalDate.parse(preliminarProjectRecover.getDateStart(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        dpStartDate.setValue(localStartDate);
        LocalDate localEndDate= LocalDate.parse(preliminarProjectRecover.getDateEnd(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        dpEndDate.setValue(localEndDate);    
       try {
           getStudents();
       } catch (BusinessException ex) {
          if(ex.getMessage().equals("DataBase connection failed ")){
                AlertMessage alertMessage = new AlertMessage();
                alertMessage.showAlert("Error en la conexion con la base de datos");
            }else{  
                Log.logException(ex);
            }
       }
    }
    
    
    private void getStudents() throws BusinessException{
        PreliminarProjectDAO preliminarProjectDAO =new PreliminarProjectDAO();
         preliminarProjectRecover.setStudents(preliminarProjectDAO.getStudents(preliminarProjectRecover.getKey()));
         ArrayList<Student> students= preliminarProjectRecover.getStudents();
         int i=0;
        int numberStudent=0;
        int numberRows=3;
        GridPane gridPane= new GridPane();
        gridPane.setHgap (5);
        gridPane.setVgap (5);
        while (i < ( students.size() * numberRows)){  
                TextField tfEnrollmentStudent = new TextField(students.get(numberStudent).getEnrollment());
                TextField tfNameStudent = new TextField(students.get(numberStudent).getName());
                Label label = new Label("Estudiante");
                tfNameStudent.setPrefWidth(100);
                gridPane.add(label,1,i);
                gridPane.add(tfEnrollmentStudent,1,(i + 1));
                gridPane.add(tfNameStudent,1, (i + 2));
                i=i+3;
                numberStudent++;
           }
            paneStudent.getChildren().add(gridPane);
    }
    
    public void initialize(URL url, ResourceBundle rb) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        dpStartDate.setConverter(new LocalDateStringConverter(formatter, null));
        dpEndDate.setConverter(new LocalDateStringConverter(formatter, null));
    }    
    
}
