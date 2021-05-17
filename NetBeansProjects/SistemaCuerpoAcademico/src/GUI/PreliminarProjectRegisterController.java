/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.net.URL;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ResourceBundle;
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
import javafx.util.StringConverter;
import javafx.util.converter.LocalDateStringConverter;


public class PreliminarProjectRegisterController implements Initializable {
    @FXML private TextField tfTitle;
    @FXML private TextField tfDirector;
    @FXML private TextArea taDescription;
    @FXML private TextArea taCodirectors;
    @FXML private TextField tfNumberStudents;
    @FXML private Button btOk;
    @FXML private Button btSave;
    @FXML private Button btExit;
    @FXML private Pane paneStudent;
    @FXML DatePicker dpStartDate;
    @FXML DatePicker dpEndDate;
    private String[] codirectorsParts;
    


    @FXML 
    private void addStudents(ActionEvent actionEvent){  
        GridPane gridPane= new GridPane();
        Validation validation =new Validation();
        if(!tfNumberStudents.getText().isEmpty() && (validation.validateNumberField(tfNumberStudents.getText()))){
            Integer lgacs=Integer.parseInt(tfNumberStudents.getText());  
            gridPane.setHgap (5);
            gridPane.setVgap (5);
            int i=0;
            int sizeRows=3;
           while (i < ( lgacs * sizeRows)){  
                TextField tfEnrollmentStudent = new TextField();
                tfEnrollmentStudent .setPromptText("Matricula: ");   
                TextField tfNameStudent = new TextField();
                tfNameStudent.setPrefWidth(200);
                tfNameStudent.setPromptText("Nombre: ");
                Label label = new Label("Estudiante");
                gridPane.add(label,1,i);
                gridPane.add(tfEnrollmentStudent,1,(i + 1));
                gridPane.add(tfNameStudent,1, (i + 2));
                i=i+3;
           }  
        }
        paneStudent.getChildren().add(gridPane);
    }

    @FXML
    private void actionSave(ActionEvent actionEvent){   
        String title =tfTitle.getText();
        String description= taDescription.getText();
        String codirectors = taCodirectors.getText();
        String director= tfDirector.getText();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate date = dpStartDate.getValue();
        String startDate;
        String endDate;
        startDate = dpStartDate.getValue().format(formatter);
        endDate = dpEndDate.getValue().format(formatter);   
    }
    
    @FXML 
    private void actionExit(ActionEvent actionEvent){   
        Stage stage = (Stage) btExit.getScene().getWindow();
        stage.close();
    }
    
    private void saveColaborators(String director, String[] codirectors){    
        
    
    }
    
    private boolean validateFieldEmpty(){ 
          boolean value=false;
          if(tfTitle.getText().isEmpty()  || taCodirectors.getText().isEmpty()
           || taDescription.getText().isEmpty() || tfDirector.getText().isEmpty()  
           ){
              value=true;
          }
          return value;
    }
    
    private boolean validateInformationField(){ 
         boolean value=true;
        Validation validation=new Validation();
        if(validation.findInvalidField(tfTitle.getText())
        || validation.findInvalidField(taDescription.getText()) || validation.findInvalidField(tfDirector.getText()) 
        || validation.findInvalidField(taCodirectors.getText()) ){   
            value=false;
        }  
        return value;
    }
    
    private boolean divisionCodirectorsSucessful (String codirectors){  
        boolean value=false;
        if (codirectors.contains(",")) {
             codirectorsParts = codirectors.split(",");
             value=true;
        } else {
            AlertMessage alertMessage = new AlertMessage ();
            alertMessage.showAlert("Por favor escribe los nombres separados por comas");    
        }
        return value;
    }
   
    public void initialize(URL url, ResourceBundle rb) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        dpStartDate.setConverter(new LocalDateStringConverter(formatter, null));
        dpEndDate.setConverter(new LocalDateStringConverter(formatter, null));
        
       
    }   
    
}
