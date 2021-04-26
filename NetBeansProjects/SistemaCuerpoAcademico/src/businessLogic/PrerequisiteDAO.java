
package businessLogic;

import dataaccess.Connector;
import domain.Member;
import domain.Prerequisite;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PrerequisiteDAO implements IPrerequisiteDAO{


    public int searchId(Prerequisite prerequisite, int idMeeting) {
        Integer idAuxiliar=0;
        try{
            Connector connectorDataBase=new Connector();
            Connection connectionDataBase = connectorDataBase.getConnection();
            ResultSet resultSet;
            String selectIdPrerequisite = "SELECT idPrerequisito from  Prerequisito where descripcion=? and idReunion=? ";
     
            PreparedStatement preparedStatement = connectionDataBase.prepareStatement(selectIdPrerequisite);
            preparedStatement.setString(1, prerequisite.getDescription());          
            preparedStatement.setInt(2, idMeeting);     
            
            resultSet=preparedStatement.executeQuery();
            if(resultSet.next()){
                idAuxiliar=Integer.parseInt(resultSet.getString("idPrerequisito"));
            }
                connectorDataBase.disconnect();
        }catch(SQLException sqlException) {
            throw new IllegalStateException("DataBase connection failed ", sqlException);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MeetingDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return idAuxiliar;
    }
    

    
    public boolean savePrerequisites(Prerequisite prerequisite, int idMeeting) {
        String professionalLicense= prerequisite.getMandated().getProfessionalLicense();
        boolean saveSuccess=false;
        try {
                Connector connectorDataBase=new Connector();
                Connection connectionDataBase = connectorDataBase.getConnection();
                String insertPrerequisite = "INSERT INTO Prerequisito(descripcion, cedula, idReunion) VALUES (?,?, ?)";
            
                PreparedStatement preparedStatement = connectionDataBase.prepareStatement(insertPrerequisite);
                
                preparedStatement.setString(1, prerequisite.getDescription());
                 preparedStatement.setString(2, professionalLicense);
                preparedStatement.setInt(3, idMeeting);
                
                preparedStatement.executeUpdate();
                connectorDataBase.disconnect();
                saveSuccess=true;
            } catch (SQLException sqlException) {
                throw new IllegalStateException("DataBase connection failed ", sqlException);
            } catch (ClassNotFoundException ex) {
            Logger.getLogger(MeetingDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return saveSuccess;
    }




    public String getPrerequisiteDescription(int id, int idMeeting) {
        String description=" ";
        try{
            Connector connectorDataBase=new Connector();
            Connection connectionDataBase = connectorDataBase.getConnection();
            ResultSet resultSet;
            String selectIdPrerequisite = "SELECT descripcion from  Prerequisito where idPrerequisito=? and idReunion=? ";
     
            PreparedStatement preparedStatement = connectionDataBase.prepareStatement(selectIdPrerequisite);
            preparedStatement.setInt(1, id);          
            preparedStatement.setInt(2, idMeeting);
            
            
            resultSet=preparedStatement.executeQuery();
            if(resultSet.next()){
                description=resultSet.getString("descripcion");
            }
                connectorDataBase.disconnect();
        }catch(SQLException sqlException) {
            throw new IllegalStateException("DataBase connection failed ", sqlException);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MeetingDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return description;
    }

 
     public boolean updatePrerequisite(int id, Prerequisite prerequisite){
         boolean updateSuccess=false;
         String professionalLicense= prerequisite.getMandated().getProfessionalLicense();
       
        try{
            Connector connectorDataBase=new Connector();
            Connection connectionDataBase = connectorDataBase.getConnection();
            String updatePrerequisite= "UPDATE Prerequisito set descripcion=?, cedula=? where idPrerequisito=? ";
     
            PreparedStatement preparedStatement = connectionDataBase.prepareStatement(updatePrerequisite);
            preparedStatement.setString(1, prerequisite.getDescription());          
            preparedStatement.setString(2,professionalLicense );
            preparedStatement.setInt(3, id);
            
            preparedStatement.executeUpdate();
            updateSuccess=true;
            connectorDataBase.disconnect();
        }catch(SQLException sqlException) {
            throw new IllegalStateException("DataBase connection failed ", sqlException);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MeetingDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return updateSuccess;
     }
 

    public int getId(Prerequisite prerequisite, int idMeeting) {
        Integer idAuxiliar=0;
        try{
            Connector connectorDataBase=new Connector();
            Connection connectionDataBase = connectorDataBase.getConnection();
            ResultSet resultSet;
            String selectIdPrerequisite = "SELECT idPrerequisito from  Prerequisito where descripcion=? and idReunion=? ";
     
            PreparedStatement preparedStatement = connectionDataBase.prepareStatement(selectIdPrerequisite);
            preparedStatement.setString(1, prerequisite.getDescription());          
            preparedStatement.setInt(2, idMeeting);
            
            
            resultSet=preparedStatement.executeQuery();
            if(resultSet.next()){
                idAuxiliar=Integer.parseInt(resultSet.getString("idPrerequisito"));
            }
                connectorDataBase.disconnect();
        }catch(SQLException sqlException) {
            throw new IllegalStateException("DataBase connection failed ", sqlException);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MeetingDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return idAuxiliar;
   
    }

}
