
package GUI;
<<<<<<< HEAD
import javafx.scene.control.Alert;

public class AlertMessage {
    
    public void showMessageSave(String message){    
    
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setContentText(message + " ha sido guardado con exito");
        alert.showAndWait();
    }
    
     public void showAlert(String message){ 
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText(null);
         alert.setContentText(message);
        alert.showAndWait();
    }
     
     public void showUpdateMessage(){ 
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setTitle("Info");
        alert.setContentText( "Los cambios han sido guardados con exito");
        alert.showAndWait();
         
     }
=======

import javafx.fxml.FXML;
import javafx.scene.control.Alert;

public class AlertMessage {
    @FXML
    public static void showAlertValidateFailed(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setTitle("");
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    @FXML
    public static void showAlertSuccesfulSave(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setTitle("Información guardada");
        alert.setContentText(message);
        alert.showAndWait();
    }
>>>>>>> MarianaChangesGUI
}
