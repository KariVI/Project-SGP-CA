
package GUI;

import businessLogic.GroupAcademicDAO;
import businessLogic.MemberDAO;
import businessLogic.ProjectDAO;
import businessLogic.ReceptionWorkDAO;
import businessLogic.StudentDAO;
import domain.Member;
import domain.PreliminarProject;
import domain.Project;
import domain.ReceptionWork;
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
import javafx.scene.control.CheckBox;
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
import javafx.stage.Stage;
import javafx.util.converter.LocalDateStringConverter;
import log.BusinessException;
import log.Log;

public class ReceptionWorkModifyController implements Initializable {

    @FXML private TextFieldLimited tfTitle;
    @FXML private TextArea taDescription;
    @FXML private Button btSave;
    @FXML private Button btExit;
    @FXML private ScrollPane spStudents;
    @FXML private ComboBox cbType;
    @FXML private ComboBox cbProject;
    @FXML private ComboBox cbState;
    @FXML private DatePicker dpStartDate;
    @FXML private  DatePicker dpEndDate;
    private ObservableList<String> types;
    private ObservableList<String> states;
    private ObservableList<Project> projects;
    private ObservableList<Member> members ;
    @FXML private ComboBox cbDirector;
    @FXML private ComboBox cbCodirectors;
    private ReceptionWork receptionWorkRecover;
    private ReceptionWork receptionWorkNew= new ReceptionWork();    
    private Member member;
    private String keyGroupAcademic;
    private GridPane gridPane= new GridPane();
    private int nextRowPosition=0;
    private int newStudents=0;
    private int numberStudents = 0; 
    public void setMember(Member member) {
        this.member = member;
    }
    
     private void initializeNextRowPosition(){  
        int sizeRows=3;
        int sizeStudentsCurrently= receptionWorkRecover.getStudents().size();
        if(sizeStudentsCurrently>0){
            nextRowPosition=  nextRowPosition + ( sizeStudentsCurrently * sizeRows);
        }
    }
     
     @FXML 
    private void actionAddStudent(ActionEvent actionEvent){     
            gridPane.setHgap (5);
            gridPane.setVgap (5);
            int sizeRows=3;
            TextField tfEnrollmentStudent = new TextField();
            tfEnrollmentStudent .setPromptText("Matricula: ");   
            TextField tfNameStudent = new TextField();
            tfNameStudent.setPrefWidth(200);
            tfNameStudent.setPromptText("Nombre: ");
            Label label = new Label("Estudiante");
            if(numberStudents <=2){
                 gridPane.add(label,1,nextRowPosition);
                 gridPane.add(tfEnrollmentStudent,1,(nextRowPosition + 1));
                 gridPane.add(tfNameStudent,1, (nextRowPosition + 2));
                 nextRowPosition= nextRowPosition + sizeRows ;
                 spStudents.setContent(gridPane);
                 newStudents++;
                 numberStudents++;
            }else{
                AlertMessage alertMessage = new AlertMessage();
                alertMessage.showAlertValidateFailed("Solo es posible agregar 3 estudiantes"); 
            }
           
            
            
    }

    public void setKeyGroupAcademic(String keyGroupAcademic) {
        this.keyGroupAcademic = keyGroupAcademic;
        initializeMembers();
        cbDirector.getSelectionModel().selectFirst();
        cbCodirectors.getSelectionModel().selectFirst();
    }

     public void setReceptionWork(ReceptionWork receptionWork){
        this.receptionWorkRecover= receptionWork;
    }
    
