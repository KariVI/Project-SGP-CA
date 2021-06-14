package GUI;


import businessLogic.GroupAcademicDAO;
import businessLogic.MemberDAO;
import domain.GroupAcademic;
import domain.Member;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
    private String groupAcademicKey;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      roles = FXCollections.observableArrayList();
     roles.add("Integrante");
     roles.add("Colaborador");
     roles.add("Responsable");
     cbRoles.setItems(roles);
     degrees = FXCollections.observableArrayList();
     degrees.add("Maestria");
     degrees.add("Doctorado");
     cbDegrees.setItems(degrees);
     years = FXCollections.observableArrayList();
     for(int i = getActualYear(); i>1899; i--){
            years.add(i);
     }
     cbYears.setItems(years);
     cbYears.getSelectionModel().selectFirst();

    }    

    @FXML 
    public void save(){
       String name = "", role = "", degree = "", professionalLicense = "", nameDegree = "", universityName = "";
       int degreeYear = 0;
       name = tfName.getText();
       role = cbRoles.getSelectionModel().getSelectedItem();
       degree = cbDegrees.getValue();
       professionalLicense = tfProfessionalLicense.getText();
       nameDegree = tfNameDegree.getText();
       degreeYear = cbYears.getSelectionModel().getSelectedItem();
       universityName = tfUniversity.getText();
       Member newMember = new Member(professionalLicense, name, role, degree,nameDegree,universityName, degreeYear,groupAcademicKey);
       MemberDAO memberDAO = new MemberDAO();
       if(validateMember(newMember)){
             try { 
              memberDAO.saveMember(newMember);
              AlertMessage alertMessage = new AlertMessage();
              alertMessage.showAlertSuccesfulSave("El miembro fue registrado con éxito");
              close();
             } catch (BusinessException ex) {
                Log.logException(ex);
             }
       }
    }
    
    @FXML
    public void close() {
        Stage stage = (Stage)btClose.getScene().getWindow();
        stage.close();
    }
    
   
    public int getActualYear(){
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }
    
    public boolean validateMember(Member member){
        boolean value = true;
        AlertMessage alertMessage = new AlertMessage();
        if(isEmptyFields(member)){
            value = false;
            alertMessage.showAlertValidateFailed("Campos vacios");
        }
        if(isAlreadyRegisterd(member)){
            value = false;
            alertMessage.showAlertValidateFailed("El miembro ya se encuentra registrado");
        }
        
        if(!invalidFields(member)){
            value = false;
            alertMessage.showAlertValidateFailed("Campos inválidos");
        }
        
        return value;
    }
    public void setGroupAcademicKey(String groupAcademicKey){
        this.groupAcademicKey = groupAcademicKey;
    }
    
    public boolean isEmptyFields(Member member){
        boolean emptyFields = false;
        if((member.getProfessionalLicense().isEmpty())||(member.getName().isEmpty())||(member.getRole().isEmpty())||(member.getDegree().isEmpty())|| (member.getNameDegree().isEmpty())||(member.getUniversityName().isEmpty())|| member.getDegreeYear() == 0){
            emptyFields = true;
        }
        return emptyFields;
    }
    
    public boolean isAlreadyRegisterd(Member member){
        boolean value = false;
        AlertMessage alertMessage = new AlertMessage();
        try {
            MemberDAO memberDAO = new MemberDAO();
            memberDAO.getMemberByLicense(member.getProfessionalLicense());
            value = true;
        } catch (BusinessException ex) {
            if(ex.getMessage().equals("DataBase connection failed ")){
                alertMessage.showAlertValidateFailed("Error en la conexion con la base de datos");
            }else{  
                Log.logException(ex);
            }
        }
        return value;
    }
    
    public boolean invalidFields(Member member){
        boolean value = true;
        Validation validation = new Validation();
        if(validation.findInvalidField(member.getName())||validation.findInvalidField(member.getNameDegree())||
           validation.findInvalidField(member.getUniversityName())){
            value = false;
        }
        return value;
    }
}
