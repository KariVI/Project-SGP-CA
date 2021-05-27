package GUI;

import businessLogic.MemberDAO;
import businessLogic.MinuteDAO;
import domain.Agreement;
import domain.Member;
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
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import log.BusinessException;


public class MinuteRegisterController implements Initializable {

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
    @FXML TextArea taNotes;
    @FXML TableView<Agreement> tvAgreement;
    @FXML Button btDelete;
    @FXML Button btAdd;
    @FXML Button btFinish;
    private int idMinute = 1;
    private int indexAgreement;
    private ListChangeListener<Agreement> tableAgreementListener;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tcAgreement.setCellValueFactory(new PropertyValueFactory("agreementName"));
        tcPeriod.setCellValueFactory(new PropertyValueFactory("period"));
        tcNumber.setCellValueFactory(new PropertyValueFactory("finishTime"));
        tcMember.setCellValueFactory(new PropertyValueFactory("professionalLicense"));
        agreements = FXCollections.observableArrayList();
        tvAgreement.setItems(agreements);
        members = FXCollections.observableArrayList();
        initializeMembers();
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
                Logger.getLogger(TopicRegisterController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    void initializeMembers() {
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
    
     @FXML
    private void add(ActionEvent event){
        String period = ""; 
        String agreementDescription = "";
        Member member = cbMember.getSelectionModel().getSelectedItem();
        period = tfPeriod.getText();
        agreementDescription = tfAgreement.getText();
        Agreement agreement = new Agreement(period,agreementDescription,member.getProfessionalLicense());
       // if(validateTopic(topic)){
       //     topics.add(topic);
       //}
       
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
    
    public void actionSave(){
       MinuteDAO minuteDAO = new MinuteDAO();
       
       // try {
        //  for(int i = 0; i < minutes.size(); i++){
        //     topicDAO.save(topics.get(i));
         //  }   
        //   AlertMessage alertMessage = new AlertMessage();
       //    alertMessage.showAlertSuccesfulSave("Los temas fueron registrados con éxito");
       //  } catch (BusinessException ex) {
        //       Logger.getLogger(TopicRegisterController.class.getName()).log(Level.SEVERE, null, ex);
       //  }
       
       // Stage stage = (Stage)btSave.getScene().getWindow();
        //stage.close();
    }
   
   public void actionCancel(){
      //Stage stage = (Stage)btCancel.getScene().getWindow();
     // stage.close(); 
   }
    
    public boolean validateTopic(Topic topic){
        boolean value = true;
        AlertMessage alertMessage = new AlertMessage();
        if(isEmptyFields(topic)){
            value = false;
            alertMessage.showAlertValidateFailed("Campos vacios");
        }
        
        if(invalidFields(topic)){
            value = false;
            alertMessage.showAlertValidateFailed("Campos inválidos");
        }
        
        if(!validateHours(topic)){
            value = false;
            alertMessage.showAlertValidateFailed("Ingresa la hora en formato HH:MM ");
        }
        
        if(repeatedTopic(topic)){
            value = false;
            alertMessage.showAlertValidateFailed("Tema repetido");
        }
        return value;
    }
    
    public boolean isEmptyFields(Topic topic){
        boolean value = false;
        if(topic.getStartTime().isEmpty()||topic.getFinishTime().isEmpty()||topic.getTopicName().isEmpty()){
            value = true;
        }
        return value;
    }
    
    public boolean invalidFields(Topic topic){
        boolean value = false;
        Validation validation = new Validation();
        if(validation.findInvalidField(topic.getTopicName())){
           value = true;
        }
        return value;
    }
    
    public boolean validateHours(Topic topic){
        boolean value = false;
        Validation validation = new Validation();
        if(validation.validateHour(topic.getFinishTime())){
            value = true;
        }
        return value;
    }
    
    public boolean repeatedTopic(Topic topic){
        Boolean value = false;
        int i = 0;
        while(!value && i<topics.size()){
            if(topics.get(i).equals(topic)){
                value = true;
            }
            i++;
        }
       return value;
    }
    
    
}
