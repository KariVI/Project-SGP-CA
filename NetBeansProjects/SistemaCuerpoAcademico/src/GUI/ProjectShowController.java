package GUI;

import businessLogic.ProjectDAO;
import domain.LGAC;
import domain.Member;
import domain.Project;
import domain.ReceptionWork;
import domain.Student;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import log.BusinessException;
import log.Log;

public class ProjectShowController implements Initializable {

    @FXML private Label lbTitle;
    @FXML private Label lbStartDate;
    @FXML private Label lbFinishDate;
    @FXML private TextArea taDescription;
    @FXML private TableView<LGAC> tvLGAC;
    @FXML private TableColumn<LGAC, String> tcLGAC;
    @FXML private TableView<Member> tvMember;
    @FXML private TableColumn<Member, String> tcMember;
    @FXML private TableView<Student> tvStudent;
    @FXML private TableColumn<Student, String> tcStudent;
    @FXML private Button btReturn;
    @FXML private Button btUpdate;
    private ObservableList<LGAC> lgacs;
    private ObservableList<Member> members;
    private ObservableList<Student> students;
    private Project project;
    private Member member;

    public void initialize(URL url, ResourceBundle rb) {
        tcLGAC.setCellValueFactory(new PropertyValueFactory<LGAC,String>("name"));
        tcMember.setCellValueFactory(new PropertyValueFactory<Member,String>("name"));
        tcStudent.setCellValueFactory(new PropertyValueFactory<Student,String>("name"));
        lgacs = FXCollections.observableArrayList();     
        members = FXCollections.observableArrayList();  
        students = FXCollections.observableArrayList();    
        tvLGAC.setItems(lgacs);
        tvMember.setItems(members);
        tvStudent.setItems(students);
    }    
    
    public void setProject(Project project){
        this.project = project;
        initializeProject(project);
        initializeLGACs(project);
        initializeMembers(project);
        initializeStudents(project);
        initializeReceptionWorks(project);
    }
    
    public void setMember(Member member){
        this.member = member;
    }
    
    private void initializeProject(Project project){
        lbTitle.setText(project.getTitle());
        lbStartDate.setText(project.getStartDate());
        lbFinishDate.setText(project.getFinishDate());
        taDescription.setText(project.getDescription());
    }
    
    private void initializeLGACs(Project project){
        ProjectDAO projectDAO = new ProjectDAO();
        try {
            ArrayList<LGAC> lgacList = new ArrayList<LGAC>();
            lgacList = projectDAO.getLGACs(project);
            for(int i = 0; i< lgacList.size(); i++){
                lgacs.add(lgacList.get(i));
            }
        } catch (BusinessException ex) {
           Log.logException(ex);
                 AlertMessage alertMessage  = new AlertMessage();
                alertMessage.showAlertValidateFailed("Error en la conexión con la base de datos");
                Stage stage = (Stage) btReturn.getScene().getWindow();
                stage.close();
                openLogin();
        }
    }
    
    private void initializeMembers(Project project){
        ProjectDAO projectDAO = new ProjectDAO();
        try {
            ArrayList<Member> memberList = new ArrayList<Member>();
            memberList = projectDAO.getColaborators(project);
            for(int i = 0; i< memberList.size(); i++){
                members.add(memberList.get(i));
            }
        } catch (BusinessException ex) {
            Log.logException(ex);
                 AlertMessage alertMessage  = new AlertMessage();
                alertMessage.showAlertValidateFailed("Error en la conexión con la base de datos");
                Stage stage = (Stage) btReturn.getScene().getWindow();
                stage.close();
                openLogin();
        }
    }
    
    private void initializeStudents(Project project){
        ProjectDAO projectDAO = new ProjectDAO();
        try {
            ArrayList<Student> studentList = new ArrayList<Student>();
            studentList = projectDAO.getStudents(project);
            for(int i = 0; i< studentList.size(); i++){
                students.add(studentList.get(i));
            }
        } catch (BusinessException ex) {
            Log.logException(ex);
                 AlertMessage alertMessage  = new AlertMessage();
                alertMessage.showAlertValidateFailed("Error en la conexión con la base de datos");
                Stage stage = (Stage) btReturn.getScene().getWindow();
                stage.close();
                openLogin();
        }
    }
    
    private void initializeReceptionWorks(Project project){
        ProjectDAO projectDAO = new ProjectDAO();
        try {
            ArrayList<ReceptionWork> receptionWorkList = new ArrayList<ReceptionWork>();
            receptionWorkList = projectDAO.getReceptionWorks(project);
        } catch (BusinessException ex) {
            Log.logException(ex);
                 AlertMessage alertMessage  = new AlertMessage();
                alertMessage.showAlertValidateFailed("Error en la conexión con la base de datos");
                Stage stage = (Stage) btReturn.getScene().getWindow();
                stage.close();
                openLogin();
        }
    }
    
    @FXML
    public void actionReturn(){
         Stage stage = (Stage) btReturn.getScene().getWindow();
         stage.close();  
         openViewProjectList();
    }
    
   private void  openViewProjectList(){   
        Stage primaryStage = new Stage();
        try{
              URL url = new File("src/GUI/ProjectList.fxml").toURI().toURL();
              FXMLLoader loader = new FXMLLoader(url);
              loader.setLocation(url);
              loader.load();
              ProjectListController projectList = loader.getController();
              projectList.setMember(member);
              Parent root = loader.getRoot();
              Scene scene = new Scene(root);
              primaryStage.setScene(scene);
              primaryStage.show();
            }catch (IOException ex) {
                Log.logException(ex);
            }
    }
    
    @FXML
    public void actionUpdate(){
        try {
            URL url = new File("src/GUI/ProjectModify.fxml").toURI().toURL();
            FXMLLoader loader = new FXMLLoader(url);
            loader.setLocation(url);
            try {
                loader.load();
            } catch (IOException ex) {
                Log.logException(ex);
            }
            ProjectModifyController  projectModifyController = loader.getController();
            projectModifyController.setProject(project);
            projectModifyController.setMember(member);
            Parent root = loader.getRoot();
            Scene scene = new Scene(root);
            Stage primaryStage= new Stage();
            primaryStage.setScene(scene);
            Stage stage = (Stage) btUpdate.getScene().getWindow();
            stage.close();
            primaryStage.show();
        } catch (MalformedURLException ex) {
            Log.logException(ex);
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
