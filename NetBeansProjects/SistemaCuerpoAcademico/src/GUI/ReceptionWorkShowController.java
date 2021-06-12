
package GUI;

import businessLogic.PreliminarProjectDAO;
import businessLogic.ReceptionWorkDAO;
import domain.LGAC;
import domain.Member;
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
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import log.BusinessException;
import log.Log;


public class ReceptionWorkShowController implements Initializable {

 
    @FXML private Label lbTitle;
    @FXML private  Label lbDirector;
    @FXML private TextArea taDescription;
    @FXML private TextArea taCodirectors;
    @FXML private Label lbStartDate;
    @FXML private Label lbEndDate;
    @FXML private  Label lbType;
    @FXML private  Label lbPreliminarProject;
    @FXML  private Label lbState;
    @FXML private Button btUpdate;
    @FXML private Button btReturn;
    @FXML private Pane studentPane;
    @FXML  private Pane lgacsPane;
    private String codirectors="";  
    private PreliminarProject preliminarProject;
    private ReceptionWork receptionWork;
      private ObservableList<PreliminarProject> preliminarProjectsUnassigned;
    
    

    public void setPreliminarProjectsUnassigned(ObservableList<PreliminarProject> preliminarProjectsUnassigned) {
          for( int i = 0; i<preliminarProjectsUnassigned.size(); i++) {
                  this.preliminarProjectsUnassigned.add(preliminarProjectsUnassigned.get(i));
            }
    }
 
    @FXML 
    private void actionReturn(ActionEvent actionEvent) throws BusinessException{  
        Stage stage = (Stage) btReturn.getScene().getWindow();
        stage.close();
        openListWindow();
        
    }
    
    @FXML
    private void actionUpdate(ActionEvent actionEvent){ 
        try{ 
            Stage primaryStage= new Stage();
            URL url = new File("src/GUI/ReceptionWorkModify.fxml").toURI().toURL();
           try{
              FXMLLoader loader = new FXMLLoader(url);
              loader.setLocation(url);
              loader.load();
              ReceptionWorkModifyController receptionWorkController =loader.getController(); 
              receptionWorkController.setReceptionWork(receptionWork);
              receptionWorkController.initializeReceptionWork();
              receptionWorkController.setPreliminarProjects(preliminarProjectsUnassigned);
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
       lbPreliminarProject. setText("Anteproyecto: "+ receptionWork.getPreliminarProject());
       lbState.setText("Estado: "+ receptionWork.getActualState());
       recoverColaborators ();
           try {
               getStudents();
               getLGACS();
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
                 if(colaborators.get(i).getRole().equals("Director")){  
                     lbDirector.setText(colaborators.get(i).getName());
                 }else{ 
                     codirectors= codirectors + colaborators.get(i).getName();
                     codirectors= codirectors + ",";
                 }
             }
        } catch (BusinessException ex) {
            Log.logException(ex);
        }
        taCodirectors.setText("Codirectores: "+ codirectors);
    }
    
    private void getStudents() throws BusinessException{
        ReceptionWorkDAO receptionWorkDAO =new ReceptionWorkDAO();
         receptionWork.setStudents(receptionWorkDAO.getStudents(receptionWork.getKey()));
         ArrayList<Student> students= receptionWork.getStudents();
         int i=0;
        int numberStudent=0;
        int numberRows=2;
        GridPane gridPane= new GridPane();
        gridPane.setHgap (5);
        gridPane.setVgap (5);
        if(students.size()> 0){
            while (i < ( students.size() * numberRows)){ 
                    Label lbEnrollmentStudent = new Label("Matricula: "+ students.get(numberStudent).getEnrollment());
                    Label lbNameStudent = new Label("Nombre: "+ students.get(numberStudent).getName());
                    gridPane.add(lbEnrollmentStudent,1,i);
                    gridPane.add(lbNameStudent,1, (i + 1));
                    i=i+2;
                    numberStudent++;
            }
            studentPane.getChildren().add(gridPane);
        }
    }
    
    
      private void getLGACS() throws BusinessException{
        ReceptionWorkDAO receptionWorkDAO =new ReceptionWorkDAO();
         receptionWork.setLGACs(receptionWorkDAO.getLGACs(receptionWork.getKey()));
         ArrayList<LGAC> lgacs= receptionWork.getLGACs();
         int i=0;
         int indexGridPane=1;
        GridPane gridPane= new GridPane();
        gridPane.setHgap (2);
        gridPane.setVgap (2);
        gridPane.add(new Label("LGACs relacionadas: "),1,0);

        if(lgacs.size()> 0){
            while (i <lgacs.size()){ 
                    Label lbLGAC = new Label(lgacs.get(i).getName());
                    gridPane.add(lbLGAC,1,indexGridPane);
                    i++;
                    indexGridPane++;
                    
            }
            lgacsPane.getChildren().add(gridPane);
        }
    }
      
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        preliminarProjectsUnassigned= FXCollections.observableArrayList();
    }    
    
}
