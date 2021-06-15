
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
import java.time.LocalDate;
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
import javafx.stage.Stage;
import javafx.util.converter.LocalDateStringConverter;
import log.BusinessException;
import log.Log;

public class PreliminarProjectModifyController implements Initializable {

   @FXML private TextField tfTitle;
    @FXML private TextArea taDescription;
    @FXML private Button btSave;
    @FXML private Button btExit;
    @FXML private DatePicker dpStartDate;
    @FXML private DatePicker dpEndDate;
    @FXML private ScrollPane spStudents;
    private PreliminarProject preliminarProjectRecover = new PreliminarProject();
    private PreliminarProject preliminarProjectNew = new PreliminarProject();
    @FXML private ComboBox cbDirector;
    @FXML private ComboBox cbCodirectors;
    @FXML private TableColumn tcCodirector;
    @FXML private Button btAddCodirector;
    @FXML private Button btDelete;
    @FXML private  TableView<Member> tvCodirectors;
    private ObservableList<Member> members;
    private ListChangeListener<Member> tableCodirectorsListener;
    private int indexCodirectors;
    private ObservableList<Member> codirectors ;
    private ObservableList<Member> codirectorsNew;
    private Member member;
    private String keyGroupAcademic;

    public void setMember(Member member) {
        this.member = member;
    }

    public void setKeyGroupAcademic(String keyGroupAcademic) {
        this.keyGroupAcademic = keyGroupAcademic;
    }
    

    @FXML
    private void actionSave(ActionEvent actionEvent){   
        String title =tfTitle.getText();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String description = taDescription.getText();
        String startDate;
        String endDate;
        
            if(!validateFieldEmpty() && validateInformationField() ){
                startDate = dpStartDate.getValue().format(formatter);
                endDate = dpEndDate.getValue().format(formatter);
                int key = preliminarProjectRecover.getKey();
                preliminarProjectNew.setKey(key);
                preliminarProjectNew.setTitle(title);
                preliminarProjectNew.setDescription(description);
                preliminarProjectNew.setDateStart(startDate);
                preliminarProjectNew.setDateEnd(endDate);
                preliminarProjectNew.setKeyGroupAcademic(preliminarProjectRecover.getKeyGroupAcademic());
                if(validateColaborators()){
                    updatePreliminarProject ();
                }
            }else{  
                sendAlert();
            }        
    }
    
