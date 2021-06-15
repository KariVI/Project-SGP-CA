package GUI;

import businessLogic.GroupAcademicDAO;
import businessLogic.MemberDAO;
import businessLogic.ProjectDAO;
import businessLogic.ReceptionWorkDAO;
import domain.LGAC;
import domain.Member;
import domain.Project;
import domain.ReceptionWork;
import domain.Student;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import log.BusinessException;

public class ProjectRegisterController implements Initializable {

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
    @FXML private Button save;
    @FXML private Button cancel;
    @FXML private TextField tfName;
    @FXML private TextField tfEnrollment;
    @FXML private ComboBox<Member> cbMember;
    @FXML private ComboBox<ReceptionWork> cbReceptionWork;
    @FXML private ComboBox<LGAC> cbLGAC;
    private ObservableList<LGAC> lgacs;
    private ObservableList<LGAC> lgacsTable;
    private ObservableList<Member> members;
    private ObservableList<Member> membersTable;
    private ObservableList<Student> studentsTable;
    private ObservableList<ReceptionWork> receptionWorks;
    private ObservableList<ReceptionWork> receptionWorksTable;
    private String groupAcademicKey;

 
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tcLGAC.setCellValueFactory(new PropertyValueFactory<LGAC,String>("name"));
        tcMember.setCellValueFactory(new PropertyValueFactory<Member,String>("name"));
        tcStudentName.setCellValueFactory(new PropertyValueFactory<Student,String>("name"));
        tcStudentEnrollment.setCellValueFactory(new PropertyValueFactory<Student,String>("enrollment"));
        tcReceptionWork.setCellValueFactory(new PropertyValueFactory<ReceptionWork,String>("title"));
        lgacs = FXCollections.observableArrayList();  
        lgacsTable = FXCollections.observableArrayList(); 
        members = FXCollections.observableArrayList();  
        membersTable = FXCollections.observableArrayList(); 
        receptionWorks = FXCollections.observableArrayList();  
        receptionWorksTable = FXCollections.observableArrayList();  
        studentsTable = FXCollections.observableArrayList(); 
        cbLGAC.setItems(lgacs);
        cbMember.setItems(members);
        cbReceptionWork.setItems(receptionWorks);
        tvMember.setItems(membersTable);
        tvReceptionWork.setItems(receptionWorksTable);
        tvLGAC.setItems(lgacsTable);
        tvStudent.setItems(studentsTable);
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
         project.setStudents((ArrayList<Student>) studentsTable);
         project.setMembers((ArrayList<Member>) membersTable);
         project.setReceptionWorks((ArrayList<ReceptionWork>) receptionWorksTable);
         project.setMembers((ArrayList<Member>) membersTable);
         if(validateProject(project)){         
             
             
         }
       }else{
           alertMessage.showAlertValidateFailed("La fecha de fin debe ser mayor a la de inicio");
       }
           
   }
   
   private boolean validateProject(Project project){
       boolean value = true;
       AlertMessage alertMessage = new AlertMessage();
       if(emptyFields(project)){
           value = false;
           alertMessage.showAlertValidateFailed("Campos vacios");
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
        Student student = new Student(name, enrollment);
        if(validateStudent(student)){
            studentsTable.add(student);  
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

    public void setGroupAcademic(String groupAcademicKey){
        this.groupAcademicKey = groupAcademicKey;    
        initializeMembers();
        initializeLGACs();
        initializeReceptionWorks();
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
            memberList = memberDAO.getMembers();
            for(int i = 0; i< memberList.size(); i++){
                if(memberList.get(i).getKeyGroupAcademic().equals(groupAcademicKey)){
                     members.add(memberList.get(i));
                }        
            }
            cbMember.getSelectionModel().selectFirst();
        } catch (BusinessException ex) {
            Logger.getLogger(ProjectShowController.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(ProjectShowController.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(ProjectShowController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
