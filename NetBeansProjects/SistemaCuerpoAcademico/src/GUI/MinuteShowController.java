package GUI;

import businessLogic.MinuteDAO;
import businessLogic.AgreementDAO;
import domain.Agreement;
import domain.Member;
import domain.Minute;
import java.io.File;
import java.io.IOException;
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
    @FXML Button btReturn;
    private int idMinute = 1;
    private int idMeeting = 1;
    private int indexAgreement;
    private Minute minute;
    private ListChangeListener<Agreement> tableAgreementListener;
    @FXML private ToggleGroup tgApproveMinute;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tcMember.setCellValueFactory(new PropertyValueFactory<Member,String>("professionalLicense"));
        tcAgreement.setCellValueFactory(new PropertyValueFactory<Agreement,String>("description"));
        tcPeriod.setCellValueFactory(new PropertyValueFactory<Agreement,String>("period"));
        agreements = FXCollections.observableArrayList();
        initializeAgreements();
        initializeMinute();
        tvAgreement.setItems(agreements);
    }
    
    public void initializeMinute(){
        try {
            MinuteDAO minuteDAO = new MinuteDAO();
            this.minute = minuteDAO.getMinute(idMeeting);
            System.out.println("h"+minute.getDue());
            taDue.setText(minute.getDue());
            taNote.setText(minute.getNote());
        } catch (BusinessException ex) {
            Logger.getLogger(MinuteShowController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
    @FXML
    public void actionReturn(){
        System.out.println("hh");
        String approveMinute = ((RadioButton) tgApproveMinute.getSelectedToggle()).getText();
        if(approveMinute.equals("No estoy de acuerdo")){
         
        }
    } 
    
    public void goMinuteComment(){
         try {
            Stage primaryStage= new Stage();
            URL url = new File("src/GUI/MinuteComment.fxml").toURI().toURL();
            FXMLLoader loader = new FXMLLoader(url);
            loader.setLocation(url);
            loader.load();
            MinuteCommentController minuteComment =loader.getController();
            Parent root = loader.getRoot();
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            Stage stage = (Stage) btReturn.getScene().getWindow();
            primaryStage.show();
        }catch (IOException ex) {
            Logger.getLogger(MemberViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
