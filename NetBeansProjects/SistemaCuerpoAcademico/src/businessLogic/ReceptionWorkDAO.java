
package businessLogic;

import dataaccess.Connector;
import domain.ReceptionWork;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import log.BusinessException;
import log.Log;


public class ReceptionWorkDAO implements IReceptionWorkDAO {

    public boolean save(ReceptionWork receptionWork) throws BusinessException {
        boolean value=false;
        int idPreliminarProject= receptionWork.getPreliminarProject().getKey();
            try {
                Connector connectorDataBase=new Connector();
                Connection connectionDataBase = connectorDataBase.getConnection();
                String insertPreliminarProject = "INSERT INTO TrabajoRecepcional(titulo, tipo, descripcion, fechaInicio,fechaFin, estadoActual, idAnteproyecto ) VALUES (?,?,?,?,?,?,?)";
            
                PreparedStatement preparedStatement = connectionDataBase.prepareStatement(insertPreliminarProject );
                preparedStatement.setString(1, receptionWork.getTitle());
                preparedStatement.setString(2, receptionWork.getType());
                preparedStatement.setString(3, receptionWork.getDescription());
                preparedStatement.setString(4, receptionWork.getDateStart());
                preparedStatement.setString(5, receptionWork.getDateEnd());
                preparedStatement.setString(6, receptionWork.getActualState());
                preparedStatement.setInt(7,idPreliminarProject );
                
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

    public boolean update(int id, ReceptionWork receptionWork) throws BusinessException {
        boolean updateSuccess=false;
        int idPreliminarProject= receptionWork.getPreliminarProject().getKey();
       
        try{
            Connector connectorDataBase=new Connector();
            Connection connectionDataBase = connectorDataBase.getConnection();
            String updateReceptionWork= "UPDATE TrabajoRecepcional set titulo =?, tipo=?,descripcion=?, fechaInicio=?,fechaFin=?, estadoActual=?, idAnteproyecto=? where idTrabajoRecepcional=? ";
     
            PreparedStatement preparedStatement = connectionDataBase.prepareStatement(updateReceptionWork );
            preparedStatement.setString(1, receptionWork.getTitle());
            preparedStatement.setString(2, receptionWork.getType());
            preparedStatement.setString(3, receptionWork.getDescription());
            preparedStatement.setString(4, receptionWork.getDateStart());
            preparedStatement.setString(5, receptionWork.getDateEnd());
            preparedStatement.setString(6, receptionWork.getActualState());
            preparedStatement.setInt(7,idPreliminarProject );
            preparedStatement.setInt(8,id);
            
            preparedStatement.executeUpdate();
            updateSuccess=true;
            connectorDataBase.disconnect();
        }catch(SQLException sqlException) {
            throw new BusinessException("DataBase connection failed ", sqlException);
        } catch (ClassNotFoundException ex) {
            Log.logException(ex);
        }
        return updateSuccess;
    }

    @Override
    public ReceptionWork getReceptionWorkById(int id) throws BusinessException {
        ReceptionWork receptionWork=null;
        Connector connectorDataBase=new Connector();

        try {
            Connection connectionDataBase = connectorDataBase.getConnection();
            String query="SELECT * from TrabajoRecepcional where idTrabajoRecepcional=?";
            PreparedStatement preparedStatement = connectionDataBase.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet;
            resultSet=preparedStatement.executeQuery();
            if(resultSet.next()){   
                int idReceptionWork= resultSet.getInt(1);
                String title= resultSet.getString("titulo");
                String type= resultSet.getString("tipo");
                String description=resultSet.getString("descripcion");
                String dateStart= resultSet.getString("fechaInicio");
                String dateEnd= resultSet.getString("fechaFin");
                String actualState= resultSet.getString("estadoActual");
                receptionWork=new ReceptionWork(title,type, description, dateStart, dateEnd, actualState);
                receptionWork.setKey(idReceptionWork);
            }
            
        } catch (ClassNotFoundException ex) {
            Log.logException(ex);
        } catch (SQLException sqlException) {
            throw new BusinessException("DataBase connection failed ", sqlException);
        }
        return receptionWork;
    }


    public int getId(ReceptionWork receptionWork) throws BusinessException {
        Integer id= 0;
        try{
            Connector connectorDataBase=new Connector();
            Connection connectionDataBase = connectorDataBase.getConnection();
            ResultSet resultSet;
            String selectId = "SELECT idTrabajoRecepcional from  TrabajoRecepcional where titulo=? and descripcion=? and tipo=? and fechaInicio=? and fechaFin=?;";
     
            PreparedStatement preparedStatement = connectionDataBase.prepareStatement(selectId);
            preparedStatement.setString(1, receptionWork.getTitle());
            preparedStatement.setString(2, receptionWork.getDescription());
            preparedStatement.setString(3, receptionWork.getType());
            preparedStatement.setString(4, receptionWork.getDateStart());
            preparedStatement.setString(5, receptionWork.getDateEnd());      
            resultSet=preparedStatement.executeQuery();
           
            if(resultSet.next()){
                id=Integer.parseInt(resultSet.getString("idTrabajoRecepcional"));
            }
            connectorDataBase.disconnect();
        }catch(SQLException sqlException) {
            throw new BusinessException("DataBase connection failed ", sqlException);
        } catch (ClassNotFoundException ex) {
            Log.logException(ex);
        }
        return id;
    }
    public ArrayList<ReceptionWork> getReceptionWorks() throws BusinessException {
        ArrayList<ReceptionWork> receptionWorkList = new ArrayList<>();
        try{
            Connector connectorDataBase = new Connector();
            Connection connectionDataBase = connectorDataBase.getConnection();
            String queryReceptionWork="SELECT * FROM TrabajoRecepcional";

               PreparedStatement preparedStatement;
               preparedStatement = connectionDataBase.prepareStatement(queryReceptionWork);
               ResultSet resultSet;
               resultSet = preparedStatement.executeQuery();
               while(resultSet.next()){
                   int key= resultSet.getInt(1);
                    String title = resultSet.getString("titulo");
                    String description = resultSet.getString("descripcion");
                    String type = resultSet.getString("tipo");
                    String dateStart=resultSet.getString("fechaInicio");
                    String dateEnd=resultSet.getString("fechaFin");
                    String actualState = resultSet.getString("estadoActual");
                    ReceptionWork receptionWorkAuxiliar = new ReceptionWork(title,type, description, dateStart, dateEnd, actualState);
                    receptionWorkAuxiliar.setKey(key);
                    receptionWorkList.add(receptionWorkAuxiliar);
                }
                connectorDataBase.disconnect();
            }catch(SQLException sqlException) {
                   throw new BusinessException("Database failed ", sqlException);         
            }catch(ClassNotFoundException ex) {
                        Log.logException(ex);
            }
            return receptionWorkList;
    }
    
}