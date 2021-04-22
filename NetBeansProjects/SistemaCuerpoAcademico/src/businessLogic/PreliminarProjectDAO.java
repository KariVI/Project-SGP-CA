
package businessLogic;

import dataaccess.Connector;
import domain.PreliminarProject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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

    @Override
    public ArrayList<PreliminarProject> getPreliminarProjects() throws BusinessException {
       ArrayList<PreliminarProject> preliminarProjectList = new ArrayList<>();
        try{
            Connector connectorDataBase = new Connector();
            Connection connectionDataBase = connectorDataBase.getConnection();
            String queryLgac="SELECT * FROM Anteproyecto";

               PreparedStatement preparedStatement;
               preparedStatement = connectionDataBase.prepareStatement(queryLgac);
               ResultSet resultSet;
               resultSet = preparedStatement.executeQuery();
               while(resultSet.next()){
                   int key= resultSet.getInt(1);
                    String title = resultSet.getString("titulo");
                    String description = resultSet.getString("descripcion");
                    String dateStart=resultSet.getString("fechaInicio");
                    String dateEnd=resultSet.getString("fechaFin");
                    PreliminarProject preliminarProjectAuxiliar = new PreliminarProject(title,description, dateStart, dateEnd);
                    preliminarProjectAuxiliar.setKey(key);
                    preliminarProjectList.add(preliminarProjectAuxiliar);
                }
                connectorDataBase.disconnect();
            }catch(SQLException sqlException) {
                   throw new BusinessException("Database failed ", sqlException);         
            }catch(ClassNotFoundException ex) {
                        Log.logException(ex);
            }
            return preliminarProjectList;
    }

    @Override
    public PreliminarProject getById(int id) throws BusinessException {
        PreliminarProject preliminarProject=null;
        Connector connectorDataBase=new Connector();

        try {
            Connection connectionDataBase = connectorDataBase.getConnection();
            String query="SELECT * from Anteproyecto where idAnteproyecto=?";
            PreparedStatement preparedStatement = connectionDataBase.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet;
            resultSet=preparedStatement.executeQuery();
            if(resultSet.next()){   
                int idPreliminarProject= resultSet.getInt(1);
                String title= resultSet.getString("titulo");
                String description=resultSet.getString("descripcion");
                String dateStart= resultSet.getString("fechaInicio");
                String dateEnd= resultSet.getString("fechaFin");
                preliminarProject=new PreliminarProject(title, description, dateStart, dateEnd);
                preliminarProject.setKey(idPreliminarProject);
            }
            
        } catch (ClassNotFoundException ex) {
            Log.logException(ex);
        } catch (SQLException sqlException) {
            throw new BusinessException("DataBase connection failed ", sqlException);
        }
        return preliminarProject;
    }
    
}
