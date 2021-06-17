package GUI;

import businessLogic.MemberDAO;
import businessLogic.MinuteDAO;
import businessLogic.AgreementDAO;
import businessLogic.MeetingDAO;
import domain.Agreement;
import domain.Meeting;
import domain.Member;
import domain.Minute;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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


public class MinuteModifyController implements Initializable {

    private ObservableList<Member> members;
    private ObservableList<Agreement> agreements;
     private ObservableList<Agreement> agreementsOld;
    @FXML ComboBox<Member> cbMember;
    @FXML TextField tfAgreement;
    @FXML TextField tfPeriod;
    @FXML TableColumn <Member,String>tcMember;
    @FXML TableColumn <Agreement,String> tcAgreement;
    @FXML TableColumn <Agreement,String>tcPeriod;
    @FXML TableColumn tcNumber;
    @FXML TextArea taDue;
    @FXML TextArea taNote;
    @FXML TableView<Agreement> tvAgreement;
    @FXML Button btDelete;
    @FXML Button btAdd;
    @FXML Button btSave;
    @FXML Button btReturn;
    private int idMinute = 1;
    private int idMeeting = 1;
    private int indexAgreement;
    private ListChangeListener<Agreement> tableAgreementListener;
    private Member member;
    private Minute minute;
    private Meeting meeting;
    
    public void setMember(Member member){
        this.member = member;      
    }
     
    public void initializeAgreements(){
        AgreementDAO agreementDAO = new AgreementDAO();
        try {
            ArrayList<Agreement> agreementList = new ArrayList<Agreement>();
            agreementList = agreementDAO.getAgreementsMinute(idMinute);
            for(int i = 0; i < agreementList.size(); i++){
                agreements.add(agreementList.get(i));
                agreementsOld.add(agreementList.get(i));
            }
        } catch (BusinessException ex) {
            Log.logException(ex);
        }
    }
    
    public void initializeMinute(Meeting meeting){  
        this.meeting = meeting;
        idMeeting = meeting.getKey();        
        try {
            MinuteDAO minuteDAO = new MinuteDAO();
            this.minute = minuteDAO.getMinute(idMeeting); 
            this.idMinute = minute.getIdMinute();
            taDue.setText(minute.getDue());
            taNote.setText(minute.getNote());
            agreements = FXCollections.observableArrayList();
            initializeAgreements();
            initializeMembers();
            tvAgreement.setItems(agreements);

        } catch (BusinessException ex) {
            Log.logException(ex);
        }
    }
        
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tcAgreement.setCellValueFactory(new PropertyValueFactory("description"));
        tcPeriod.setCellValueFactory(new PropertyValueFactory("period"));
        tcNumber.setCellValueFactory(new PropertyValueFactory("finishTime"));
        tcMember.setCellValueFactory(new PropertyValueFactory("professionalLicense"));
        agreements = FXCollections.observableArrayList();
        agreementsOld = FXCollections.observableArrayList();
        tvAgreement.setItems(agreements);
        members = FXCollections.observableArrayList();
       
        cbMember.setItems(members);
        cbMember.getSelectionModel().selectFirst();

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
    
    public void initializeMeeting(int idMeeting){
            this.idMeeting = idMeeting;
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
                tfPeriod.setText(agreement.getPeriod());
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
        } catch (BusinessException ex) {
            Log.logException(ex);
        }
    }
    
     @FXML
    private void add(ActionEvent event){
        String period = ""; 
        String agreementDescription = "";
        Member member = cbMember.getSelectionModel().getSelectedItem();
        period = tfPeriod.getText();
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
        tfPeriod.setText("");
    }
    
    @FXML
    public void actionSave(){
       String note = "";
       String due = "";
       String initialState = "pendiente";
       note = taNote.getText();
       due = taDue.getText();
       Minute newMinute = new Minute(note,initialState,due,idMeeting);
       if(validateMinute(newMinute)){
           try {
               MinuteDAO minuteDAO = new MinuteDAO();
               newMinute.setIdMinute(idMinute);
               minuteDAO.updatedSucessful(newMinute);
               addAgreements();
               AlertMessage alertMessage = new AlertMessage();
               alertMessage.showAlertSuccesfulSave("La minuta");
               deleteComments();
               Stage stage = (Stage)btSave.getScene().getWindow();
               stage.close();
           } catch (BusinessException ex) {
                Log.logException(ex);
           }          
       }
    }
    
    public void deleteComments(){
        try {
            MinuteDAO minuteDAO = new MinuteDAO();
            minuteDAO.deletedSucessfulMinutesComments(idMinute);
        } catch (BusinessException ex) {
            Log.logException(ex);
        }
    }
    public void addAgreements(){
         try {
          AgreementDAO agreementDAO = new AgreementDAO();
          for(int i = 0; i < agreementsOld.size(); i++){
              agreementDAO.deletedSucessful(agreementsOld.get(i));
          }
    
           for(int i = 0; i < agreements.size(); i++){ 
                  agreements.get(i).setIdMinute(idMinute);
                  System.out.println(idMinute);
                  agreementDAO.savedSucessfulAgreement(agreements.get(i));
              
           }
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
    
    private boolean isEmptyFields(Minute minute){
        boolean value = false;
        if(minute.getDue().isEmpty()||minute.getNote().isEmpty()){
            value = true;
        }
        return value;
    }
    @FXML
    public void actionReturn(){
         Stage stage = (Stage) btReturn.getScene().getWindow();
         stage.close();
         openMinuteCheckComment();
    }
    
  
    private void openMinuteCheckComment(){
         try {
            Stage primaryStage= new Stage();
            URL url = new File("src/GUI/MinuteCheckComment.fxml").toURI().toURL();
            FXMLLoader loader = new FXMLLoader(url);
            loader.setLocation(url);
            loader.load();
            MinuteCheckCommentController minuteCheckCommentController = loader.getController();
            minuteCheckCommentController.setMeeting(meeting);
            minuteCheckCommentController.setMember(member);
            minuteCheckCommentController.setMinute(minute);
            Parent root = loader.getRoot();
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            Stage stage = (Stage) btReturn.getScene().getWindow();
            primaryStage.show();
            stage.close();
        }catch (IOException ex) {
            Log.logException(ex);
        }
    }
    
}
