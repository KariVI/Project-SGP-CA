package GUI;

import businessLogic.MemberDAO;
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
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import log.BusinessException;
import log.Log;

public class MemberModifyController implements Initializable {

    private ObservableList<String> roles;
    private ObservableList<String> degrees;
    private ObservableList<Integer> years;
    @FXML
    private ComboBox<Integer> cbYears;
    @FXML
    private ComboBox<String> cbRoles;
    @FXML
    private ComboBox<String> cbDegrees;
    @FXML
    private TextFieldLimited tfName;
    @FXML
    private Label lbProfessionalLicense;
    @FXML
    private TextFieldLimited tfNameDegree;
    @FXML
    private TextFieldLimited tfUniversity;
    @FXML
    private Button btReturn;
    @FXML
    private Button btSave;
    @FXML
    private ToggleGroup tgState;
    @FXML
    private RadioButton rbActive;
    @FXML
    private RadioButton rbInactive;
    
    private Member member;
    private Member loginMember;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tfName.setMaxLength(150);
        tfNameDegree.setMaxLength(200);
        tfUniversity.setMaxLength(200);
        roles = FXCollections.observableArrayList();
        roles.add("Integrante");
        roles.add("Colaborador");
        cbRoles.setItems(roles);
        degrees = FXCollections.observableArrayList();
        degrees.add("Maestria");
        degrees.add("Doctorado");
        cbDegrees.setItems(degrees);
        years = FXCollections.observableArrayList();
        for (int i = getCurrentYear(); i > 1899; i--) {
            years.add(i);
        }

        cbYears.setItems(years);
        cbYears.getSelectionModel().selectFirst();
    }
    
    public void setLoginMember(Member loginMember) {
        this.loginMember = loginMember;
    }

    @FXML
    private void save() {
        String name = "";
        String role = "";
        String degree = "";
        String professionalLicense = "";
        String nameDegree = "";
        String universityName = "";
        String state = "";
        int degreeYear = 0;
        name = tfName.getText();
        role = cbRoles.getSelectionModel().getSelectedItem();
        degree = cbDegrees.getValue();
        professionalLicense = lbProfessionalLicense.getText();
        nameDegree = tfNameDegree.getText();
        degreeYear = cbYears.getSelectionModel().getSelectedItem();
        universityName = tfUniversity.getText();
        state = ((RadioButton) tgState.getSelectedToggle()).getText();
        Member newMember = new Member(professionalLicense, name, role, degree, nameDegree, universityName, degreeYear, state, member.getKeyGroupAcademic());
        MemberDAO memberDAO = new MemberDAO();
        if (validateMember(newMember)) {
            try {
                AlertMessage alertMessage = new AlertMessage();
                memberDAO.updatedSucessful(newMember);
                alertMessage.showUpdateMessage();
                Stage stage = (Stage) btSave.getScene().getWindow();
                stage.close();
                openShowMember(newMember);
            } catch (BusinessException ex) {
                Log.logException(ex);
                 AlertMessage alertMessage  = new AlertMessage();
                alertMessage.showAlertValidateFailed("Error en la conexión con la base de datos");
                Stage stage = (Stage) btReturn.getScene().getWindow();
                stage.close();
                openLogin();
            }
        }
    }

    public void initializeMember(Member member) {
        this.member = member;
        tfName.setText(member.getName());
        cbRoles.getSelectionModel().select(member.getRole());
        cbDegrees.getSelectionModel().select(member.getDegree());
        cbYears.getSelectionModel().select((Integer) member.getDegreeYear());
        tfNameDegree.setText(member.getNameDegree());
        tfUniversity.setText(member.getUniversityName());
        lbProfessionalLicense.setText(member.getProfessionalLicense());
        if (member.getState().equals("Activo")) {
            rbActive.setSelected(true);
        } else {
            rbInactive.setSelected(true);
        }

    }

    @FXML
    private void actionReturn() {
        Stage stage = (Stage) btReturn.getScene().getWindow();
        stage.close();
        openShowMember(member);
    }

    private int getCurrentYear() {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }

    private boolean validateMember(Member member) {
        boolean value = true;
        AlertMessage alertMessage = new AlertMessage();
        if (isEmptyFields(member)) {
            value = false;
            alertMessage.showAlertValidateFailed("Campos vacios");
        }
        if (!isAlreadyRegisterd(member)) {
            value = false;
            alertMessage.showAlertValidateFailed("El miembro ya se encuentra registrado");
        }

        if (!invalidFields(member)) {
            value = false;
            alertMessage.showAlertValidateFailed("Campos inválidos");
        }

        return value;
    }

    private boolean isEmptyFields(Member member) {
        boolean emptyFields = false;
        if (verifyEmptySpaces() || (member.getProfessionalLicense().isEmpty()) || (member.getName().isEmpty()) || (member.getRole().isEmpty()) || (member.getDegree().isEmpty()) || (member.getNameDegree().isEmpty()) || (member.getUniversityName().isEmpty()) || member.getDegreeYear() == 0) {
            emptyFields = true;
        }

        return emptyFields;
    }

    private boolean isAlreadyRegisterd(Member member) {
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

    private boolean invalidFields(Member member) {
        boolean value = true;
        Validation validation = new Validation();
        if (validation.findInvalidField(member.getName()) || validation.findInvalidField(member.getNameDegree())
                || validation.findInvalidField(member.getUniversityName())) {
            value = false;
        }

        return value;
    }

    private void openShowMember(Member member) {
        Stage primaryStage = new Stage();
        try {
            URL url = new File("src/GUI/MemberView.fxml").toURI().toURL();
            FXMLLoader loader = new FXMLLoader(url);
            loader.setLocation(url);
            loader.load();
            MemberViewController memberViewController = loader.getController();
            memberViewController.initializeMember(member);
             memberViewController.setMemberCurrently(loginMember);
            Parent root = loader.getRoot();
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException ex) {
            Log.logException(ex);
        }
    }

    private boolean verifyEmptySpaces() {
        boolean value = false;
        Validation validation = new Validation();
        if (validation.emptyField(tfUniversity.getText()) || validation.emptyField(tfName.getText()) || validation.emptyField(lbProfessionalLicense.getText()) || validation.emptyField(tfNameDegree.getText())) {
            value = true;
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
