
package GUI;

import businessLogic.GroupAcademicDAO;
import domain.GroupAcademic;
import domain.LGAC;
import domain.Member;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import log.BusinessException;
import log.Log;

public class GroupAcademicShowController implements Initializable {
    
    @FXML private Label lbName;
    @FXML private Label lbKey; 
    @FXML private Label lbConsolidateGrade; 
    @FXML private TextArea taObjetive;
    @FXML private TextArea taVision;
    @FXML private TextArea taMision;
    @FXML private Pane lgacsPane;
    @FXML private Button btUpdate;
    @FXML private Button btReturn;
    private Member member;
    private GroupAcademic groupAcademic;
    
    public void setGroupAcademic(GroupAcademic groupAcademic){   
        this.groupAcademic= groupAcademic;
    }
    
     public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }
    
    private void disableButtonUpdate(){   
        if(member.getRole().equals("Integrante")){  
            btUpdate.setOpacity(0);
            btUpdate.setDisable(true);
        
        }
    }
    
    public void initializeGroupAcademic(){
        lbName.setText( groupAcademic.getName());
        lbKey.setText(groupAcademic.getKey());
        lbConsolidateGrade.setText(groupAcademic.getConsolidationGrade());
        taObjetive.setText("Objetivo: "+groupAcademic.getObjetive() );
        taVision.setText("Visión: "+groupAcademic.getVision());
        taMision.setText("Misión: "+groupAcademic.getMission());
        disableButtonUpdate();
        try {
            getlgacs();
        } catch (BusinessException ex) {
            Log.logException(ex);
        }
    }
  
    @FXML
    private void actionUpdate(ActionEvent actionEvent){  
        Stage primaryStage= new Stage();
        URL url=null;
        try{ 
             url = new File("src/GUI/groupAcademicModify.fxml").toURI().toURL();
        } catch (MalformedURLException ex) {
            Log.logException(ex);
        }
        
        try{
              FXMLLoader loader = new FXMLLoader(url);
              loader.setLocation(url);
              loader.load();
              GroupAcademicModifyController groupAcademicModifyController =loader.getController();      
              groupAcademicModifyController.setGroupAcademic(groupAcademic);
              groupAcademicModifyController.initializeGroupAcademic();
              Parent root = loader.getRoot();
              Scene scene = new Scene(root);
              primaryStage.setScene(scene);
              Stage stage = (Stage) btUpdate.getScene().getWindow();
              stage.close();
            } catch (IOException ex) {
                    Log.logException(ex);
            }
            primaryStage.show();
       
    }   
    
    @FXML 
    private void actionReturn(ActionEvent actionEvent){   
        Stage stage = (Stage) btReturn.getScene().getWindow();
        stage.close();
    }
 
   
    private void getlgacs() throws BusinessException{
        GridPane gridPane= new GridPane();
        GroupAcademicDAO groupAcademicDAO =new GroupAcademicDAO();
        ArrayList<LGAC> lgacs = groupAcademicDAO.getLGACs(groupAcademic.getKey());
        groupAcademic.setLGACs(lgacs);
        int i=0;
        int numberlgacs=0;
        int numberRows=3;
        while (i < ( lgacs.size() * numberRows)){  
                String name= "Nombre : " +lgacs.get(numberlgacs).getName();
                Label lbNamelgac = new Label(name);
                String description= "Descripcion: "+ lgacs.get(numberlgacs).getDescription();
                TextArea taDescriptionlgac = new TextArea(description);
                taDescriptionlgac.setPrefHeight(38); 
                taDescriptionlgac.setPrefWidth(660);
                String lgacName= "LGAC "+ (numberlgacs + 1);
                Label label = new Label(lgacName);
                gridPane.add(label,1,i);
                gridPane.add(lbNamelgac,1,(i + 1));
                
                gridPane.add(taDescriptionlgac,1, (i + 2));
                i=i+3;
                numberlgacs++;
        }
        lgacsPane.getChildren().add(gridPane);
    }
    
    
    public void initialize(URL url, ResourceBundle rb) {
       
        
    }    
    
}
