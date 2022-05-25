
package GUI;


import javafx.fxml.FXML;
import javafx.scene.control.Alert;

public class AlertMessage {
    @FXML
    public  void showAlertValidateFailed(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setTitle("Información invalida");
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    @FXML
     public  void showAlertSuccesfulSave(String message){    
    
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setTitle("Información guardada");
        alert.setContentText(message + " ha sido guardado con exito");
        alert.showAndWait();
    }
    
    @FXML
    public void showUpdateMessage(){ 
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setTitle("Información guardada");
        alert.setContentText( "Los cambios han sido guardados con exito");
        alert.showAndWait();
         
     }  

}
