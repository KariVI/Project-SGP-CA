package businessLogic;

import dataaccess.Connector;
import domain.LoginCredential;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import log.BusinessException;
import log.Log;

/*
        *@author Mariana Vargas
*/
public class LoginCredentialDAO implements ILoginCredentialDAO {
    
    /*
        *@param credential Credencial a registrar
        *@return Si la credencial pudo ser registrada (true) o no (false) en la base de datos 
        *@throws BusinessException Se cacho una excepción de tipo SQLException
    */
    @Override
    public boolean registerSuccesful(LoginCredential credential) throws BusinessException {
        boolean saveSuccess = false;
        try {  
            try { 
                Connector connectorDataBase = new Connector();
                Connection connectionDataBase;
                connectionDataBase = connectorDataBase.getConnection();   
                PreparedStatement insertStatment;
                insertStatment = connectionDataBase.prepareStatement("INSERT INTO Credenciales(usuario,contrasenia,cedula) VALUES(?,hex(AES_ENCRYPT(?,?)),?)");
                insertStatment.setString(1, credential.getUser());
                insertStatment.setString(2, credential.getPassword()); 
                insertStatment.setString(3, "Key");
                insertStatment.setString(4, credential.getProfessionalLicense());
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
    
    /*
        *@param credential Credencial a buscar
        *@return La credencial recuperada de la base de datos (LoginCredential)
        *@throws BusinessException Se cacho una excepción de tipo SQLException o no se encontró la credencial
    */     
    @Override
    public LoginCredential searchLoginCredential(LoginCredential credential)throws BusinessException  {
        LoginCredential retrievedCredential = null;
        try{
            Connector connectorDataBase=new Connector();
            Connection connectionDataBase = connectorDataBase.getConnection(); 
            try{
                ResultSet resultSet;
                PreparedStatement preparedStatement = connectionDataBase.prepareStatement("SELECT usuario,cedula,(AES_DECRYPT(unhex(contrasenia),?)) from Credenciales where usuario=? ");
                preparedStatement.setString(1, "Key");    
                preparedStatement.setString(2, credential.getUser()); 
                resultSet = preparedStatement.executeQuery();
                if(resultSet.next()){
                String user = resultSet.getString("usuario");
                String professionalLicense = resultSet.getString("cedula");
                String password = resultSet.getString("(AES_DECRYPT(unhex(contrasenia),'Key'))");
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
