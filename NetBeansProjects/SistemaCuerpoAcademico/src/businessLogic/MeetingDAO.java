
package businessLogic;

import dataaccess.Connector;
import domain.Meeting;
import domain.Member;
import domain.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import log.BusinessException;
import log.Log;

public class MeetingDAO implements IMeetingDAO{

    public boolean savedSucessful(Meeting meeting) throws BusinessException  {    
        boolean saveSuccess=false;
            try {
                Connector connectorDataBase=new Connector();
                Connection connectionDataBase = connectorDataBase.getConnection();
                String insertMeeting = "INSERT INTO Reunion(asunto,hora, fecha, estado) VALUES (?,?,?,?)";
            
                PreparedStatement preparedStatement = connectionDataBase.prepareStatement(insertMeeting);
                
                preparedStatement.setString(1, meeting.getSubject());
                preparedStatement.setString(2, meeting.getHourStart());
                preparedStatement.setString(3, meeting.getDate());
                preparedStatement.setString(4, "Registrada");
                
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
    
    public boolean findMeetingById(int id) {
        boolean value=true;
       if(id== 0){
           value=false;
       }
       return value;
    }

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
               
                meetingAuxiliar=new Meeting(id,subject,date,hourStart, state);
            }
                connectorDataBase.disconnect();
        }catch(SQLException sqlException) {
            throw new BusinessException("DataBase connection failed ", sqlException);
        } catch (ClassNotFoundException ex) {
            Log.logException(ex);
        }
        return meetingAuxiliar;
        
    }

    public ArrayList<Meeting>  getMeetings(){
        ArrayList<Meeting> meetingList = new ArrayList<Meeting>();
        try{
            Connector connectorDataBase = new Connector();
            Connection connectionDataBase = connectorDataBase.getConnection();
            String queryMeeting="SELECT * FROM Reunion";

               PreparedStatement preparedStatement;
               preparedStatement = connectionDataBase.prepareStatement(queryMeeting);
               ResultSet resultSet;
               resultSet = preparedStatement.executeQuery();
               while(resultSet.next()){
                    int keyMeeting=resultSet.getInt(1);
                    String subject = resultSet.getString("asunto");
                    String hourStart = resultSet.getString("hora");
                    String date = resultSet.getString("fecha");
                    String state= resultSet.getString("estado");
                    Meeting meetingAuxiliar = new Meeting(keyMeeting,subject, hourStart, date, state);
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

    @Override
    public boolean deletedSucessfulAssistants(Meeting meeting) throws BusinessException {
        boolean deleteSucess=false;
        int idMeeting=meeting.getKey();
        ArrayList<Member> assistants= meeting.getAssistants();
        try{
            Connector connectorDataBase=new Connector();
            Connection connectionDataBase = connectorDataBase.getConnection();
            String delete = "delete from ParticipaReunion where cedula=? and idReunion=?";
     
            PreparedStatement preparedStatement = connectionDataBase.prepareStatement(delete);
            int i=0;
            while(i< assistants.size()){
               preparedStatement.setString(1, assistants.get(i).getProfessionalLicense());
               preparedStatement.setInt(2, idMeeting);
               preparedStatement.executeUpdate();
               i++;
            }
            deleteSucess=true;
            connectorDataBase.disconnect();         
        }catch(SQLException sqlException) {
            throw new BusinessException("DataBase connection failed ", sqlException);
        } catch (ClassNotFoundException ex) {
            Log.logException(ex);
        }
        return deleteSucess;
    }

    @Override
    public ArrayList<Member> getAssistants(int idMeeting) throws BusinessException {
         ArrayList<Member> assistants= new ArrayList<Member> ();
        MemberDAO memberDAO= new MemberDAO();
        try{
            Connector connectorDataBase = new Connector();
            Connection connectionDataBase = connectorDataBase.getConnection();
            String query="SELECT cedula FROM ParticipaReunion where idReunion=?";

               PreparedStatement preparedStatement = connectionDataBase.prepareStatement(query);
               preparedStatement.setInt(1, idMeeting);
               ResultSet resultSet;
               resultSet = preparedStatement.executeQuery();
               while(resultSet.next()){
                    String professionalLicense= resultSet.getString("cedula");
                    Member member = memberDAO.getMemberByLicense(professionalLicense);
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


