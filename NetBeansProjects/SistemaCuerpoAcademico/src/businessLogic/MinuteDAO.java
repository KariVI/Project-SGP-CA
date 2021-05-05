package businessLogic;

import dataaccess.Connector;
import domain.Member;
import domain.Minute;
import domain.MinuteComment;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import log.Log;
import log.BusinessException;

/**
 *
 * @author Mariana
 */
public class MinuteDAO implements IMinuteDAO {

    @Override
    public boolean saveMinute(Minute minute)throws BusinessException {
                    boolean saveSuccess = false;
                    try{
                        Connector connectorDataBase = new Connector();
                        Connection connectionDataBase = connectorDataBase.getConnection();
                        try{
                            
                            PreparedStatement insertMinuteStatment;
                            insertMinuteStatment = connectionDataBase.prepareStatement("INSERT INTO Minuta(nota, estado, pendiente, idReunion) VALUES(?,?,?,?) ");
                            insertMinuteStatment.setString(1, minute.getNote());
                            insertMinuteStatment.setString(2,  minute.getSate());
                            insertMinuteStatment.setString(3, minute.getDue());
                            insertMinuteStatment.setInt(4, minute.getIdMeeting());
                            insertMinuteStatment.executeUpdate();
                            
                            connectorDataBase.disconnect();
                             saveSuccess = true;
                        }catch(SQLException sqlException) {
                            throw new BusinessException("Parameter index ", sqlException);
                        }
                    }catch(ClassNotFoundException ex) {
                        Log.logException(ex);
                    }
                    return saveSuccess;
    }

    @Override
    public void approveMinute(int idMinute, String professionalLicense)throws BusinessException{
                    try{
                        Connector connectorDataBase = new Connector();
                        Connection connectionDataBase = connectorDataBase.getConnection();
                        try{
                            
                            PreparedStatement insertMinuteStatment;
                            insertMinuteStatment = connectionDataBase.prepareStatement("INSERT INTO ValidarMinuta(idMinuta, cedula) VALUES(?,?) ");
                            insertMinuteStatment.setInt(1, idMinute);
                            insertMinuteStatment.setString(2, professionalLicense);
                  
                            insertMinuteStatment.executeUpdate();
                            
                            connectorDataBase.disconnect();
                        }catch(SQLException sqlException) {
                            throw new BusinessException("Parameter index ", sqlException);
                        }
                    }catch(ClassNotFoundException ex) {
                        Log.logException(ex);
                    }
    }

    @Override
    public void disapproveMinute(int idMinute, String professionalLicense, String comments)throws BusinessException {
                    try{
                        Connector connectorDataBase = new Connector();
                        Connection connectionDataBase = connectorDataBase.getConnection();
                        try{
                            
                            PreparedStatement insertMinuteStatment;
                            insertMinuteStatment = connectionDataBase.prepareStatement("INSERT INTO ValidarMinuta(idMinuta, cedula,comentario,estado) VALUES(?,?,?,?) ");
                            insertMinuteStatment.setInt(1, idMinute);
                            insertMinuteStatment.setString(2, professionalLicense);
                            insertMinuteStatment.setString(3, comments);
                            insertMinuteStatment.setString(4, "Pendiente");
                  
                            insertMinuteStatment.executeUpdate();
                            
                            connectorDataBase.disconnect();
                        }catch(SQLException sqlException) {
                            throw new BusinessException("Parameter index ", sqlException);
                        }
                    }catch(ClassNotFoundException ex) {
                        Log.logException(ex);
                    }
    }
    @Override
    public ArrayList<Minute>  getMinutes() throws BusinessException{
                     ArrayList<Minute> minuteList = new ArrayList<>();
                     try{
                        Connector connectorDataBase = new Connector();
                        Connection connectionDataBase = connectorDataBase.getConnection();
                        try{
                            
                            PreparedStatement getMinutesStatment;
                            getMinutesStatment = connectionDataBase.prepareStatement("SELECT * FROM Minuta");
                            ResultSet minuteResultSet;
                            
                            minuteResultSet = getMinutesStatment.executeQuery();
                            
                            while(minuteResultSet.next()){
                                int idMinute = minuteResultSet.getInt("idMinuta");
                                String note = minuteResultSet.getString("nota");
                                String due = minuteResultSet.getString("pendiente");
                                String state = minuteResultSet.getString("estado");
                                int idMeeting = minuteResultSet.getInt("idReunion");
                                Minute minute = new Minute(idMinute, note, state, due,idMeeting);
                                minuteList.add(minute);
                            }
                              
                            connectorDataBase.disconnect();
                           
                        }catch(SQLException sqlException) {
                            throw new BusinessException("Parameter index ", sqlException);
                        }
                    }catch(ClassNotFoundException ex) {
                        Log.logException(ex);
                    }
                    return minuteList;  
    }
    
