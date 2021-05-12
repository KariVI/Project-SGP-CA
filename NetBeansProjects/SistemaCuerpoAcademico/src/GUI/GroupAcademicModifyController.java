
package GUI;

import businessLogic.GroupAcademicDAO;
import domain.GroupAcademic;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import log.BusinessException;
import log.Log;


public class GroupAcademicModifyController implements Initializable {

     @FXML private TextField tfName;
    @FXML private TextField tfKey;
    @FXML private TextField tfConsolidationGrade;
    @FXML private TextArea tAObjetive;
    @FXML private TextArea tAVision;
    @FXML private TextArea tAMision; 
    @FXML private TextField tfLgcasNumber;
    @FXML private Button btOk;
    @FXML private Button btSave;
    @FXML private Button btReturn;
    @FXML private Node groupAcademicPanel;
    @FXML private AnchorPane anchorPaneGroupRegister;
    @FXML private Pane anchorPaneLGCA;
    
   public void getGroupAcademic(String key){
        GroupAcademicDAO groupAcademicDAO =new GroupAcademicDAO();
        GroupAcademic groupAcademic = null;
        try {
            groupAcademic = groupAcademicDAO.getGroupAcademicById(key);
        } catch (BusinessException ex) {
            Log.logException(ex);
        }
        String name="Nombre: " + groupAcademic.getName();
        String keyGroup="Clave: " + groupAcademic.getKey();
        String consolidateGrade="Grado de consolidación: "+ groupAcademic.getConsolidationGrade();
        String objetive= groupAcademic.getObjetive();
        String vision="Visión: "+groupAcademic.getVision();
        String mision= "Misión "+ groupAcademic.getMission();
        
        tfName.setText(name);
        tfKey.setText(keyGroup);
        tfConsolidationGrade.setText(consolidateGrade);
        tAObjetive.setText(objetive);
        tAVision.setText(vision);
        tAMision.setText(mision);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
    
}
