
package sistemacuerpoacademico;


import GUI.GroupAcademicShowController;
import GUI.MenuController;
import domain.GroupAcademic;
import java.io.File;
import log.Log;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
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
        
        try{
            
            URL url = new File("src/GUI/Menu.fxml").toURI().toURL();
            try{
                FXMLLoader loader = new FXMLLoader(url);
                loader.setLocation(url);
                loader.load();
                MenuController menuController =loader.getController();
                Parent root = loader.getRoot();
                Scene scene = new Scene(root);
                primaryStage.setScene(scene);
                
            } catch (IOException ex) {
                Log.logException(ex);
            }
            primaryStage.show();
            
        } catch (MalformedURLException ex) {
                Log.logException(ex);
        }
        
    }
        
    public static void main(String[] args){
        launch(args);
    }

    
}