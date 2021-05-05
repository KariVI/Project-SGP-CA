
package GUI;

import domain.GroupAcademic;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;


public class GroupAcademicRegisterController implements Initializable  {
    @FXML private TextField tfName;
    @FXML private TextField tfKey;
    @FXML private TextField tfConsolidationGrade;
    @FXML private TextArea tAObjetive;
    @FXML private TextArea tAVision;
    @FXML private TextArea tAMision; 
    @FXML private TextField tfLgcasNumber;
    @FXML private Button btOk;
    @FXML private Button btSave;
    @FXML private Button btCancel;

     
  
    
    @FXML
      private void actionSave (ActionEvent actionEvent){    
        String name = tfName.getText();
        String consolidationGrade= tfConsolidationGrade.getText();
        String objetive= tAObjetive.getText();
        String vision= tAVision.getText();
        String mision= tAMision.getText();
        String key= tfKey.getText();
        GroupAcademic groupAcademicAuxiliar =new GroupAcademic(key,name,objetive,consolidationGrade,mision,vision);
      }
      
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tfName.setText("---");
    }

    
}
