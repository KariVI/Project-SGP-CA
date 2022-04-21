/*
        *@author Karina Valdes

    */
package businessLogic;

import dataaccess.Connector;
import domain.Meeting;
import domain.Member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import log.BusinessException;
import log.Log;

public class MeetingDAO implements IMeetingDAO{
    
     /*
        *@params meeting Reunión a guardar 
        *@return Si la reunión pudo ser guardada (true) o no (false) en la base de datos 
        *@throws Se cacho una excepción de tipo SQLException
    */

    public boolean savedSucessful(Meeting meeting) throws BusinessException  {    
        boolean saveSuccess=false;
            try {
                Connector connectorDataBase=new Connector();
                Connection connectionDataBase = connectorDataBase.getConnection();
                String insertMeeting = "INSERT INTO Reunion(asunto,hora, fecha, estado,clave_CA) VALUES (?,?,?,?,?)";
            
                PreparedStatement preparedStatement = connectionDataBase.prepareStatement(insertMeeting);
                
                preparedStatement.setString(1, meeting.getSubject());
                preparedStatement.setString(2, meeting.getHourStart());
                preparedStatement.setString(3, meeting.getDate());
                preparedStatement.setString(4, "Registrada");
                preparedStatement.setString(5, meeting.getKeyGroupAcademic() );
                
                preparedStatement.executeUpdate();
                saveSuccess=true;
                connectorDataBase.disconnect();
            } catch (SQLException sqlException) {
                throw new BusinessException("DataBase connection failed ", sqlException);
            } catch (ClassNotFoundException ex) {
                Log.logException(ex);
            }
        return saveSuccess;
        
    } 
    
    /*
        *@params meeting Reunión de la cual se busca su id
        *@return La id de la reunión
        *@throws Se cacho una excepción de tipo SQLException o si no es localizada la reunión manda una excepción de tipo BusinessException
    */

    public int getId(Meeting meeting) throws BusinessException {
        Integer idAuxiliar=0;
        try{
            Connector connectorDataBase=new Connector();
            Connection connectionDataBase = connectorDataBase.getConnection();
            ResultSet resultSet;
            String selectIdMeeting = "SELECT idReunion from  Reunion where asunto=? and hora=? and fecha=? ";
     
            PreparedStatement preparedStatement = connectionDataBase.prepareStatement(selectIdMeeting);
            preparedStatement.setString(1, meeting.getSubject());          
            preparedStatement.setString(2, meeting.getHourStart());
            preparedStatement.setString(3, meeting.getDate());
            
            resultSet=preparedStatement.executeQuery();
            if(resultSet.next()){
                idAuxiliar=Integer.parseInt(resultSet.getString("idReunion"));
            }else{
                throw new BusinessException("Meeting not found");
            }
                connectorDataBase.disconnect();
        }catch(SQLException sqlException) {
            throw new BusinessException("DataBase connection failed ", sqlException);
        } catch (ClassNotFoundException ex) {
            Log.logException(ex);
        }
        return idAuxiliar;
    }
    
  /*
        *@params id Identificador de la reunión que va a recuperarse 
        *@return La reunión recuperada  de acuerdo al id
        *@throws Se cacho una excepción de tipo SQLException o si no es localizada la reunión manda una excepción de tipo BusinessException
    */
    public Meeting getMeetingById(int idMeeting) throws BusinessException  {
        Meeting meetingAuxiliar = null;
        try{
            Connector connectorDataBase=new Connector();
            Connection connectionDataBase = connectorDataBase.getConnection();
            ResultSet resultSet;
            String selectIdMeeting = "SELECT * from  Reunion where idReunion=?";
     
            PreparedStatement preparedStatement = connectionDataBase.prepareStatement(selectIdMeeting);
            preparedStatement.setInt(1, idMeeting);        
            resultSet=preparedStatement.executeQuery();
           
            if(resultSet.next()){
                int id= resultSet.getInt(1);
                String subject= resultSet.getString("asunto");
                String hourStart= resultSet.getString("hora");
                String date= resultSet.getString("fecha");
                String state=resultSet.getString("estado");
                String keyGroupAcademic= resultSet.getString("clave_CA");
                meetingAuxiliar=new Meeting(id,subject,date,hourStart, state);
                meetingAuxiliar.setKeyGroupAcademic(keyGroupAcademic);
            }else{  
                throw new BusinessException("Meeting not found ");
            }
                connectorDataBase.disconnect();
        }catch(SQLException sqlException) {
            throw new BusinessException("DataBase connection failed ", sqlException);
        } catch (ClassNotFoundException ex) {
            Log.logException(ex);
        }
        return meetingAuxiliar;
        
    }

