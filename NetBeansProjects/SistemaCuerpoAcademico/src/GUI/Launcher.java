
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
            URL url = new File("src/GUI/MemberList.fxml").toURI().toURL();
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
