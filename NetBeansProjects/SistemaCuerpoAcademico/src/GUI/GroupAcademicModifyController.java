
package GUI;

import businessLogic.GroupAcademicDAO;
import businessLogic.LGACDAO;
import domain.GroupAcademic;
import domain.LGAC;
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
    @FXML private TextArea taObjetive;
    @FXML private TextArea taVision;
    @FXML private TextArea taMision; 
    @FXML private TextField tflgacsNumber;
    @FXML private Button btOk;
    @FXML private Button btSave;
    @FXML private Button btReturn;
    @FXML private Node groupAcademicPanel;
    @FXML private Pane lgacPane;
    @FXML private ComboBox<String> cbConsolidateGrade;
     private ObservableList<String> consolidateGrades;
    private GroupAcademic groupAcademic= new GroupAcademic();
    private GroupAcademic groupAcademicNew=new GroupAcademic();
  
   
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
            lgacPane.getChildren().add(gridPane);
    }
  
    @FXML
    private void actionSave (ActionEvent actionEvent){    
        String name = tfName.getText();
        String consolidationGrade= cbConsolidateGrade.getSelectionModel().getSelectedItem();;
        String objetive= taObjetive.getText();
        String vision= taVision.getText();
        String mision= taMision.getText();
        String key= tfKey.getText();
        
        if(!validateFieldEmpty() && validateFields()){  
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
    
    @FXML 
    private void actionReturn(ActionEvent actionEvent) throws BusinessException{  
        Stage stage = (Stage) btReturn.getScene().getWindow();
        stage.close();
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
                if(!tfKey.getText().isEmpty()){
                    groupAcademic= groupAcademicDAO.getGroupAcademicById(groupAcademicNew.getKey());
                    System.out.println(groupAcademicNew.getConsolidationGrade());

                }else{  
                    groupAcademic= groupAcademicDAO.getGroupAcademicById(this.groupAcademic.getKey());
                }
                groupAcademicShowController.setGroupAcademic(groupAcademic);
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
    
    public void updateGroupAcademic (){    
        GroupAcademicDAO groupAcademicDAO =new GroupAcademicDAO();
        AlertMessage alertMessage =new AlertMessage();
        try {
            if(groupAcademicDAO.updatedSucessful(groupAcademic.getKey(),groupAcademicNew)){
                recoverlgacs();
                alertMessage.showUpdateMessage();
            }
        } catch (BusinessException ex) {
           if(ex.getMessage().equals("DataBase connection failed ")){
                alertMessage.showAlert("Error en la conexion con la base de datos");
            }else{  
                Log.logException(ex);
            }
        }                
    }
    
    private void recoverlgacs(){   
        GridPane gridPane= (GridPane) lgacPane.getChildren().get(0);
            int i=1;
            int indexLGAC=0;
            ArrayList<LGAC> lgacs = groupAcademic.getLGACs();
            
            int sizeRows=3;
           while (i < (sizeRows * lgacs.size())){
               TextField namelgac = (TextField) getNodeFromGridPane( gridPane, 1, i);
               TextArea descriptionlgac = (TextArea) getNodeFromGridPane( gridPane, 1, (i + 1));
               if(validateFieldslgacs(namelgac,descriptionlgac)){         
                 String name= namelgac.getText();
                 String description= descriptionlgac.getText(); 
                 LGAC lgac = new LGAC(name, description);
                 String nameLast = lgacs.get(indexLGAC).getName();
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
        
        try {
                LGAC lgacLast = lgacDAO.getLgacByName(nameLGACLast);
                lgacDAO.updatedSucessful(nameLGACLast, lgac);
                groupAcademicDAO.deletedLGACSuccesful(groupAcademic.getKey(),lgacLast);
                groupAcademicDAO.addedLGACSucessful(groupAcademicNew, lgac);

        } catch (BusinessException ex) {
            if(ex.getMessage().equals("DataBase connection failed ")){
                AlertMessage alertMessage = new AlertMessage();
                alertMessage.showAlert("Error en la conexion con la base de datos");
            }else{  
                Log.logException(ex);
            }
        }
    }
    
       
    private boolean validateFieldEmpty(){ 
          boolean value=false;
          if(tfName.getText().isEmpty() || taObjetive.getText().isEmpty() 
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
      consolidateGrades= FXCollections.observableArrayList();
      consolidateGrades.add("En formación");
      consolidateGrades.add("En consolidación");
      consolidateGrades.add("Consolidado");
      cbConsolidateGrade.setItems(consolidateGrades);
      
    }
    
}
