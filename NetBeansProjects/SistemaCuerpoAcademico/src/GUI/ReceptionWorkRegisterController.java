
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


public class ReceptionWorkRegisterController implements Initializable {

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

    private ObservableList<PreliminarProject> preliminarProjects;
    private String[] codirectorsParts;
    private ReceptionWork receptionWork = new ReceptionWork();
    
    
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
    private void actionSave(ActionEvent actionEvent){
      String title =tfTitle.getText();
        String description= taDescription.getText();
        String codirectors = taCodirectors.getText();
        String director= tfDirector.getText();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String type = (String) cbType.getSelectionModel().getSelectedItem();
        String state = (String) cbState.getSelectionModel().getSelectedItem();
        PreliminarProject preliminarProject = (PreliminarProject) cbPreliminarProject.getSelectionModel().getSelectedItem();        
        String startDate;
        String endDate;
        if((!validateFieldEmpty()) && validateInformationField() ){
            if(divisionCodirectorsSucessful(codirectors) && validateDates()){ 
                startDate = dpStartDate.getValue().format(formatter);
                endDate = dpEndDate.getValue().format(formatter);
                receptionWork.setTitle(title);
                receptionWork.setDescription(description);
                receptionWork.setDateStart(startDate);
                receptionWork.setDateEnd(endDate);
                receptionWork.setType(type);
                receptionWork.setPreliminarProject(preliminarProject);
                receptionWork.setActualState(state);

                if(!searchRepeateReceptionWork ()){    
                    saveReceptionWork ();
                }else{  
                    sendAlert();
                }
            }else{  
                sendAlert();
            }
        }else{
            sendAlert();
        }
    }
    
