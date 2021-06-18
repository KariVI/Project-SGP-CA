package GUI;

import businessLogic.MemberDAO;
import businessLogic.MinuteDAO;
import businessLogic.AgreementDAO;
import businessLogic.MeetingDAO;
import domain.Agreement;
import domain.Meeting;
import domain.Member;
import domain.Minute;
import java.io.IOException;
import java.net.URL;
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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import log.BusinessException;
import log.Log;


public class MinuteRegisterController implements Initializable {

    private ObservableList<Member> members;
    private ObservableList<Agreement> agreements;
    private ObservableList<String> periods;
    @FXML ComboBox<Member> cbMember;
    @FXML ComboBox<String> cbPeriod;
    @FXML TextField tfAgreement;  
    @FXML TableColumn <Member,String>tcMember;
    @FXML TableColumn <Agreement,String> tcAgreement;
    @FXML TableColumn <Agreement,String>tcPeriod;
    @FXML TextArea taDue;
    @FXML TextArea taNote;
    @FXML TableView<Agreement> tvAgreement;
    @FXML Button btDelete;
    @FXML Button btAdd;
    @FXML Button btFinish;
    private int idMinute;
    private Meeting meeting;
    private int indexAgreement;
    private Member member;
    private ListChangeListener<Agreement> tableAgreementListener;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tcAgreement.setCellValueFactory(new PropertyValueFactory("description"));
        tcPeriod.setCellValueFactory(new PropertyValueFactory("period"));
        tcMember.setCellValueFactory(new PropertyValueFactory("professionalLicense"));
        agreements = FXCollections.observableArrayList();
        tvAgreement.setItems(agreements);
        members = FXCollections.observableArrayList(); 
        cbMember.setItems(members);
        periods = FXCollections.observableArrayList();
        periods.add("Feb-Jun");
        periods.add("Ago-Ene");
        cbPeriod.setItems(periods);
        cbPeriod.getSelectionModel().selectFirst();
        tvAgreement.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                 setSelectedAgreement();
             }
            }
        );
        
        
        tableAgreementListener = new ListChangeListener<Agreement>(){
            @Override
            public void onChanged(ListChangeListener.Change<? extends Agreement> agreement) {
                setSelectedAgreement();
            }
        };        
    }
    
    public void setMeeting(Meeting meeting){
            this.meeting = meeting;
    }
    public void setMember(Member member){
        this.member = member;
        initializeMembers();
    }
    
    private Agreement getSelectedAgreement(){
        Agreement agreement = null;
        int tamTable = 1;
        if(tvAgreement != null){
            List<Agreement> agreementTable = tvAgreement.getSelectionModel().getSelectedItems();
            if(agreementTable.size() == tamTable){
                agreement = agreementTable.get(0);
            }
        }
        return agreement;
    }
    
    private void setSelectedAgreement(){
        Agreement agreement = getSelectedAgreement();
        indexAgreement = agreements.indexOf(agreement);
        if(agreement != null){
            try {
                MemberDAO memberDAO = new MemberDAO();
                Member member = memberDAO.getMemberByLicense(agreement.getProfessionalLicense());
                tfAgreement.setText(agreement.getDescription());
                cbPeriod.getSelectionModel().select(agreement.getPeriod());
                cbMember.getSelectionModel().select(member);
                
            } catch (BusinessException ex) {
                Log.logException(ex);
            }
        }
    }
    
    void initializeMembers() {
        try {
            MemberDAO memberDAO = new MemberDAO();
            ArrayList <Member> memberList = new ArrayList<Member>();
            memberList = memberDAO.getMembers(member.getKeyGroupAcademic());
            for( int i = 0; i<memberList.size(); i++) {
                members.add(memberList.get(i));
            }
            cbMember.getSelectionModel().selectFirst();
        } catch (BusinessException ex) {
            Log.logException(ex);
        }
    }
    
     @FXML
    private void add(ActionEvent event){
        String period = ""; 
        String agreementDescription = "";
        Member member = cbMember.getSelectionModel().getSelectedItem();
        period = cbPeriod.getSelectionModel().getSelectedItem();
        agreementDescription = tfAgreement.getText();
        Agreement agreement = new Agreement(period,agreementDescription,member.getProfessionalLicense());
        if(validateAgreement(agreement)){
          agreements.add(agreement);
        }      
        cleanFields();
    }
    
    @FXML
    private void delete(ActionEvent event){
        agreements.remove(indexAgreement);
        cleanFields();
    }
    
    @FXML
    private void cleanFields(){
        tfAgreement.setText("");
    }
    
    public void actionFinish(){
       String note = "";
       String due = "";
       String initialState = "Registrada";
       note = taNote.getText();
       due = taDue.getText();
       Minute minute = new Minute(note,initialState,due,meeting.getKey());
       if(validateMinute(minute)){
           int idMinute = 0;
           try {
               MinuteDAO minuteDAO = new MinuteDAO();
               minuteDAO.savedSucessfulMinute(minute);
               idMinute = minuteDAO.getIdMinute(minute);
               AgreementDAO agreementDAO = new AgreementDAO();
               for(int i = 0; i < agreements.size(); i++){
                   agreements.get(i).setIdMinute(idMinute);
                   agreementDAO.savedSucessfulAgreement(agreements.get(i));
               }
               AlertMessage alertMessage = new AlertMessage();
               alertMessage.showAlertSuccesfulSave("La minuta");
               changeStateMeeting();
               Stage stage = (Stage)btFinish.getScene().getWindow();
               stage.close();
               openMeetingShow();
           } catch (BusinessException ex) {
                Log.logException(ex);
           }          
       }
    }
  
    public void changeStateMeeting(){
        try {
            MeetingDAO meetingDAO = new MeetingDAO();
            Meeting newMeeting = meetingDAO.getMeetingById(meeting.getKey());
            meeting.setState("Concluida");
            newMeeting.setState("Concluida");
            meetingDAO.changedStateSucessful(newMeeting);
            
        } catch (BusinessException ex) {
            Log.logException(ex);
        }
    }
    public boolean validateAgreement(Agreement agreement){
        boolean value = true;
        AlertMessage alertMessage = new AlertMessage();
        if(isEmptyFields(agreement)){
            value = false;
            alertMessage.showAlertValidateFailed("Campos vacios");
        }
        
        if(invalidFields(agreement)){
            value = false;
            alertMessage.showAlertValidateFailed("Campos inválidos");
        }
             
        if(repeatedAgreement(agreement)){
            value = false;
            alertMessage.showAlertValidateFailed("Acuerdo repetido");
        }
        return value;
    }
    
        
    public boolean validateMinute(Minute minute){
        boolean value = true;
        AlertMessage alertMessage = new AlertMessage();
        if(isEmptyFields(minute)){
            value = false;
            alertMessage.showAlertValidateFailed("Campos vacios");
        }
        
        if(repeatedMinute(minute)){
            value = false;
            alertMessage.showAlertValidateFailed("Minuta repetida");
        }
        return value;
    }
    
    public boolean isEmptyFields(Agreement agreement){
        boolean value = false;
        if(agreement.getDescription().isEmpty()||agreement.getPeriod().isEmpty()||agreement.getProfessionalLicense().isEmpty()){
            value = true;
        }
        return value;
    }
    
    public boolean invalidFields(Agreement agreement){
        boolean value = false;
        Validation validation = new Validation();
        if(validation.findInvalidField(agreement.getDescription())){
           value = true;
        }
        return value;
    }
    
    public boolean repeatedAgreement(Agreement agreement){
        Boolean value = false;
        int i = 0;
        while(!value && i<agreements.size()){
            if(agreements.get(i).equals(agreement)){
                value = true;
            }
            i++;
        }
       return value;
    }
    
    public boolean isEmptyFields(Minute minute){
        boolean value = false;
        if(minute.getDue().isEmpty()||minute.getNote().isEmpty()){
            value = true;
        }
        return value;
    }
    
    
    public boolean repeatedMinute(Minute minute){
        boolean value = false;
        AlertMessage alertMessage = new AlertMessage();
        try {
            MinuteDAO minuteDAO = new MinuteDAO();
            minuteDAO.getIdMinute(minute);
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
    
    private void openMeetingShow(){ 
        try {
                 FXMLLoader loader = new FXMLLoader(getClass().getResource("MeetingShow.fxml"));
                 Parent root = loader.load();
                 MeetingShowController meetingShowController = loader.getController();
                 meetingShowController.setMeeting(meeting);
                 meetingShowController.setMember(member);
                      try {
                          meetingShowController.initializeMeeting();
                      } catch (BusinessException ex) {
                         Log.logException(ex);
                      }
                 Scene scene = new Scene(root);
                 Stage stage = new Stage();
                 stage.setScene(scene);
                 stage.showAndWait();
             } catch (IOException ex) {
                 Log.logException(ex);
             }    
    }
    
}
