
package businessLogic;

import dataaccess.Connector;
import domain.GroupAcademic;
import domain.LGAC;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import log.BusinessException;
import log.Log;

public class GroupAcademicDAO implements IGroupAcademicDAO {

    @Override
    public boolean savedSucessful(GroupAcademic groupAcademic) throws BusinessException {
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
                connectorDataBase.disconnect();
                value=true;
            } catch (SQLException sqlException) {
                throw new BusinessException("DataBase connection failed ", sqlException);
            } catch (ClassNotFoundException ex) {
               Log.logException(ex);
            }
        return value;
    }
 
@Override
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
            }else{  
                throw new BusinessException("GroupAcademic not found");
            }
                connectorDataBase.disconnect();
        }catch(SQLException sqlException) {
            throw new BusinessException("DataBase connection failed ", sqlException);
        } catch (ClassNotFoundException ex) {
            Log.logException(ex);
        }
        return groupAcademicAuxiliar;
        
    }  

    @Override
    public boolean addedLGACSucessful(GroupAcademic groupAcademic, LGAC lgac) throws BusinessException {
        boolean addSucess=false;
        Connector connectorDataBase=new Connector();
        try {
            Connection connectionDataBase = connectorDataBase.getConnection();
            String addLGAC="INSERT INTO CuerpoLGAC (claveCuerpoAcademico, nombreLGAC) values (?,?)";
            PreparedStatement preparedStatement= connectionDataBase.prepareStatement(addLGAC);
            preparedStatement.setString(1, groupAcademic.getKey());
            preparedStatement.setString(2, lgac.getName());
            preparedStatement.executeUpdate();
            connectorDataBase.disconnect();
            addSucess=true;       
        } catch (ClassNotFoundException ex) {
            Log.logException(ex);
        } catch (SQLException sqlException) {
             throw new BusinessException("DataBase connection failed ", sqlException);
        }
        return addSucess;
    }

 
    public boolean updatedSucessful(String lastKey,GroupAcademic groupAcademic) throws BusinessException {
        boolean updateSucess=false;
        Connector connectorDataBase=new Connector();
        try {
            Connection connectionDataBase = connectorDataBase.getConnection();
            String updateLgac = "UPDATE CuerpoAcademico set nombre=?, objetivo=?, mision=?, vision=?, gradoConsolidacion=? where clave=?";
            
            PreparedStatement preparedStatement = connectionDataBase.prepareStatement(updateLgac);
            preparedStatement.setString(1, groupAcademic.getName());
            preparedStatement.setString(2, groupAcademic.getObjetive());
            preparedStatement.setString(3, groupAcademic.getMission());
            preparedStatement.setString(4, groupAcademic.getVision());
            preparedStatement.setString(5, groupAcademic.getConsolidationGrade());
            preparedStatement.setString(6, lastKey);
                
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
    
    public ArrayList<LGAC> getLGACs(String keyGroupAcademic) throws BusinessException{  
        ArrayList<LGAC> lgacList = new ArrayList<LGAC>();
        Connector connectorDataBase=new Connector();
        try {
            Connection connectionDataBase = connectorDataBase.getConnection();
            String query="Select nombreLGAC from CuerpoLGAC where claveCuerpoAcademico= ?";
            PreparedStatement preparedStatement= connectionDataBase.prepareStatement(query);
            preparedStatement.setString(1, keyGroupAcademic);
            ResultSet resultSet ;
            resultSet=preparedStatement.executeQuery();
            
            while(resultSet.next()){    
                String lgacName= resultSet.getString(1);              
                LGACDAO lgacDAO = new LGACDAO();               
                LGAC lgac = lgacDAO.getLgacByName(lgacName);
                lgacList.add(lgac);
            }
            
            connectorDataBase.disconnect();
        } catch (ClassNotFoundException ex) {
            Log.logException(ex);
        } catch (SQLException sqlException) {
             throw new BusinessException("DataBase connection failed ", sqlException);
        }
      return lgacList;
    }

   public boolean deletedLGACSuccesful(String keyGroupAcademic, LGAC lgac) throws BusinessException {
       boolean value=false;
       Connector connectorDataBase=new Connector();
        try {
            Connection connectionDataBase = connectorDataBase.getConnection();
            String query="DELETE FROM CuerpoLGAC where claveCuerpoAcademico= ? and nombreLGAC=?";
            PreparedStatement preparedStatement= connectionDataBase.prepareStatement(query);
            preparedStatement.setString(1, keyGroupAcademic);
            preparedStatement.setString(2,lgac.getName());
            preparedStatement.executeUpdate();            
            connectorDataBase.disconnect();
            value=true;
        } catch (ClassNotFoundException ex) {
            Log.logException(ex);
        } catch (SQLException sqlException) {
             throw new BusinessException("DataBase connection failed ", sqlException);
        }
       return value;
   }
   
    
}
