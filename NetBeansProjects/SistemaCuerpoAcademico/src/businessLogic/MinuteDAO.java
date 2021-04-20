package businessLogic;

import dataaccess.Connector;
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
                                Minute minute = new Minute(idMinute, note, state, due);
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

}