    /*
        *@params keyGroupAcademic Clave del cuerpo académico para filtrar las reuniones que le corresponden
        *@params professionalLicense Cedula para identificar las reuniones a las que asiste
        *@return Reuniones relacionadas a un cuerpo académico
        *@throws Se cacho una excepción de tipo SQLException
    */
    public ArrayList<Meeting>  getMeetings(String keyGroupAcademic, String professionalLicense ){
        ArrayList<Meeting> meetingList = new ArrayList<Meeting>();
        try{
            Connector connectorDataBase = new Connector();
            Connection connectionDataBase = connectorDataBase.getConnection();
            String queryMeeting="select Reunion.idReunion, asunto,hora,fecha,estado from Reunion INNER JOIN ParticipaReunion ON Reunion.idReunion= ParticipaReunion.idReunion"
                    + " where cedula=? and clave_CA=? ;";

               PreparedStatement preparedStatement;
               preparedStatement = connectionDataBase.prepareStatement(queryMeeting);
               ResultSet resultSet;
               preparedStatement.setString(1,professionalLicense);
               preparedStatement.setString(2, keyGroupAcademic);
               resultSet = preparedStatement.executeQuery();
               while(resultSet.next()){
                    int keyMeeting=resultSet.getInt(1);
                    String subject = resultSet.getString("asunto");
                    String hourStart = resultSet.getString("hora");
                    String date = resultSet.getString("fecha");
                    String state= resultSet.getString("estado");
                    
                    Meeting meetingAuxiliar = new Meeting(keyMeeting,subject,date, hourStart, state);
                    meetingAuxiliar.setKeyGroupAcademic(keyGroupAcademic);
                    
                    meetingList.add(meetingAuxiliar);
                }
                connectorDataBase.disconnect();
            }catch(SQLException sqlException) {
                   throw new IllegalStateException("Parameter index ", sqlException);         
            }catch(ClassNotFoundException ex) {
                        Log.logException(ex);
            }
            return meetingList;
        }

      /*
        *@params meeting La nueva información de la reunión que se va actualizar  
        *@return Si la reunión pudo ser actualizada (true) o no (false) en la base de datos 
        *@throws Se cacho una excepción de tipo SQLException 
    */
    public boolean updatedSucessful(Meeting meeting) throws BusinessException{
      boolean updateSuccess=false;
            try {
                Connector connectorDataBase=new Connector();
                Connection connectionDataBase = connectorDataBase.getConnection();
                String updateMeeting = "UPDATE Reunion set asunto=? ,hora=?, fecha=? where idReunion=?";
            
                PreparedStatement preparedStatement = connectionDataBase.prepareStatement(updateMeeting);
                
                preparedStatement.setString(1, meeting.getSubject());
                preparedStatement.setString(2, meeting.getHourStart());
                preparedStatement.setString(3, meeting.getDate());
                preparedStatement.setInt(4, meeting.getKey());
                
                preparedStatement.executeUpdate();
                updateSuccess=true;
                connectorDataBase.disconnect();
            } catch (SQLException sqlException) {
                throw new BusinessException("DataBase connection failed ", sqlException);
            } catch (ClassNotFoundException ex) {
                Log.logException(ex);
            }
        return updateSuccess;  
    }    
    
     /*
        *@params meeting La reunión a la cual se le modificará el estado 
        *@return Si el estado de la reunión pudo ser actualizada (true) o no (false) en la base de datos 
        *@throws Se cacho una excepción de tipo SQLException 
    */

    @Override
    public boolean changedStateSucessful(Meeting meeting) throws BusinessException {
          boolean updateSuccess=false;
            try {
                Connector connectorDataBase=new Connector();
                Connection connectionDataBase = connectorDataBase.getConnection();
                String updateMeeting = "UPDATE Reunion set estado=?  where idReunion=?";
            
                PreparedStatement preparedStatement = connectionDataBase.prepareStatement(updateMeeting);
                
                preparedStatement.setString(1, meeting.getState());
                preparedStatement.setInt(2, meeting.getKey());
                
                preparedStatement.executeUpdate();
                updateSuccess=true;
                connectorDataBase.disconnect();
            } catch (SQLException sqlException) {
                throw new BusinessException("DataBase connection failed ", sqlException);
            } catch (ClassNotFoundException ex) {
                Log.logException(ex);
            }
        return updateSuccess;    
    }
    
