/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import domain.PreliminarProject;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

/**
 * FXML Controller class
 *
 * @author kari
 */
public class PreliminarProjectShowController implements Initializable {

    PreliminarProject preliminarProject;
    
     public void setGroupAcademic(PreliminarProject preliminarProject){   
        this.preliminarProject= preliminarProject;
    }
    
    public void initializeGroupAcademic(){
        /*lbName.setText( groupAcademic.getName());
        lbKey.setText(groupAcademic.getKey());
        lbConsolidateGrade.setText(groupAcademic.getConsolidationGrade());
        taObjetive.setText("Objetivo: "+groupAcademic.getObjetive());
        taVision.setText("Visión: "+groupAcademic.getVision());
        taMision.setText("Misión "+groupAcademic.getMission());
        try {
            getlgacs();
        } catch (BusinessException ex) {
            Log.logException(ex);
        }*/
    }
  
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
