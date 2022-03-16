
package GUI;

import businessLogic.GroupAcademicDAO;
import businessLogic.LGACDAO;
import domain.GroupAcademic;
import domain.LGAC;
import domain.Member;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
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
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import log.BusinessException;
import log.Log;


public class GroupAcademicModifyController implements Initializable {

     @FXML private TextFieldLimited tfName;
    @FXML private TextFieldLimited tfKey;
    @FXML private TextArea taObjetive;
    @FXML private TextArea taVision;
    @FXML private TextArea taMision; 
    @FXML private TextField tflgacsNumber;
    @FXML private Button btOk;
    @FXML private Button btSave;
    @FXML private Button btReturn;
    @FXML private Node groupAcademicPanel;
    @FXML private ScrollPane spLGACs;
    @FXML private ComboBox<String> cbConsolidateGrade;
    private ObservableList<String> consolidateGrades;
    private GroupAcademic groupAcademic= new GroupAcademic();
    private GroupAcademic groupAcademicNew=new GroupAcademic();
    private GridPane gridPane = new GridPane();
    int nextRowPosition=0;
    private Member member;
    private int newLgacs=0;
    private ArrayList<LGAC> lgacsNew = new ArrayList<LGAC>();
    private ArrayList<LGAC> lgacsOld = new ArrayList<LGAC>();
    public void setMember(Member member) {
        this.member = member;
    }
   
   public void setGroupAcademic(GroupAcademic groupAcademic){
      this.groupAcademic.setKey(groupAcademic.getKey());
      this.groupAcademic.setConsolidationGrade(groupAcademic.getConsolidationGrade());
      this.groupAcademic.setName(groupAcademic.getName());
      this.groupAcademic.setObjetive(groupAcademic.getObjetive());
      this.groupAcademic.setMission(groupAcademic.getMission());
      this.groupAcademic.setVision(groupAcademic.getVision());
    }
   
    public void initializeGroupAcademic(){
        tfName.setText(groupAcademic.getName());
        tfKey.setText(groupAcademic.getKey());
        taObjetive.setText(groupAcademic.getObjetive());
        cbConsolidateGrade.setValue (groupAcademic.getConsolidationGrade());
        taVision.setText(groupAcademic.getVision());
        taMision.setText(groupAcademic.getMission());
    
        try {
           getLgacs();
         } catch (BusinessException ex) {
             Log.logException(ex);
         }
    }

    private void getLgacs() throws BusinessException{
        GroupAcademicDAO groupAcademicDAO =new GroupAcademicDAO();
        groupAcademic.setLGACs(groupAcademicDAO.getLGACs(groupAcademic.getKey()));
         lgacsOld= groupAcademicDAO.getLGACs(groupAcademic.getKey());
        int i=0;
        int numberlgacs=0;
        int numberRows=3;
        gridPane.setHgap (5);
        gridPane.setVgap (5);
        while (i < (numberRows * lgacsOld.size()  )){  
                TextField tfNamelgac = new TextField(lgacsOld.get(numberlgacs).getName());
                TextArea taDescriptionlgac = new TextArea(lgacsOld.get(numberlgacs).getDescription());
                String lgacName= "LGAC "+ (numberlgacs + 1);
                Label label = new Label(lgacName);
                taDescriptionlgac.setPrefHeight(80); 
                taDescriptionlgac.setPrefWidth(170);
                gridPane.add(label,1,i);
                gridPane.add(tfNamelgac,1,(i + 1));
                gridPane.add(taDescriptionlgac,1, (i + 2));
                i=i+3;
                numberlgacs++;
           }
        spLGACs.setContent(gridPane);
        initializeNextRowPosition();
    }
     
    private void initializeNextRowPosition() {  
        int sizeRows=3;
        GroupAcademicDAO groupAcademicDAO = new GroupAcademicDAO();
        int sizeLGACsCurrently=0;
        sizeLGACsCurrently = lgacsOld.size() + newLgacs;
       
       if(sizeLGACsCurrently>0){     
          
            nextRowPosition=  nextRowPosition + ( sizeLGACsCurrently * sizeRows);
       }
    }
  
