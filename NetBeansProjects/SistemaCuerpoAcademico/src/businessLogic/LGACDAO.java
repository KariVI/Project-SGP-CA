/*
        *@author Karina Valdes

    */
package businessLogic;

import dataaccess.Connector;
import domain.LGAC;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import log.BusinessException;
import log.Log;


public class LGACDAO implements ILGACDAO {
    
    /*
        *@params lgac LGAC a guardar 
        *@return Si la LGAC pudo ser guardada (true) o no (false) en la base de datos 
        *@throws Se cacho una excepción de tipo SQLException
    */
    public boolean savedSucessful(LGAC lgac) throws BusinessException{    
        boolean value=false;
        Connector connectorDataBase=new Connector();
        try {
            Connection connectionDataBase = connectorDataBase.getConnection();
            String insertLgac = "INSERT INTO LGAC(nombre,descripcion) VALUES (?,?)";
            
            PreparedStatement preparedStatement = connectionDataBase.prepareStatement(insertLgac);
            preparedStatement.setString(1, lgac.getName());
            preparedStatement.setString(2, lgac.getDescription());
                
            preparedStatement.executeUpdate();
            value=true;  
            connectorDataBase.disconnect();
        }catch (ClassNotFoundException ex) {
            Log.logException(ex);
        } catch (SQLException sqlException) {
           throw new BusinessException("DataBase connection failed ", sqlException);
            
        }
        return value;
    }

     /*
        *@params name nombre de la LGAC a buscar
        *@return La LGAC pudo ser recuperada de acuerdo al nombre
        *@throws Se cacho una excepción de tipo SQLException o o si no es localizada la LGAC manda una excepción de tipo BusinessException
    */
    @Override
    public LGAC getLgacByName(String name) throws BusinessException {
        LGAC lgacAuxiliar=null;
        Connector connectorDataBase=new Connector();
        try {
            Connection connectionDataBase = connectorDataBase.getConnection();
            String query="SELECT * from LGAC where nombre=?";
            PreparedStatement preparedStatement = connectionDataBase.prepareStatement(query);
            preparedStatement.setString(1, name);
            ResultSet resultSet;
            resultSet=preparedStatement.executeQuery();
            
            if(resultSet.next()){   
                String nameLgac= resultSet.getString("nombre");
                String description=resultSet.getString("descripcion");
                lgacAuxiliar=new LGAC(nameLgac, description);
            }else{
                throw new BusinessException("LGAC not found");
            } 
            
        } catch (ClassNotFoundException ex) {
            Log.logException(ex);
        } catch (SQLException sqlException) {
            throw new BusinessException("DataBase connection failed ", sqlException);
        }
        return lgacAuxiliar;
    }

   /*
        *@params beforeName Nombre anterior de la LGAC ha actualizar
        *@params lgac La nueva información de la LGAC a actualizar 
        *@return Si la LGAC pudo ser actualizada (true) o no (false) en la base de datos 
        *@throws Se cacho una excepción de tipo SQLException 
    */
    public boolean updatedSucessful(String beforeName, LGAC lgac) throws BusinessException {
        boolean updateSucess=false;
        Connector connectorDataBase=new Connector();
        try {
            Connection connectionDataBase = connectorDataBase.getConnection();
            String updateLgac = "UPDATE LGAC set nombre=?,descripcion=? where nombre=?";
            
            PreparedStatement preparedStatement = connectionDataBase.prepareStatement(updateLgac);
            preparedStatement.setString(1, lgac.getName());
            preparedStatement.setString(2, lgac.getDescription());
            preparedStatement.setString(3,beforeName);
                
            preparedStatement.executeUpdate();
            updateSucess=true;  
            connectorDataBase.disconnect();
        }catch (ClassNotFoundException ex) {
            Log.logException(ex);
        } catch (SQLException sqlException) {
           throw new BusinessException("DataBase connection failed ", sqlException);
            
        }
        return updateSucess;
    }

   
 
    
        
}
