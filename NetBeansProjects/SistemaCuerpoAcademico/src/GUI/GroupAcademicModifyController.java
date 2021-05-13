
package GUI;

import businessLogic.GroupAcademicDAO;
import businessLogic.LGACDAO;
import domain.GroupAcademic;
import domain.LGAC;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import log.BusinessException;
import log.Log;


public class GroupAcademicModifyController implements Initializable {

     @FXML private TextField tfName;
    @FXML private TextField tfKey;
    @FXML private TextField tfConsolidationGrade;
    @FXML private TextArea taObjetive;
    @FXML private TextArea taVision;
    @FXML private TextArea taMision; 
    @FXML private TextField tflgacsNumber;
    @FXML private Button btOk;
    @FXML private Button btSave;
    @FXML private Button btReturn;
    @FXML private Node groupAcademicPanel;
    @FXML private Pane lgacPane;
    private GroupAcademic groupAcademic;
    private GroupAcademic groupAcademicNew=new GroupAcademic();
  
   
   public void setGroupAcademic(GroupAcademic groupAcademic){
        
        this.groupAcademic= groupAcademic;
    }
   
    public void initializeGroupAcademic(){
        String objetive= "Objetivo: "+groupAcademic.getObjetive();
        String vision="Visión: "+groupAcademic.getVision();
        String mision= "Misión "+ groupAcademic.getMission();

        tfName.setText(groupAcademic.getName());
        tfKey.setText(groupAcademic.getKey());
        tfConsolidationGrade.setText(groupAcademic.getConsolidationGrade());
        taObjetive.setText(objetive);
        taVision.setText(vision);
        taMision.setText(mision);
    
        try {
           getlgacs();
         } catch (BusinessException ex) {
             Log.logException(ex);
         }
    }

     private void getlgacs() throws BusinessException{
        GroupAcademicDAO groupAcademicDAO =new GroupAcademicDAO();
         groupAcademic.setLGACs(groupAcademicDAO.getLGACs(groupAcademic.getKey()));
         ArrayList<LGAC> lgacs= groupAcademic.getLGACs();
         int i=0;
        int numberlgacs=0;
        int numberRows=3;
        GridPane gridPane= new GridPane();
        gridPane.setHgap (5);
        gridPane.setVgap (5);
        while (i < ( lgacs.size() * numberRows)){  
                TextField tfNamelgac = new TextField(lgacs.get(numberlgacs).getName());
                TextArea taDescriptionlgac = new TextArea(lgacs.get(numberlgacs).getDescription());
                String lgacName= "lgac "+ (numberlgacs + 1);
                Label label = new Label(lgacName);
                taDescriptionlgac.setPrefHeight(80); 
                taDescriptionlgac.setPrefWidth(170);
                gridPane.add(label,1,i);
                gridPane.add(tfNamelgac,1,(i + 1));
                gridPane.add(taDescriptionlgac,1, (i + 2));
                i=i+3;
                numberlgacs++;
           }
            lgacPane.getChildren().add(gridPane);
    }
  
    @FXML
    private void actionSave (ActionEvent actionEvent){    
        String name = tfName.getText();
        String consolidationGrade= tfConsolidationGrade.getText();
        String objetive= taObjetive.getText();
        String vision= taVision.getText();
        String mision= taMision.getText();
        String key= tfKey.getText();
        
        if(!validateFieldEmpty() && validateFields()){  
          if(!searchRepeateGroupAcademic()){ 
              //groupAcademicNew =new GroupAcademic(key,name,objetive,consolidationGrade,mision,vision);
              groupAcademicNew.setKey(key);
              groupAcademicNew.setConsolidationGrade(consolidationGrade);
              groupAcademicNew.setName(name);
              groupAcademicNew.setObjetive(objetive);
              groupAcademicNew.setMission(mision);
              groupAcademicNew.setVision(vision);
              updateGroupAcademic();
          }else{  
            sendAlert();
          }
        }else{  
            sendAlert();
        }
        
    }
    
    public void updateGroupAcademic (){    
        GroupAcademicDAO groupAcademicDAO =new GroupAcademicDAO();
        AlertMessage alertMessage =new AlertMessage();
        try {
            if(groupAcademicDAO.updatedSucessful(groupAcademic.getKey(),groupAcademicNew)){
                recoverlgacs();
                alertMessage.showMessageSave("Cuerpo Academico");
            }
        } catch (BusinessException ex) {
            
            alertMessage.showAlert("Error en la conexion con la base de datos");
        }                
    }
    
    private void recoverlgacs(){   
        GridPane gridPane= (GridPane) lgacPane.getChildren().get(0);
            int i=1;
            int indexLGAC=0;
            Integer lgacs=Integer.parseInt(tflgacsNumber.getText());  
            ArrayList<LGAC> lgcas = groupAcademic.getLGACs();
            int sizeRows=3;
           while (i < (sizeRows * lgacs)){
               TextField namelgac = (TextField) getNodeFromGridPane( gridPane, 1, i);
               TextArea descriptionlgac = (TextArea) getNodeFromGridPane( gridPane, 1, (i + 1));
               if(validateFieldslgacs(namelgac,descriptionlgac)){         
                 String name= namelgac.getText();
                 String description= descriptionlgac.getText(); 
                 LGAC lgac = new LGAC(name, description);
                 String nameLast = lgcas.get(indexLGAC).getName();
                 updatelgacs(nameLast,  lgac);
               }
               i=i+3;
               indexLGAC++;
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
    
    private void updatelgacs(String nameLGACLast ,LGAC lgac){   
        GroupAcademicDAO groupAcademicDAO= new GroupAcademicDAO();
        LGACDAO lgacDAO =new LGACDAO();
        AlertMessage alertMessage =new AlertMessage();
        
        try {
            if(!searchRepeatedLGAC(lgac.getName())){
                lgacDAO.updatedSucessful(nameLGACLast, lgac);
                groupAcademicDAO.addedLGACSucessful(groupAcademicNew, lgac);
            }else { 
                alertMessage.showAlert("La LGCA ya se encuentra registrada");
            }
        } catch (BusinessException ex) {
            alertMessage.showAlert("Error en la conexion con la base de datos");
        }
    }
    
       
    private boolean validateFieldEmpty(){ 
          boolean value=false;
          if(tfName.getText().isEmpty() || tfConsolidationGrade.getText().isEmpty() || taObjetive.getText().isEmpty() 
           || taVision.getText().isEmpty()  || taMision.getText().isEmpty() || tfKey.getText().isEmpty()  
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
      
    public boolean searchRepeateGroupAcademic(){ 
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
     
    private boolean validateFields(){    
        boolean value=true;
        Validation validation=new Validation();
        if(validation.findInvalidKeyAlphanumeric(tfKey.getText()) || validation.findInvalidField(tfConsolidationGrade.getText())
        || validation.findInvalidField(taObjetive.getText()) || validation.findInvalidField(taVision.getText()) 
        || validation.findInvalidField(taMision.getText()) || validation.findInvalidField(tfName.getText())){   
            value=false;
        }
          
        return value;
    }
    
     private boolean validateFieldslgacs(TextField name, TextArea description){
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
    
}
