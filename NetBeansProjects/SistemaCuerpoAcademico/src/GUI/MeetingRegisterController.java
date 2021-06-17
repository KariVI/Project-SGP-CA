
package GUI;

import businessLogic.MeetingDAO;
import businessLogic.MemberDAO;
import businessLogic.PrerequisiteDAO;
import businessLogic.TopicDAO;
import domain.Meeting;
import domain.Member;
import domain.Prerequisite;
import domain.Topic;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.converter.LocalDateStringConverter;
import log.BusinessException;
import log.Log;


public class MeetingRegisterController implements Initializable {

    private ObservableList<Member> members;
    private ObservableList<Prerequisite> prerequisites;
    private ObservableList<Topic> topics;
    @FXML private TextFieldLimited tfSubject;
    @FXML private TextFieldLimited tfHour;
    @FXML private Button btExit;
    @FXML private ComboBox cbLeader;
    @FXML private ComboBox cbSecretary;
    @FXML private  DatePicker dpDate;
    @FXML private TableColumn tcDescription;
    @FXML private TableColumn tcMandated;
    @FXML private TextFieldLimited tfDescription;
    @FXML private ComboBox cbMember;
    private int indexPrerequisite;
    @FXML TableView<Prerequisite> tvPrerequisite;
    private ListChangeListener<Prerequisite> tablePrerequisiteListener;
    @FXML private Button btAddTopic;
    @FXML private Button btSave;
    @FXML private Button btAddPrerequisite;
    @FXML private Button btDelete;
    private int idMeeting;
    private String keyGroupAcademic;
    private Member member;

    public void setMember(Member member) {
        this.member = member;
    }

    public String getKeyGroupAcademic() {
        return keyGroupAcademic;
    }

