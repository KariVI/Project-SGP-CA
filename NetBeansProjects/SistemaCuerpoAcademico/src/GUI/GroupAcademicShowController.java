
package GUI;

import businessLogic.GroupAcademicDAO;
import businessLogic.LGCADAO;
import domain.GroupAcademic;
import domain.LGCA;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import log.BusinessException;
import log.Log;

public class GroupAcademicShowController implements Initializable {
    
    @FXML private Label lbName;
    @FXML private Label lbKey; 
    @FXML private Label lbConsolidateGrade; 
    @FXML private TextArea taObjetive;
    @FXML private TextArea taVision;
    @FXML private TextArea taMision;
    @FXML private Pane lgcasPane;
    @FXML private Button btUpdate;
    GroupAcademic groupAcademic;
    
    
    
    
    public void setGroupAcademic(GroupAcademic groupAcademic){
        
        this.groupAcademic= groupAcademic;
       /* GroupAcademicDAO groupAcademicDAO =new GroupAcademicDAO();
        GroupAcademic groupAcademic = null;
        try {
            groupAcademic = groupAcademicDAO.getGroupAcademicById(key);
        } catch (BusinessException ex) {
            Log.logException(ex);
        }
        String name="Nombre: " + groupAcademic.getName();
        String keyGroup="Clave: " + groupAcademic.getKey();
        String consolidateGrade="Grado de consolidación: "+ groupAcademic.getConsolidationGrade();
        String objetive= "Objetivo: "+groupAcademic.getObjetive();
        String vision="Visión: "+groupAcademic.getVision();
        String mision= "Misión "+ groupAcademic.getMission();
        
        lbName.setText(name);
        lbKey.setText(keyGroup);
        lbConsolidateGrade.setText(consolidateGrade);
        taObjetive.setText(objetive);
        taVision.setText(vision);
        taMision.setText(mision);*/
    }
    
    
    private void getLgcas(String keyGroupAcademic) throws BusinessException{
        GridPane gridPane= new GridPane();
        GroupAcademicDAO groupAcademicDAO =new GroupAcademicDAO();
        ArrayList<LGCA> lgcas = groupAcademicDAO.getLGACs(keyGroupAcademic);
         int i=0;
        int numberLgcas=0;
        int numberRows=3;
        while (i < ( lgcas.size() * numberRows)){  
                TextField tfNameLgca = new TextField();
                tfNameLgca .setText(lgcas.get(numberLgcas).getName());   
                TextField tfDescriptionLgca = new TextField();
                tfDescriptionLgca.setText(lgcas.get(numberLgcas).getDescription());
                String lgcaName= "LGCA "+ numberLgcas;
                Label label = new Label(lgcaName);
                gridPane.add(label,1,i);
                gridPane.add(tfNameLgca,1,(i + 1));
                gridPane.add(tfDescriptionLgca,1, (i + 2));
                System.out.println("aa");
                i=i+3;
                numberLgcas++;
           }
    
    }
    
    
    @FXML
    private void actionUpdate(ActionEvent actionEvent){ 
    
    }
    
    public void initialize(URL url, ResourceBundle rb) {
        taObjetive.setStyle("-fx-focus-color: transparent; -fx-text-box-border: transparent;");
        taVision.setStyle("-fx-focus-color: transparent; -fx-text-box-border: transparent;");
        taMision.setStyle("-fx-focus-color: transparent; -fx-text-box-border: transparent;");
        String objetive= "Objetivo: "+groupAcademic.getObjetive();
        String vision="Visión: "+groupAcademic.getVision();
        String mision= "Misión "+ groupAcademic.getMission();

        lbName.setText(groupAcademic.getName());
        lbKey.setText(groupAcademic.getKey());
        lbConsolidateGrade.setText(groupAcademic.getConsolidationGrade());
        taObjetive.setText(objetive);
        taVision.setText(vision);
        taMision.setText(mision);
    }    
    
}
