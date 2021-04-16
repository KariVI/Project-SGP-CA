
package businessLogic;

import dataaccess.Connector;
import domain.ReceptionWork;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import log.BusinessException;
import log.Log;


public class ReceptionWorkDAO implements IReceptionWorkDAO {

    @Override
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
    
}
