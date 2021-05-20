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
    private ObservableList<GroupAcademic> academicGroups;
    @FXML private ComboBox<GroupAcademic> cbAcademicGroups;
    @FXML private ComboBox<Integer> cbYears;
    @FXML private ComboBox<String> cbRoles;
    @FXML private ComboBox<String> cbDegrees;
    @FXML private TextFieldLimited tfName;
    @FXML private TextField tfProfessionalLicense;
    @FXML private TextField tfNameDegree;
    @FXML private TextField tfUniversity;
    @FXML private Button btClose;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      tfName.setMaxlength(10);
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
     academicGroups = FXCollections.observableArrayList();
     initializeAcademicGroups();
     cbAcademicGroups.setItems(academicGroups);
     for(int i = getActualYear(); i>1899; i--){
            years.add(i);
     }
     cbYears.setItems(years);
     cbYears.getSelectionModel().selectFirst();
     cbAcademicGroups.getSelectionModel().selectFirst();
    }    

    @FXML 
    public void save(){
       String name = "", role = "", degree = "", professionalLicense = "", nameDegree = "", universityName = "";
       int degreeYear = 0;
       GroupAcademic groupAcademic;
       name = tfName.getText();
       role = cbRoles.getSelectionModel().getSelectedItem();
       degree = cbDegrees.getValue();
       professionalLicense = tfProfessionalLicense.getText();
       nameDegree = tfNameDegree.getText();
       degreeYear = cbYears.getSelectionModel().getSelectedItem();
       universityName = tfUniversity.getText();
       groupAcademic = cbAcademicGroups.getSelectionModel().getSelectedItem();
       Member newMember = new Member(professionalLicense, name, role, degree,nameDegree,universityName, degreeYear,groupAcademic.getKey());
       MemberDAO memberDAO = new MemberDAO();
       if(validateMember(newMember)){
             try { 
             memberDAO.saveMember(newMember);
              AlertMessage.showAlertSuccesfulSave("El miembro fue registrado con éxito");
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
    
    public void initializeAcademicGroups(){
        try {
            GroupAcademicDAO groupAcademicDAO = new GroupAcademicDAO();
            GroupAcademic academicGroup = groupAcademicDAO.getGroupAcademicById("1491");
            academicGroups.add(academicGroup);
        } catch (BusinessException ex) {
            Logger.getLogger(MemberRegisterController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public int getActualYear(){
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }
    
    public boolean validateMember(Member member){
        boolean value = true;
        if(isEmptyFields(member)){
            value = false;
            AlertMessage.showAlertValidateFailed("Campos vacios");
        }
        if(!isAlreadyRegisterd(member)){
            value = false;
            AlertMessage.showAlertValidateFailed("El miembro ya se encuentra registrado");
        }
        
        if(!invalidFields(member)){
            value = false;
            AlertMessage.showAlertValidateFailed("Campos inválidos");
        }
        
        return value;
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
        try {
            MemberDAO memberDAO = new MemberDAO();
            memberDAO.getMemberByLicense(member.getProfessionalLicense());
            value = true;
        } catch (BusinessException ex) {
            Log.logException(ex);
        }
        return value;
    }
    
    public boolean invalidFields(Member member){
        boolean value = true;
        if(Validation.findInvalidField(member.getName())||Validation.findInvalidField(member.getNameDegree())||
           Validation.findInvalidField(member.getUniversityName())){
            value = false;
        }
        return value;
    }
}
