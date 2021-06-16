
package GUI;

import businessLogic.MemberDAO;
import businessLogic.PreliminarProjectDAO;
import businessLogic.StudentDAO;
import domain.Member;
import domain.PreliminarProject;
import domain.Student;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.converter.LocalDateStringConverter;
import log.BusinessException;
import log.Log;


public class PreliminarProjectRegisterController implements Initializable {
    private ObservableList<Member> members;
    @FXML private TextFieldLimited tfTitle;
    @FXML private TextArea taDescription;
    @FXML private TextFieldLimited tfNumberStudents;
    @FXML private Button btOk;
    @FXML private Button btSave;
    @FXML private Button btExit;
    @FXML private Pane paneStudent;
    @FXML private DatePicker dpStartDate;
    @FXML private DatePicker dpEndDate;
    @FXML private ComboBox cbDirector;
    @FXML private ComboBox cbCodirectors;
    @FXML private TableColumn tcCodirector;
    @FXML private Button btAddCodirector;
    @FXML private Button btDelete;
    @FXML private TableView<Member> tvCodirectors;
    @FXML private ScrollPane spStudents;
    private ListChangeListener<Member> tableCodirectorsListener;
    private int indexCodirectors;
    private String keyGroupAcademic;
    private PreliminarProject preliminarProject = new PreliminarProject();
    private ObservableList<Member> codirectors ;
    private Member member;

    public void setMember(Member member) {
        this.member = member;
    }

   public void setKeyGroupAcademic(String keyGroupAcademic){
       this.keyGroupAcademic= keyGroupAcademic;
       initializeMembers();
       cbDirector.getSelectionModel().selectFirst();
       cbCodirectors.getSelectionModel().selectFirst();
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
        
        spStudents.setContent(gridPane);
    }

    @FXML
    private void actionSave(ActionEvent actionEvent){   
        String title =tfTitle.getText();
        String description= taDescription.getText();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String startDate;
        String endDate;

            if(!validateFieldEmpty() && validateInformationField() && validateColaborators() ){
                startDate = dpStartDate.getValue().format(formatter);
                endDate = dpEndDate.getValue().format(formatter);
                preliminarProject.setTitle(title);
                preliminarProject.setDescription(description);
                preliminarProject.setDateStart(startDate);
                preliminarProject.setDateEnd(endDate);
                preliminarProject.setKeyGroupAcademic(keyGroupAcademic);
                if(!searchRepeatePreliminarProject ()){    
                    savePreliminarProject ();
                }else{  
                    sendAlert();
                }
            }else{
                sendAlert();
            }
    }
    
