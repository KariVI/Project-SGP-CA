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
/*
        *@author Mariana Vargas
*/
public class MinuteDAO implements IMinuteDAO {
    
     /*
        *@param minute Minuta a guardar 
        *@return Si la minuta pudo ser guardada (true) o no (false) en la base de datos 
        *@throws BusinessException Se cacho una excepción de tipo SQLException
    */
    @Override
    public boolean savedSucessfulMinute(Minute minute)throws BusinessException {
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
    
    /*
        *@param idMinute Minuta a aprobar
        *@param professionalLicense cedula del miembro que aprueba la minuta
        *@return Si la minuta pudo ser aprobada (true) o no (false) en la base de datos 
        *@throws BusinessException Se cacho una excepción de tipo SQLException
    */   
    @Override
    public boolean approveMinute(int idMinute, String professionalLicense)throws BusinessException{
        boolean approveSuccess = false;
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
                approveSuccess = true;
            }catch(SQLException sqlException) {
                throw new BusinessException("Parameter index ", sqlException);
            }
            
        }catch(ClassNotFoundException ex) {
            Log.logException(ex);
        }
        
        return approveSuccess;
    }
    
    /*
        *@param minuteComment Comentario de la minuta que se desaprueba
        *@return Si la minuta pudo ser desaprobada (true) o no (false) en la base de datos 
        *@throws BusinessException Se cacho una excepción de tipo SQLException
    */   
    @Override
    public boolean disapproveMinute(MinuteComment minuteComment)throws BusinessException {
        boolean disapproveSucess = false;
        try{
            Connector connectorDataBase = new Connector();
            Connection connectionDataBase = connectorDataBase.getConnection();
            try{
                PreparedStatement insertMinuteStatment;
                insertMinuteStatment = connectionDataBase.prepareStatement("INSERT INTO ValidarMinuta(idMinuta, cedula,comentario,estado) VALUES(?,?,?,?) ");
                insertMinuteStatment.setInt(1,  minuteComment.getIdMinute());
                insertMinuteStatment.setString(2,  minuteComment.getProfessionalLicense());
                insertMinuteStatment.setString(3,  minuteComment.getComment());
                insertMinuteStatment.setString(4, "Pendiente");
                insertMinuteStatment.executeUpdate();
                connectorDataBase.disconnect();
                disapproveSucess = true;
            }catch(SQLException sqlException) {
                throw new BusinessException("Parameter index ", sqlException);
            }
            
        }catch(ClassNotFoundException ex) {
            Log.logException(ex);
        }
        
        return disapproveSucess;
    }
    
    /*
        *@return Todas las minutas almacenadas en la base de datosArrayList<Minute> 
        *@throws BusinessException Se cacho una excepción de tipo SQLException
    */    
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
    
    /*
        *@params idMinute ID de la Minuta de la cual se buscarán comentarios
        *@return Comentarios de la minuta relacionados a una minuta (ArrayList<MinuteComment>)
        *@throws BusinessException Se cacho una excepción de tipo SQLException
    */
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
    
    /*
        *@param newMinute minuta con los datos nuevos a actualizar
        *@return Si la minuta pudo ser actualizada (true) o no (false) en la base de datos
        *@throws BusinessException Se cacho una excepción de tipo SQLException
    */    
    @Override
    public boolean updatedSucessful(Minute newMinute) throws BusinessException {
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
    
    /*
        *@param minute  la Minuta de la cual se buscarán los miembros que la aprobaron
        *@return Miembros que aprobaron la minuta(ArrayList<Member>)
        *@throws BusinessException Se cacho una excepción de tipo SQLException
    */
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
    
    /*
        *@param minute  la Minuta de la cual se busca obtener el ID
        *@return Si se encuentra la minuta, su ID (int)
        *@throws BusinessException Se cacho una excepción de tipo SQLException o si no se encontro la minuta a buscar
    */
    @Override
    public int getIdMinute(Minute minute) throws BusinessException {
        int idMinute = 0;
        try{
            Connector connectorDataBase = new Connector();
            Connection connectionDataBase = connectorDataBase.getConnection();
            PreparedStatement preparedStatement = connectionDataBase.prepareStatement("SELECT * FROM Minuta where estado = ? and pendiente = ? and nota = ? and idReunion = ?");
            preparedStatement.setString(1, minute.getSate());
            preparedStatement.setString(2, minute.getDue());
            preparedStatement.setString(3, minute.getNote());
            preparedStatement.setInt(4, minute.getIdMeeting());
            ResultSet resultSet;
            resultSet = preparedStatement.executeQuery(); 
            if(resultSet.next()){
                idMinute = resultSet.getInt("idMinuta");
            }else{
                throw new BusinessException("minute not found");
            }
            
            connectorDataBase.disconnect();
        }catch(SQLException sqlException) {
            throw new BusinessException("Database failed ", sqlException);         
        }catch(ClassNotFoundException ex) {
            Log.logException(ex);
        }
        
        return idMinute;
    }
    
    /*
        *@params idMeeting el ID de la reunión de la cual se buscará la minuta
        *@return La minuta de la reunión correpondiente (Minute)
        *@throws BusinessException Se cacho una excepción de tipo SQLException o si no se encontro la minuta a buscar
    */   
    @Override
    public Minute getMinute(int idMeeting) throws BusinessException {
        Minute minute = null ;
        try{
            Connector connectorDataBase = new Connector();
            Connection connectionDataBase = connectorDataBase.getConnection();
            PreparedStatement preparedStatement = connectionDataBase.prepareStatement("SELECT * FROM Minuta where idReunion = ?");
            preparedStatement.setInt(1, idMeeting);
            ResultSet resultSet;
            resultSet = preparedStatement.executeQuery(); 
            if(resultSet.next()){
                int idMinute = resultSet.getInt("idMinuta");
                String due = resultSet.getString("pendiente");
                String note = resultSet.getString("nota");
                String state = resultSet.getString("estado");
                minute = new Minute(idMinute,due,state,note,idMeeting);                
            }else{
                throw new BusinessException("minute not found");
            }
            connectorDataBase.disconnect();
        }catch(SQLException sqlException) {
            throw new BusinessException("Database failed ", sqlException);         
        }catch(ClassNotFoundException ex) {
            Log.logException(ex);
        }
        return minute;
    }
    
    /*
        *@param minuteComment Comentario a eliminar
        *@return Si el comentario pudo ser eliminado (true) o no (false) en la base de datos
        *@throws BusinessException se cacho una excepción de tipo SQLException
    */
    @Override
    public boolean deletedSucessfulMinuteComment(MinuteComment minuteComment) throws BusinessException {
        boolean deleteSucess = false;
        Connector connectorDataBase=new Connector();
        try {
            Connection connectionDataBase = connectorDataBase.getConnection();
            PreparedStatement preparedStatement = connectionDataBase.prepareStatement("Delete from ValidarMinuta where idMinuta = ? and cedula = ?");
            preparedStatement.setInt(1, minuteComment.getIdMinute());
            preparedStatement.setString(2, minuteComment.getProfessionalLicense());
            preparedStatement.executeUpdate();
            deleteSucess=true;  
            connectorDataBase.disconnect();
        }catch (ClassNotFoundException ex) {
            Log.logException(ex);
        } catch (SQLException sqlException) {
           throw new BusinessException("DataBase connection failed ", sqlException);
        }
      
        return deleteSucess;
    }

    /*
        *@param idMinute ID de la minuta de la cual se eliminarán los comentarios
        *@return Si los comentarios puedieron ser eliminados (true) o no (false) en la base de datos
        *@throws BusinessException Se cacho una excepción de tipo SQLException
    */
    @Override
    public boolean deletedSucessfulMinutesComments(int idMinute) throws BusinessException {
       boolean deleteSucess = false;
        Connector connectorDataBase=new Connector();
        try {
            Connection connectionDataBase = connectorDataBase.getConnection();
            PreparedStatement preparedStatement = connectionDataBase.prepareStatement("Delete from ValidarMinuta where idMinuta = ? ");
            preparedStatement.setInt(1, idMinute);          
            preparedStatement.executeUpdate();
            deleteSucess = true;  
            connectorDataBase.disconnect();
        }catch (ClassNotFoundException ex) {
            Log.logException(ex);
        } catch (SQLException sqlException) {
           throw new BusinessException("DataBase connection failed ", sqlException);
        }
      
        return deleteSucess;
    }

}