    @FXML
    private void actionExit(ActionEvent actionEvent){
        Stage stage = (Stage) btExit.getScene().getWindow();
        stage.close();
        try{ 
            Stage primaryStage= new Stage();
            URL url = new File("src/GUI/receptionWorkList.fxml").toURI().toURL();
           try{
              FXMLLoader loader = new FXMLLoader(url);
              loader.setLocation(url);
              loader.load();
              ReceptionWorkListController receptionWorkListController =loader.getController();      
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
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            types=FXCollections.observableArrayList();
            states = FXCollections.observableArrayList();
            types.add("Práctico técnico");
            types.add("Práctico");
            types.add("Tesis");
            types.add("Tesina");
            types.add("Monografía");
            states.add("Concluido");
            states.add("Abandonado");
            states.add("Registrado");
            cbType.setItems(types);
            cbState.setItems(states);
            cbType.getSelectionModel().selectFirst();
            cbState.getSelectionModel().selectFirst();
            preliminarProjects=FXCollections.observableArrayList();
            initializePreliminarProjects();
            cbPreliminarProject.setItems(preliminarProjects);
            cbPreliminarProject.getSelectionModel().selectFirst();
            addlgacs();
        } catch (BusinessException ex) {
            Log.logException(ex);
        }

    }    
    
    private void saveReceptionWork(){   
         ReceptionWorkDAO receptionWorkDAO =  new ReceptionWorkDAO();
        try{  
            if(! findRepeateColaborators()){
                if(receptionWorkDAO.savedSucessful(receptionWork)){  
                    receptionWork.setKey(receptionWorkDAO.getId(receptionWork));
                    saveColaborators();
                    recoverStudents();
                    recoverLgacs();
                    AlertMessage alertMessage = new AlertMessage();
                    alertMessage.showAlertSuccesfulSave("Trabajo recepcional");
                }
           }else{    
          AlertMessage alertMessage = new AlertMessage();
          alertMessage.showAlertValidateFailed("El director y el codirector no pueden ser el mismo");
      }
        } catch (BusinessException ex){ 
            if(ex.getMessage().equals("DataBase connection failed ")){
                AlertMessage alertMessage = new AlertMessage();
                alertMessage.showAlertValidateFailed("Error en la conexion con la base de datos");
            }else{  
                Log.logException(ex);
            }
        }
    }
    
    private boolean validateDates(){
        boolean value=false;
        
        if(dpStartDate.getValue()!=null && dpEndDate.getValue()!=null){
           LocalDate dateStart= dpStartDate.getValue();
            LocalDate dateEnd = dpEndDate.getValue();
          if(dateEnd.isAfter(dateStart) ){ 
               value=true;
         }
        }
        return value;
    }
    
    private boolean findRepeateColaborators(){ 
        boolean value= false;
        String director= tfDirector.getText();
        int i=0; 
        while(i< codirectorsParts.length && value==false){  
            if(director.equals(codirectorsParts[i])){   
                value=true;
            }; 
            i++;
        }
    
        return value;
    }
    
    
     private void saveColaborators(){    
       String directorProfessionalLicense= tfDirector.getText();
       ReceptionWorkDAO receptionWorkDAO = new ReceptionWorkDAO();
       MemberDAO memberDAO = new MemberDAO();
       ArrayList<Member> members = new ArrayList<Member>();
      
              try {
            Member director = memberDAO.getMemberByLicense(directorProfessionalLicense);
            director.setRole("Director");
            if(director!=null){
                receptionWork.addMember(director);
            }
            for(int i=0; i< codirectorsParts.length; i++ ){  
                Member codirector= memberDAO.getMemberByLicense(codirectorsParts[i]);
                codirector.setRole("Codirector");
                 receptionWork.addMember(codirector);
            } 
         receptionWorkDAO.addedSucessfulColaborators(receptionWork);
        } catch (BusinessException ex) {
            if(ex.getMessage().equals("DataBase connection failed ")){
                AlertMessage alertMessage = new AlertMessage();
                alertMessage.showAlertValidateFailed("Error en la conexion con la base de datos");
            }else{  
                Log.logException(ex);
            }
        }
      
    }
    
    private void recoverStudents() throws BusinessException{   
        GridPane gridPane= (GridPane) paneStudent.getChildren().get(0);
        ArrayList<Student> students = new ArrayList<Student>();
            int i=1;
            Integer lgacs=Integer.parseInt(tfNumberStudents.getText());  
            int sizeRows=3;
           while (i < (sizeRows * lgacs)){
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
           receptionWork.setStudents(students);
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
    
    private void sendAlert(){ 
          AlertMessage alertMessage= new AlertMessage();
          if(validateFieldEmpty() ){  
              alertMessage.showAlertValidateFailed("No se han llenado todos los campos");
          }
          if(!validateInformationField()){
             alertMessage.showAlertValidateFailed("Existen campos con caracteres invalidos");
          }

          if(searchRepeateReceptionWork()){
              alertMessage.showAlertValidateFailed("El trabajo recepcional ya se encuentra registrado");
          }
          
          if(!validateDates()){
            alertMessage.showAlertValidateFailed("La fecha de fin debe ser mayor a la de inicio");

          }
          
          
      }
    
    private void addStudentsInPreliminarProject(){ 
        ReceptionWorkDAO receptionWorkDAO = new ReceptionWorkDAO();
        try {
           receptionWorkDAO.addedSucessfulStudents(receptionWork);
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
    
    private void addlgacs() throws BusinessException{    
        
         GridPane gridPane= new GridPane();
         GroupAcademicDAO groupAcademicDAO = new GroupAcademicDAO ();
            gridPane.setHgap (2);
            gridPane.setVgap (2);
            int i=0;
            gridPane.add(new Label ("Selecciona LGAC relacionadas: "),1,0);
            int indexGridPane=1;
            ArrayList <LGAC> lgacs = groupAcademicDAO.getLGACs("JDOEIJ804");
           while (i < lgacs.size()){  
                CheckBox checkBox = new CheckBox(lgacs.get(i).getName());
                gridPane.add(checkBox,1,indexGridPane);
                i++;
                indexGridPane++;
           }  
        
            lgacsPane.getChildren().add(gridPane);

    }
    
    private void recoverLgacs() throws BusinessException{    
        GridPane gridPane= (GridPane) lgacsPane.getChildren().get(0);
        ArrayList<LGAC> lgacs = new ArrayList<LGAC>();
        LGACDAO lgacDAO = new LGACDAO();
            int i=1;
            int indexLGACs =0;
            GroupAcademicDAO groupAcademicDAO = new GroupAcademicDAO ();
            ArrayList <LGAC> lgacsAuxiliar = groupAcademicDAO.getLGACs("JDOEIJ804");
           while (i ==lgacsAuxiliar.size() ){
               CheckBox checkBox = (CheckBox) getNodeFromGridPane( gridPane, 1, i);
               if(checkBox.isSelected()){   
                 LGAC lgac= lgacDAO.getLgacByName(checkBox.getText());
                 lgacs.add(lgac);
               }
               i++;
           }
           receptionWork.setLGACs(lgacs);
           addLGACs();
    }
    
    private void addLGACs() throws BusinessException{
        ReceptionWorkDAO receptionWorkDAO = new ReceptionWorkDAO();
        receptionWorkDAO.addLGACs(receptionWork);
    }
    
     private boolean searchRepeateReceptionWork()   { 
       boolean value=false; 
        try {   
            ReceptionWorkDAO receptionWorkDAO= new ReceptionWorkDAO();
           receptionWorkDAO.getId(receptionWork);
            value=true;
        }catch (BusinessException ex){ 
            if(ex.getMessage().equals("DataBase connection failed ")){
                AlertMessage alertMessage = new AlertMessage();
                alertMessage.showAlertValidateFailed("Error en la conexion con la base de datos");
            }else{  
                Log.logException(ex);
            }
        }
        return value;
    }
     
    public void initializePreliminarProjects() throws BusinessException{   
            PreliminarProjectDAO preliminarProjectDAO = new PreliminarProjectDAO();
                ArrayList <PreliminarProject> preliminarProjectList = preliminarProjectDAO.getPreliminarProjects() ;
               
            for( int i = 0; i<preliminarProjectList.size(); i++) {
                  preliminarProjects.add(preliminarProjectList.get(i));
            }
    }
    
}
