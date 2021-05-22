<<<<<<< HEAD
package GUI;

import domain.GroupAcademic;
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


public class Launcher extends Application {
    
    
    public void start(Stage primaryStage) {
       
            try{
              FXMLLoader loader = new FXMLLoader(getClass().getResource("groupAcademicShow.fxml"));
              loader.setLocation(getClass().getResource("groupAcademicShow.fxml"));
               loader.load();
               GroupAcademicShowController groupAcademicShowController =loader.getController(); 
               GroupAcademic groupAcademic= new GroupAcademic("JDOEIJ804", "Ingenieria y Tecnologias de Software", "Desarrollar métodos, técnicas y herramientas para el desarrollo de software con un enfoque sistemático, disciplinado y cuantificable y apegado a estándares de calidad"
               , "En consolidacion", " Generar conocimiento y formar recursos humanos en Ingeniería de Software que contribuyan al desarrollo de software de calidad; a través de proyectos de investigación cuyos resultados se trasladen a  la docencia y la sociedad; y se difundan en foros especializados y de divulgación, fortaleciendo la vinculación academia-industria",
              "El Cuerpo Académico se encuentra consolidado y es líder en Ingeniería de Software y áreas relacionadas; todos los miembros trabajan colaborativamente en actividades de docencia, vinculación, generación y aplicación del conocimiento en las que participan activamente estudiantes de licenciatura y posgrado");
        
              groupAcademicShowController.setGroupAcademic(groupAcademic);
              Parent root = loader.getRoot();
                 Scene scene = new Scene(root);
                primaryStage.setScene(scene);
               
            } catch (IOException ex) {
                Log.logException(ex);
            }
            primaryStage.show();
        }
        
    public static void main(String[] args){
        launch(args);
      }
    }
=======

package GUI;
import businessLogic.MemberDAO;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import log.BusinessException;
public class Launcher extends Application {
    @Override
    public void start(Stage primaryStage) {
  
        try {
            URL url = new File("src/GUI/TopicModify.fxml").toURI().toURL();
            AnchorPane root = (AnchorPane)FXMLLoader.load(url);
            Scene scene = new Scene(root,600,400);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException ex) {
            Logger.getLogger(Launcher.class.getName()).log(Level.SEVERE, null, ex);
        }
                  
    }
    
    public static void main(String[] args) throws BusinessException{
        launch(args);
    }

}
>>>>>>> MarianaChangesGUI
