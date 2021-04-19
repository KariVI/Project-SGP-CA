
package businessLogic;

import dataaccess.Connector;
import domain.PreliminarProject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import log.BusinessException;
import log.Log;

public class PreliminarProjectDAO implements IPreliminarProjectDAO {

    @Override
    public boolean save(PreliminarProject preliminarProject) throws BusinessException {
         boolean value=false;
            try {
                Connector connectorDataBase=new Connector();
                Connection connectionDataBase = connectorDataBase.getConnection();
                String insertPreliminarProject = "INSERT INTO Anteproyecto(titulo,descripcion, fechaInicio,fechaFin ) VALUES (?,?,?,?)";
            
                PreparedStatement preparedStatement = connectionDataBase.prepareStatement(insertPreliminarProject );
                preparedStatement.setString(1, preliminarProject.getTitle());
                preparedStatement.setString(2, preliminarProject.getDescription());
                preparedStatement.setString(3, preliminarProject.getDateStart());
                preparedStatement.setString(4, preliminarProject.getDateEnd());
                
                
                preparedStatement.executeUpdate();
                value=true;
                connectorDataBase.disconnect();
            } catch (SQLException sqlException) {
                throw new BusinessException("DataBase connection failed ", sqlException);
            } catch (ClassNotFoundException ex) {
               Log.logException(ex);
            }
        return value;
    }

    @Override
    public int getId(PreliminarProject preliminarProject) throws BusinessException {
        Integer id= 0;
        try{
            Connector connectorDataBase=new Connector();
            Connection connectionDataBase = connectorDataBase.getConnection();
            ResultSet resultSet;
            String selectId = "SELECT idAnteproyecto from  Anteproyecto where titulo=? and descripcion=? and fechaInicio=? and fechaFin=?;";
     
            PreparedStatement preparedStatement = connectionDataBase.prepareStatement(selectId);
            preparedStatement.setString(1, preliminarProject.getTitle());
            preparedStatement.setString(2, preliminarProject.getDescription());
            preparedStatement.setString(3, preliminarProject.getDateStart());
            preparedStatement.setString(4, preliminarProject.getDateEnd());      
            resultSet=preparedStatement.executeQuery();
           
            if(resultSet.next()){
                id=Integer.parseInt(resultSet.getString("idAnteproyecto"));
            }
            connectorDataBase.disconnect();
        }catch(SQLException sqlException) {
            throw new BusinessException("DataBase connection failed ", sqlException);
        } catch (ClassNotFoundException ex) {
            Log.logException(ex);
        }
        return id;
    }

    @Override
    public boolean update(int id,PreliminarProject preliminarProject) throws BusinessException {
        boolean updateSucess=false;
        try{
            Connector connectorDataBase=new Connector();
            Connection connectionDataBase = connectorDataBase.getConnection();
            String updatePreliminarProject = "UPDATE Anteproyecto set titulo=? , descripcion=?, fechaInicio=?, fechaFin=? where idAnteproyecto=?";
            PreparedStatement preparedStatement = connectionDataBase.prepareStatement(updatePreliminarProject );
            preparedStatement.setString(1, preliminarProject.getTitle());
            preparedStatement.setString(2, preliminarProject.getDescription());
            preparedStatement.setString(3, preliminarProject.getDateStart());
            preparedStatement.setString(4, preliminarProject.getDateEnd());
            preparedStatement.setInt(5, id);
            
            preparedStatement.executeUpdate();
            connectorDataBase.disconnect();
            updateSucess=true;
        }catch(SQLException sqlException){
         throw new BusinessException("DataBase connection failed ", sqlException);
        }catch (ClassNotFoundException ex) {
            Log.logException(ex);
        }
        
        return updateSucess;
    }
    
}
