
package GUI;

import businessLogic.GroupAcademicDAO;
import domain.GroupAcademic;
import log.BusinessException;
import log.Log;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;



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
        
        if(!validateFieldEmpty()){
            GroupAcademic groupAcademicAuxiliar =new GroupAcademic(key,name,objetive,consolidationGrade,mision,vision);
        }else{  
            sendAlert();
        }
        
    }
      
      @FXML 
    private void actionCancel(ActionEvent actionEvent){   
        Stage stage = (Stage) btCancel.getScene().getWindow();
        stage.close();
    }
         
    private boolean validateFieldEmpty(){ 
          boolean value=false;
          if(tfName.getText().isEmpty() || tfConsolidationGrade.getText().isEmpty() || tAObjetive.getText().isEmpty() 
           || tAVision.getText().isEmpty()  || tAMision.getText().isEmpty() || tfKey.getText().isEmpty()  
           ){
              value=true;
          }
          return value;
      }
      
      private void sendAlert(){ 
          AlertMessage alertMessage= new AlertMessage();
          if(validateFieldEmpty() ){  
              alertMessage.showAlert("No se han llenado todos los campos");
          }
          
      }
      
      
    public boolean searchRepeateGroupAcademic(String key )  { 
       boolean value=false; 
        try {   
            GroupAcademicDAO groupAcademicDAO= new GroupAcademicDAO();
            groupAcademicDAO.getGroupAcademicById(key);
            value=true;
        }catch (BusinessException ex){ 
            Log.logException(ex);

        }
        return value;
    }
    
   
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }

    
}
