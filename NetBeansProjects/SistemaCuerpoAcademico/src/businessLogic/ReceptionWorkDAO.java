
package businessLogic;

import dataaccess.Connector;
import domain.Member;
import domain.ReceptionWork;
import domain.Student;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import log.BusinessException;
import log.Log;


public class ReceptionWorkDAO implements IReceptionWorkDAO {

    public boolean save(ReceptionWork receptionWork) throws BusinessException {
        boolean value=false;
        int idPreliminarProject= receptionWork.getPreliminarProject().getKey();
            try {
                Connector connectorDataBase=new Connector();
                Connection connectionDataBase = connectorDataBase.getConnection();
                String insertPreliminarProject = "INSERT INTO TrabajoRecepcional(titulo, tipo, descripcion, fechaInicio,fechaFin, estadoActual, idAnteproyecto ) VALUES (?,?,?,?,?,?,?)";
            
                PreparedStatement preparedStatement = connectionDataBase.prepareStatement(insertPreliminarProject );
                preparedStatement.setString(1, receptionWork.getTitle());
                preparedStatement.setString(2, receptionWork.getType());
                preparedStatement.setString(3, receptionWork.getDescription());
                preparedStatement.setString(4, receptionWork.getDateStart());
                preparedStatement.setString(5, receptionWork.getDateEnd());
                preparedStatement.setString(6, receptionWork.getActualState());
                preparedStatement.setInt(7,idPreliminarProject );
                
                preparedStatement.executeUpdate();
                value=true;
                connectorDataBase.disconnect();
            } catch (SQLException sqlException) {
                throw new BusinessException("DataBase connection failed ", sqlException);
            } catch (ClassNotFoundException ex) {
               Log.logException(ex);
            } 
        return value;
    }