      @FXML 
    private void actionAddLGAC(ActionEvent actionEvent){ 
            
            int sizeRows=3;
               TextField tfNamelgac = new TextField();
                tfNamelgac .setPromptText("Nombre: ");   
                tfNamelgac.setPrefWidth(200);
                TextArea taDescriptionlgac = new TextArea();
                taDescriptionlgac.setPrefHeight(80); 
                taDescriptionlgac.setPrefWidth(170);
                taDescriptionlgac.setPromptText("Descripción");
                String lgacName= "Nueva LGAC ";
                Label label = new Label(lgacName);
                gridPane.add(label,1,nextRowPosition);
                gridPane.add(tfNamelgac,1,(nextRowPosition + 1));
                gridPane.add(taDescriptionlgac,1, (nextRowPosition + 2));
                nextRowPosition= nextRowPosition + sizeRows ;
                spLGACs.setContent(gridPane);
                newLgacs++;   
    }
    
    @FXML
    private void actionSave (ActionEvent actionEvent){    
        String name = tfName.getText();
        String consolidationGrade= cbConsolidateGrade.getSelectionModel().getSelectedItem();;
        String objetive= taObjetive.getText();
        String vision= taVision.getText();
        String mision= taMision.getText();
        String key= tfKey.getText();
        
        if(!validateFieldEmpty() && validateFields() && validateLGACs()){  
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
    }
    
    public void updateGroupAcademic (){    
        GroupAcademicDAO groupAcademicDAO =new GroupAcademicDAO();
        AlertMessage alertMessage =new AlertMessage(); 
        try {
          
            if(groupAcademicDAO.updatedSucessful(groupAcademic.getKey(),groupAcademicNew)){
            
                deleteLgacs();
                saveLgacs();
                alertMessage.showUpdateMessage();
                Stage stage = (Stage) btSave.getScene().getWindow();
                stage.close();
                openShowWindow();
            }
        } catch (BusinessException ex) {
           if(ex.getMessage().equals("DataBase connection failed ")){
                alertMessage.showAlertValidateFailed("Error en la conexion con la base de datos");
            }else{  
                Log.logException(ex);
            }
        }                
    }
    
    private void saveLgacs() throws BusinessException{  
            for(int i = 0; i < lgacsNew.size(); i++){
                saveLgac(groupAcademicNew,lgacsNew.get(i));
                
            }
                
    }
    public boolean validateLGACs(){
            GridPane gridPane= (GridPane) spLGACs.getContent();
            int i=1;
            GroupAcademicDAO groupAcademicDAO = new GroupAcademicDAO();
            int sizeRows=3;
            int size= (lgacsOld.size() + newLgacs) * sizeRows;
            boolean valide = true;
             while (i < size){
               TextField namelgac = (TextField) getNodeFromGridPane( gridPane, 1, i);
               TextArea descriptionlgac = (TextArea) getNodeFromGridPane( gridPane, 1, (i + 1));           
               if(validateFieldslgacs(namelgac,descriptionlgac)){   
                   String name= namelgac.getText();
                   String description= descriptionlgac.getText(); 
                   LGAC lgac = new LGAC(name, description);
                   lgacsNew.add(lgac);
               }else{
                    valide = false;
               }
             
               i=i+3;           
           }
           return valide;
    }
    
    private boolean validateFieldslgacs(TextField name, TextArea description){
        boolean value = true;
        AlertMessage alertMessage =new AlertMessage();
        Validation validation = new Validation();
        if( validation.findInvalidField(name.getText()) || validation.findInvalidField(description.getText())){    
            value=false;
            alertMessage.showAlertValidateFailed("Existen campos con caracteres invalidos");
        }else if( (!validation.emptyField(name.getText()) && validation.emptyField(description.getText())) || (validation.emptyField(name.getText()) && !validation.emptyField(description.getText()))){ 
            alertMessage.showAlertValidateFailed("Existen campos vacios");
            value=false;
        }
        return value;
    }
    
    @FXML 
    private void actionReturn(ActionEvent actionEvent) throws BusinessException{  
        Stage stage = (Stage) btSave.getScene().getWindow();
        stage.close();
        openShowWindow();
        
    }
    
    private void deleteLgacs() throws BusinessException{ 
        GroupAcademicDAO groupAcademicDAO = new GroupAcademicDAO();
        LGACDAO lgacDAO = new LGACDAO();
        int i =0;
        if(!lgacsOld.isEmpty()){
            while(i< lgacsOld.size()){
            groupAcademicDAO.deletedLGACSuccesful(groupAcademic.getKey(), lgacsOld.get(i));
            i++;  
            } 
        }
        
    
    }
    private void openShowWindow() throws BusinessException{  
        try{ 
            Stage primaryStage= new Stage();
            URL url = new File("src/GUI/groupAcademicShow.fxml").toURI().toURL();
           try{
              FXMLLoader loader = new FXMLLoader(url);
              loader.setLocation(url);
              loader.load();
                GroupAcademicShowController groupAcademicShowController =loader.getController();
                GroupAcademicDAO groupAcademicDAO= new GroupAcademicDAO();
                GroupAcademic groupAcademic;          
                groupAcademic= groupAcademicDAO.getGroupAcademicById(groupAcademicNew.getKey());
                groupAcademicShowController.setGroupAcademic(groupAcademic);
                groupAcademicShowController.setMember(member);
                groupAcademicShowController.initializeGroupAcademic();
                Parent root = loader.getRoot();
                Scene scene = new Scene(root);
                primaryStage.setScene(scene);
           }catch (IOException ex) {
                    Log.logException(ex);
            }
            primaryStage.show();
       } catch (MalformedURLException ex) {
                Log.logException(ex);
        }
    
    }
      
     private void saveLgac(GroupAcademic groupAcademic,LGAC lgac){   
        GroupAcademicDAO groupAcademicDAO= new GroupAcademicDAO();
        LGACDAO lgacDAO =new LGACDAO();
        AlertMessage alertMessage =new AlertMessage();
        Validation validation = new Validation();
        
        try {
            if(!validation.emptyField(lgac.getName()) && validation.emptyField(lgac.getName()) ){ 
            if(!searchRepeatedLgac(lgac.getName())){
                lgacDAO.savedSucessful(lgac);
            }
            if(!searchRepeatedInAcademicGroup(lgac)){
                groupAcademicDAO.addedLGACSucessful(groupAcademic, lgac);
            } 
            }
        } catch (BusinessException ex) {
            if(ex.getMessage().equals("DataBase connection failed ")){
                alertMessage.showAlertValidateFailed("Error en la conexion con la base de datos");
            }else{  
                Log.logException(ex);
            }
        }
    }
     
    
     public boolean searchRepeatedLgac(String name)   { 
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
     
      public boolean searchRepeatedInAcademicGroup(LGAC lgac){
       boolean value=false; 
        try {   
            GroupAcademicDAO groupAcademicDAO = new GroupAcademicDAO();
             LGACDAO lgacDAO = new LGACDAO();
            ArrayList<LGAC> lgacsNew2 = groupAcademicDAO.getLGACs(groupAcademic.getKey());
            int i =0;
            while(i< lgacsNew2.size() && value==false){  
                if(lgacsNew2.get(i).equals(lgac)){   
                    value=true;
                }
                i++;
            }
                
        }catch (BusinessException ex){ 
            Log.logException(ex);
        }
        return value;
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
          Validation validation = new Validation();
          if(validation.emptyField(tfName.getText()) || validation.emptyField(taObjetive.getText()) 
           || validation.emptyField(taVision.getText())  || validation.emptyField(taMision.getText()) || validation.emptyField(tfKey.getText())  
           ){
              value=true;
          }
          return value;
      }
      


    private boolean validateFields(){    
        boolean value=true;
        Validation validation=new Validation();
        if(validation.findInvalidKeyAlphanumeric(tfKey.getText())
        || validation.findInvalidField(taObjetive.getText()) || validation.findInvalidField(taVision.getText()) 
        || validation.findInvalidField(taMision.getText()) || validation.findInvalidField(tfName.getText())){   
            value=false;
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
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
      tfName.setMaxLength(100);
      tfKey.setMaxLength(10);
      consolidateGrades= FXCollections.observableArrayList();
      consolidateGrades.add("En formación");
      consolidateGrades.add("En consolidación");
      consolidateGrades.add("Consolidado");
      cbConsolidateGrade.setItems(consolidateGrades);
      
    }
    
}
