package GUI;
import businessLogic.LoginCredentialDAO;
import businessLogic.MemberDAO;
import domain.LoginCredential;
import domain.Member;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Blob;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import log.BusinessException;


public class LoginController implements Initializable {
    @FXML PasswordField pfPassword;
    @FXML TextField tfUser;
    @FXML Button btLogin;
    private LoginCredential credentialRetrieved = null;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
     
    }    
      
    @FXML 
    public void login(){
       String password = "";
       String user = "";
       user = tfUser.getText();
       password = pfPassword.getText();
       user = tfUser.getText();
       LoginCredential credential = new LoginCredential(user,password);     
       if(verificarCredenciales(credential)){
            MemberDAO memberDAO = new MemberDAO();
            Member member = null;
            try {
               member = memberDAO.getMemberByLicense(credentialRetrieved.getProfessionalLicense());
            } catch (BusinessException ex) {
               Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            }
            Stage primaryStage= new Stage();
            try{
              URL url = new File("src/GUI/Menu.fxml").toURI().toURL();
              FXMLLoader loader = new FXMLLoader(url);
              loader.setLocation(url);
              loader.load();
              MenuController menu = loader.getController();
              menu.initializeMenu(member);
              Parent root = loader.getRoot();
              Scene scene = new Scene(root);
              primaryStage.setScene(scene);
              Stage stage = (Stage) btLogin.getScene().getWindow();
              stage.close();
              primaryStage.show();
            }catch (IOException ex) {
             Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            }
       }else{
           AlertMessage alertMessage = new AlertMessage();
           alertMessage.showAlertValidateFailed("Credenciales incorrectas");
       }
    }
    
     public boolean verificarCredenciales(LoginCredential credential){
        boolean value = true;
        try {
            LoginCredentialDAO credentialDAO = new LoginCredentialDAO();
            credentialRetrieved = credentialDAO.searchLoginCredential(credential);        
            if(!credentialRetrieved.getPassword().equals(credential.getPassword())
                    ||!credentialRetrieved.getUser().equals(credentialRetrieved.getUser())){
                value = false;      
            }           
            
        } catch (BusinessException ex) {
            value = false;
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }   
       return value;
    }
}
