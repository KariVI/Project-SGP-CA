
package businessLogic;

import dataaccess.Connector;
import domain.Member;
import domain.Prerequisite;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import log.BusinessException;
import log.Log;

public class PrerequisiteDAO implements IPrerequisiteDAO{


    public int searchId(Prerequisite prerequisite, int idMeeting) throws BusinessException {
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
            }else{
                throw new BusinessException("Prerequisite not found");
            }
                connectorDataBase.disconnect();
        }catch(SQLException sqlException) {
            throw new BusinessException("DataBase connection failed ", sqlException);
        } catch (ClassNotFoundException ex) {
            Log.logException(ex);
        }
        return idAuxiliar;
    }
    

    public boolean savedSucessfulPrerequisites(Prerequisite prerequisite, int idMeeting) throws BusinessException {
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
                throw new BusinessException("DataBase connection failed ", sqlException);
            } catch (ClassNotFoundException ex) {
               Log.logException(ex);
            }
        return saveSuccess;
    }

   

 
     public boolean updatedSucessfulPrerequisite(int id, Prerequisite prerequisite) throws BusinessException{
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
            throw new BusinessException("DataBase connection failed ", sqlException);
        } catch (ClassNotFoundException ex) {
            Log.logException(ex);
        }
        return updateSuccess;
     }
 

    public int getId(Prerequisite prerequisite, int idMeeting) throws BusinessException {
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
            throw new BusinessException("DataBase connection failed ", sqlException);
        } catch (ClassNotFoundException ex) {
            Log.logException(ex);
        }
        return idAuxiliar;
   
    }

    public boolean deletedSucessful(int idPrerequisite) throws BusinessException{
        boolean deleteSucess=false;
        try{
            Connector connectorDataBase=new Connector();
            Connection connectionDataBase = connectorDataBase.getConnection();
            String selectIdPrerequisite = "DELETE FROM Prerequisito where idPrerequisito=?";
     
            PreparedStatement preparedStatement = connectionDataBase.prepareStatement(selectIdPrerequisite);
            preparedStatement.setInt(1, idPrerequisite);
            preparedStatement.executeUpdate();
            deleteSucess=true;
            connectorDataBase.disconnect();         
        }catch(SQLException sqlException) {
            throw new BusinessException("DataBase connection failed ", sqlException);
        } catch (ClassNotFoundException ex) {
            Log.logException(ex);
        }
        return deleteSucess;
    }
    
     public ArrayList<Prerequisite> getPrerequisites(int idMeeting) throws BusinessException{
        ArrayList<Prerequisite> prerequisiteList = new ArrayList<Prerequisite>();
        try{
            Connector connectorDataBase = new Connector();
            Connection connectionDataBase = connectorDataBase.getConnection();
            String queryPrerequisite="SELECT * FROM Prerequisito where idReunion=?";

               PreparedStatement preparedStatement;
               preparedStatement = connectionDataBase.prepareStatement(queryPrerequisite);
               preparedStatement.setInt(1, idMeeting);
               ResultSet resultSet;
               resultSet = preparedStatement.executeQuery();
               while(resultSet.next()){
                    int key=resultSet.getInt(1);
                    
                    String description = resultSet.getString("descripcion");
                    String professionalLicense= resultSet.getString("cedula");
                    Prerequisite prerequisiteAuxiliar = new Prerequisite(description);
                    prerequisiteAuxiliar.setKey(key);
                    MemberDAO memberDAO = new MemberDAO();
                    Member memberAuxiliar = memberDAO.getMemberByLicense(professionalLicense);
                    prerequisiteAuxiliar.setMandated(memberAuxiliar);
                    prerequisiteList.add(prerequisiteAuxiliar);
                    
                }
                connectorDataBase.disconnect();
            }catch(SQLException sqlException) {
                   throw new BusinessException("Database failed ", sqlException);         
            }catch(ClassNotFoundException ex) {
                        Log.logException(ex);
            }
            return prerequisiteList;
        }
     
    /* public boolean deletedSucessfulPrerequisites(ArrayList<Prerequisite> prerequisites, int idMeeting ) throws BusinessException{ 
            boolean deleteSucess=false;
            try{
            Connector connectorDataBase=new Connector();
            Connection connectionDataBase = connectorDataBase.getConnection();
            String selectIdPrerequisite = "DELETE FROM Prerequisito where idReunion=?";
     
            PreparedStatement preparedStatement = connectionDataBase.prepareStatement(selectIdPrerequisite);
            int i=0;
            while(i< prerequisites.size()){
                preparedStatement.setInt(1, idMeeting);
                preparedStatement.executeUpdate();
                deleteSucess=true;
                i++;
            }
            connectorDataBase.disconnect();         
        }catch(SQLException sqlException) {
            throw new BusinessException("DataBase connection failed ", sqlException);
        } catch (ClassNotFoundException ex) {
            Log.logException(ex);
        }
        return deleteSucess;
     }*/

}
