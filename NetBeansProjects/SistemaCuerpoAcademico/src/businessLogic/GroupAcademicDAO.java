
package businessLogic;

import dataaccess.Connector;
import domain.GroupAcademic;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import log.BusinessException;
import log.Log;

public class GroupAcademicDAO implements IGroupAcademicDAO {

    public boolean save(GroupAcademic groupAcademic) throws BusinessException {
        boolean value=false;
            try {
                Connector connectorDataBase=new Connector();
                Connection connectionDataBase = connectorDataBase.getConnection();
                String insertGroupAcademic = "INSERT INTO CuerpoAcademico(clave,nombre, objetivo, mision , vision , gradoConsolidacion) VALUES (?,?,?,?,?,?)";
            
                PreparedStatement preparedStatement = connectionDataBase.prepareStatement(insertGroupAcademic);
                
                preparedStatement.setString(1, groupAcademic.getKey());
                preparedStatement.setString(2, groupAcademic.getName());
                preparedStatement.setString(3, groupAcademic.getObjetive());
                preparedStatement.setString(4, groupAcademic.getMission());
                preparedStatement.setString(5, groupAcademic.getVision());
                preparedStatement.setString(6, groupAcademic.getConsolidationGrade());
                
                
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
 
    public GroupAcademic getGroupAcademicById(String key ) throws BusinessException{
        GroupAcademic groupAcademicAuxiliar = null;
        try{
            Connector connectorDataBase=new Connector();
            Connection connectionDataBase = connectorDataBase.getConnection();
            ResultSet resultSet;
            String selectIdGroupAcademic = "SELECT * from  CuerpoAcademico where clave=?";
     
            PreparedStatement preparedStatement = connectionDataBase.prepareStatement(selectIdGroupAcademic);
            preparedStatement.setString(1, key);        
            resultSet=preparedStatement.executeQuery();
           
            if(resultSet.next()){
                String keyGroup= resultSet.getString(1);
                String name= resultSet.getString("nombre");
                String objetive= resultSet.getString("objetivo");
                String mission= resultSet.getString("mision");
                String vision=resultSet.getString("vision");
                String consolidationGrade=resultSet.getString("gradoConsolidacion");
                groupAcademicAuxiliar= new GroupAcademic(keyGroup, name, objetive, consolidationGrade, mission,vision);
            }
                connectorDataBase.disconnect();
        }catch(SQLException sqlException) {
            throw new BusinessException("DataBase connection failed ", sqlException);
        } catch (ClassNotFoundException ex) {
            Log.logException(ex);
        }
        return groupAcademicAuxiliar;
        
    }  
    
}
