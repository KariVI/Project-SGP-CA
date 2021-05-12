
package sistemacuerpoacademico;


import java.io.File;
import log.Log;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;


public class PrincipalMenu extends Application {
    
    
    public void start(Stage primaryStage) {
        try {
            URL url = new File("src/GUI/groupAcademicRegister.fxml").toURI().toURL();
            try{
              Parent root = FXMLLoader.load(url);
                 Scene scene = new Scene(root);
                primaryStage.setScene(scene);
               
            } catch (IOException ex) {
                Log.logException(ex);
            }
        } catch (MalformedURLException ex) {
            Log.logException(ex);
        }
        primaryStage.show();
        
    }
        
    public static void main(String[] args){
        launch(args);
    }

    
}