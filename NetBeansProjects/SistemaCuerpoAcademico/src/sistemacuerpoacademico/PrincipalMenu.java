
package sistemacuerpoacademico;


<<<<<<< HEAD
import GUI.LoginController;
import GUI.MemberRegisterController;
import GUI.ProjectListController;
=======
>>>>>>> 54e15c11f3015f398b0cf30d76748543db5d4c5d
import GUI.GroupAcademicShowController;
import GUI.LoginController;
import GUI.MenuController;
import GUI.MinuteRegisterController;
import GUI.MinuteShowController;
import GUI.TopicModifyController;
import GUI.TopicShowController;
<<<<<<< HEAD
=======
import GUI.WorkPlanListController;
>>>>>>> 54e15c11f3015f398b0cf30d76748543db5d4c5d
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
        
        try{
<<<<<<< HEAD
           
=======
            
>>>>>>> 54e15c11f3015f398b0cf30d76748543db5d4c5d
            URL url = new File("src/GUI/Login.fxml").toURI().toURL();
            try{
                FXMLLoader loader = new FXMLLoader(url);
                loader.setLocation(url);
                loader.load();
                LoginController login = loader.getController();
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