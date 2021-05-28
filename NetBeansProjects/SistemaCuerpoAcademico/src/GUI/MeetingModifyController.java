
package GUI;

import businessLogic.MeetingDAO;
import businessLogic.MemberDAO;
import businessLogic.PrerequisiteDAO;
import domain.Meeting;
import domain.Member;
import domain.Prerequisite;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.converter.LocalDateStringConverter;
import log.BusinessException;
import log.Log;


public class MeetingModifyController implements Initializable {

    private ObservableList<Member> members;
    private ObservableList<Prerequisite> prerequisites;
    private ObservableList<Prerequisite> oldPrerequisites;
    @FXML TextField tfSubject;
    @FXML TextField tfHour;
    @FXML Button btExit;
    @FXML ComboBox cbLeader;
    @FXML ComboBox cbSecretary;
    @FXML DatePicker dpDate;
    @FXML TableColumn tcDescription;
    @FXML TableColumn tcMandated;
    @FXML TextField tfDescription;
    @FXML ComboBox cbMember;
    private int indexPrerequisite;
    @FXML TableView<Prerequisite> tvPrerequisites;
    private ListChangeListener<Prerequisite> tablePrerequisiteListener;
    @FXML Button btSave;
    @FXML Button btDelete;
    private Meeting oldMeeting= new Meeting();
    private Meeting newMeeting=new Meeting();
     
    
    @FXML 
    private void actionSave (ActionEvent actionEvent){    
        String subject= tfSubject.getText();
        String hour= tfHour.getText();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        if(!validateFieldEmpty() && validateInformationField() && validateDate()){
            String date= dpDate.getValue().format(formatter);           
                newMeeting.setSubject(subject);
                newMeeting.setDate(date);
                newMeeting.setHourStart(hour);
                newMeeting.setKey(oldMeeting.getKey());
                update();
                 
        }else{  
            sendAlert();
        }
    }
    
    @FXML
    private void actionExit(ActionEvent actionEvent) {   
        Stage stage = (Stage) btExit.getScene().getWindow();
        stage.close();
         try {
             openWindow();
         } catch (BusinessException ex) {
             Log.logException(ex);
         }          
    }
    
