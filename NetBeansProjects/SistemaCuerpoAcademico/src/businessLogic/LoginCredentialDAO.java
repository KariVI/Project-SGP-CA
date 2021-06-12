package businessLogic;


import businessLogic.ILoginCredentialDAO;
import dataaccess.Connector;
import domain.LoginCredential;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import log.BusinessException;
import log.Log;

public class LoginCredentialDAO implements ILoginCredentialDAO {
     public boolean register(LoginCredential credential) throws BusinessException {
        boolean saveSuccess = false;
        try {  
            try { 
            Connector connectorDataBase = new Connector();
            Connection connectionDataBase;
            connectionDataBase = connectorDataBase.getConnection();   
            PreparedStatement insertStatment;
            insertStatment = connectionDataBase.prepareStatement("INSERT INTO Credenciales(usuario,contrasenia,cedula) VALUES(?,hex(AES_ENCRYPT(?,'key')),?)");
            insertStatment.setString(1, credential.getUser());
            insertStatment.setString(2, credential.getPassword());      
            insertStatment.setString(3, credential.getProfessionalLicense());
            
            insertStatment.executeUpdate();
            
            connectorDataBase.disconnect();
            saveSuccess = true;
             }catch(SQLException sqlException) {
                throw new BusinessException("Parameter index ", sqlException);
            }   
         }catch(ClassNotFoundException ex) {
            Log.logException(ex);
        }     
        return saveSuccess;
    } 
       public LoginCredential searchLoginCredential(LoginCredential credential)throws BusinessException  {
        LoginCredential retrievedCredential = null;
        try{
            Connector connectorDataBase=new Connector();
            Connection connectionDataBase = connectorDataBase.getConnection();
          try{
            ResultSet resultSet;
            PreparedStatement preparedStatement = connectionDataBase.prepareStatement("SELECT usuario,cedula,(AES_DECRYPT(unhex(contrasenia),'key')) from Credenciales where usuario=? ");
     
            preparedStatement.setString(1, credential.getUser());          
            
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                String user = resultSet.getString("usuario");
                String professionalLicense = resultSet.getString("cedula");
                String password = resultSet.getString("(AES_DECRYPT(unhex(contrasenia),'key'))");
                retrievedCredential = new LoginCredential(user,password,professionalLicense);
            }else{
                 throw new BusinessException("credential not found");
            }
                connectorDataBase.disconnect();
                
         }catch(SQLException sqlException) {
                throw new BusinessException("Parameter index ", sqlException);
         }        
        }catch(ClassNotFoundException ex) {
            Log.logException(ex);
        }
        return retrievedCredential;
    } 
}
