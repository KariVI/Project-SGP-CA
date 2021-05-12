package GUI;


import businessLogic.MemberDAO;
import domain.Member;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
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
    @FXML private TextFieldLimited tfName;
    @FXML private TextFieldLimited tfProfessionalLicense;
    @FXML private TextFieldLimited tfNameDegree;
    @FXML private TextFieldLimited tfUniversity;
    @FXML private Button btClose;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
     tfName.setMaxlength(1);
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
       Member newMember = new Member(professionalLicense, name, role, degree,nameDegree,universityName, degreeYear,"1491");
       MemberDAO memberDAO = new MemberDAO();
       if(!isEmptyFields(newMember)){
           if(!isAlreadyRegisterd(newMember)){
             try { 
             memberDAO.saveMember(newMember);
              showAlertSucesfulSave();
             } catch (BusinessException ex) {
                Log.logException(ex);
            }
          } else{
            showAlertTroubleSave("El miembro ya se encuentra registrado");   
           }
       }else{
           showAlertTroubleSave("Campos vacios");
       }
    }
    
    @FXML
    public void close() {
        Stage stage = (Stage)btClose.getScene().getWindow();
        stage.close();
    }
    
    
    @FXML
    private void showAlertSucesfulSave() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setTitle("Información guardada");
        alert.setContentText("Miembro guardado con exito");
        alert.showAndWait();
    }
    
    @FXML
    private void showAlertTroubleSave(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setTitle("");
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    public int getActualYear(){
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
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
}
