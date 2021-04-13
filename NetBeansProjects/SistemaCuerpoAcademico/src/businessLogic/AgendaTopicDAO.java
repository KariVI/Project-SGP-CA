/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.

package businessLogic;

import dataaccess.Connector;
import domain.AgendaTopic;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


public class AgendaTopicDAO implements IAgendaTopic {

    @Override
    public boolean save(AgendaTopic agendaTopic, int idMeeting) {
                    boolean saveSuccess = false;
                    try{
                        Connector connectorDataBase = new Connector();
                        Connection connectionDataBase = connectorDataBase.getConnection();
                        try{
                            
                            PreparedStatement insertAgendaTopicStatment;
                            insertAgendaTopicStatment = connectionDataBase.prepareStatement("INSERT INTO TemaAgenda(idReunion, tema, horaInicio, horaFin, cedula) VALUES(?,?,?,?) ");
                            insertAgendaTopicStatment.setInt(1, idMeeting);
                            insertAgendaTopicStatment.setString(2,  agendaTopic.getTopicName());
                            insertAgendaTopicStatment.setString(3, agendaTopic.getStartTime());
                            insertAgendaTopicStatment.setString(4, agendaTopic.getFinishTime());
                            insertAgendaTopicStatment.setString(5, agendaTopic.getProfessionalLicense());
                  
                            insertAgendaTopicStatment.executeUpdate();
                            
                            connectorDataBase.disconnect();
                            saveSuccess = true;
                        }catch(SQLException sqlException) {
                            throw new IllegalStateException("Parameter index ", sqlException);
                        }
                    }catch(ClassNotFoundException ex) {
                        Logger.getLogger(ProjectDAO.class.getName()).log(Level.SEVERE, null, ex);
                    }    
                    return saveSuccess;
    }

    @Override
    public ArrayList<AgendaTopic> getAgendaTopics(int idMeeting) {
                     ArrayList<AgendaTopic> agendaList = new ArrayList<>();
                     try{
                        Connector connectorDataBase = new Connector();
                        Connection connectionDataBase = connectorDataBase.getConnection();
                        try{
                            
                            PreparedStatement getAgendaStatment;
                            getAgendaStatment = connectionDataBase.prepareStatement("SELECT * FROM TemaAgenda where idReunion = ?");
                            getAgendaStatment.setInt(1,idMeeting);
                            ResultSet agendaResultSet;
                            
                            agendaResultSet = getAgendaStatment.executeQuery();
                            
                            while(agendaResultSet.next()){
                                int idTopic = agendaResultSet.getInt("idTema");
                                String ProfessionalLicense = agendaResultSet.getString("cedula");
                                String topicName = agendaResultSet.getString("tema");
                                String startTime = agendaResultSet.getString("horaInicio");
                                String finishTime = agendaResultSet.getString("horaFin");
                                AgendaTopic agendaData = new AgendaTopic(idTopic, topicName, startTime, finishTime,ProfessionalLicense);
                                agendaList.add(agendaData);
                            }
                              
                            connectorDataBase.disconnect();
                           
                        }catch(SQLException sqlException) {
                            throw new IllegalStateException("Parameter index ", sqlException);
                        }
                    }catch(ClassNotFoundException ex) {
                        Logger.getLogger(ProjectDAO.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    return agendaList;  
    }
    
}
 */