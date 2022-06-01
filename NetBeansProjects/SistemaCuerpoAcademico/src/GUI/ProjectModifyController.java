package GUI;

import businessLogic.GroupAcademicDAO;
import businessLogic.MemberDAO;
import businessLogic.ProjectDAO;
import businessLogic.ReceptionWorkDAO;
import businessLogic.StudentDAO;
import domain.LGAC;
import domain.Member;
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
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import log.BusinessException;
import log.Log;

public class ProjectModifyController implements Initializable {

    @FXML private DatePicker dpFinishDate;
    @FXML private DatePicker dpStartDate;
    @FXML private TextFieldLimited tfTitle;
    @FXML private TextArea taDescription;
    @FXML private TableView<LGAC> tvLGAC;
    @FXML private TableColumn<LGAC, String> tcLGAC;
    @FXML private TableView<Member> tvMember;
    @FXML private TableColumn<Member, String> tcMember;
    @FXML private TableView<Student> tvStudent;
    @FXML private TableColumn<Student, String> tcStudentName;
    @FXML private TableColumn<Student, String> tcStudentEnrollment;
    @FXML private Button btSave;
    @FXML private Button btCancel;
    @FXML private TextFieldLimited tfName;
    @FXML private TextFieldLimited tfEnrollment;
    @FXML private ComboBox<Member> cbMember;
    @FXML private ComboBox<LGAC> cbLGAC;
    private ObservableList<LGAC> lgacs;
    private ObservableList<LGAC> lgacsTable;
    private ObservableList<LGAC> lgacsTableOld;
    private ObservableList<Member> members;
    private ObservableList<Member> membersTable;
    private ObservableList<Student> studentsTable;
    private ObservableList<Member> membersTableOld;
    private ObservableList<Student> studentsTableOld;
    private String groupAcademicKey;
    private int indexMember;
    private int indexLGAC;
    private int indexStudent;
    private Project projectRetrieved;
    private Member member;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tfTitle.setMaxLength(200);
        tfName.setMaxLength(150);
        tfEnrollment.setMaxLength(10);
        tcLGAC.setCellValueFactory(new PropertyValueFactory<LGAC,String>("name"));
        tcMember.setCellValueFactory(new PropertyValueFactory<Member,String>("name"));
        tcStudentName.setCellValueFactory(new PropertyValueFactory<Student,String>("name"));
        tcStudentEnrollment.setCellValueFactory(new PropertyValueFactory<Student,String>("enrollment"));
        lgacs = FXCollections.observableArrayList();  
        lgacsTable = FXCollections.observableArrayList(); 
        lgacsTableOld = FXCollections.observableArrayList(); 
        members = FXCollections.observableArrayList();  
        membersTable = FXCollections.observableArrayList(); 
        membersTableOld = FXCollections.observableArrayList(); 
        studentsTable = FXCollections.observableArrayList(); 
        studentsTableOld = FXCollections.observableArrayList(); 
        cbLGAC.setItems(lgacs);
        cbMember.setItems(members);
        tvMember.setItems(membersTable);
        tvLGAC.setItems(lgacsTable);
        tvStudent.setItems(studentsTable);
        
