/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import businessLogic.MemberDAO;
import businessLogic.PreliminarProjectDAO;
import businessLogic.StudentDAO;
import domain.Member;
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
import javafx.scene.Node;
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

        if(divisionCodirectorsSucessful(codirectors)){  
            System.out.println("uy se pudo");
        }else{  
           System.out.println("uy no se pudo");

        }
        if(!validateFieldEmpty() && validateInformationField() ){
            startDate = dpStartDate.getValue().format(formatter);
            endDate = dpEndDate.getValue().format(formatter);
            preliminarProjectNew.setTitle(title);
            preliminarProjectNew.setDescription(description);
            preliminarProjectNew.setDateStart(startDate);
            preliminarProjectNew.setDateEnd(endDate);
                savePreliminarProject ();
            
        
            
        }else{  
            sendAlert();
        }
    }
    
  private void savePreliminarProject (){   
        PreliminarProjectDAO preliminarProjectDAO =  new PreliminarProjectDAO();
        try{  
           if(preliminarProjectDAO.savedSucessful(preliminarProjectNew)){  
               preliminarProjectNew.setKey(preliminarProjectDAO.getId(preliminarProjectNew));
               saveColaborators();
               recoverStudents();
               AlertMessage alertMessage = new AlertMessage();
               alertMessage.showAlertSuccesfulSave("Anteproyecto");
           }
        } catch (BusinessException ex){ 
            System.out.println("a");
            if(ex.getMessage().equals("DataBase connection failed ")){
                AlertMessage alertMessage = new AlertMessage();
                alertMessage.showAlertValidateFailed("Error en la conexion con la base de datos");
            }else{  
                Log.logException(ex);
            }
        }
    }
    
    @FXML 
    private void actionExit(ActionEvent actionEvent){   
        Stage stage = (Stage) btExit.getScene().getWindow();
        stage.close();
    }
    
    private void saveColaborators(){    
       String directorProfessionalLicense= tfDirector.getText();
       PreliminarProjectDAO preliminarProjectDAO = new PreliminarProjectDAO();
       MemberDAO memberDAO = new MemberDAO();
       ArrayList<Member> members = new ArrayList<Member>();
        try {
            Member director = memberDAO.getMemberByLicense(directorProfessionalLicense);
            director.setRole("Director");
            if(director!=null){
                preliminarProjectNew.addMember(director);
            }
            for(int i=0; i< codirectorsParts.length; i++ ){  
                Member codirector= memberDAO.getMemberByLicense(codirectorsParts[i]);
                codirector.setRole("Codirector");
                 preliminarProjectNew.addMember(codirector);

            } 
         preliminarProjectDAO.addedSucessfulColaborators(preliminarProjectNew);
        } catch (BusinessException ex) {
            if(ex.getMessage().equals("DataBase connection failed ")){
                AlertMessage alertMessage = new AlertMessage();
                alertMessage.showAlertValidateFailed("Error en la conexion con la base de datos");
            }else{  
                Log.logException(ex);
            }
        }
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
           getColaborators ();
           getStudents();
       } catch (BusinessException ex) {
          if(ex.getMessage().equals("DataBase connection failed ")){
                AlertMessage alertMessage = new AlertMessage();
                alertMessage.showAlertValidateFailed("Error en la conexion con la base de datos");
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
    
    public void getCodirectors(ArrayList<Member> colaborators){
          int i=0;
          String codirectors="";
          if(!colaborators.isEmpty()){  
              codirectors= colaborators.get(i).getProfessionalLicense();
              i++;
          }
           while( i< colaborators.size()){ 
                      colaborators.get(i).getProfessionalLicense();
                     codirectors= codirectors + "," + colaborators.get(i).getProfessionalLicense();
                     getCodirectors(colaborators);
                     i++;
                 }
            taCodirectors.setText( codirectors);
         
        }
                          
    public void getColaborators (){ 
        PreliminarProjectDAO preliminarProjectDAO = new PreliminarProjectDAO ();
        ArrayList<Member> colaborators ;
        String codirectors="";
        try {
             colaborators=preliminarProjectDAO.getColaborators(preliminarProjectRecover.getKey());
             for(int i=0; i< colaborators.size(); i++){ 
                 if(colaborators.get(i).getRole().equals("Director")){  
                      colaborators.get(i).getProfessionalLicense();
                      tfDirector.setText(colaborators.get(i).getProfessionalLicense());
                     colaborators.remove(i);
                 }
             }
        getCodirectors(colaborators);
        } catch (BusinessException ex) {
            Log.logException(ex);
        }
    }
    
    private void recoverStudents() throws BusinessException{   
        GridPane gridPane= (GridPane) paneStudent.getChildren().get(0);
        ArrayList<Student> students = new ArrayList<Student>();
            int i=1;
            int sizeRows=3;
           while (i < (sizeRows * students.size())){
               TextField enrollment = (TextField) getNodeFromGridPane( gridPane, 1, i);
               TextField name = (TextField) getNodeFromGridPane( gridPane, 1, (i + 1));
               i=i+3;
               if(validateFieldsStudent(enrollment,name)){         
                 String enrollmentStudent= enrollment.getText();
                 String nameStudent= name.getText(); 
                 Student student = new Student(enrollmentStudent,nameStudent);
                 students.add(student);
                 saveStudent(student);
               }
           }
           preliminarProjectRecover.setStudents(students);
           addStudentsInPreliminarProject();
    }
    
     private boolean validateFieldsStudent(TextField enrollment, TextField name){
        boolean value=true;
        AlertMessage alertMessage =new AlertMessage();
        Validation validation=new Validation();
        if( validation.findInvalidField(name.getText()) || validation.findInvalidKeyAlphanumeric(enrollment.getText())){    
            value=false;
            alertMessage.showAlertValidateFailed("Existen campos con caracteres invalidos");
        }else if( name.getText().isEmpty()|| enrollment.getText().isEmpty()  ){ 
            alertMessage.showAlertValidateFailed("Existen campos vacios");
            value=false;
        }
     return value;
    }
    
    private void saveStudent(Student student ) throws BusinessException{   
        if(!searchRepeateStudent(student)){
           StudentDAO studentDAO = new StudentDAO();
           studentDAO.savedSucessful(student);
        }
         
    }
    
    private void addStudentsInPreliminarProject(){ 
        PreliminarProjectDAO preliminarProjectDAO = new PreliminarProjectDAO();
        try {
            preliminarProjectDAO.addedSucessfulStudents(preliminarProjectRecover);
        } catch (BusinessException ex) {
            if(ex.getMessage().equals("DataBase connection failed ")){
                AlertMessage alertMessage= new AlertMessage();
                alertMessage.showAlertValidateFailed("Error en la conexion con la base de datos");
            }else{  
                Log.logException(ex);
            }
        }
    }
    
   
    private boolean searchRepeateStudent(Student student){ 
        boolean value=false;
        StudentDAO studentDAO = new StudentDAO();
        try{    
            if(student.equals(studentDAO.getByEnrollment(student.getEnrollment()))){ 
                value=true;
            }
        }catch(BusinessException ex){   
            Log.logException(ex);
        }
        return value;
    }
     
    
    private Node getNodeFromGridPane(GridPane gridPane, int column, int row) {
        Node primaryNode=null;
        for (Node node : gridPane.getChildren()) {
            if (GridPane.getColumnIndex(node) == column && GridPane.getRowIndex(node) == row) {
                primaryNode=node;
            }
        }
        return primaryNode;
   }

     private boolean validateFieldEmpty(){ 
          boolean value=false;
          if(tfTitle.getText().isEmpty()  || taCodirectors.getText().isEmpty()
           || taDescription.getText().isEmpty() || tfDirector.getText().isEmpty() || dpStartDate == null 
            || dpEndDate==null 
           ){
              value=true;
          }
          return value;
    }
    
     private void sendAlert(){ 
          AlertMessage alertMessage= new AlertMessage();
          if(validateFieldEmpty() ){  
              alertMessage.showAlertValidateFailed("No se han llenado todos los campos");
          }
          if(!validateInformationField()){
             alertMessage.showAlertValidateFailed("Existen campos con caracteres invalidos");
          }

          
      }
     
  
    
    private boolean validateInformationField(){ 
         boolean value=true;
        Validation validation=new Validation();
        if(validation.findInvalidField(tfTitle.getText())
        || validation.findInvalidField(taDescription.getText()) || validation.findInvalidKeyAlphanumeric(tfDirector.getText()) 
        || validation.findInvalidKeyAlphanumeric(taCodirectors.getText()) ){   
            value=false;
        }  
        return value;
    }
    
    private boolean divisionCodirectorsSucessful (String codirectors){  
        boolean value=false;
        int sizeProfessionalLicense=7;
        if(codirectors.length() == sizeProfessionalLicense ){   
            codirectorsParts= new String[1];
            codirectorsParts[0]= codirectors;
            value=true;
        }else{
            if (codirectors.contains(",")){
                 codirectorsParts = codirectors.split(",");
                 value=true;
            } else {
                AlertMessage alertMessage = new AlertMessage ();
                alertMessage.showAlertValidateFailed("Por favor escribe las cedulas separadas por comas");    
            }
        }
        return value;
    }
   
    
    public void initialize(URL url, ResourceBundle rb) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        dpStartDate.setConverter(new LocalDateStringConverter(formatter, null));
        dpEndDate.setConverter(new LocalDateStringConverter(formatter, null));
    }    
    
}
