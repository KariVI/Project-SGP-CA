
package GUI;

import businessLogic.PreliminarProjectDAO;
import domain.Member;
import domain.PreliminarProject;
import domain.Student;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
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

public class PreliminarProjectShowController implements Initializable {
    @FXML private Label lbTitle;
    @FXML private  Label lbDirector;
    @FXML private TextArea taDescription;
    @FXML private TextArea taCodirectors;
    @FXML private Label lbStartDate;
    @FXML  private Label lbEndDate;
    @FXML private Button btUpdate;
    @FXML private Button btReturn;
    @FXML private Pane paneStudents;
    private String codirectors="";  
    private PreliminarProject preliminarProject;
    private Member member;
    private String keyGroupAcademic;

    public void setMember(Member member) {
        this.member = member;
    }

    public void setKeyGroupAcademic(String keyGroupAcademic) {
        this.keyGroupAcademic = keyGroupAcademic;
    }
  
     public void setPreliminarProject(PreliminarProject preliminarProject){   
        this.preliminarProject= preliminarProject;
    }
     
    
    public void initializePreliminarProject(){
       lbTitle.setText(preliminarProject.getTitle());
       lbStartDate.setText(preliminarProject.getDateStart());
       lbEndDate.setText(preliminarProject.getDateEnd());
       taDescription.setText("Descripción: " + preliminarProject.getDescription());
       recoverColaborators();
        try {
            getStudents();
        } catch (BusinessException ex) {
            Log.logException(ex);
        }
    }
    
    public void recoverColaborators (){ 
        PreliminarProjectDAO preliminarProjectDAO = new PreliminarProjectDAO ();
        ArrayList<Member> colaborators ;
        try {
             colaborators=preliminarProjectDAO.getColaborators(preliminarProject.getKey());
             for(int i=0; i< colaborators.size(); i++){ 
                 if(colaborators.get(i).getRole().equals("Director")){  
                     lbDirector.setText( colaborators.get(i).getName());
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
        PreliminarProjectDAO preliminarProjectDAO =new PreliminarProjectDAO();
         preliminarProject.setStudents(preliminarProjectDAO.getStudents(preliminarProject.getKey()));
         ArrayList<Student> students= preliminarProject.getStudents();
        int numberStudent=0;
        int numberRows=2;
        GridPane gridPane= new GridPane();
        gridPane.setHgap (5);
        gridPane.setVgap (5);
        if(students.size()> 0){
            Label label = new Label("Estudiantes");
            gridPane.add(label,1, 0 );
            int i=1;
            while (i <= students.size()){ 
                    Label lbNameStudent = new Label("-"+ students.get(numberStudent).getName());
                    gridPane.add(lbNameStudent,1, i );
                    i++;
                    numberStudent++;
            }
            paneStudents.getChildren().add(gridPane);
        }
    }
  
    
    @FXML 
    private void actionUpdate(ActionEvent actionEvent){ 
         Stage primaryStage= new Stage();
        URL url=null;
        try{ 
             url = new File("src/GUI/PreliminarProjectModify.fxml").toURI().toURL();
        } catch (MalformedURLException ex) {
            Log.logException(ex);
        }
        
        try{
              FXMLLoader loader = new FXMLLoader(url);
              loader.setLocation(url);
              loader.load();
              PreliminarProjectModifyController preliminarProjectModifyController =loader.getController();      
               preliminarProjectModifyController.setPreliminarProject(preliminarProject);
               preliminarProjectModifyController.initializePreliminarProject();
               preliminarProjectModifyController.setKeyGroupAcademic(keyGroupAcademic);
               preliminarProjectModifyController.setMember(member);
              Parent root = loader.getRoot();
              Scene scene = new Scene(root);
              primaryStage.setScene(scene);
              Stage stage = (Stage) btUpdate.getScene().getWindow();
              stage.close();
            } catch (IOException ex) {
                    Log.logException(ex);
            }
            primaryStage.show();
    
    }
    
     @FXML 
    private void actionReturn(ActionEvent actionEvent){   
        Stage stage = (Stage) btReturn.getScene().getWindow();
        stage.close();
        openPreliminarProjectList();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }   
    
     private void openPreliminarProjectList(){   
         try{ 
            Stage primaryStage= new Stage();
            URL url = new File("src/GUI/preliminarProjectList.fxml").toURI().toURL();
           try{
              FXMLLoader loader = new FXMLLoader(url);
              loader.setLocation(url);
              loader.load();
              PreliminarProjectListController preliminarProjectListController =loader.getController();   
              preliminarProjectListController.setMember(member);
              preliminarProjectListController.setKeyGroupAcademic(keyGroupAcademic);
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
