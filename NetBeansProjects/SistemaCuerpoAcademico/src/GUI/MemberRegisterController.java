package GUI;


import businessLogic.LoginCredentialDAO;
import businessLogic.MemberDAO;
import domain.LoginCredential;
import domain.Member;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import log.BusinessException;
import log.Log;


public class MemberRegisterController implements Initializable {
    private ObservableList<String> roles;
    private ObservableList<String> degrees;
    private ObservableList<Integer> years;
    @FXML private ComboBox<Integer> cbYears;
    @FXML private ComboBox<String> cbRoles;
    @FXML private ComboBox<String> cbDegrees;
    @FXML private TextField tfName;
    @FXML private TextField tfProfessionalLicense;
    @FXML private TextField tfNameDegree;
    @FXML private TextField tfUniversity;
    @FXML private Button btClose;
    private Member loginMember;
    @FXML
    private TextField tfEmail;
    @FXML
    private TextField tfCURP;
    
    public void setMember(Member member){
        this.loginMember = member;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        roles = FXCollections.observableArrayList();
        roles.add("Integrante");
        roles.add("Colaborador");
        cbRoles.setItems(roles);
        degrees = FXCollections.observableArrayList();
        degrees.add("Maestria");
        degrees.add("Doctorado");
        cbDegrees.setItems(degrees);
        years = FXCollections.observableArrayList();
        for(int i = getCurrentYear(); i>1899; i--){
               years.add(i);
        }
       
        cbYears.setItems(years);
        cbRoles.getSelectionModel().selectFirst();
        cbDegrees.getSelectionModel().selectFirst();
        cbYears.getSelectionModel().selectFirst();
    }    

    @FXML 
    private void save(){
       String name = "";
       String role = ""; 
       String degree = "";
       String professionalLicense = ""; 
       String nameDegree = "";
       String universityName = "";
       int degreeYear = 0;
       name = tfName.getText();
       role = cbRoles.getSelectionModel().getSelectedItem();
       degree = cbDegrees.getValue();
       professionalLicense = tfProfessionalLicense.getText();
       nameDegree = tfNameDegree.getText();
       degreeYear = cbYears.getSelectionModel().getSelectedItem();
       universityName = tfUniversity.getText();
       Member newMember = new Member(professionalLicense, name, role, degree,nameDegree,universityName, degreeYear,loginMember.getKeyGroupAcademic());
       String user = tfEmail.getText();
       String password = tfCURP.getText();
       LoginCredential loginCredential = new LoginCredential(user, password, professionalLicense);
       MemberDAO memberDAO = new MemberDAO();
       LoginCredentialDAO loginCredentialDAO = new LoginCredentialDAO();
       
       if(validateCredential(loginCredential) && validateMember(newMember)){
             try { 
              memberDAO.savedSucessfulMember(newMember);
              loginCredentialDAO.registerSuccesful(loginCredential);
              AlertMessage alertMessage = new AlertMessage();
              alertMessage.showAlertSuccesfulSave("El miembro");
              close();
              openListMember();
             } catch (BusinessException ex) {
                 if(ex.getMessage().equals("DataBase connection failed ")){
                     AlertMessage alertMessage = new AlertMessage();
                    alertMessage.showAlertValidateFailed("Error en la conexion con la base de datos");
                    Stage stage = (Stage) btClose.getScene().getWindow();
                    stage.close();
                    openLogin();
                }else{  
                    Log.logException(ex);
                }
            }
       }
       
    }
    
    private void openListMember(){   
        Stage primaryStage = new Stage();
        try{
              URL url = new File("src/GUI/MemberList.fxml").toURI().toURL();
              FXMLLoader loader = new FXMLLoader(url);
              loader.setLocation(url);
              loader.load();
              MemberListController memberListController  = loader.getController();
              memberListController.setMember(loginMember);
              Parent root = loader.getRoot();
              Scene scene = new Scene(root);
              primaryStage.setScene(scene);
              primaryStage.show();
            }catch (IOException ex) {
                Log.logException(ex);
            }
    }
    
    @FXML
    private void close() {
        Stage stage = (Stage)btClose.getScene().getWindow();
        stage.close();
        openListMember();
    }
   
    private int getCurrentYear(){
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }
    
    private boolean validateMember(Member member){
        boolean value = true;
        AlertMessage alertMessage = new AlertMessage();
        Validation validation = new Validation();
        if(validation.emptyField(member.getProfessionalLicense()) || validation.emptyField(member.getName()) || validation.emptyField(member.getRole()) || validation.emptyField(member.getDegree()) ||
           validation.emptyField(member.getNameDegree()) || validation.emptyField(member.getUniversityName()) ||  member.getDegreeYear() == 0 ){
            value = false;
            alertMessage.showAlertValidateFailed("Campos vacios");
        }
        
        if(validation.findInvalidField(member.getName())||validation.findInvalidField(member.getNameDegree())||
           validation.findInvalidField(member.getUniversityName())){
           value = false;
           alertMessage.showAlertValidateFailed("Campos invalidos");
        }
        
        if(value && isAlreadyRegisterd(member)){
            value = false;
            alertMessage.showAlertValidateFailed("El miembro ya se encuentra registrado");
        }
        
        if(validation.existsInvalidCharactersForCurp(tfCURP.getText())){
            value = false;
            alertMessage.showAlertValidateFailed("CURP inválida");
        }
        
        return value;
    }
    
        
    private boolean validateCredential(LoginCredential loginCredential){
        boolean value = true;
        AlertMessage alertMessage = new AlertMessage();
        Validation validation = new Validation();
        if(validation.emptyField(loginCredential.getUser()) || validation.emptyField(loginCredential.getPassword())){
            value = false;
            alertMessage.showAlertValidateFailed("Campos vacios");
        }
        
        if(validation.existsInvalidCharactersForEmail(loginCredential.getUser())){
            value = false;
            alertMessage.showAlertValidateFailed("Correo inválido");
        }
        return value;
    }
    
    private boolean isAlreadyRegisterd(Member member){
        boolean value = false;
        AlertMessage alertMessage = new AlertMessage();
        try {
            MemberDAO memberDAO = new MemberDAO();
            memberDAO.getMemberByLicense(member.getProfessionalLicense());
            value = true;
        } catch (BusinessException ex) {
            if(ex.getMessage().equals("DataBase connection failed ")){
                alertMessage.showAlertValidateFailed("Error en la conexion con la base de datos");
                Stage stage = (Stage) btClose.getScene().getWindow();
                stage.close();
                openLogin();
            }else{  
                Log.logException(ex);
            }
        }
        return value;
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
