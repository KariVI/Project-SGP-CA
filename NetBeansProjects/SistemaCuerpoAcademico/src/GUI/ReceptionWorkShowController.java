
package GUI;

import businessLogic.PreliminarProjectDAO;
import businessLogic.ReceptionWorkDAO;
import domain.Member;
import domain.PreliminarProject;
import domain.ReceptionWork;
import domain.Student;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import log.BusinessException;
import log.Log;

/**
 * FXML Controller class
 *
 * @author kari
 */
public class ReceptionWorkShowController implements Initializable {

 
       @FXML private Label lbTitle;
    @FXML private  Label lbDirector;
    @FXML private TextArea taDescription;
    @FXML private TextArea taCodirectors;
    @FXML Label lbStartDate;
    @FXML  Label lbEndDate;
    @FXML  Label lbType;
    @FXML  Label lbPreliminarProject;
    @FXML Button btUpdate;
    @FXML Button btReturn;
    @FXML Pane studentsPane;
    @FXML  Pane lgacsPane;
    private String codirectors="";  
    private PreliminarProject preliminarProject;
    private ReceptionWork receptionWork;
    
    
    
    
    public void setReceptionWork(ReceptionWork receptionWork){
        this.receptionWork= receptionWork;
    }
    
    public void initializeReceptionWork(){
       lbTitle.setText("Título: "+ receptionWork.getTitle());
       lbStartDate.setText("Fecha inicio: "+receptionWork.getDateStart());
       lbEndDate.setText("Fecha fin: "+receptionWork.getDateEnd());
       lbType.setText("Tipo:   " +receptionWork.getType());
       taDescription.setText("Descripción: " + receptionWork.getDescription());
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
                 if(colaborators.get(i).getRole().equals("Director")){  
                     lbDirector.setText(colaborators.get(i).getProfessionalLicense());
                 }else{ 
                     codirectors= codirectors + colaborators.get(i).getProfessionalLicense();
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
            studentsPane.getChildren().add(gridPane);
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    
    
}
