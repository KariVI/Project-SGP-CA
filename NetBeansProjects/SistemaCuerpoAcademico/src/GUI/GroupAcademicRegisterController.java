
package GUI;

import businessLogic.GroupAcademicDAO;
import businessLogic.LGACDAO;
import domain.GroupAcademic;
import domain.LGAC;
import log.BusinessException;
import log.Log;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;



public class GroupAcademicRegisterController implements Initializable  {
    @FXML private TextField tfName;
    @FXML private TextField tfKey;
    @FXML private TextArea tAObjetive;
    @FXML private TextArea tAVision;
    @FXML private TextArea tAMision; 
    @FXML private TextField tflgacsNumber;
    @FXML private Button btOk;
    @FXML private Button btSave;
    @FXML private Button btCancel;
    @FXML private Node groupAcademicPanel;
    @FXML private AnchorPane anchorPaneGroupRegister;
    @FXML private Pane anchorPanelgac;
    @FXML private ComboBox<String> cbConsolidateGrade;
     private ObservableList<String> consolidateGrades;

     
  
    
    @FXML
    private void actionSave (ActionEvent actionEvent) throws BusinessException{    
        String name = tfName.getText();
        String consolidationGrade= cbConsolidateGrade.getSelectionModel().getSelectedItem();
        String objetive= tAObjetive.getText();
        String vision= tAVision.getText();
        String mision= tAMision.getText();
        String key= tfKey.getText();
        if(!validateFieldEmpty() && validateFields()){  
          if(!searchRepeateGroupAcademic()){ 
              GroupAcademic groupAcademicAuxiliar =new GroupAcademic(key,name,objetive,consolidationGrade,mision,vision);
              saveGroupAcademic(groupAcademicAuxiliar);
          }else{  
            sendAlert();
          }
        }else{  
            sendAlert();
        }       
    }
    
    @FXML 
    private void actionCancel(ActionEvent actionEvent){   
        Stage stage = (Stage) btCancel.getScene().getWindow();
        stage.close();
    }
 
    
    public void saveGroupAcademic (GroupAcademic groupAcademic){    
        GroupAcademicDAO groupAcademicDAO =new GroupAcademicDAO();
        AlertMessage alertMessage =new AlertMessage();
        try {
            groupAcademicDAO.savedSucessful(groupAcademic);
            if(groupAcademicDAO.savedSucessful(groupAcademic)){
                recoverlgacs(groupAcademic);
                alertMessage.showMessageSave("Cuerpo Academico");
            }
        } catch (BusinessException ex) {
            
            alertMessage.showAlert("Error en la conexion con la base de datos");
        }                
    }
    
   @FXML 
    private void addlgacs(ActionEvent actionEvent){  
        GridPane gridPane= new GridPane();
        Validation validation =new Validation();
        if(!tflgacsNumber.getText().isEmpty() && (validation.validateNumberField(tflgacsNumber.getText()))){
            Integer lgacs=Integer.parseInt(tflgacsNumber.getText());  
            gridPane.setHgap (5);
            gridPane.setVgap (5);
            int i=0;
            int numberlgacs=1;
            int sizeRows=3;
           while (i < ( lgacs * sizeRows)){  
                TextField tfNamelgac = new TextField();
                tfNamelgac .setPromptText("Nombre: ");   
                tfNamelgac.setPrefWidth(200);
                TextArea taDescriptionlgac = new TextArea();
                taDescriptionlgac.setPrefHeight(80); 
                taDescriptionlgac.setPrefWidth(170);
                taDescriptionlgac.setPromptText("Descripción");
                String lgacName= "LGAC "+ numberlgacs;
                Label label = new Label(lgacName);
                gridPane.add(label,1,i);
                gridPane.add(tfNamelgac,1,(i + 1));
                gridPane.add(taDescriptionlgac,1, (i + 2));
                i=i+3;
                numberlgacs++;
           }
            
        }
        anchorPanelgac.getChildren().add(gridPane);
    }
    
    
    private boolean validateFieldslgacs(TextField name, TextField description){
        boolean value=true;
        AlertMessage alertMessage =new AlertMessage();
        Validation validation=new Validation();
        if( validation.findInvalidField(name.getText()) || validation.findInvalidField(description.getText())){    
            value=false;
            alertMessage.showAlert("Existen campos con caracteres invalidos");
        }else if( name.getText().isEmpty()|| description.getText().isEmpty()  ){ 
            alertMessage.showAlert("Existen campos vacios");
            value=false;
        }
     return value;
    }
    
    
    
    private void recoverlgacs(GroupAcademic groupAcademic){   
        GridPane gridPane= (GridPane) anchorPanelgac.getChildren().get(0);
            int i=1;
            Integer lgacs=Integer.parseInt(tflgacsNumber.getText());  
            int sizeRows=3;
           while (i < (sizeRows * lgacs)){
               TextField namelgac = (TextField) getNodeFromGridPane( gridPane, 1, i);
               TextField descriptionlgac = (TextField) getNodeFromGridPane( gridPane, 1, (i + 1));
               if(validateFieldslgacs(namelgac,descriptionlgac)){         
                 String name= namelgac.getText();
                 String description= descriptionlgac.getText(); 
                 LGAC lgac = new LGAC(name, description);
                 savelgacs(groupAcademic, lgac);
               }
               i=i+3;
           }
    }
    
    private void savelgacs(GroupAcademic groupAcademic,LGAC lgac){   
        GroupAcademicDAO groupAcademicDAO= new GroupAcademicDAO();
        LGACDAO lgacDAO =new LGACDAO();
        AlertMessage alertMessage =new AlertMessage();
        
        try {
            if(!searchRepeatedLGAC(lgac.getName())){
                lgacDAO.savedSucessful(lgac);
                groupAcademicDAO.addedLGACSucessful(groupAcademic, lgac);
            }else { 
                alertMessage.showAlert("La LGCA ya se encuentra registrada");
            }
        } catch (BusinessException ex) {
            alertMessage.showAlert("Error en la conexion con la base de datos");
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
  
   
    
    
    private boolean validateFieldEmpty(){ 
          boolean value=false;
          if(tfName.getText().isEmpty() || tAObjetive.getText().isEmpty() 
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
        if(validation.findInvalidKeyAlphanumeric(tfKey.getText())
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
    
    public boolean searchRepeatedLGAC(String name)   { 
       boolean value=false; 
        try {   
            LGACDAO lgacDAO = new LGACDAO();
            lgacDAO.getLgacByName(name);
            value=true;
        }catch (BusinessException ex){ 
            Log.logException(ex);
        }
        return value;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
     consolidateGrades= FXCollections.observableArrayList();
     consolidateGrades.add("En formación");
     consolidateGrades.add("En consolidación");
     consolidateGrades.add("Consolidado");
     cbConsolidateGrade.setItems(consolidateGrades);
     cbConsolidateGrade.setValue ("En formación");
    }
    
   
    

    
}
