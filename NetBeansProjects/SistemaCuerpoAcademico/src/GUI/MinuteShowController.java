package GUI;

import businessLogic.MinuteDAO;
import businessLogic.AgreementDAO;
import businessLogic.MeetingDAO;
import domain.Agreement;
import domain.Meeting;
import domain.Member;
import domain.Minute;
import domain.MinuteComment;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import log.BusinessException;
import log.Log;


public class MinuteShowController implements Initializable {

    private ObservableList<Member> members;
    private ObservableList<Agreement> agreements;
    @FXML ComboBox<Member> cbMember;
    @FXML TextField tfAgreement;
    @FXML TextField tfPeriod;
    @FXML TableColumn <Member,String>tcMember;
    @FXML TableColumn <Agreement,String> tcAgreement;
    @FXML TableColumn <Agreement,String>tcPeriod;
    @FXML TableColumn tcNumber;
    @FXML TextArea taDue;
    @FXML TextArea taNote;
    @FXML RadioButton rbApproveMinute;
    @FXML RadioButton rbDisapproveMinute;
    @FXML TableView<Agreement> tvAgreement;
    @FXML Button btReturn;
    @FXML Button btModify;
    @FXML Button btShowComments;
    private int idMinute = 0;
    private int idMeeting = 0;
    private Meeting meeting;
    private int indexAgreement;
    private Minute minute;
    private ListChangeListener<Agreement> tableAgreementListener;
    @FXML private ToggleGroup tgApproveMinute;
    private Member member;
    
  
    
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
            tvAgreement.setItems(agreements);
            verifyApprove();
            verifyMember();
        } catch (BusinessException ex) {
            Log.logException(ex);
                 AlertMessage alertMessage  = new AlertMessage();
                alertMessage.showAlertValidateFailed("Error en la conexi??n con la base de datos");
                Stage stage = (Stage) btReturn.getScene().getWindow();
                stage.close();
                openLogin();
        }
        
    }
    
   private void verifyApprove(){
        if(verifyMemberApproved()||verifyMinuteStateApprove()||!verifyMeetingAssistant()){
            disableRadioButtons();
        }
     
        if(verifyMinuteStateApprove()){
           disableBtShowComments();
        }
     
   }    
   
   private boolean verifyMeetingAssistant(){
        boolean value = false;
        try {
            MeetingDAO meetingDAO = new MeetingDAO();
            ArrayList<Member> memberList = meetingDAO.getAssistants(idMeeting);
            int i = 0;
            while(!value && i < memberList.size()){
                 if(member.equals(memberList.get(i))){
                    value = true;
                }  
                 
                i++; 
            }
             
        } catch (BusinessException ex) {
           Log.logException(ex);
                 AlertMessage alertMessage  = new AlertMessage();
                alertMessage.showAlertValidateFailed("Error en la conexi??n con la base de datos");
                Stage stage = (Stage) btReturn.getScene().getWindow();
                stage.close();
                openLogin();
        }
       
        return value;
   }
    
   private boolean verifyMinuteStateApprove(){
       boolean value = false;
       MinuteDAO minuteDAO = new MinuteDAO();
       MeetingDAO meetingDAO = new MeetingDAO();
        try {
            ArrayList<Member> ListMemberApprove = minuteDAO.getMembersApprove(minute);
            ArrayList<Member> ListMemberAssistants = meetingDAO.getAssistants(idMeeting);
            if(ListMemberAssistants.size() == ListMemberApprove.size()){
                value = true;
                minute.setState("Aprobada");
                minuteDAO.updatedSucessful(minute);
            }
        } catch (BusinessException ex) {
            Log.logException(ex);
                 AlertMessage alertMessage  = new AlertMessage();
                alertMessage.showAlertValidateFailed("Error en la conexi??n con la base de datos");
                Stage stage = (Stage) btReturn.getScene().getWindow();
                stage.close();
                openLogin();
        }
       return value;
   }
   
   private boolean verifyMemberApproved(){
       boolean value = false;
         try {
            MinuteDAO minuteDAO = new MinuteDAO();
            ArrayList<MinuteComment> ListMemberDisapprove = minuteDAO.getMinutesComments(idMinute);
            ArrayList<Member> ListMemberApprove = minuteDAO.getMembersApprove(minute);
            int i = 0;   
            while(i<ListMemberApprove.size()){
                if(ListMemberApprove.get(i).getProfessionalLicense().equals(member.getProfessionalLicense())){
                    value = true;
                    i = ListMemberApprove.size();
                }
                i++;
            }
            
            i = 0;
           while(i<ListMemberDisapprove.size()){
                if(ListMemberDisapprove.get(i).getProfessionalLicense().equals(member.getProfessionalLicense())){
                      value = true;
                  
                    i = ListMemberDisapprove.size();
                }
                i++;
            }
           
        } catch (BusinessException ex) {
            Log.logException(ex);
                 AlertMessage alertMessage  = new AlertMessage();
                alertMessage.showAlertValidateFailed("Error en la conexi??n con la base de datos");
                Stage stage = (Stage) btReturn.getScene().getWindow();
                stage.close();
                openLogin();
        } 
         return value;
   }
   

   
    private void disableRadioButtons(){
        rbApproveMinute.setOpacity(0);
        rbDisapproveMinute.setOpacity(0);
        rbApproveMinute.setDisable(true);
        rbDisapproveMinute.setDisable(true);
    }
    
    
    private void initializeAgreements(){
        AgreementDAO agreementDAO = new AgreementDAO();
        try {
            ArrayList<Agreement> agreementList = new ArrayList<Agreement>();
            agreementList = agreementDAO.getAgreementsMinute(idMinute);
            for(int i = 0; i < agreementList.size(); i++){
                agreements.add(agreementList.get(i));
            }
        } catch (BusinessException ex) {
            Log.logException(ex);Log.logException(ex);
                 AlertMessage alertMessage  = new AlertMessage();
                alertMessage.showAlertValidateFailed("Error en la conexi??n con la base de datos");
                Stage stage = (Stage) btReturn.getScene().getWindow();
                stage.close();
                openLogin();
        }
    }
    
    @FXML
    private void actionReturn(){
        checkRadioButtons();
        Stage stage = (Stage) btReturn.getScene().getWindow();
        stage.close();
        openMeetingShow();
    }
   
   private void checkRadioButtons(){
       if(!rbApproveMinute.isDisabled()){
        String approveMinute = ((RadioButton) tgApproveMinute.getSelectedToggle()).getText();
        if(approveMinute != null && approveMinute.equals("Estoy de acuerdo")){
            try {
                MinuteDAO minuteDAO = new MinuteDAO();
                minuteDAO.approveMinute(idMinute, member.getProfessionalLicense());
                AlertMessage alertMessage= new AlertMessage();
                alertMessage.showAlertSuccesfulSave("La validacion");    
            } catch (BusinessException ex) {
                Log.logException(ex);
                 AlertMessage alertMessage  = new AlertMessage();
                alertMessage.showAlertValidateFailed("Error en la conexi??n con la base de datos");
                Stage stage = (Stage) btReturn.getScene().getWindow();
                stage.close();
                openLogin();
            }
         } 
       }
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
   
    public void setMember(Member member){
        this.member = member;      
    }
    
    private void verifyMember(){
        if(!verifyRole()){
            disableBtShowComments();         
        }
        
    }
    
    private boolean verifyRole(){
        MeetingDAO meetingDAO = new MeetingDAO();
        boolean value = false;    
        try {
            ArrayList<Member> listAssistants = meetingDAO.getAssistants(idMeeting);
            int i = 0;
            while(i < listAssistants.size()){
                if(member.getProfessionalLicense().equals(listAssistants.get(i).getProfessionalLicense())&&
                   (listAssistants.get(i).getRole().equals("Lider") || listAssistants.get(i).getRole().equals( "Secretario"))){
                    value = true;
                }
                i++;
            }
            
        } catch (BusinessException ex) {
            Log.logException(ex);
                 AlertMessage alertMessage  = new AlertMessage();
                alertMessage.showAlertValidateFailed("Error en la conexi??n con la base de datos");
                Stage stage = (Stage) btReturn.getScene().getWindow();
                stage.close();
                openLogin();
        } 
        return value;
    }
         
    private void disableBtShowComments(){
        btShowComments.setOpacity(0);       
        btShowComments.setDisable(true);
    }
    
    @FXML
    private void actionMinuteComment(){
         try {
            Stage primaryStage= new Stage();
            URL url = new File("src/GUI/MinuteComment.fxml").toURI().toURL();
            FXMLLoader loader = new FXMLLoader(url);
            loader.setLocation(url);
            loader.load();
            MinuteCommentController minuteCommentController = loader.getController();
            minuteCommentController.setMember(member);
            minuteCommentController.setMinute(minute);
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
    
    @FXML
    private void actionMinuteCheckComment(){
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
            Stage stage = (Stage) btShowComments.getScene().getWindow();
            primaryStage.show();
            stage.close();
        }catch (IOException ex) {
            Log.logException(ex);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tcMember.setCellValueFactory(new PropertyValueFactory<Member,String>("professionalLicense"));
        tcAgreement.setCellValueFactory(new PropertyValueFactory<Agreement,String>("description"));
        tcPeriod.setCellValueFactory(new PropertyValueFactory<Agreement,String>("period")); 
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