        tvMember.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                 setSelectedMember();
             }
            }
        );
        
        tvStudent.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                 setSelectedStudent();
             }
            }
        );
        
       
        tvLGAC.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                 setSelectedLGAC();
             }
            }
        );
    }
    
   @FXML 
   private void actionSave(){
       
       DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
       String title = "";
       String description = "";
       String startDate = "";
       String finishDate = "";
       Validation validation = new Validation();
       AlertMessage alertMessage = new AlertMessage();
       if(validation.validateDates(dpStartDate, dpFinishDate)){
         title = tfTitle.getText();
         description = taDescription.getText();
         startDate = dpStartDate.getValue().format(formatter);
         finishDate = dpFinishDate.getValue().format(formatter);
         Project project = new Project(title,description,startDate,finishDate,groupAcademicKey);  
          project = setProjectInfo(project);
         if(validateProject(project)){   
             try {
                 saveStudents();
                 ProjectDAO projectDAO = new ProjectDAO();
                 project.setIdProject(projectDAO.searchId(projectRetrieved));
                 deleteProjectInfo(project);
                 addProjectInfo(project);
                 alertMessage.showAlertSuccesfulSave("Proyecto");
                 Stage stage = (Stage)btSave.getScene().getWindow();
                 stage.close();
                 openViewProject(project);
             } catch (BusinessException ex) {
                  if(ex.getMessage().equals("DataBase connection failed ")){
                     alertMessage.showAlertValidateFailed("Error en la conexion con la base de datos");
                  }else{  
                     Log.logException(ex);
                  }
             }
         }
       }else{
           alertMessage.showAlertValidateFailed("La fecha de fin debe ser mayor a la de inicio");
       }
           
   }
   
   private void deleteProjectInfo(Project project){
        try {
            ProjectDAO projectDAO = new ProjectDAO();
            projectDAO.updatedSucessful(project);
            projectDAO.deletedSucessfulStudents(project);
            projectDAO.deletedSucessfulLGACS(project);
            projectDAO.deletedSucessfulColaborators(project);
            projectDAO.deletedSucessfulReceptionWorks(project);            
        } catch (BusinessException ex) {
            Log.logException(ex);
                 AlertMessage alertMessage  = new AlertMessage();
                alertMessage.showAlertValidateFailed("Error en la conexión con la base de datos");
                Stage stage = (Stage) btCancel.getScene().getWindow();
                stage.close();
                openLogin();
        }
   }
   
   private void addProjectInfo(Project project){
        try {
           ProjectDAO projectDAO = new ProjectDAO();
           projectDAO.addStudents(project);
           projectDAO.addLGAC(project);
           projectDAO.addColaborators(project);
           projectDAO.addReceptionWork(project);          
        } catch (BusinessException ex) {
            Log.logException(ex);
                 AlertMessage alertMessage  = new AlertMessage();
                alertMessage.showAlertValidateFailed("Error en la conexión con la base de datos");
                Stage stage = (Stage) btCancel.getScene().getWindow();
                stage.close();
                openLogin();
        }
   }
   
   @FXML
   private void actionCancel(){
       Stage stage = (Stage)btCancel.getScene().getWindow();
       stage.close();
       openViewProject(projectRetrieved);
   }
   
    private void  openViewProject(Project project){   
        Stage primaryStage = new Stage();
        try{
              URL url = new File("src/GUI/ProjectShow.fxml").toURI().toURL();
              FXMLLoader loader = new FXMLLoader(url);
              loader.setLocation(url);
              loader.load();
              ProjectShowController projectShowController = loader.getController();
              projectShowController.setMember(member);
              projectShowController.setProject(project);
              Parent root = loader.getRoot();
              Scene scene = new Scene(root);
              primaryStage.setScene(scene);
              primaryStage.show();
        }catch (IOException ex) {
            Log.logException(ex);
        }
    }
   
   private void saveStudents(){  
       for(int i = 0; i < studentsTable.size(); i++){
           if(!studentAlreadyRegistered(studentsTable.get(i))){
               try {
                   StudentDAO studentDAO = new StudentDAO();
                   studentDAO.savedSucessful(studentsTable.get(i));
               } catch (BusinessException ex) {
                  if(ex.getMessage().equals("DataBase connection failed ")){
                     AlertMessage alertMessage= new AlertMessage();
                     alertMessage.showAlertValidateFailed("Error en la conexion con la base de datos");
                  }else{  
                     Log.logException(ex);
                  }
                  
               }
           }
           
       }
       
   }
   
   private boolean studentAlreadyRegistered(Student student){
      boolean value = false;
        try {
            StudentDAO studentDAO = new StudentDAO();
            Student studentRetrivied;
            studentRetrivied = studentDAO.getByEnrollment(student.getEnrollment());
            if(studentRetrivied.equals(student)){
                value = true;
            }
        } catch (BusinessException ex) {
            Log.logException(ex);
             
        }
        
        return value;
   }
   
   private Project setProjectInfo(Project project){
       
       for(int i = 0; i < studentsTable.size(); i++){
           project.setStudent(studentsTable.get(i));
       }
       
       for(int i = 0; i < membersTable.size(); i++){
           project.setMember(membersTable.get(i));
       } 
       
       for(int i = 0; i < lgacsTable.size(); i++){
           project.setLGAC(lgacsTable.get(i));
       }  
       
       return project;
   }
   
   private boolean validateProject(Project project){
       boolean value = true;
       AlertMessage alertMessage = new AlertMessage();
       if(emptyFields(project)){
           value = false;
           alertMessage.showAlertValidateFailed("Campos vacios");
       }
       
       if(invalidFields(project)){
           value = false;
           alertMessage.showAlertValidateFailed("Campos invalidos");
       }
       
       return value;     
   }
    
   private boolean emptyFields(Project project){
       boolean value = false;
       if(project.getDescription().isEmpty()||project.getTitle().isEmpty()||
          project.getMembers().isEmpty()||project.getLGACs().isEmpty()){
           value = true;
       }
       
       return value;
   }
   
   private boolean invalidFields(Project project){
       boolean value = false;
       Validation validation = new Validation();
       if(validation.findInvalidField(project.getTitle())){
           value = true;
       }
       
       return value;
   }
 
   @FXML
   private void addMember(ActionEvent event){
        Member newMember = cbMember.getSelectionModel().getSelectedItem();
        if(validateMember(newMember)){
            membersTable.add(newMember);           
        }else{
            AlertMessage alertMessage = new AlertMessage();
            alertMessage.showAlertValidateFailed("Participante del CA repetido");
        }
    }
    
    private boolean validateMember(Member member){
        Boolean value = true;
        int i = 0;
        while(value && i<membersTable.size()){
            if(membersTable.get(i).equals(member)){
                value = false;
            }
            
            i++;
        }
        
       return value;
    }
    
    private Member getSelectedMember(){
        Member newMember = null;
        int tableSize = 1;
        if(tvMember != null){
            List<Member> memberTable = tvMember.getSelectionModel().getSelectedItems();
            if(memberTable.size() == tableSize){
                newMember = memberTable.get(0);
            }
            
        }
        
        return newMember;
    }
    
    private void setSelectedMember(){
        Member newMember = getSelectedMember();
        indexMember = membersTable.indexOf(newMember);
        if(newMember != null){
            cbMember.getSelectionModel().select(newMember);
        }
    }
    
    @FXML
    private void actionDeleteMember(ActionEvent event){
        membersTable.remove(indexMember);
    }
    
    @FXML
    private void addLgac(ActionEvent event){
        LGAC lgac = cbLGAC.getSelectionModel().getSelectedItem();
        if(validateLgac(lgac)){
            lgacsTable.add(lgac);           
        }else{
            AlertMessage alertMessage = new AlertMessage();
            alertMessage.showAlertValidateFailed("LGAC repetida");
        }
    } 
    
    private boolean validateLgac(LGAC lgac){
        Boolean value = true;
        int i = 0;
        while(value && i<lgacsTable.size()){
            if(lgacsTable.get(i).equals(lgac)){
                value = false;
            }
            i++;
        }
       return value;
    }
    
    private LGAC getSelectedLGAC(){
        LGAC lgac = null;
        int tableSize = 1;
        if(tvLGAC != null){
            List<LGAC> lgacTable = tvLGAC.getSelectionModel().getSelectedItems();
            if(lgacTable.size() == tableSize){
                lgac = lgacTable.get(0);
            }
        }
        return lgac;
    }
    
    private void setSelectedLGAC(){
        LGAC lgac = getSelectedLGAC();
        indexLGAC = lgacsTable.indexOf(lgac);
        if(lgac != null){
            cbLGAC.getSelectionModel().select(lgac);
        }
    }
    
    @FXML
    private void actionDeleteLGAC(ActionEvent event){
        lgacsTable.remove(indexLGAC);
    }
    
   @FXML
    private void addStudent(ActionEvent event){
        String name = "";
        String enrollment = "";
        name = tfName.getText();
        enrollment = tfEnrollment.getText();
        Student student = new Student(enrollment, name);
        if(validateStudent(student)){
            studentsTable.add(student); 
            cleanFields();
        }       
    }  
    
    private boolean validateStudent(Student student){
        boolean value = true;
        AlertMessage alertMessage = new AlertMessage();
        if(invalidFields(student)){
           value = false;
           alertMessage.showAlertValidateFailed("Campos invalidos");
        }
        if(emptyFields(student)){
            value = false;
            alertMessage.showAlertValidateFailed("Existen campos vacios");
        }
        if(repeatedStudent(student)){
            value = false;
            alertMessage.showAlertValidateFailed("Estudiante repetido");
        }
        return value;
    }
    
    private Student getSelectedStudent(){
        Student student = null;
        int tableSize = 1;
        if(tvStudent!= null){
            List<Student> studentTable = tvStudent.getSelectionModel().getSelectedItems();
            if(studentTable.size() == tableSize){
                student = studentTable.get(0);
            }
            
        }
        
        return student;
    }
    
    private void setSelectedStudent(){
        Student student = getSelectedStudent();
        indexStudent = studentsTable.indexOf(student);
        if(student != null){
            tfName.setText(student.getName());
            tfEnrollment.setText(student.getEnrollment());
        }
        
    }
    
    @FXML
    private void actionDeleteStudent(ActionEvent event){
       studentsTable.remove(indexStudent);
       cleanFields();
    }
    
    private void cleanFields(){
        tfName.setText("");
        tfEnrollment.setText("");
    }
    
    private boolean invalidFields(Student student){
        boolean value = false;
        Validation validation=new Validation();
        if( validation.findInvalidField(student.getName()) || validation.findInvalidKeyAlphanumeric(student.getEnrollment())){  
            value = true;
        }
        
        return value;
    }
    
    private boolean emptyFields(Student student){
        boolean value = false;
        if( student.getName().isEmpty()|| student.getEnrollment().isEmpty()){        
            value = true;
        }
        
        return value;
    }

    
    private boolean repeatedStudent(Student student){
        Boolean value = false;
        int i = 0;
        while(!value && i<studentsTable.size()){
            if(studentsTable.get(i).equals(student)){
                value = true;
            }
            
            i++;
        }
        
       return value;
    }
    
    private void initializeMembers(){
        MemberDAO memberDAO = new MemberDAO();
        try {
            ArrayList<Member> memberList = new ArrayList<Member>();
            memberList = memberDAO.getMembers(groupAcademicKey);
            for(int i = 0; i< memberList.size(); i++){
                     members.add(memberList.get(i));                  
            }
            
            cbMember.getSelectionModel().selectFirst();
        } catch (BusinessException ex) {
             Log.logException(ex);
                 AlertMessage alertMessage  = new AlertMessage();
                alertMessage.showAlertValidateFailed("Error en la conexión con la base de datos");
                Stage stage = (Stage) btCancel.getScene().getWindow();
                stage.close();
                openLogin();
        }
    }
    
    private void initializeLGACs(){
        GroupAcademicDAO groupAcademicDAO = new GroupAcademicDAO();
        try {
            ArrayList<LGAC> lgacList = new ArrayList<LGAC>();
            lgacList = groupAcademicDAO.getLGACs(groupAcademicKey);
            for(int i = 0; i< lgacList.size(); i++){
                     lgacs.add(lgacList.get(i));   
            }
            
            cbLGAC.getSelectionModel().selectFirst();
        } catch (BusinessException ex) {
             Log.logException(ex);
                 AlertMessage alertMessage  = new AlertMessage();
                alertMessage.showAlertValidateFailed("Error en la conexión con la base de datos");
                Stage stage = (Stage) btCancel.getScene().getWindow();
                stage.close();
                openLogin();
        }
    }
    
    public void setProject(Project projectRetrieved){
        this.projectRetrieved = projectRetrieved;
        this.groupAcademicKey = projectRetrieved.getGroupAcademicKey();
        initializeMembers();
        initializeLGACs();
        initializeProject(projectRetrieved);
        initializeLGACs(projectRetrieved);
        initializeMembers(projectRetrieved);
        initializeStudents(projectRetrieved);
    }
    
    public void setMember(Member member){
        this.member = member;
    }
    
    private void initializeProject(Project projectRetrieved){
        tfTitle.setText(projectRetrieved.getTitle());
        LocalDate startDate = LocalDate.parse(projectRetrieved.getStartDate(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        LocalDate finishDate = LocalDate.parse(projectRetrieved.getFinishDate(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        dpStartDate.setValue(startDate);
        dpFinishDate.setValue(finishDate);
        taDescription.setText(projectRetrieved.getDescription());
    }
    
    private void initializeLGACs(Project projectRetrieved){
        ProjectDAO projectDAO = new ProjectDAO();
        try {
            ArrayList<LGAC> lgacList = new ArrayList<LGAC>();
            lgacList = projectDAO.getLGACs(projectRetrieved);
            for(int i = 0; i< lgacList.size(); i++){
                lgacsTableOld.add(lgacList.get(i));
                lgacsTable.add(lgacList.get(i));
            }
            
        } catch (BusinessException ex) {
             Log.logException(ex);
                 AlertMessage alertMessage  = new AlertMessage();
                alertMessage.showAlertValidateFailed("Error en la conexión con la base de datos");
                Stage stage = (Stage) btCancel.getScene().getWindow();
                stage.close();
                openLogin();
        }
    }
    
    private void initializeMembers(Project projectRetrieved){
        ProjectDAO projectDAO = new ProjectDAO();
        try {
            ArrayList<Member> memberList = new ArrayList<Member>();
            memberList = projectDAO.getColaborators(projectRetrieved);
            for(int i = 0; i< memberList.size(); i++){
                membersTableOld.add(memberList.get(i));
                membersTable.add(memberList.get(i));
            }
            
        } catch (BusinessException ex) {
             Log.logException(ex);
                 AlertMessage alertMessage  = new AlertMessage();
                alertMessage.showAlertValidateFailed("Error en la conexión con la base de datos");
                Stage stage = (Stage) btCancel.getScene().getWindow();
                stage.close();
                openLogin();
        }
    }
    
    private void initializeStudents(Project projecRetrieved){
        ProjectDAO projectDAO = new ProjectDAO();
        try {
            ArrayList<Student> studentList = new ArrayList<Student>();
            studentList = projectDAO.getStudents(projectRetrieved);
            for(int i = 0; i< studentList.size(); i++){
                studentsTableOld.add(studentList.get(i));
                studentsTable.add(studentList.get(i));
            }
            
        } catch (BusinessException ex) {
             Log.logException(ex);
                 AlertMessage alertMessage  = new AlertMessage();
                alertMessage.showAlertValidateFailed("Error en la conexión con la base de datos");
                Stage stage = (Stage) btCancel.getScene().getWindow();
                stage.close();
                openLogin();
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