     /*
        *@params meeting Reunión a la cual se le añaden los asistentes
        *@return Si la reunión pudo ser guardar sus asistentes (true) o no (false) en la base de datos 
        *@throws Se cacho una excepción de tipo SQLException 
        *@see Meeting
    */
    
     public boolean addedSucessfulAssistants(Meeting meeting) throws BusinessException {
        boolean addAssistantSuccess=false;
        int idMeeting=meeting.getKey();
        ArrayList<Member> assistants= meeting.getAssistants();
         try {
                Connector connectorDataBase=new Connector();
                Connection connectionDataBase = connectorDataBase.getConnection();
                String insertAssistant = "INSERT INTO ParticipaReunion(idReunion,cedula, rol) VALUES (?,?,?)";
                int i=0;
                while(i< assistants.size()){
                    PreparedStatement preparedStatement = connectionDataBase.prepareStatement(insertAssistant);
                    preparedStatement.setInt(1, idMeeting);
                    preparedStatement.setString(2, assistants.get(i).getProfessionalLicense());
                    preparedStatement.setString(3, assistants.get(i).getRole());
                    preparedStatement.executeUpdate();
                    i++;
                } 
                addAssistantSuccess=true; 
               connectorDataBase.disconnect();
            } catch (SQLException sqlException) {
                throw new BusinessException("DataBase connection failed ", sqlException);
            } catch (ClassNotFoundException ex) {
                Log.logException(ex);
            }
        return addAssistantSuccess;
    }
     
   /*
        *@params meeting Reunión a la cual se le eliminan los asistentes
        *@return Si fue posible eliminar los asistentes de una reunión (true) o no (false) en la base de datos 
        *@throws Se cacho una excepción de tipo SQLException 
        *@see Meeting
    */

    @Override
    public boolean deletedSucessfulAssistants(Meeting meeting, Member assistant) throws BusinessException {
        boolean deleteSucess=false;
        int idMeeting=meeting.getKey();
        ArrayList<Member> assistants= meeting.getAssistants();
        try{
            Connector connectorDataBase=new Connector();
            Connection connectionDataBase = connectorDataBase.getConnection();
            String delete = "delete from participareunion where cedula=? and idReunion=? and rol=?";
     
            PreparedStatement preparedStatement = connectionDataBase.prepareStatement(delete);
               preparedStatement.setString(1, assistant.getProfessionalLicense());
               preparedStatement.setInt(2, idMeeting);
               preparedStatement.setString(3, assistant.getRole());
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
    
    /*
        *@params idMeeting Identificador de la reunión del cual se buscan sus asistentes
        *@return Listado de asistentes de una reunión
        *@throws Se cacho una excepción de tipo SQLException
    */

    @Override
    public ArrayList<Member> getAssistants(int idMeeting) throws BusinessException {
         ArrayList<Member> assistants= new ArrayList<Member> ();
        MemberDAO memberDAO= new MemberDAO();
        try{
            Connector connectorDataBase = new Connector();
            Connection connectionDataBase = connectorDataBase.getConnection();
            String query="SELECT cedula,rol FROM ParticipaReunion where idReunion=?";

               PreparedStatement preparedStatement = connectionDataBase.prepareStatement(query);
               preparedStatement.setInt(1, idMeeting);
               ResultSet resultSet;
               resultSet = preparedStatement.executeQuery();
               while(resultSet.next()){
                    String professionalLicense= resultSet.getString("cedula");
                    String role =resultSet.getString("rol");
                    Member member = memberDAO.getMemberByLicense(professionalLicense);
                    member.setRole(role);
                    assistants.add(member);
                    
                }
                connectorDataBase.disconnect();
            }catch(SQLException sqlException) {
                   throw new BusinessException("Database failed ", sqlException);         
            }catch(ClassNotFoundException ex) {
                        Log.logException(ex);
            }

        return assistants;
    }

}


