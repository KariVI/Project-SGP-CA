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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import log.BusinessException;
import log.Log;

public class ProjectModifyController implements Initializable {

    @FXML private DatePicker dpFinishDate;
    @FXML private DatePicker dpStartDate;
    @FXML private TextField tfTitle;
    @FXML private TextArea taDescription;
    @FXML private TableView<LGAC> tvLGAC;
    @FXML private TableColumn<LGAC, String> tcLGAC;
    @FXML private TableView<ReceptionWork> tvReceptionWork;
    @FXML private TableColumn<ReceptionWork, String> tcReceptionWork;
    @FXML private TableView<Member> tvMember;
    @FXML private TableColumn<Member, String> tcMember;
    @FXML private TableView<Student> tvStudent;
    @FXML private TableColumn<Student, String> tcStudentName;
     @FXML private TableColumn<Student, String> tcStudentEnrollment;
    @FXML private Button btAddLGAC;
    @FXML private Button btAddRecepcionalWork;
    @FXML private Button btAddStudent;
    @FXML private Button btAddMember;
    @FXML private Button btSave;
    @FXML private Button btCancel;
    @FXML private TextField tfName;
    @FXML private TextField tfEnrollment;
    @FXML private ComboBox<Member> cbMember;
    @FXML private ComboBox<ReceptionWork> cbReceptionWork;
    @FXML private ComboBox<LGAC> cbLGAC;
    private ObservableList<LGAC> lgacs;
    private ObservableList<LGAC> lgacsTable;
    private ObservableList<LGAC> lgacsTableOld;
    private ObservableList<Member> members;
    private ObservableList<Member> membersTable;
    private ObservableList<Student> studentsTable;
    private ObservableList<Member> membersTableOld;
    private ObservableList<Student> studentsTableOld;
    private ObservableList<ReceptionWork> receptionWorks;
    private ObservableList<ReceptionWork> receptionWorksTable;
    private ObservableList<ReceptionWork> receptionWorksTableOld;
    private String groupAcademicKey;
    private int indexMember;
    private int indexLGAC;
    private int indexReceptionWork;
    private int indexStudent;
    private Project projectRetrieved;
    private Member member;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tcLGAC.setCellValueFactory(new PropertyValueFactory<LGAC,String>("name"));
        tcMember.setCellValueFactory(new PropertyValueFactory<Member,String>("name"));
        tcStudentName.setCellValueFactory(new PropertyValueFactory<Student,String>("name"));
        tcStudentEnrollment.setCellValueFactory(new PropertyValueFactory<Student,String>("enrollment"));
        tcReceptionWork.setCellValueFactory(new PropertyValueFactory<ReceptionWork,String>("title"));
        lgacs = FXCollections.observableArrayList();  
        lgacsTable = FXCollections.observableArrayList(); 
        lgacsTableOld = FXCollections.observableArrayList(); 
        members = FXCollections.observableArrayList();  
        membersTable = FXCollections.observableArrayList(); 
        membersTableOld = FXCollections.observableArrayList(); 
        receptionWorks = FXCollections.observableArrayList();  
        receptionWorksTable = FXCollections.observableArrayList();  
        receptionWorksTableOld = FXCollections.observableArrayList();
        studentsTable = FXCollections.observableArrayList(); 
        studentsTableOld = FXCollections.observableArrayList(); 
        cbLGAC.setItems(lgacs);
        cbMember.setItems(members);
        cbReceptionWork.setItems(receptionWorks);
        tvMember.setItems(membersTable);
        tvReceptionWork.setItems(receptionWorksTable);
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
       tvReceptionWork.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                 setSelectedReceptionWork();
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
   public void actionSave(){
       
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
   
   public void deleteProjectInfo(Project project){
        try {
            ProjectDAO projectDAO = new ProjectDAO();
            projectDAO.update(project);
            projectDAO.deleteStudents(project);
            projectDAO.deleteLGACS(project);
            projectDAO.deleteColaborators(project);
            projectDAO.deleteReceptionWorks(project);            
        } catch (BusinessException ex) {
            Log.logException(ex);
        }
   }
   
      public void addProjectInfo(Project project){
        try {
           ProjectDAO projectDAO = new ProjectDAO();
           projectDAO.addStudents(project);
           projectDAO.addLGAC(project);
           projectDAO.addColaborators(project);
           projectDAO.addReceptionWork(project);          
        } catch (BusinessException ex) {
            Log.logException(ex);
        }
   }
   
   @FXML
   public void actionCancel(){
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
   
   public boolean studentAlreadyRegistered(Student student){
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
       
       for(int i = 0; i < receptionWorksTable.size(); i++){
           project.setReceptionWork(receptionWorksTable.get(i));
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
          project.getReceptionWorks().isEmpty() || project.getMembers().isEmpty()||
          project.getStudents().isEmpty()||project.getLGACs().isEmpty()){
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
        Member member = cbMember.getSelectionModel().getSelectedItem();
        if(validateMember(member)){
            membersTable.add(member);           
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
        Member member = null;
        int tamTable = 1;
        if(tvMember != null){
            List<Member> memberTable = tvMember.getSelectionModel().getSelectedItems();
            if(memberTable.size() == tamTable){
                member = memberTable.get(0);
            }
        }
        return member;
    }
    
    private void setSelectedMember(){
        Member member = getSelectedMember();
        indexMember = membersTable.indexOf(member);
        if(member != null){
            cbMember.getSelectionModel().select(member);
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
        int tamTable = 1;
        if(tvLGAC != null){
            List<LGAC> lgacTable = tvLGAC.getSelectionModel().getSelectedItems();
            if(lgacTable.size() == tamTable){
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
    private void addReceptionWork(ActionEvent event){
        ReceptionWork receptionWork = cbReceptionWork.getSelectionModel().getSelectedItem();
        if(validateReceptionWork(receptionWork)){
           receptionWorksTable.add(receptionWork);           
        }else{
            AlertMessage alertMessage = new AlertMessage();
            alertMessage.showAlertValidateFailed("Trabajo recepcional repetido");
        }  
    } 
    
    private ReceptionWork getSelectedReceptionWork(){
        ReceptionWork receptionWork = null;
        int tamTable = 1;
        if(tvReceptionWork != null){
            List<ReceptionWork> receptionWorkTable = tvReceptionWork.getSelectionModel().getSelectedItems();
            if(receptionWorkTable.size() == tamTable){
                receptionWork = receptionWorkTable.get(0);
            }
        }
        return receptionWork;
    }
    
    private void setSelectedReceptionWork(){
        ReceptionWork receptionWork = getSelectedReceptionWork();
        indexReceptionWork = receptionWorksTable.indexOf(receptionWork);
        if(receptionWork != null){
            cbReceptionWork.getSelectionModel().select(receptionWork);
        }
    }    
    
    @FXML
    private void actionDeleteReceptionWork(ActionEvent event){
       receptionWorksTable.remove(indexReceptionWork);
    }
    
    private boolean validateReceptionWork(ReceptionWork receptionWork){
        Boolean value = true;
        int i = 0;
        while(value && i<receptionWorksTable.size()){
            if(receptionWorksTable.get(i).equals(receptionWork)){
                value = false;
            }
            i++;
        }
       return value;
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
        int tamTable = 1;
        if(tvStudent!= null){
            List<Student> studentTable = tvStudent.getSelectionModel().getSelectedItems();
            if(studentTable.size() == tamTable){
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
    
    @FXML
    public void actionUpdateStudent(){
        String name = "";
        String enrollment = "";
        name = tfName.getText();
        enrollment = tfEnrollment.getText();
        Student student = new Student(enrollment, name);
        if(validateStudent(student)){    
            studentsTable.set(indexStudent,student);
        }
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
        }
    }
    
    private void initializeReceptionWorks(){
        ReceptionWorkDAO  receptionWorkDAO  = new  ReceptionWorkDAO ();
        try {
            ArrayList<ReceptionWork> receptionWorkList = new ArrayList<ReceptionWork>();
            receptionWorkList  = receptionWorkDAO.getReceptionWorks(groupAcademicKey);
            for(int i = 0; i< receptionWorkList .size(); i++){
                     receptionWorks.add(receptionWorkList.get(i));   
            }
            cbReceptionWork.getSelectionModel().selectFirst();      
        } catch (BusinessException ex) {
            Log.logException(ex);
        }
    }
    
    public void setProject(Project projectRetrieved){
        this.projectRetrieved = projectRetrieved;
        this.groupAcademicKey = projectRetrieved.getGroupAcademicKey();
        initializeMembers();
        initializeLGACs();
        initializeReceptionWorks();
        initializeProject(projectRetrieved);
        initializeLGACs(projectRetrieved);
        initializeMembers(projectRetrieved);
        initializeStudents(projectRetrieved);
        initializeReceptionWorks(projectRetrieved);
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
        }
    }
    
    private void initializeReceptionWorks(Project projectRetrieved){
        ProjectDAO projectDAO = new ProjectDAO();
        try {
            ArrayList<ReceptionWork> receptionWorkList = new ArrayList<ReceptionWork>();
            receptionWorkList = projectDAO.getReceptionWorks(projectRetrieved);
            for(int i = 0; i< receptionWorkList.size(); i++){
                receptionWorksTableOld.add(receptionWorkList.get(i));
                receptionWorksTable.add(receptionWorkList.get(i));
            }
        } catch (BusinessException ex) {
            Log.logException(ex);
        }
    }
    
}