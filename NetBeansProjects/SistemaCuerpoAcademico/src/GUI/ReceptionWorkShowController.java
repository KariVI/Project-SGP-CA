
package GUI;

import businessLogic.PreliminarProjectDAO;
import businessLogic.ReceptionWorkDAO;
import domain.LGAC;
import domain.Member;
import domain.Participant;
import domain.PreliminarProject;
import domain.ReceptionWork;
import domain.Student;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import log.BusinessException;
import log.Log;



public class ReceptionWorkShowController implements Initializable {

 
    @FXML private Label lbTitle;
    @FXML private TextArea taDescription;
    @FXML private TableView<Participant> tvMember;
    @FXML private TextArea taCodirectors;
    @FXML private Label lbStartDate;
    @FXML private Label lbEndDate;
    @FXML private  Label lbType;
    @FXML  private Label lbState;
    @FXML  private Label lbProject;
    @FXML private Button btUpdate;
    @FXML private Button btReturn;
    @FXML private TableColumn<Participant, String> tcName;
    @FXML private TableColumn<Participant, String> tcRole;
    private ObservableList<Participant> assistants;
    
    private String codirectors="";  
    private ReceptionWork receptionWork;
    private String keyGroupAcademic;
    private Member member;

    public void setMember(Member member) {
        this.member = member;
    }
    
     public void setKeyGroupAcademic(String keyGroupAcademic) {
        this.keyGroupAcademic = keyGroupAcademic;
    }

 
 
    @FXML 
    private void actionReturn(ActionEvent actionEvent) throws BusinessException{  
        Stage stage = (Stage) btReturn.getScene().getWindow();
        stage.close();
        openListWindow();
        
    }
    
    @FXML
    private void actionUpdate(ActionEvent actionEvent) throws BusinessException{ 
        Stage stage = (Stage) btUpdate.getScene().getWindow();
        stage.close();
        try{ 
            Stage primaryStage= new Stage();
            URL url = new File("src/GUI/ReceptionWorkModify.fxml").toURI().toURL();
           try{
              FXMLLoader loader = new FXMLLoader(url);
              loader.setLocation(url);
              loader.load();
              ReceptionWorkModifyController receptionWorkController =loader.getController(); 
              receptionWorkController.setReceptionWork(receptionWork);
              receptionWorkController.setKeyGroupAcademic(keyGroupAcademic);
              receptionWorkController.setMember(member);
              receptionWorkController.initializeReceptionWork();
              receptionWorkController.initializeProjects();
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
    
    private void openListWindow() throws BusinessException{  
        try{ 
            Stage primaryStage= new Stage();
            URL url = new File("src/GUI/ReceptionWorkList.fxml").toURI().toURL();
           try{
              FXMLLoader loader = new FXMLLoader(url);
              loader.setLocation(url);
              loader.load();
                ReceptionWorkListController receptionWorkListController =loader.getController();
                receptionWorkListController.setKeyGroupAcademic(keyGroupAcademic);
                receptionWorkListController.setMember(member);
                Parent root = loader.getRoot();
                Scene scene = new Scene(root);
                primaryStage.setScene(scene);
           }catch (IOException ex) {
                    Log.logException(ex);
            }
            primaryStage.show();
       } catch (MalformedURLException ex) {
                Log.logException(ex);
        }
    
    }

    public void setReceptionWork(ReceptionWork receptionWork){
        this.receptionWork= receptionWork;
    }
    
    public void initializeReceptionWork(){
       lbTitle.setText("Título: "+ receptionWork.getTitle());
       lbStartDate.setText("Fecha inicio: "+receptionWork.getDateStart());
       lbEndDate.setText("Fecha fin: "+receptionWork.getDateEnd());
       lbType.setText("Tipo:   " +receptionWork.getType());
       taDescription.setText("Descripción: " + receptionWork.getDescription());

       lbProject. setText("Proyecto: "+ receptionWork.getProject());

       lbState.setText("Estado: "+ receptionWork.getActualState());
       recoverColaborators ();
           try {
               getStudents();

           } catch (BusinessException ex) {
               Log.logException(ex);
           }
       
    }
    
    public void recoverColaborators (){ 
       ReceptionWorkDAO receptionWorkDAO = new ReceptionWorkDAO ();
        ArrayList<Member> colaborators ;
        try {
             colaborators=receptionWorkDAO.getColaborators(receptionWork.getKey());
             for(int i=0; i< colaborators.size(); i++){ 
                 Participant auxiliar = new Participant (colaborators.get(i).getName(),colaborators.get(i).getRole());
                 assistants.add(auxiliar);
             }
        } catch (BusinessException ex) {
            Log.logException(ex);
        }
    }
    
    private void getStudents() throws BusinessException{
        ReceptionWorkDAO receptionWorkDAO =new ReceptionWorkDAO();
         receptionWork.setStudents(receptionWorkDAO.getStudents(receptionWork.getKey()));
         ArrayList<Student> students= receptionWork.getStudents();
         for(int i=0; i< students.size(); i++){ 
                 Participant auxiliar = new Participant (students.get(i).getName(),"Estudiante");
                 assistants.add(auxiliar);
             }
    }
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tcName.setCellValueFactory(new PropertyValueFactory<Participant,String>("name"));
        tcRole.setCellValueFactory(new PropertyValueFactory<Participant,String>("role"));
        assistants = FXCollections.observableArrayList(); 
        tvMember.setItems(assistants);
    }    
    
}
