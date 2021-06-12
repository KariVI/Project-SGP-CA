/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ProjectRegisterController implements Initializable {

    @FXML private DatePicker dpFinishDate;
    @FXML private DatePicker dpStartDate;
    @FXML private TextField lbTitle;
    @FXML private TextArea taDescription;
    @FXML private TableView<?> tvLGAC;
    @FXML private TableColumn<?, ?> tcLGAC;
    @FXML private TableView<?> tvRecepcionalWork;
    @FXML private TableColumn<?, ?> tcRecepcionalWork;
    @FXML private TableView<?> tvMember;
    @FXML private TableColumn<?, ?> tcMember;
    @FXML private TableView<?> tvStudent;
    @FXML private TableColumn<?, ?> tcStudent;
    @FXML private Button addLGAC;
    @FXML private Button addRecepcionalWork;
    @FXML private Button addStudent;
    @FXML private Button addMember;
    @FXML private Button save;
    @FXML private Button cancel;
 
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
}