  private void updatePreliminarProject (){   
        PreliminarProjectDAO preliminarProjectDAO =  new PreliminarProjectDAO();
        try{  
            deleteColaborators();
            deleteStudents();
           if(preliminarProjectDAO.updatedSucessful(preliminarProjectRecover.getKey(), preliminarProjectNew)){  
               preliminarProjectNew.setKey(preliminarProjectRecover.getKey());
               saveColaborators();
               recoverStudents();
               AlertMessage alertMessage = new AlertMessage();
               alertMessage.showUpdateMessage();
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
    
    @FXML 
    private void actionExit(ActionEvent actionEvent){   
       try {
           Stage stage = (Stage) btExit.getScene().getWindow();
           stage.close();
           openShowView();
       } catch (BusinessException ex) {
           if(ex.getMessage().equals("DataBase connection failed ")){
                AlertMessage alertMessage = new AlertMessage();
                alertMessage.showAlertValidateFailed("Error en la conexion con la base de datos");
            }else{  
                Log.logException(ex);
            }
       }
    }
    
    private void openShowView() throws BusinessException{
        try{ 
            Stage primaryStage= new Stage();
            URL url = new File("src/GUI/PreliminarProjectShow.fxml").toURI().toURL();
        try{
              FXMLLoader loader = new FXMLLoader(url);
              loader.setLocation(url);
              loader.load();
                PreliminarProjectShowController preliminarProjectShowController =loader.getController();
                PreliminarProjectDAO preliminarProjectDAO = new PreliminarProjectDAO();
                PreliminarProject preliminarProjectAuxiliar = preliminarProjectDAO.getById(preliminarProjectRecover.getKey());
                preliminarProjectShowController.setPreliminarProject(preliminarProjectAuxiliar);
                preliminarProjectShowController.initializePreliminarProject();
                preliminarProjectShowController.setKeyGroupAcademic(keyGroupAcademic);
                preliminarProjectShowController.setMember(member);
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
    
    private boolean deleteColaborators() throws BusinessException{ 
        PreliminarProjectDAO preliminarProjectDAO = new PreliminarProjectDAO();
        ArrayList<Member> colaborators = preliminarProjectDAO.getColaborators(preliminarProjectRecover.getKey());
        preliminarProjectRecover.setMembers(colaborators);
        return preliminarProjectDAO.deletedSucessfulColaborators(preliminarProjectRecover);

    }
    
    private boolean deleteStudents() throws BusinessException{  
        PreliminarProjectDAO preliminarProjectDAO = new PreliminarProjectDAO();
        ArrayList<Student> students = preliminarProjectDAO.getStudents(preliminarProjectRecover.getKey());
        preliminarProjectRecover.setStudents(students);
    
        return preliminarProjectDAO.deletedSucessfulStudents(preliminarProjectRecover);
    }
    
    private void saveColaborators(){    
       PreliminarProjectDAO preliminarProjectDAO = new PreliminarProjectDAO();
       MemberDAO memberDAO = new MemberDAO();
       ArrayList<Member> members = new ArrayList<Member>();
        try {
            Member director = (Member) cbDirector.getSelectionModel().getSelectedItem();
            director.setRole("Director");
            if(director!=null){
                preliminarProjectNew.addMember(director);
            }                        
            for(int i=0; i < codirectorsNew.size(); i++){   
                preliminarProjectNew.addMember(codirectorsNew.get(i));
            }
            ArrayList<Member> membersAuxiliar = new ArrayList<Member>();
            preliminarProjectDAO.addedSucessfulColaborators(preliminarProjectNew);
            preliminarProjectNew.setMembers(membersAuxiliar);
        } catch (BusinessException ex) {
            if(ex.getMessage().equals("DataBase connection failed ")){
                AlertMessage alertMessage = new AlertMessage();
                alertMessage.showAlertValidateFailed("Error en la conexion con la base de datos");
            }else{  
                Log.logException(ex);
            }
        }
    }
    
    
    
    
    private void initializeColaborators() throws BusinessException{  
        PreliminarProjectDAO preliminarProjectDAO = new PreliminarProjectDAO();
        preliminarProjectRecover.setMembers(preliminarProjectDAO.getColaborators(preliminarProjectRecover.getKey()));
        ArrayList<Member> members = preliminarProjectRecover.getMembers();
        int i=0;
        while(i< members.size()){  
            if(members.get(i).getRole().equals("Director")){
                cbDirector.setValue(members.get(i));
            }else{  
                codirectors.add(members.get(i));
                codirectorsNew.add(members.get(i));
            }
            
            i++;
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
           initializeColaborators();
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
            spStudents.setContent(gridPane);
    }
    
    private void recoverStudents() throws BusinessException{   
        GridPane gridPane= (GridPane) spStudents.getContent();
        ArrayList<Student> studentsOld = preliminarProjectRecover.getStudents();
        ArrayList<Student> students = new ArrayList<Student>();
            int i=1;
            int sizeRows=3;
           while (i < (sizeRows * studentsOld.size())){
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
           preliminarProjectNew.setStudents(students);
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
            preliminarProjectDAO.addedSucessfulStudents(preliminarProjectNew);
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

          
      }
     
  
    
   @FXML 
    private void actionAddCodirector(ActionEvent actionEvent){    
        Member codirector = (Member) cbCodirectors.getSelectionModel().getSelectedItem();    
        if(!repeatedCodirector(codirector)){
           codirectorsNew.add(codirector);
        }else{  
            AlertMessage alertMessage = new AlertMessage();
            alertMessage.showAlertValidateFailed("Codirector repetido");
        }
    }
    
    @FXML
    private void actionDelete(ActionEvent event){
        codirectorsNew.remove(indexCodirectors);
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
    
   
   
    public boolean repeatedCodirector(Member codirector){
        Boolean value = false;
        int i = 0;
        while((value==false) && (i<codirectorsNew.size())){
            String enrollmentCodirector= codirectorsNew.get(i).getProfessionalLicense();
            if(enrollmentCodirector.equals(codirector.getProfessionalLicense())){
                value = true;
            }
            i++;
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
    
    public void initialize(URL url, ResourceBundle rb) {
        tcCodirector.setCellValueFactory(new PropertyValueFactory<Member,String>("name"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        dpStartDate.setConverter(new LocalDateStringConverter(formatter, null));
        dpEndDate.setConverter(new LocalDateStringConverter(formatter, null));
        members = FXCollections.observableArrayList();
        codirectors= FXCollections.observableArrayList();
        codirectorsNew= FXCollections.observableArrayList();
        initializeMembers();
        cbDirector.setItems(members);
        cbDirector.getSelectionModel().selectFirst();
        cbCodirectors.setItems(members);
        cbCodirectors.getSelectionModel().selectFirst();
        tvCodirectors.setItems(codirectorsNew);
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
    
    
}
