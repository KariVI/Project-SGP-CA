
package sistemacuerpoacademico;


<<<<<<< HEAD
import GUI.LoginController;
import GUI.MemberRegisterController;
import GUI.ProjectListController;
=======
import GUI.GroupAcademicShowController;
import GUI.LoginController;
import GUI.MenuController;
import GUI.MinuteRegisterController;
import GUI.MinuteShowController;
import GUI.TopicModifyController;
import GUI.TopicShowController;
>>>>>>> 68b8011ddd1283c3f8e4367bfc1f4edf97f67303
import GUI.WorkPlanRegisterController;
import domain.GroupAcademic;
import java.io.File;
import log.Log;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;


public class PrincipalMenu extends Application {
    
    
    public void start(Stage primaryStage) {
        //MemberRegister
        try{
            
<<<<<<< HEAD
            URL url = new File("src/GUI/Login.fxml").toURI().toURL();
=======
           URL url = new File("src/GUI/Login.fxml").toURI().toURL();
>>>>>>> 68b8011ddd1283c3f8e4367bfc1f4edf97f67303
            try{
                FXMLLoader loader = new FXMLLoader(url);
                loader.setLocation(url);
                loader.load();
                LoginController login = loader.getController();
<<<<<<< HEAD
               
=======
>>>>>>> 68b8011ddd1283c3f8e4367bfc1f4edf97f67303
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