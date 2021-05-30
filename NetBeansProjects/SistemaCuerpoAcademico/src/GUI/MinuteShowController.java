package GUI;

import businessLogic.MemberDAO;
import businessLogic.MinuteDAO;
import businessLogic.AgreementDAO;
import domain.Agreement;
import domain.Member;
import domain.Minute;
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
    @FXML TableView<Agreement> tvAgreement;
    @FXML Button btDelete;
    @FXML Button btAdd;
    @FXML Button btFinish;
    private int idMinute = 1;
    private int idMeeting = 1;
    private int indexAgreement;
    private ListChangeListener<Agreement> tableAgreementListener;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tcMember.setCellValueFactory(new PropertyValueFactory<Member,String>("professionalLicense"));
        tcAgreement.setCellValueFactory(new PropertyValueFactory<Agreement,String>("description"));
        tcPeriod.setCellValueFactory(new PropertyValueFactory<Agreement,String>("period"));
        agreements = FXCollections.observableArrayList();
        initializeAgreements();
        tvAgreement.setItems(agreements);
    }
   
    public void initializeAgreements(){
        AgreementDAO agreementDAO = new AgreementDAO();
        try {
            ArrayList<Agreement> agreementList = new ArrayList<Agreement>();
            agreementList = agreementDAO.getAgreementsMinute(idMinute);
            for(int i = 0; i < agreementList.size(); i++){
                agreements.add(agreementList.get(i));
            }
        } catch (BusinessException ex) {
            Logger.getLogger(TopicShowController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     


}