    public boolean update(int id, ReceptionWork receptionWork) throws BusinessException {
        boolean updateSuccess=false;
        int idPreliminarProject= receptionWork.getPreliminarProject().getKey();
       
        try{
            Connector connectorDataBase=new Connector();
            Connection connectionDataBase = connectorDataBase.getConnection();
            String updateReceptionWork= "UPDATE TrabajoRecepcional set titulo =?, tipo=?,descripcion=?, fechaInicio=?,fechaFin=?, estadoActual=?, idAnteproyecto=? where idTrabajoRecepcional=? ";
     
            PreparedStatement preparedStatement = connectionDataBase.prepareStatement(updateReceptionWork );
            preparedStatement.setString(1, receptionWork.getTitle());
            preparedStatement.setString(2, receptionWork.getType());
            preparedStatement.setString(3, receptionWork.getDescription());
            preparedStatement.setString(4, receptionWork.getDateStart());
            preparedStatement.setString(5, receptionWork.getDateEnd());
            preparedStatement.setString(6, receptionWork.getActualState());
            preparedStatement.setInt(7,idPreliminarProject );
            preparedStatement.setInt(8,id);
            
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

    @Override
    public ReceptionWork getReceptionWorkById(int id) throws BusinessException {
        ReceptionWork receptionWork=null;
        Connector connectorDataBase=new Connector();

        try {
            Connection connectionDataBase = connectorDataBase.getConnection();
            String query="SELECT * from TrabajoRecepcional where idTrabajoRecepcional=?";
            PreparedStatement preparedStatement = connectionDataBase.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet;
            resultSet=preparedStatement.executeQuery();
            if(resultSet.next()){   
                int idReceptionWork= resultSet.getInt(1);
                String title= resultSet.getString("titulo");
                String type= resultSet.getString("tipo");
                String description=resultSet.getString("descripcion");
                String dateStart= resultSet.getString("fechaInicio");
                String dateEnd= resultSet.getString("fechaFin");
                String actualState= resultSet.getString("estadoActual");
                receptionWork=new ReceptionWork(title,type, description, dateStart, dateEnd, actualState);
                receptionWork.setKey(idReceptionWork);
            }
            
        } catch (ClassNotFoundException ex) {
            Log.logException(ex);
        } catch (SQLException sqlException) {
            throw new BusinessException("DataBase connection failed ", sqlException);
        }
        return receptionWork;
    }


    public int getId(ReceptionWork receptionWork) throws BusinessException {
        Integer id= 0;
        try{
            Connector connectorDataBase=new Connector();
            Connection connectionDataBase = connectorDataBase.getConnection();
            ResultSet resultSet;
            String selectId = "SELECT idTrabajoRecepcional from  TrabajoRecepcional where titulo=? and descripcion=? and tipo=? and fechaInicio=? and fechaFin=?;";
     
            PreparedStatement preparedStatement = connectionDataBase.prepareStatement(selectId);
            preparedStatement.setString(1, receptionWork.getTitle());
            preparedStatement.setString(2, receptionWork.getDescription());
            preparedStatement.setString(3, receptionWork.getType());
            preparedStatement.setString(4, receptionWork.getDateStart());
            preparedStatement.setString(5, receptionWork.getDateEnd());      
            resultSet=preparedStatement.executeQuery();
           
            if(resultSet.next()){
                id=Integer.parseInt(resultSet.getString("idTrabajoRecepcional"));
            }
            connectorDataBase.disconnect();
        }catch(SQLException sqlException) {
            throw new BusinessException("DataBase connection failed ", sqlException);
        } catch (ClassNotFoundException ex) {
            Log.logException(ex);
        }
        return id;
    }
    public ArrayList<ReceptionWork> getReceptionWorks() throws BusinessException {
        ArrayList<ReceptionWork> receptionWorkList = new ArrayList<>();
        try{
            Connector connectorDataBase = new Connector();
            Connection connectionDataBase = connectorDataBase.getConnection();
            String queryReceptionWork="SELECT * FROM TrabajoRecepcional";

               PreparedStatement preparedStatement;
               preparedStatement = connectionDataBase.prepareStatement(queryReceptionWork);
               ResultSet resultSet;
               resultSet = preparedStatement.executeQuery();
               while(resultSet.next()){
                   int key= resultSet.getInt(1);
                    String title = resultSet.getString("titulo");
                    String description = resultSet.getString("descripcion");
                    String type = resultSet.getString("tipo");
                    String dateStart=resultSet.getString("fechaInicio");
                    String dateEnd=resultSet.getString("fechaFin");
                    String actualState = resultSet.getString("estadoActual");
                    ReceptionWork receptionWorkAuxiliar = new ReceptionWork(title,type, description, dateStart, dateEnd, actualState);
                    receptionWorkAuxiliar.setKey(key);
                    receptionWorkList.add(receptionWorkAuxiliar);
                }
                connectorDataBase.disconnect();
            }catch(SQLException sqlException) {
                   throw new BusinessException("Database failed ", sqlException);         
            }catch(ClassNotFoundException ex) {
                        Log.logException(ex);
            }
            return receptionWorkList;
    }
    
    public boolean addColaborators(ReceptionWork receptionWork) throws BusinessException {
        boolean addColaboratorSuccess=false;
        int idReceptionWork=receptionWork.getKey();
        ArrayList<Member> colaborators= receptionWork.getMembers();
         try {
                Connector connectorDataBase=new Connector();
                Connection connectionDataBase = connectorDataBase.getConnection();
                String insertColaborators = "INSERT INTO Dirige(idTrabajoRecepcional,cedula,rol) VALUES (?,?,?)";
                int i=0;
                while(i< colaborators.size()){
                    PreparedStatement preparedStatement = connectionDataBase.prepareStatement(insertColaborators);
                    preparedStatement.setInt(1, idReceptionWork);
                    preparedStatement.setString(2, colaborators.get(i).getProfessionalLicense());
                    preparedStatement.setString(3, colaborators.get(i).getRole());
                    preparedStatement.executeUpdate();
                    addColaboratorSuccess=true; 
                    i++;
                } 
               connectorDataBase.disconnect();
            } catch (SQLException sqlException) {
                throw new BusinessException("DataBase connection failed ", sqlException);
            } catch (ClassNotFoundException ex) {
                Log.logException(ex);
            }
        return addColaboratorSuccess;
    }

    public boolean addStudents(ReceptionWork receptionWork) throws BusinessException {
        boolean addStudentsSuccess=false;
        int idReceptionWork=receptionWork.getKey();
        ArrayList<Student> students= receptionWork.getStudents();
         try {
                Connector connectorDataBase=new Connector();
                Connection connectionDataBase = connectorDataBase.getConnection();
                String insertColaborators = "INSERT INTO ParticipaTrabajoRecepcional(idTrabajoRecepcional,matricula) VALUES (?,?)";
                int i=0;
                while(i< students.size()){
                    PreparedStatement preparedStatement = connectionDataBase.prepareStatement(insertColaborators);
                    preparedStatement.setInt(1, idReceptionWork);
                    preparedStatement.setString(2, students.get(i).getEnrollment());
                    preparedStatement.executeUpdate();
                    addStudentsSuccess=true; 
                    i++;
                } 
               connectorDataBase.disconnect();
            } catch (SQLException sqlException) {
                throw new BusinessException("DataBase connection failed ", sqlException);
            } catch (ClassNotFoundException ex) {
                Log.logException(ex);
            }
        return addStudentsSuccess;
    }

    @Override
    public ArrayList<Member> getColaborators(int idPreliminarProject) throws BusinessException {
        ArrayList<Member> colaborators= new ArrayList<Member> ();
        MemberDAO memberDAO= new MemberDAO();
        try{
            Connector connectorDataBase = new Connector();
            Connection connectionDataBase = connectorDataBase.getConnection();
            String query = "SELECT cedula FROM Dirige where idTrabajoRecepcional=?";

               PreparedStatement preparedStatement = connectionDataBase.prepareStatement(query);
               preparedStatement.setInt(1, idPreliminarProject);
               ResultSet resultSet;
               resultSet = preparedStatement.executeQuery();
               while(resultSet.next()){
                    int key= resultSet.getInt(1);
                    String professionalLicense= resultSet.getString("cedula");
                    Member member = memberDAO.getMemberByLicense(professionalLicense);
                    colaborators.add(member);
                    
                }
                connectorDataBase.disconnect();
            }catch(SQLException sqlException) {
                   throw new BusinessException("Database failed ", sqlException);         
            }catch(ClassNotFoundException ex) {
                        Log.logException(ex);
            }

        return colaborators;
    }
    
    @Override
    public boolean deleteColaborators(ReceptionWork receptionWork) throws BusinessException {
        boolean deleteSucess=false;
        int idReceptionWork = receptionWork.getKey();
        ArrayList<Member> colaborators= receptionWork.getMembers();
        try{
            Connector connectorDataBase=new Connector();
            Connection connectionDataBase = connectorDataBase.getConnection();
            String delete = "delete from Dirige where cedula=? and idTrabajoRecepcional=?";
     
            PreparedStatement preparedStatement = connectionDataBase.prepareStatement(delete);
            int i=0;
            while(i< colaborators.size()){
               preparedStatement.setString(1, colaborators.get(i).getProfessionalLicense());
               preparedStatement.setInt(2, idReceptionWork);
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
    public ArrayList<Student> getStudents(int idReceptionWork) throws BusinessException {
        ArrayList<Student> students= new ArrayList<Student> ();
        StudentDAO studentDAO= new StudentDAO();
        try{
            Connector connectorDataBase = new Connector();
            Connection connectionDataBase = connectorDataBase.getConnection();
            String query="SELECT matricula FROM ParticipaTrabajoRecepcional where idTrabajoRecepcional=?";

               PreparedStatement preparedStatement = connectionDataBase.prepareStatement(query);
               preparedStatement.setInt(1, idReceptionWork);
               ResultSet resultSet;
               resultSet = preparedStatement.executeQuery();
               while(resultSet.next()){
                    String enrollment= resultSet.getString("matricula");
                    Student student = studentDAO.getByEnrollment(enrollment);
                    students.add(student);
                    
                }
                connectorDataBase.disconnect();
            }catch(SQLException sqlException) {
                   throw new BusinessException("Database failed ", sqlException);         
            }catch(ClassNotFoundException ex) {
                        Log.logException(ex);
            }

        return students;
    }

    @Override
    public boolean deleteStudents(ReceptionWork receptionWork) throws BusinessException {
        boolean deleteSucess=false;
        int idReceptionWork=receptionWork.getKey();
        ArrayList<Student> students= receptionWork.getStudents();
        try{
            Connector connectorDataBase=new Connector();
            Connection connectionDataBase = connectorDataBase.getConnection();
            String delete = "delete from ParticipaTrabajoRecepcional where matricula=? and idTrabajoRecepcional=?";
     
            PreparedStatement preparedStatement = connectionDataBase.prepareStatement(delete);
            int i=0;
            while(i< students.size()){
               preparedStatement.setString(1, students.get(i).getEnrollment());
               preparedStatement.setInt(2, idReceptionWork);
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
    
}