    public void setKeyGroupAcademic(String keyGroupAcademic) {
        this.keyGroupAcademic = keyGroupAcademic;
        initializeMembers();
        cbMember.getSelectionModel().selectFirst();
        cbLeader.getSelectionModel().selectFirst();
        cbSecretary.getSelectionModel().selectFirst();
    }

    
    @FXML
    private void actionAddTopics(ActionEvent actionEvent){  
         try{ 
            Stage primaryStage= new Stage();
            URL url = new File("src/GUI/topicRegister.fxml").toURI().toURL();
           try{
              FXMLLoader loader = new FXMLLoader(url);
              loader.setLocation(url);
              loader.load();
              TopicRegisterController topicRegisterController =loader.getController(); 
              topicRegisterController.setMember(member);
              topicRegisterController.initializeMeeting(idMeeting,this);          
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
    
    
    public void setTopics(ObservableList<Topic> topics){
       this.topics = topics;
       btSave.setDisable(false);
       btAddTopic.setDisable(true);
    }
    
    @FXML 
    private void actionSave (ActionEvent actionEvent){    
        String subject= tfSubject.getText();
        String hour= tfHour.getText();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        if(!validateFieldEmpty() && validateInformationField() && validateDate()){
            String date= dpDate.getValue().format(formatter);
            Meeting meeting = new Meeting(subject,date,hour,keyGroupAcademic);
            if(!searchRepeateMeeting(meeting)){  
                save(meeting);
            }else{  
                AlertMessage alertMessage = new AlertMessage();
                alertMessage.showAlertValidateFailed("La reunión ya se encuentra registrado");

            }        
        }else{  
            sendAlert();
        }
    }
    
  
   
    @FXML
    private void actionExit(ActionEvent actionEvent){   
        Stage stage = (Stage) btExit.getScene().getWindow();
        stage.close();
        openMeetingList();
    
    }
    
    private void openMeetingList(){ 
         try{ 
            Stage primaryStage= new Stage();
            URL url = new File("src/GUI/meetingList.fxml").toURI().toURL();
           try{
              FXMLLoader loader = new FXMLLoader(url);
              loader.setLocation(url);
              loader.load();
              MeetingListController meetingListController =loader.getController();   
              meetingListController.setMember(member);
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
    
    private void disableButtonSave(){   
        if(topics==null){   
            btSave.setDisable(true);
        
        }
    }
    
    @FXML
    private void actionDelete(ActionEvent event){
        prerequisites.remove(indexPrerequisite);
        cleanFields();
    }
    
    @FXML 
    private void actionAddPrerequisite(ActionEvent actionEvent){    
        String description="";
        Member member = (Member) cbMember.getSelectionModel().getSelectedItem();
        description = tfDescription.getText();
       
        Prerequisite prerequisite = new Prerequisite(description);
        prerequisite.setMandated(member);
        if(validatePrerequisite(prerequisite)){
            prerequisites.add(prerequisite);
        }
        cleanFields();
    }
    
    private void save(Meeting meeting){ 
        MeetingDAO meetingDAO = new MeetingDAO ();
        try {
            if( validateAssistants()){
                if(meetingDAO.savedSucessful(meeting)){
                    idMeeting= meetingDAO.getId(meeting);
                    savePrerequisite();
                    saveTopics();
                    meeting.setKey(idMeeting);
                    saveAssistants( meeting);
                    AlertMessage alertMessage = new AlertMessage();
                    alertMessage.showAlertSuccesfulSave("Reunión");
                    Stage stage = (Stage) btSave.getScene().getWindow();
                    stage.close();
                    openMeetingList();
                 }
            }
        } catch (BusinessException ex) {
            exceptionShow(ex);
        }
    }
    
    private boolean validateAssistants(){  
        boolean value = true;
        Member leader= (Member) cbLeader.getSelectionModel().getSelectedItem();
        Member secretary = (Member) cbSecretary.getSelectionModel().getSelectedItem();
        System.out.print(leader);
        System.out.print(secretary);

        if(leader.getProfessionalLicense().equals(secretary.getProfessionalLicense())){
            value = false;
            AlertMessage alertMessage = new AlertMessage();
            alertMessage.showAlertValidateFailed("El lider y secretario no pueden ser el mismo");
        }
        
        return value;
    }
    
    private void savePrerequisite() throws BusinessException{ 
    PrerequisiteDAO prerequisiteDAO = new PrerequisiteDAO();
        for(int i = 0; i < prerequisites.size(); i++){
             prerequisiteDAO.savedSucessfulPrerequisites(prerequisites.get(i), idMeeting); 
           } 
    }
    public void saveTopics(){
        TopicDAO topicDAO = new TopicDAO();
        try {
          for(int i = 0; i < topics.size(); i++){
              topics.get(i).setIdMeeting(idMeeting);
             topicDAO.savedSucessful(topics.get(i));           
           }   
        } catch (BusinessException ex) {
            Log.logException(ex);
        }    
    }
    
    private void saveAssistants(Meeting meeting) throws BusinessException{  
        MeetingDAO meetingDAO = new MeetingDAO();
        Member leader= (Member) cbLeader.getSelectionModel().getSelectedItem();
        leader.setRole("Lider");
        Member secretary = (Member) cbSecretary.getSelectionModel().getSelectedItem();
        secretary.setRole("Secretario");
        ArrayList<Member> assistants= new ArrayList<Member> ();
        assistants.add(leader);
        assistants.add(secretary);
        String professionalLicenseSecretary= secretary.getProfessionalLicense();
        String professionalLicenseLeader= leader.getProfessionalLicense();
        for(int i=0; i< members.size(); i++){
            Member memberAuxiliar = (Member) members.get(i);
            String professionalLicense= memberAuxiliar.getProfessionalLicense();
            if((! professionalLicense.equals(professionalLicenseLeader))
            && (! professionalLicense.equals(professionalLicenseSecretary))){
                memberAuxiliar.setRole("Asistente");
                assistants.add(memberAuxiliar);
            }
        }  
        meeting.setAssistants(assistants);
        meetingDAO.addedSucessfulAssistants(meeting);
    }
    
    
    
    
    private boolean validatePrerequisite(Prerequisite prerequisite){ 
        boolean value=true;
        Validation validation = new Validation();
        AlertMessage alertMessage = new AlertMessage();
            if(prerequisite.getDescription().isEmpty()){  
                value=false;
                alertMessage.showAlertValidateFailed("Campos vacios");
            }
            
            if(validation.findInvalidField(prerequisite.getDescription())){   
                value=false;
               alertMessage.showAlertValidateFailed("Campos invalidos");
            } 
            
            if(repeatedPrerequisite(prerequisite)){ 
               value=false;
               alertMessage.showAlertValidateFailed("Prerequisito repetido");
            }
        return value;
    }
    
    public boolean searchRepeateMeeting(Meeting meeting)   { 
       boolean value=false; 
        try {   
            MeetingDAO preliminarProjectDAO= new MeetingDAO();
            preliminarProjectDAO.getId(meeting);
            value=true;
        }catch (BusinessException ex){ 
            if(ex.getMessage().equals("DataBase connection failed ")){
                AlertMessage alertMessage = new AlertMessage();
                alertMessage.showAlertValidateFailed("Error en la conexion con la base de datos");
            }else{  
                Log.logException(ex);
            }
        }
        return value;
    }
    
    private void cleanFields(){
        tfDescription.setText("");
    }
    
    private void initializeMembers() {
        try {
            MemberDAO memberDAO = new MemberDAO();
            ArrayList <Member> memberList = new ArrayList<Member>();
            memberList = memberDAO.getMembers(keyGroupAcademic);
            for( int i = 0; i<memberList.size(); i++) {
                members.add(memberList.get(i));
            }
        } catch (BusinessException ex) {
            Log.logException(ex);
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       tfSubject.setMaxlength(200);
       tfHour.setMaxlength(5);
       tfDescription.setMaxlength(200);
       disableButtonSave();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        dpDate.setConverter(new LocalDateStringConverter(formatter, null));
        tcDescription.setCellValueFactory(new PropertyValueFactory<Prerequisite,String>("description"));
        tcMandated.setCellValueFactory(new PropertyValueFactory<Prerequisite,Member>("mandated"));
        members = FXCollections.observableArrayList();
        prerequisites = FXCollections.observableArrayList();
        tvPrerequisite.setItems(prerequisites);
        initializeMembers();
        cbMember.setItems(members);
        cbLeader.setItems(members);
        cbSecretary.setItems(members);
        
        tvPrerequisite.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                 setSelectedPrerequisite();
             }
            }
        );

        tablePrerequisiteListener = new ListChangeListener<Prerequisite>(){
            @Override
            public void onChanged(ListChangeListener.Change<? extends Prerequisite> prerequisite) {
                setSelectedPrerequisite();
            }
        };
    } 
    
    private Prerequisite getSelectedPrerequisite(){
        Prerequisite prerequisite = null;
        int tamTable = 1;
        if(tvPrerequisite != null){
            List<Prerequisite> prerequisiteTable = tvPrerequisite.getSelectionModel().getSelectedItems();
            if(prerequisiteTable.size() == tamTable){
                prerequisite = prerequisiteTable.get(0);
            }
        }
        return prerequisite;
    }
    
    private void setSelectedPrerequisite(){
        Prerequisite prerequisite = getSelectedPrerequisite();
        indexPrerequisite = prerequisites.indexOf(prerequisite);
        if(prerequisite != null){
            MemberDAO memberDAO = new MemberDAO();
            Member member =prerequisite.getMandated();
            tfDescription.setText(prerequisite.getDescription());
            cbMember.getSelectionModel().select(member);
        }
    }
        
    private boolean validateFieldEmpty(){ 
          boolean value=false;
          if(tfSubject.getText().isEmpty()  || tfHour.getText().isEmpty() || dpDate == null ){
              value=true;
          }
          return value;
    }
    
    private boolean validateInformationField(){ 
         DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
          String date= dpDate.getValue().format(formatter);
         boolean value=true;
        Validation validation=new Validation();
        if(!(validation.validateHour(tfHour.getText()))
        || validation.findInvalidField(tfSubject.getText()) ||(! validation.validateDate(date))){   
            value=false;
        }  
        return value;
    }
    
    private void exceptionShow(BusinessException ex){ 
         if(ex.getMessage().equals("DataBase connection failed ")){
                AlertMessage alertMessage = new AlertMessage();
                alertMessage.showAlertValidateFailed("Error en la conexion con la base de datos");
            }else{  
                Log.logException(ex);
            }
    }
    
    private boolean validateDate(){
        boolean value=false;
        
        if(dpDate.getValue()!=null){
            LocalDate dateMeeting = dpDate.getValue();
            LocalDate dateCurrently = LocalDate.now();
          if(dateMeeting.isAfter(dateCurrently) ){ 
               value=true;
         }
        }
        return value;
    }
    
    public boolean repeatedPrerequisite(Prerequisite prerequisite){
        Boolean value = false;
        int i = 0;
        while(!value && i<prerequisites.size()){
            if(prerequisites.get(i).equals(prerequisite)){
                value = true;
            }
            i++;
        }
       return value;
    }
    
     private void sendAlert(){ 
          AlertMessage alertMessage= new AlertMessage();
          if(validateFieldEmpty() ){  
              alertMessage.showAlertValidateFailed("No se han llenado todos los campos");
          }
          if(!validateInformationField()){
             alertMessage.showAlertValidateFailed("Existen campos con caracteres invalidos");
          }
          
          if(!validateDate()){  
             alertMessage.showAlertValidateFailed("La fecha de reunión debe ser mayor  que la actual");

          }

      }
}
