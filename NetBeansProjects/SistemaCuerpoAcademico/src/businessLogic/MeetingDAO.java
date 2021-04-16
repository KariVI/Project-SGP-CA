
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

    public boolean save(Meeting meeting) throws BusinessException  {    
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

    public int searchId(Meeting meeting) throws BusinessException {
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
                     ArrayList<Meeting> meetingList = new ArrayList<>();
                     try{
                        Connector connectorDataBase = new Connector();
                        Connection connectionDataBase = connectorDataBase.getConnection();
                        String queryMeeting="SELECT * FROM Reunion";
                        try{

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
                        }
                    }catch(ClassNotFoundException ex) {
                        Log.logException(ex);
                    }
                    return meetingList;
        }

     public boolean addAssistant(int idMeeting, String enrollment, String role) throws BusinessException  {
         boolean addAssistantSuccess=false;
         try {
                Connector connectorDataBase=new Connector();
                Connection connectionDataBase = connectorDataBase.getConnection();
                String insertMeeting = "INSERT INTO ParticipaReunion(idReunion,cedula, rol) VALUES (?,?,?)";
            
                PreparedStatement preparedStatement = connectionDataBase.prepareStatement(insertMeeting);
                
                preparedStatement.setInt(1, idMeeting);
                preparedStatement.setString(2, enrollment);
                preparedStatement.setString(3, role);
                addAssistantSuccess=true;
                
                preparedStatement.executeUpdate();
                connectorDataBase.disconnect();
            } catch (SQLException sqlException) {
                throw new BusinessException("DataBase connection failed ", sqlException);
            } catch (ClassNotFoundException ex) {
                Log.logException(ex);
            }
        return addAssistantSuccess;
    }
   
     
     
   public Member getAssistant(int idMeeting, String profesionalLicense)throws BusinessException  {
        Member assistant=null;
      try{
        Connector connectorDataBase = new Connector();
        Connection connectionDataBase = connectorDataBase.getConnection();
        String queryNameAssistant="SELECT Miembro.nombre, Miembro.cedula FROM ParticipaReunion, Miembro where ParticipaReunion.cedula=Miembro.cedula and idReunion=? and ParticipaReunion.cedula=?; ";
        try{
            PreparedStatement preparedStatement;
            preparedStatement = connectionDataBase.prepareStatement(queryNameAssistant);
            preparedStatement.setInt(1, idMeeting);
            preparedStatement.setString(2, profesionalLicense);
            ResultSet resultSet;

            resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                String name = resultSet.getString(1);
                String professionalLicense=resultSet.getString(2);
                assistant=new Member(professionalLicense, name);
            }

            connectorDataBase.disconnect();

        }catch(SQLException sqlException) {
           throw new BusinessException("DataBase connection failed ", sqlException);
        }
        }catch(ClassNotFoundException ex) {
           Log.logException(ex);
        }
        return assistant;
    }

    public boolean update(Meeting meeting) throws BusinessException{
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
    

}


