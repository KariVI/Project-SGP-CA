
package GUI;

import businessLogic.GroupAcademicDAO;
import businessLogic.LGCADAO;
import domain.GroupAcademic;
import domain.LGCA;
import java.awt.Insets;
import log.BusinessException;
import log.Log;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
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
    @FXML private Node groupAcademicPanel;
    @FXML private AnchorPane anchorPaneGroupRegister;
    @FXML private Pane anchorPaneLGCA;

     
  
    
    @FXML
    private void actionSave (ActionEvent actionEvent){    
        String name = tfName.getText();
        String consolidationGrade= tfConsolidationGrade.getText();
        String objetive= tAObjetive.getText();
        String vision= tAVision.getText();
        String mision= tAMision.getText();
        String key= tfKey.getText();
        
        if(!validateFieldEmpty() && validateFields()){  
          if(!searchRepeateGroupAcademic()){ 
              GroupAcademic groupAcademicAuxiliar =new GroupAcademic(key,name,objetive,consolidationGrade,mision,vision);
              GroupAcademicDAO groupAcademicDAO =new GroupAcademicDAO();
             // groupAcademicDAO.savedSucessful(groupAcademicAuxiliar);
              recoverLgcas();
              AlertMessage alertMessage=new AlertMessage();
              alertMessage.showMessageSave("Cuerpo Academico");
          }else{  
            sendAlert();
          }
        }else{  
            sendAlert();
        }
        
    }
    
   @FXML 
    private void addLGCAs(ActionEvent actionEvent){  
        GridPane gridPane= new GridPane();
        if(!tfLgcasNumber.getText().isEmpty() && (validateNumberLGCA(tfLgcasNumber.getText()))){
            Integer lgcas=Integer.parseInt(tfLgcasNumber.getText());  
            gridPane.setHgap (10);
            gridPane.setVgap (10);
            System.out.println(lgcas);
            int i=0;
            int numberLgcas=1;
            int sizeRows=3;
           while (i < ( lgcas * sizeRows)){  
                TextField tfNameLgca = new TextField();
                tfNameLgca .setPromptText("Nombre: ");   
                TextField tfDescriptionLgca = new TextField();
                tfDescriptionLgca.setPromptText("Descripción");
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
        anchorPaneLGCA.getChildren().add(gridPane);
    }
    
    private boolean validateNumberLGCA(String numberLgca){
       boolean value=false;
              try {
                    Integer.parseInt(numberLgca);
                    value=true;
               } catch (NumberFormatException exception) {
                   AlertMessage alertMessage=new AlertMessage();
                   alertMessage.showAlert("Inserte un formato de número correcto en número de LGCA");
               }

              return value;

      }
    
    private void recoverLgcas(GroupAcademic groupAcademic){   
        GridPane gridPane= (GridPane) anchorPaneLGCA.getChildren().get(0);
            int i=1;
            Integer lgcas=Integer.parseInt(tfLgcasNumber.getText());  
            int sizeRows=3;
           while (i < (sizeRows * lgcas)){
               TextField nameLgca = (TextField) getNodeFromGridPane( gridPane, 1, i);
               TextField descriptionLgca = (TextField) getNodeFromGridPane( gridPane, 1, (i + 1));
               String name= nameLgca.getText();
               String description= descriptionLgca.getText();
               LGCA lgca = new LGCA(name, description);
               
               i=i+3;
           }
    }
    
    private void saveLgcas(GroupAcademic groupAcademic,LGCA lgca){   
        GroupAcademicDAO groupAcademicDAO= new GroupAcademicDAO();
        LGCADAO lgcaDAO =new LGCADAO();
        
        try {
            lgcaDAO.savedSucessful(lgca);
            groupAcademicDAO.addedLGACSucessful(groupAcademic, lgca);
        } catch (BusinessException ex) {
            AlertMessage alertMessage= new AlertMessage();
        }
    }
    
    private Node getNodeFromGridPane(GridPane gridPane, int column, int row) {
    for (Node node : gridPane.getChildren()) {
        if (GridPane.getColumnIndex(node) == column && GridPane.getRowIndex(node) == row) {
            return node;
        }
    }
    return null;
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
          if(!validateFields()){
             alertMessage.showAlert("Existen campos con caracteres invalidos");
          }
          
          if(searchRepeateGroupAcademic()){
              alertMessage.showAlert("El cuerpo académico ya se encuentra registrado");
          }
          
          
      }
      
      private boolean validateFields(){    
        boolean value=true;
        Validation validation=new Validation();
        if(validation.findInvalidKeyAlphanumeric(tfKey.getText()) || validation.findInvalidField(tfConsolidationGrade.getText())
        || validation.findInvalidField(tAObjetive.getText()) || validation.findInvalidField(tAVision.getText()) 
        || validation.findInvalidField(tAMision.getText()) || validation.findInvalidField(tfName.getText())){   
            value=false;
        }
          
        return value;
      }
      
      
    public boolean searchRepeateGroupAcademic()   { 
       boolean value=false; 
       String key=tfKey.getText();
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
