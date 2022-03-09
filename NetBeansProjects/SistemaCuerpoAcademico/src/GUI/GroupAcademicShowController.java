
package GUI;

import businessLogic.GroupAcademicDAO;
import domain.Goal;
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
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
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
    @FXML private Button btUpdate;
    @FXML private Button btReturn;
    @FXML private TableView<LGAC> tvLGACS;
    private ListChangeListener<LGAC> tableLGACSListener;
    @FXML private TableColumn tcName;
    @FXML private TableColumn tcDescription;
    private ObservableList<LGAC> LGACS;
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
            getLgacs();
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
              groupAcademicModifyController.setMember(member);
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
        openViewMenu();
    }
    
    private void openViewMenu(){   
        Stage primaryStage = new Stage();
        try{
              URL url = new File("src/GUI/Menu.fxml").toURI().toURL();
              FXMLLoader loader = new FXMLLoader(url);
              loader.setLocation(url);
              loader.load();
              MenuController menu = loader.getController();
              menu.initializeMenu(member);
              Parent root = loader.getRoot();
              Scene scene = new Scene(root);
              primaryStage.setScene(scene);
              primaryStage.show();
            }catch (IOException ex) {
                Log.logException(ex);
            }
    }
   
    private void getLgacs() throws BusinessException{
        GridPane gridPane= new GridPane();
        GroupAcademicDAO groupAcademicDAO =new GroupAcademicDAO();
        ArrayList<LGAC> lgacs = groupAcademicDAO.getLGACs(groupAcademic.getKey());
        groupAcademic.setLGACs(lgacs);
        int i=0;
        int numberlgacs=0;
        while (i < lgacs.size() ){  
          LGACS.add(lgacs.get(i));
          i++;
        }

    }
    
    
    public void initialize(URL url, ResourceBundle rb) {
        tcName.setCellValueFactory(new PropertyValueFactory<LGAC,String>("name")); 
        tcDescription.setCellValueFactory(new PropertyValueFactory<LGAC,String>("description"));   
        LGACS = FXCollections.observableArrayList();
        tvLGACS.setItems(LGACS);
        
    }    
    
}
