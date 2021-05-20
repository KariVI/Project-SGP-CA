package businessLogic;

import dataaccess.Connector;
import domain.Topic;
import log.BusinessException;
import log.Log;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;



public class TopicDAO implements ITopic {

    @Override
    public boolean save(Topic agendaTopic)throws BusinessException {
        boolean saveSuccess = false;
        try{
            Connector connectorDataBase = new Connector();
            Connection connectionDataBase = connectorDataBase.getConnection();
            try{
                PreparedStatement insertAgendaTopicStatment;
                insertAgendaTopicStatment = connectionDataBase.prepareStatement("INSERT INTO Tema(idReunion, tema, horaInicio, horaFin, cedula) VALUES(?,?,?,?,?) ");
                insertAgendaTopicStatment.setInt(1, agendaTopic.getIdMeeting());
                insertAgendaTopicStatment.setString(2,  agendaTopic.getTopicName());
                insertAgendaTopicStatment.setString(3, agendaTopic.getStartTime());
                insertAgendaTopicStatment.setString(4, agendaTopic.getFinishTime());
                insertAgendaTopicStatment.setString(5, agendaTopic.getProfessionalLicense());
                insertAgendaTopicStatment.executeUpdate();
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
    public ArrayList<Topic> getAgendaTopics(int idMeeting) throws BusinessException {
        ArrayList<Topic> agendaList = new ArrayList<>();
        try{
            Connector connectorDataBase = new Connector();
            Connection connectionDataBase = connectorDataBase.getConnection();
            try{
                            
                PreparedStatement getAgendaStatment;
                getAgendaStatment = connectionDataBase.prepareStatement("SELECT * FROM Tema where idReunion = ?");
                getAgendaStatment.setInt(1,idMeeting);
                ResultSet agendaResultSet;                    
                agendaResultSet = getAgendaStatment.executeQuery();
                            
                while(agendaResultSet.next()){
                    int idTopic = agendaResultSet.getInt("idTema");
                    String professionalLicense = agendaResultSet.getString("cedula");
                    String topicName = agendaResultSet.getString("tema");
                    String startTime = agendaResultSet.getString("horaInicio");
                    String finishTime = agendaResultSet.getString("horaFin");
                    Topic agendaData = new Topic(idTopic, topicName, startTime, finishTime,professionalLicense, idMeeting);
                    agendaList.add(agendaData);
                }
                             
                connectorDataBase.disconnect();                         
            }catch(SQLException sqlException) {
                throw new BusinessException("Parameter index ", sqlException);
            }
        }catch(ClassNotFoundException ex) {
            Log.logException(ex);
        }
                   
        return agendaList;  
    }
       
    @Override
    public boolean update( Topic newTopic) throws BusinessException {
        boolean updateSucess = false;
        Connector connectorDataBase=new Connector();
        try {
            Connection connectionDataBase = connectorDataBase.getConnection();
            PreparedStatement preparedStatement = connectionDataBase.prepareStatement("UPDATE Tema set idReunion = ?, tema = ?, horaInicio = ?, horaFin = ?, cedula = ? WHERE idTema = ? ");
            preparedStatement.setInt(1, newTopic.getIdMeeting());
            preparedStatement.setString(2, newTopic.getTopicName());
            preparedStatement.setString(3, newTopic.getStartTime());
            preparedStatement.setString(4, newTopic.getFinishTime());
            preparedStatement.setString(5, newTopic.getProfessionalLicense());
            preparedStatement.setInt(6, newTopic.getIdTopic());
                
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
    public boolean delete(Topic topic) throws BusinessException{
        boolean deleteSucess=false;
        try{
            Connector connectorDataBase=new Connector();
            Connection connectionDataBase = connectorDataBase.getConnection();
            PreparedStatement preparedStatement = connectionDataBase.prepareStatement("DELETE FROM Tema where idTema = ? and idReunion = ? ");
            preparedStatement.setInt(1, topic.getIdTopic());
            preparedStatement.setInt(2, topic.getIdMeeting());
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
 
}
 