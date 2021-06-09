/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import businessLogic.GroupAcademicDAO;
import businessLogic.LGACDAO;
import businessLogic.MemberDAO;
import businessLogic.PreliminarProjectDAO;
import businessLogic.ReceptionWorkDAO;
import businessLogic.StudentDAO;
import domain.LGAC;
import domain.Member;
import domain.PreliminarProject;
import domain.ReceptionWork;
import domain.Student;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import log.BusinessException;
import log.Log;

public class ReceptionWorkModifyController implements Initializable {

    @FXML private TextField tfTitle;
    @FXML private TextField tfDirector;
    @FXML private TextArea taDescription;
    @FXML private TextArea taCodirectors;
    @FXML private TextField tfNumberStudents;
    @FXML private Button btOk;
    @FXML private Button btSave;
    @FXML private Button btExit;
    @FXML private Pane paneStudent;
    @FXML private Pane lgacsPane;
    @FXML private ComboBox cbType;
    @FXML private ComboBox cbPreliminarProject;
    @FXML private ComboBox cbState;
    @FXML DatePicker dpStartDate;
    @FXML DatePicker dpEndDate;
    private ObservableList<String> types;
    private ObservableList<String> states;
    private ReceptionWork receptionWorkRecover;

    
    
     public void setReceptionWork(ReceptionWork receptionWork){
        this.receptionWorkRecover= receptionWork;
    }
    
    public void initializeReceptionWork(){
       tfTitle.setText("Título: "+ receptionWorkRecover.getTitle());
       LocalDate localStartDate = LocalDate.parse(receptionWorkRecover.getDateStart(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
       LocalDate localEndDate = LocalDate.parse(receptionWorkRecover.getDateEnd(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
       dpStartDate.setValue(localStartDate);
       dpEndDate.setValue(localEndDate);
       cbType.setValue(receptionWorkRecover.getType());
       cbState.setValue(receptionWorkRecover.getActualState());
       taDescription.setText("Descripción: " + receptionWorkRecover.getDescription());
       recoverColaborators ();
           try {
               getStudents();
               getLGACS();
           } catch (BusinessException ex) {
               Log.logException(ex);
           }
       
    }
    
    @FXML
    private void addStudents(ActionEvent actionEvent){
         GridPane gridPane= new GridPane();
        Validation validation =new Validation();
        if(!tfNumberStudents.getText().isEmpty() && (validation.validateNumberField(tfNumberStudents.getText()))){
            Integer students=Integer.parseInt(tfNumberStudents.getText());  
            gridPane.setHgap (5);
            gridPane.setVgap (5);
            int i=0;
            int sizeRows=3;
           while (i < ( students * sizeRows)){  
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
    private void actionExit(ActionEvent actionEvent) throws BusinessException{  
        Stage stage = (Stage) btExit.getScene().getWindow();
        stage.close();
        //openListWindow();
    }
    
    @FXML
        private void actionSave(ActionEvent actionEvent) throws BusinessException{  
        }
    
    public void recoverColaborators (){ 
       ReceptionWorkDAO receptionWorkDAO = new ReceptionWorkDAO ();
        ArrayList<Member> colaborators ;
        String codirectors="";
        try {
             colaborators=receptionWorkDAO.getColaborators(receptionWorkRecover.getKey());
             for(int i=0; i< colaborators.size(); i++){ 
                 if(colaborators.get(i).getRole().equals("Director")){  
                     tfDirector.setText(colaborators.get(i).getProfessionalLicense());
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
        ReceptionWorkDAO receptionWorkDAO =new ReceptionWorkDAO();
         receptionWorkRecover.setStudents(receptionWorkDAO.getStudents(receptionWorkRecover.getKey()));
         ArrayList<Student> students= receptionWorkRecover.getStudents();
         int i=0;
        int numberStudent=0;
        int numberRows=2;
        GridPane gridPane= new GridPane();
        gridPane.setHgap (5);
        gridPane.setVgap (5);
        if(students.size()> 0){
            while (i < ( students.size() * numberRows)){ 
                    TextField tfEnrollmentStudent = new TextField( students.get(numberStudent).getEnrollment());
                    TextField tfNameStudent = new TextField(students.get(numberStudent).getName());
                    gridPane.add(tfEnrollmentStudent,1,i);
                    gridPane.add(tfNameStudent,1, (i + 1));
                    i=i+2;
                    numberStudent++;
            }
            paneStudent.getChildren().add(gridPane);
        }
    }
    
    
      private void getLGACS() throws BusinessException{
        ReceptionWorkDAO receptionWorkDAO =new ReceptionWorkDAO();
         receptionWorkRecover.setLGACs(receptionWorkDAO.getLGACs(receptionWorkRecover.getKey()));
         ArrayList<LGAC> lgacs= receptionWorkRecover.getLGACs();
         int i=0;
         int indexGridPane=1;
        GridPane gridPane= new GridPane();
        gridPane.setHgap (2);
        gridPane.setVgap (2);
        gridPane.add(new Label("LGACs relacionadas: "),1,0);

        if(lgacs.size()> 0){
            while (i <lgacs.size()){ 
                    CheckBox checkBoxLGAC = new CheckBox(lgacs.get(i).getName());
                    gridPane.add(checkBoxLGAC,1,indexGridPane);
                    i++;
                    indexGridPane++;
                    
            }
            lgacsPane.getChildren().add(gridPane);
        }
    }
      
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