    private void openWindow() throws BusinessException{  
           try{ 
            Stage primaryStage= new Stage();
            URL url = new File("src/GUI/meetingShow.fxml").toURI().toURL();
           try{
              FXMLLoader loader = new FXMLLoader(url);
              loader.setLocation(url);
              loader.load();
              MeetingShowController meetingShowController =loader.getController();
              MeetingDAO meetingDAO = new MeetingDAO();
              Meeting meetingAuxiliar=meetingDAO.getMeetingById(oldMeeting.getKey());
              meetingShowController.setMeeting(meetingAuxiliar);
              meetingShowController.initializeMeeting();
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
        return value;
    }
    
    public void setMeeting(Meeting meeting){
        this.oldMeeting.setKey(meeting.getKey());
        this.oldMeeting.setDate(meeting.getDate());
        this.oldMeeting.setHourStart(meeting.getHourStart());
        this.oldMeeting.setState(meeting.getState());
        this.oldMeeting.setSubject(meeting.getSubject());
    
    }   
    
    public void initializeMeeting() throws BusinessException{   
        tcDescription.setCellValueFactory(new PropertyValueFactory<Prerequisite,String>("description"));
        tcMandated.setCellValueFactory(new PropertyValueFactory<Prerequisite,Member>("mandated"));
        tfSubject.setText(oldMeeting.getSubject());
        tfHour.setText(oldMeeting.getHourStart());
        prerequisites =FXCollections.observableArrayList();
        oldPrerequisites=FXCollections.observableArrayList();
        members = FXCollections.observableArrayList();
        LocalDate localDate= LocalDate.parse(oldMeeting.getDate(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        dpDate.setValue(localDate);
        initializePrerequisites();
        tvPrerequisites.setItems(prerequisites);
        initializeMembers();
        cbMember.setItems(members);
        cbMember.getSelectionModel().selectFirst();
        cbLeader.setItems(members);
        cbLeader.getSelectionModel().selectFirst();
        cbSecretary.setItems(members);  
        cbSecretary.getSelectionModel().selectFirst();
        recoverAssistants();
    }
    
    private void update(){  
        try {
            deleteOldPrerequisites();
            deleteOldAssistants();
            MeetingDAO meetingDAO = new MeetingDAO();
            if( meetingDAO.updatedSucessful(newMeeting)){   
               savePrerequisite(); 
               saveAssistants();
               AlertMessage alertMessage = new AlertMessage();
               alertMessage.showUpdateMessage();
            };
        } catch (BusinessException ex) {
            Log.logException(ex);
        }
        
        
    }
    
    private boolean searchRepeateMeeting(Meeting meeting)   { 
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
    
    private void deleteOldPrerequisites() throws BusinessException{ 
        int i=0;
        PrerequisiteDAO prerequisiteDAO = new PrerequisiteDAO();
        while(i< oldPrerequisites.size()){ 
            Prerequisite prerequisiteAuxiliar= oldPrerequisites.get(i);
            prerequisiteDAO.deletedSucessful(prerequisiteAuxiliar.getKey());
            i++;
        }
    }
    
    private void deleteOldAssistants() throws BusinessException{
        MeetingDAO meetingDAO = new MeetingDAO();
        meetingDAO.deletedSucessfulAssistants(oldMeeting);
    }
    
    private void savePrerequisite() throws BusinessException{ 
    PrerequisiteDAO prerequisiteDAO = new PrerequisiteDAO();
        for(int i = 0; i < prerequisites.size(); i++){
             prerequisiteDAO.savedSucessfulPrerequisites(prerequisites.get(i), newMeeting.getKey()); 
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
         boolean value=true;
        Validation validation=new Validation();
        if(!(validation.validateHour(tfHour.getText()))
        || validation.findInvalidField(tfSubject.getText())){   
            value=false;
        }  
        return value;
    }
    
    
    private void initializePrerequisites(){
        PrerequisiteDAO prerequisiteDAO = new PrerequisiteDAO();
        try{
            ArrayList<Prerequisite> prerequisitesList = prerequisiteDAO.getPrerequisites(oldMeeting.getKey());
            for(int i = 0; i < prerequisitesList.size(); i++){ 
                prerequisites.add(prerequisitesList.get(i));
                oldPrerequisites.add(prerequisitesList.get(i));
            }
            
        } catch (BusinessException ex) {
            Log.logException(ex);
        }
    }
    
    private void initializeMembers() {
        try {
            MemberDAO memberDAO = new MemberDAO();
            ArrayList <Member> memberList = new ArrayList<Member>();
            memberList = memberDAO.getMembers();
            for( int i = 0; i<memberList.size(); i++) {
                members.add(memberList.get(i));
            }
        } catch (BusinessException ex) {
            Logger.getLogger(MemberListController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void recoverAssistants(){
        MeetingDAO meetingDAO = new MeetingDAO();
        try{
            ArrayList<Member> assistantsList = meetingDAO.getAssistants(oldMeeting.getKey());
            oldMeeting.setAssistants(assistantsList);
            for(int i = 0; i < assistantsList.size(); i++){
                Member memberAuxiliar = assistantsList.get(i);
                if(memberAuxiliar.getRole().equals("Lider")){ 
                    cbLeader.setValue(memberAuxiliar);
                }else if (memberAuxiliar.getRole().equals("Secretario")){
                    cbSecretary.setValue(memberAuxiliar);
                } 
            }
        } catch (BusinessException ex) {
            Log.logException(ex);
        }
    }    
    
    private void saveAssistants() throws BusinessException{  
        MeetingDAO meetingDAO = new MeetingDAO();
        Member leader= (Member) cbLeader.getSelectionModel().getSelectedItem();
        leader.setRole("Lider");
        Member secretary = (Member) cbSecretary.getSelectionModel().getSelectedItem();
        secretary.setRole("Secretario");
        ArrayList<Member> assistants= new ArrayList<Member> ();
        assistants.add(leader);
        assistants.add(secretary);
        for(int i=0; i< members.size(); i++){
            Member memberAuxiliar = (Member) members.get(i);
            if((! memberAuxiliar.equals(leader)) && (! memberAuxiliar.equals(secretary))){
                memberAuxiliar.setRole("Asistente");
                assistants.add(memberAuxiliar);
            }
        }  
        newMeeting.setAssistants(assistants);
        meetingDAO.addedSucessfulAssistants(newMeeting);
    }
    
    private Prerequisite getSelectedPrerequisite(){
        Prerequisite prerequisite = null;
        int tamTable = 1;
        if(tvPrerequisites != null){
            List<Prerequisite> prerequisiteTable = tvPrerequisites.getSelectionModel().getSelectedItems();
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
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        dpDate.setConverter(new LocalDateStringConverter(formatter, null));
        tcDescription.setCellValueFactory(new PropertyValueFactory<Prerequisite,String>("description"));
        tcMandated.setCellValueFactory(new PropertyValueFactory<Prerequisite,Member>("mandated"));
        members = FXCollections.observableArrayList();
        prerequisites = FXCollections.observableArrayList();
        tvPrerequisites.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
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
    
    private boolean validateDate(){
        boolean value=true;
        LocalDate dateMeeting = dpDate.getValue();
        LocalDate dateCurrently = LocalDate.now();
        if(dateMeeting.isBefore(dateCurrently)){ 
            value=false;
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
     
    private void cleanFields(){
        tfDescription.setText("");
    }
}
