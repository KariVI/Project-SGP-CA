
package GUI;

import businessLogic.GroupAcademicDAO;
import businessLogic.LGACDAO;
import businessLogic.MemberDAO;
import domain.GroupAcademic;
import domain.LGAC;
import domain.Member;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import log.BusinessException;
import log.Log;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;



public class GroupAcademicRegisterController implements Initializable  {
    @FXML private TextFieldLimited tfName;
    @FXML private TextFieldLimited tfKey;
    @FXML private TextArea tAObjetive;
    @FXML private TextArea tAVision;
    @FXML private TextArea tAMision; 
    @FXML private TextFieldLimited tflgacsNumber;
    @FXML private Button btOk;
    @FXML private Button btSave;
    @FXML private Button btCancel;
    @FXML private Node groupAcademicPanel;
    @FXML private AnchorPane anchorPaneGroupRegister;
    @FXML private ScrollPane spLgac;
    @FXML private ComboBox<String> cbConsolidateGrade;
     private ObservableList<String> consolidateGrades;
     private Member member;

    public void setMember(Member member) {
        this.member = member;
    }

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
        openLogin();
         
    }
 
    private void  openLogin(){   
        Stage primaryStage =  new Stage();
        try{
            
            URL url = new File("src/GUI/Login.fxml").toURI().toURL();
            try{
                FXMLLoader loader = new FXMLLoader(url);
                loader.setLocation(url);
                loader.load();
                LoginController login = loader.getController();
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
    
    public void saveGroupAcademic (GroupAcademic groupAcademic){    
        GroupAcademicDAO groupAcademicDAO =new GroupAcademicDAO();
        AlertMessage alertMessage =new AlertMessage();
        try {           
            if(groupAcademicDAO.savedSucessful(groupAcademic)){
                if(!(tflgacsNumber.getText().isEmpty())){   
                    recoverlgacs(groupAcademic);
                }
                alertMessage.showAlertSuccesfulSave("Cuerpo Academico");
                updateMember(groupAcademic.getKey());
            }
        } catch (BusinessException ex) {
            if(ex.getMessage().equals("DataBase connection failed ")){
                alertMessage.showAlertValidateFailed("Error en la conexion con la base de datos");
            }else{  
                Log.logException(ex);
            }
        }                
    }
    
    private void updateMember(String keyGroupAcademic){    
        member.setKeyGroupAcademic(keyGroupAcademic);
        MemberDAO memberDAO = new MemberDAO();
        try {
            memberDAO.update(member);
        } catch (BusinessException ex) {
            Log.logException(ex);
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
        spLgac.setContent(gridPane);
    }
    
    
    private boolean validateFieldslgacs(TextField name, TextArea description){
        boolean value=true;
        AlertMessage alertMessage =new AlertMessage();
        Validation validation=new Validation();
        if( validation.findInvalidField(name.getText()) || validation.findInvalidField(description.getText())){    
            value=false;
            alertMessage.showAlertValidateFailed("Existen campos con caracteres invalidos");
        }else if( name.getText().isEmpty()|| description.getText().isEmpty()  ){ 
            alertMessage.showAlertValidateFailed("Existen campos vacios");
            value=false;
        }
     return value;
    }
    
    
    
    private void recoverlgacs(GroupAcademic groupAcademic){   
        GridPane gridPane= (GridPane) spLgac.getContent();
            int i=1;
            Integer lgacs=Integer.parseInt(tflgacsNumber.getText());  
            int sizeRows=3;
           while (i < (sizeRows * lgacs)){
               TextField namelgac = (TextField) getNodeFromGridPane( gridPane, 1, i);
               TextArea descriptionlgac = (TextArea) getNodeFromGridPane( gridPane, 1, (i + 1));
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
                alertMessage.showAlertValidateFailed("La LGAC ya se encuentra registrada");
            }
        } catch (BusinessException ex) {
            if(ex.getMessage().equals("DataBase connection failed ")){
                alertMessage.showAlertValidateFailed("Error en la conexion con la base de datos");
            }else{  
                Log.logException(ex);
            }
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
              alertMessage.showAlertValidateFailed("No se han llenado todos los campos");
          }
          if(!validateFields()){
             alertMessage.showAlertValidateFailed("Existen campos con caracteres invalidos");
          }
          
          if(searchRepeateGroupAcademic()){
              alertMessage.showAlertValidateFailed("El cuerpo académico ya se encuentra registrado");
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
       AlertMessage alertMessage = new AlertMessage();
        try {   
            GroupAcademicDAO groupAcademicDAO= new GroupAcademicDAO();
            groupAcademicDAO.getGroupAcademicById(key);
            value=true;
        }catch (BusinessException ex){ 
            if(ex.getMessage().equals("DataBase connection failed ")){
                alertMessage.showAlertValidateFailed("Error en la conexion con la base de datos");
            }else{  
                Log.logException(ex);
            }
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
    tfName.setMaxlength(100);
    tfKey.setMaxlength(10);
    tflgacsNumber.setMaxlength(2);
     consolidateGrades= FXCollections.observableArrayList();
     consolidateGrades.add("En formación");
     consolidateGrades.add("En consolidación");
     consolidateGrades.add("Consolidado");
     cbConsolidateGrade.setItems(consolidateGrades);
     cbConsolidateGrade.setValue ("En formación");
     
    }
    
   
    

    
}