    public void initializeReceptionWork(){
       tfTitle.setText(receptionWorkRecover.getTitle());
       LocalDate localStartDate = LocalDate.parse(receptionWorkRecover.getDateStart(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
       LocalDate localEndDate = LocalDate.parse(receptionWorkRecover.getDateEnd(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
       dpStartDate.setValue(localStartDate);
       dpEndDate.setValue(localEndDate);
       cbType.setValue(receptionWorkRecover.getType());
       cbState.setValue(receptionWorkRecover.getActualState());

       cbProject.setValue(receptionWorkRecover.getProject());
       cbProject.setItems(projects);
        initializeNextRowPosition();
       taDescription.setText( receptionWorkRecover.getDescription());
           try {
               initializeColaborators();
               getStudents();
           } catch (BusinessException ex) {
               Log.logException(ex);
                 AlertMessage alertMessage  = new AlertMessage();
                alertMessage.showAlertValidateFailed("Error en la conexión con la base de datos");
                Stage stage = (Stage) btExit.getScene().getWindow();
                stage.close();
                openLogin();
           }
       
    }
    
    public boolean validateColaborators(){  
        boolean value = true;
        Member director = (Member) cbDirector.getSelectionModel().getSelectedItem();
        Member codirector = (Member) cbCodirectors.getSelectionModel().getSelectedItem();
        if(director.getName().equals(codirector.getName())){
                 value=false; 
                AlertMessage alertMessage = new AlertMessage();
                alertMessage.showAlertValidateFailed("El director y el codirector no pueden ser el mismo");  
            }
        return value;
    }
    
    @FXML
    private void actionSave(ActionEvent actionEvent){
      String title =tfTitle.getText();
        String description= taDescription.getText();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String type = (String) cbType.getSelectionModel().getSelectedItem();
        String state = (String) cbState.getSelectionModel().getSelectedItem();
        Project project = (Project) cbProject.getSelectionModel().getSelectedItem();        
        String startDate;
        String endDate;
        if(((!validateFieldEmpty())) && validateInformationField() ){
            if( validateDates()){ 
                startDate = dpStartDate.getValue().format(formatter);
                endDate = dpEndDate.getValue().format(formatter);
                receptionWorkNew.setKey(receptionWorkRecover.getKey());
                receptionWorkNew.setTitle(title);
                receptionWorkNew.setDescription(description);
                receptionWorkNew.setDateStart(startDate);
                receptionWorkNew.setDateEnd(endDate);
                receptionWorkNew.setType(type);
                
                receptionWorkNew.setProject(project);

                receptionWorkNew.setActualState(state);
                receptionWorkNew.setKeyGroupAcademic(receptionWorkRecover.getKeyGroupAcademic());
                if(receptionWorkNew.getActualState().equals("Asignado")&&receptionWorkNew.getStudents().isEmpty()){
                    receptionWorkNew.setActualState("Registrado");
                }
                if(receptionWorkNew.getActualState().equals("Registrado")&&!receptionWorkNew.getStudents().isEmpty()){
                    receptionWorkNew.setActualState("Asignado");
                }
                updateReceptionWork ();                
            }else{  
                sendAlert();
            }
        }else{
           sendAlert();
        }
    }
    
      private void updateReceptionWork (){   
        ReceptionWorkDAO receptionWorkDAO =  new ReceptionWorkDAO();
        try{  
          if(deleteColaborators() && deleteStudents() ){

                if(receptionWorkDAO.updatedSucessful(receptionWorkRecover.getKey(), receptionWorkNew)){  
                if(validateColaborators()){  
                    saveColaborators();
                    recoverStudents();
                    AlertMessage alertMessage = new AlertMessage();
                    alertMessage.showUpdateMessage();
                    Stage stage = (Stage) btSave.getScene().getWindow();
                    stage.close();
                    openShowView();
                }
               }
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
    private void actionExit(ActionEvent actionEvent) throws BusinessException{  
        Stage stage = (Stage) btExit.getScene().getWindow();
        stage.close();
        openShowView();
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
    

   
     private boolean validateFieldEmpty(){ 
          boolean value=false;
          if(tfTitle.getText().isEmpty() 
           || taDescription.getText().isEmpty()  || dpStartDate == null 
            || dpEndDate==null  || cbProject.getSelectionModel().getSelectedItem()==null
           ){
              value=true;
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
        || (!validation.validateDate(endDate)) ){   
            value=false;
        }  
        return value;
    }
    
    
    private void openShowView() throws BusinessException{
        try{ 
            Stage primaryStage= new Stage();
            URL url = new File("src/GUI/ReceptionWorkShow.fxml").toURI().toURL();
        try{
              FXMLLoader loader = new FXMLLoader(url);
              loader.setLocation(url);
              loader.load();
                ReceptionWorkShowController receptionWorkShowController =loader.getController();
                ReceptionWorkDAO receptionWorkDAO = new ReceptionWorkDAO();
                ReceptionWork receptionWorkAuxiliar = receptionWorkDAO.getReceptionWorkById(receptionWorkRecover.getKey());
                receptionWorkShowController.setReceptionWork(receptionWorkAuxiliar);               
                receptionWorkShowController.setKeyGroupAcademic(keyGroupAcademic);
                receptionWorkShowController.setMember(member);
                 receptionWorkShowController.initializeReceptionWork();
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
    
    
    
    
    private void getStudents() throws BusinessException{
        ReceptionWorkDAO receptionWorkDAO =new ReceptionWorkDAO();
         receptionWorkRecover.setStudents(receptionWorkDAO.getStudents(receptionWorkRecover.getKey()));
         ArrayList<Student> students= receptionWorkRecover.getStudents();
         int i=0;
        int numberStudent=0;
        int numberRows=3;
          numberStudents = students.size();
        gridPane.setHgap (5);
        gridPane.setVgap (5);
        if(students.size()> 0){
            while (i < ( students.size() * numberRows)){ 
                    TextField tfEnrollmentStudent = new TextField( students.get(numberStudent).getEnrollment());
                    TextField tfNameStudent = new TextField(students.get(numberStudent).getName());
                    Label label = new Label("Estudiante");
                    gridPane.add(label,1,i);
                    gridPane.add(tfEnrollmentStudent,1,(i+1));
                    gridPane.add(tfNameStudent,1, (i + 2));
                    i=i+3;
                    numberStudent++;
            }
            spStudents.setContent(gridPane);
        }
        numberStudents = numberStudent;
    }
    

    private boolean deleteColaborators() throws BusinessException{  
        ReceptionWorkDAO receptionWorkDAO = new ReceptionWorkDAO();
        ArrayList<Member> colaborators = receptionWorkDAO.getColaborators(receptionWorkRecover.getKey());
       receptionWorkRecover.setMembers(colaborators);
       return  receptionWorkDAO.deletedSucessfulColaborators(receptionWorkRecover);
    }
    
    private boolean deleteStudents() throws BusinessException{  
        ReceptionWorkDAO receptionWorkDAO = new ReceptionWorkDAO();
        ArrayList<Student> students = receptionWorkDAO.getStudents(receptionWorkRecover.getKey());
        receptionWorkRecover.setStudents(students);   
        return receptionWorkDAO.deletedSucessfulStudents(receptionWorkRecover);
    }
    
     private boolean saveColaborators(){    
        boolean value=false;
       ReceptionWorkDAO receptionWorkDAO = new ReceptionWorkDAO();
       MemberDAO memberDAO = new MemberDAO();
       ArrayList<Member> members = new ArrayList<Member>();
        try {
            Member director=(Member) cbDirector.getSelectionModel().getSelectedItem();
            director.setRole("Director");
            members.add(director);
            Member codirector = (Member) cbCodirectors.getSelectionModel().getSelectedItem();
            codirector.setRole("Codirector");
            members.add(codirector);
            receptionWorkNew.setMembers(members);
            receptionWorkDAO.addedSucessfulColaborators(receptionWorkNew);
            
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
    
    
      public void initializeProjects() throws BusinessException{   
            ProjectDAO projectDAO = new ProjectDAO();
                ArrayList <Project> projectList = projectDAO.getProjects();
               
            for( int i = 0; i<projectList.size(); i++) {
                  projects.add(projectList.get(i));
            }
    }
      
    @Override
    public void initialize(URL url, ResourceBundle rb) {
            tfTitle.setMaxLength(200);
            types=FXCollections.observableArrayList();
            states = FXCollections.observableArrayList();
            types.add("Práctico técnico");
            types.add("Práctico");
            types.add("Tesis");
            types.add("Tesina");
            types.add("Monografía");
            states.add("Registrado");
            states.add("Concluido");
            states.add("Abandonado");
            states.add("Asignado");
            cbType.setItems(types);
            cbState.setItems(states);
            cbType.getSelectionModel().selectFirst();
            cbState.getSelectionModel().selectFirst();
            projects = FXCollections.observableArrayList();
            cbProject.setItems(projects);
            cbProject.getSelectionModel().selectFirst();
            
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            dpStartDate.setConverter(new LocalDateStringConverter(formatter, null));
            dpEndDate.setConverter(new LocalDateStringConverter(formatter, null));
            members = FXCollections.observableArrayList();
            
            cbDirector.setItems(members);
            cbCodirectors.setItems(members);
        
         
           
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
                 AlertMessage alertMessage  = new AlertMessage();
                alertMessage.showAlertValidateFailed("Error en la conexión con la base de datos");
                Stage stage = (Stage) btExit.getScene().getWindow();
                stage.close();
                openLogin();
        }
    }
    
   
    
     private void initializeColaborators() throws BusinessException{  
        ReceptionWorkDAO receptionWorkDAO = new ReceptionWorkDAO();
        receptionWorkRecover.setMembers(receptionWorkDAO.getColaborators(receptionWorkRecover.getKey()));
        ArrayList<Member> members = receptionWorkRecover.getMembers();
        int i=0;
        while(i< members.size()){  
            if(members.get(i).getRole().equals("Director")){
                cbDirector.setValue(members.get(i));
            }
            if(members.get(i).getRole().equals("Codirector")){
                cbCodirectors.setValue(members.get(i));
            }
            
            i++;
        }
        
    }
    

    
    private void recoverStudents() throws BusinessException{   
        ArrayList<Student> studentsOld = receptionWorkRecover.getStudents();
        if(nextRowPosition>0){
        GridPane gridPane= (GridPane) spStudents.getContent();
        ArrayList<Student> studentsNew = new ArrayList<Student>();   
 
        int i=1;
        int sizeRows=3;
        int size= (studentsOld.size() + newStudents) * sizeRows;
         while (i <  size){
            TextField enrollment = (TextField) getNodeFromGridPane( gridPane, 1, i);
            TextField name = (TextField) getNodeFromGridPane( gridPane, 1, (i + 1));
            i=i+3;
           if(validateFieldsStudent(enrollment,name)){         
                String enrollmentStudent= enrollment.getText();
                String nameStudent= name.getText(); 
                Student student = new Student(enrollmentStudent,nameStudent);
                 if(!findRepeteadedStudents(studentsNew,student)) {  
                        studentsNew.add(student);
                        saveStudent(student);
                }
            }
           
        }
        receptionWorkNew.setStudents(studentsNew);
        addStudentsInReceptionWork();
        }
    }
    
    public boolean findRepeteadedStudents(ArrayList<Student> students,Student student){
       boolean value=false; 
            
            int i =0;
            while(i < students.size() && value==false){ 
                if(students.get(i).getEnrollment().equals(student.getEnrollment())){    
                    value=true;
                }
                i++;
            }
        return value;
    }
    
    
    private boolean validateFieldsStudent(TextField enrollment, TextField name){
        boolean value=true;
        AlertMessage alertMessage =new AlertMessage();
        Validation validation=new Validation();
        if( validation.findInvalidField(name.getText()) || validation.findInvalidKeyAlphanumeric(enrollment.getText())){    
            value=false;
            alertMessage.showAlertValidateFailed("Existen campos con caracteres invalidos");
        }else if( name.getText().isEmpty() && enrollment.getText().isEmpty()  ){ 
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
    
    private void addStudentsInReceptionWork(){ 
        ReceptionWorkDAO receptionWorkDAO = new ReceptionWorkDAO();
        try {
           receptionWorkDAO.addedSucessfulStudents(receptionWorkNew);
        } catch (BusinessException ex) {
            if(ex.getMessage().equals("DataBase connection failed ")){
                AlertMessage alertMessage= new AlertMessage();
                alertMessage.showAlertValidateFailed("Error en la conexion con la base de datos");
            }else{  
                Log.logException(ex);
            }
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
          if(!validateDates()){
            alertMessage.showAlertValidateFailed("La fecha de fin debe ser mayor a la de inicio");

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
