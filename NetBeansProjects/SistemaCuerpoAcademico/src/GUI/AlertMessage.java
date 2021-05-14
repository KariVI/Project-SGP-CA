
package GUI;

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
}
