
package businessLogic;

import dataaccess.Connector;
import domain.Meeting;
import domain.Member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MeetingDAO implements IMeetingDAO{
    private Meeting newMeeting;


    public void setMeeting( Meeting meeting){  
        newMeeting=meeting;
    }
    
    public Meeting getMeeting(){
        return newMeeting;
    }
    
    public void save(Meeting meeting) {      
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
                connectorDataBase.disconnect();
            } catch (SQLException sqlException) {
                throw new IllegalStateException("DataBase connection failed ", sqlException);
            } catch (ClassNotFoundException ex) {
            Logger.getLogger(MeetingDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    } 

    public int searchId(Meeting meeting) {
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
            throw new IllegalStateException("DataBase connection failed ", sqlException);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MeetingDAO.class.getName()).log(Level.SEVERE, null, ex);
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

    public Meeting getMeetingById(int idMeeting) {
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
            throw new IllegalStateException("DataBase connection failed ", sqlException);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MeetingDAO.class.getName()).log(Level.SEVERE, null, ex);
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
                        Logger.getLogger(MeetingDAO.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    return meetingList;
        }

    @Override
    public void addAssistant(Member member) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}