    private void savePreliminarProject (){   
        PreliminarProjectDAO preliminarProjectDAO =  new PreliminarProjectDAO();
        try{  
           if(preliminarProjectDAO.savedSucessful(preliminarProject)){  
               preliminarProject.setKey(preliminarProjectDAO.getId(preliminarProject));
               saveColaborators();
               recoverStudents();
               AlertMessage alertMessage = new AlertMessage();
               alertMessage.showAlertSuccesfulSave("Anteproyecto");
               

               
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
    
    public boolean validateColaborators(){  
        boolean value = true;
        Member director = (Member) cbDirector.getSelectionModel().getSelectedItem();
        if(repeatedCodirector(director)){
                 value=false; 
                AlertMessage alertMessage = new AlertMessage();
                alertMessage.showAlertValidateFailed("El director y el codirector no pueden ser el mismo");  
            }
        return value;
    }
    
    @FXML 
    private void actionExit(ActionEvent actionEvent){   
        Stage stage = (Stage) btExit.getScene().getWindow();
        stage.close();
        openPreliminarProjectList();
        
    }
           
     @FXML 
    private void actionAddCodirector(ActionEvent actionEvent){    
        Member codirector = (Member) cbCodirectors.getSelectionModel().getSelectedItem();    
        if(!repeatedCodirector(codirector)){
           codirectors.add(codirector);
        }else{  
            AlertMessage alertMessage = new AlertMessage();
            alertMessage.showAlertValidateFailed("Codirector repetido");
        }
    }
    
    @FXML
    private void actionDelete(ActionEvent event){
        codirectors.remove(indexCodirectors);
    }
    
    
    private boolean saveColaborators(){    
        boolean value=false;
       PreliminarProjectDAO preliminarProjectDAO = new PreliminarProjectDAO();
       MemberDAO memberDAO = new MemberDAO();
       ArrayList<Member> members = new ArrayList<Member>();
        try {
            Member director=(Member) cbDirector.getSelectionModel().getSelectedItem();
            director.setRole("Director");
            preliminarProject.addMember(director);
            
            for(int i=0; i < codirectors.size(); i++){   
                codirectors.get(i).setRole("Codirector");
                preliminarProject.addMember(codirectors.get(i));
            }
         preliminarProjectDAO.addedSucessfulColaborators(preliminarProject);
        } catch (BusinessException ex) {
            if(ex.getMessage().equals("DataBase connection failed ")){
                AlertMessage alertMessage = new AlertMessage();
                alertMessage.showAlertValidateFailed("Error en la conexion con la base de datos");
            }else{  
                Log.logException(ex);
            }
        }
        return value;
    }
    
    private void recoverStudents() throws BusinessException{   
        GridPane gridPane= (GridPane) spStudents.getContent();
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
           preliminarProject.setStudents(students);
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
            preliminarProjectDAO.addedSucessfulStudents(preliminarProject);
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
          if(tfTitle.getText().isEmpty()  || taDescription.getText().isEmpty()
            || dpStartDate == null 
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

          if(searchRepeatePreliminarProject()){
              alertMessage.showAlertValidateFailed("El anteproyecto ya se encuentra registrado");
          }
      }
     
    public boolean searchRepeatePreliminarProject()   { 
       boolean value=false; 
        try {   
            PreliminarProjectDAO preliminarProjectDAO= new PreliminarProjectDAO();
            preliminarProjectDAO.getId(preliminarProject);
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
    
    private boolean validateInformationField(){ 
         boolean value=true;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String startDate = dpStartDate.getValue().format(formatter);
        String endDate = dpEndDate.getValue().format(formatter);
        Validation validation=new Validation();
        if(validation.findInvalidField(tfTitle.getText())
        || validation.findInvalidField(taDescription.getText()) || (!validation.validateDate(startDate))
        || (!validation.validateDate(endDate))){   
            value=false;
        }  
        return value;
    }
    
    private void initializeMembers() {
        try {
            MemberDAO memberDAO = new MemberDAO();
            ArrayList <Member> memberList = new ArrayList<Member>();
            memberList = memberDAO.getMembers(keyGroupAcademic);
            for( int i = 0; i<memberList.size(); i++) {
                members.add(memberList.get(i));
            }
        } catch (BusinessException ex) {
            Log.logException(ex);
        }
    }
    
    public void initialize(URL url, ResourceBundle rb) {
        tfTitle.setMaxlength(200);
        tfNumberStudents.setMaxlength(2);
        tcCodirector.setCellValueFactory(new PropertyValueFactory<Member,String>("name"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        dpStartDate.setConverter(new LocalDateStringConverter(formatter, null));
        dpEndDate.setConverter(new LocalDateStringConverter(formatter, null));
        members = FXCollections.observableArrayList();
        codirectors= FXCollections.observableArrayList();
        cbDirector.setItems(members);       
        cbCodirectors.setItems(members);
        tvCodirectors.setItems(codirectors);
        tvCodirectors.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                 setSelectedCodirector();
             }
            }
        );

        tableCodirectorsListener = new ListChangeListener<Member>(){
            @Override
            public void onChanged(ListChangeListener.Change<? extends Member> codirector) {
                setSelectedCodirector();
            }
        };
    }   
    
     private Member getSelectedCodirector(){
        Member codirector = null;
        int tamTable = 1;
        if(tvCodirectors != null){
            List<Member> codirectorTable = tvCodirectors.getSelectionModel().getSelectedItems();
            if(codirectorTable.size() == tamTable){
                codirector = codirectorTable.get(0);
            }
        }
        return codirector;
    }
    
    private void setSelectedCodirector(){
        Member codirector = getSelectedCodirector();
        indexCodirectors = codirectors.indexOf(codirector);
            if(codirector != null){
                cbCodirectors.getSelectionModel().select(codirector);
            }
    }
    
    
    
    public boolean repeatedCodirector(Member codirector){
        Boolean value = false;
        int i = 0;
        while((value==false) && (i<codirectors.size())){
            String enrollmentCodirector= codirectors.get(i).getProfessionalLicense();
            if(enrollmentCodirector.equals(codirector.getProfessionalLicense())){
                value = true;
            }
            i++;
        }
       return value;
    }
    
    
    private void openPreliminarProjectList(){   
         try{ 
            Stage primaryStage= new Stage();
            URL url = new File("src/GUI/preliminarProjectList.fxml").toURI().toURL();
           try{
              FXMLLoader loader = new FXMLLoader(url);
              loader.setLocation(url);
              loader.load();
              PreliminarProjectListController preliminarProjectListController =loader.getController();   
              preliminarProjectListController.setMember(member);
              preliminarProjectListController.setKeyGroupAcademic(keyGroupAcademic);
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