    @Override
    public ArrayList<MinuteComment>  getMinutesComments(int idMinute) throws BusinessException{
                     ArrayList<MinuteComment> commentList = new ArrayList<>();
                     try{
                        Connector connectorDataBase = new Connector();
                        Connection connectionDataBase = connectorDataBase.getConnection();
                        try{
                            
                            PreparedStatement getMinutesStatment;
                            getMinutesStatment = connectionDataBase.prepareStatement("SELECT * FROM ValidarMinuta where idMinuta = ? and estado = ?");
                            getMinutesStatment.setInt(1,idMinute);
                            getMinutesStatment.setString(2,"Pendiente");
                            ResultSet minuteResultSet;
                            
                            minuteResultSet = getMinutesStatment.executeQuery();
                            
                            while(minuteResultSet.next()){
                                String comment = minuteResultSet.getString("comentario");
                                String professionalLicense= minuteResultSet.getString("cedula");
                                MinuteComment minuteComment = new MinuteComment(comment,"Pendiente", professionalLicense, idMinute);
                                commentList.add(minuteComment);
                            }
                              
                            connectorDataBase.disconnect();
                           
                        }catch(SQLException sqlException) {
                            throw new BusinessException("Parameter index ", sqlException);
                        }
                    }catch(ClassNotFoundException ex) {
                        Log.logException(ex);
                    }
                     
                    return commentList;  
    }
    
    @Override
    public boolean update(Minute newMinute) throws BusinessException {
        boolean updateSucess = false;
        Connector connectorDataBase=new Connector();
        try {
            Connection connectionDataBase = connectorDataBase.getConnection();
            PreparedStatement preparedStatement = connectionDataBase.prepareStatement("UPDATE Minuta set  nota = ? , estado = ?, pendiente = ?, idReunion = ? where idMinuta = ? ");
                preparedStatement.setString(1, newMinute.getNote());
                preparedStatement.setString(2,  newMinute.getSate());
                preparedStatement.setString(3,newMinute.getDue());
                preparedStatement.setInt(4, newMinute.getIdMeeting());
                preparedStatement.setInt(5, newMinute.getIdMinute());
            
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

    @Override
    public ArrayList<Member> getMembersApprove(Minute minute) throws BusinessException {
         ArrayList<Member> members = new ArrayList<Member>();
        MemberDAO memberDAO= new MemberDAO();
        try{
            Connector connectorDataBase = new Connector();
            Connection connectionDataBase = connectorDataBase.getConnection();
               PreparedStatement preparedStatement = connectionDataBase.prepareStatement("SELECT cedula FROM ValidarMinuta where idMinuta = ? and estado is NULL");
               preparedStatement.setInt(1, minute.getIdMinute());
               ResultSet resultSet;
               resultSet = preparedStatement.executeQuery();
       
               while(resultSet.next()){
                    String professionalLicense = resultSet.getString("cedula");
                    
                    Member member = memberDAO.getMemberByLicense(professionalLicense);
                    members.add(member);
                }
                connectorDataBase.disconnect();
            }catch(SQLException sqlException) {
                   throw new BusinessException("Database failed ", sqlException);         
            }catch(ClassNotFoundException ex) {
                        Log.logException(ex);
            }
        
        return members;
    }

}
