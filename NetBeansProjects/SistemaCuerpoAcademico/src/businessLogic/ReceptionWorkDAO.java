/*
        *@author Karina Valdes
    */
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

    /*
        *@params receptionWork Trabajo recepcional a guardar 
        *@return Si el trabajo recepcional pudo ser guardado (true) o no (false) en la base de datos 
        *@throws Se cacho una excepción de tipo SQLException
    */
    public boolean savedSucessful(ReceptionWork receptionWork) throws BusinessException {
        boolean value=false;
        int idProject = receptionWork.getProject().getIdProject();
            try {
                Connector connectorDataBase=new Connector();
                Connection connectionDataBase = connectorDataBase.getConnection();
                String insertPreliminarProject = "INSERT INTO TrabajoRecepcional(titulo, tipo, descripcion, fechaInicio,fechaFin, estadoActual, idProyecto ,clave_CA) VALUES (?,?,?,?,?,?,?,?)";
            
                PreparedStatement preparedStatement = connectionDataBase.prepareStatement(insertPreliminarProject );
                preparedStatement.setString(1, receptionWork.getTitle());
                preparedStatement.setString(2, receptionWork.getType());
                preparedStatement.setString(3, receptionWork.getDescription());
                preparedStatement.setString(4, receptionWork.getDateStart());
                preparedStatement.setString(5, receptionWork.getDateEnd());
                preparedStatement.setString(6, receptionWork.getActualState());
                preparedStatement.setInt(7,idProject );
                preparedStatement.setString(8, receptionWork.getKeyGroupAcademic());
                
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

    /*
        *@params id Identificador del Trabajo rececional a modificar 
        *@params receptionWork La nueva información del trabajo recepcional a actualizar  
        *@return Si el trabajo recepcional pudo ser actualizado (true) o no (false) en la base de datos 
        *@throws Se cacho una excepción de tipo SQLException 
    */
    
    public boolean updatedSucessful(int id, ReceptionWork receptionWork) throws BusinessException {
        boolean updateSuccess=false;
        int idProject = receptionWork.getProject().getIdProject();
       
        try{
            Connector connectorDataBase=new Connector();
            Connection connectionDataBase = connectorDataBase.getConnection();
            String updateReceptionWork= "UPDATE TrabajoRecepcional set titulo =?, tipo=?,descripcion=?, fechaInicio=?,fechaFin=?, estadoActual=?, idProyecto=? where idTrabajoRecepcional=? ";
     
            PreparedStatement preparedStatement = connectionDataBase.prepareStatement(updateReceptionWork );
            preparedStatement.setString(1, receptionWork.getTitle());
            preparedStatement.setString(2, receptionWork.getType());
            preparedStatement.setString(3, receptionWork.getDescription());
            preparedStatement.setString(4, receptionWork.getDateStart());
            preparedStatement.setString(5, receptionWork.getDateEnd());
            preparedStatement.setString(6, receptionWork.getActualState());
            preparedStatement.setInt(7,idProject);
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

      /*
        *@params id Identificador del Trabajo rececional que va a recuperarse 
        *@return El trabajo recepcional recuperado de acuerdo al id
        *@throws Se cacho una excepción de tipo SQLException o si no es localizado el trabajo manda una excepción de tipo BusinessException
    */
    
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
                int idProject = resultSet.getInt("idProyecto");
                String keyGroupAcademic = resultSet.getString("clave_CA");
                receptionWork=new ReceptionWork(title,type, description, dateStart, dateEnd, actualState,keyGroupAcademic);
                receptionWork.setKey(idReceptionWork);
                ProjectDAO projectDAO = new ProjectDAO();
                receptionWork.setProject(projectDAO.getProjectById(idProject));
            }else{  
                throw new BusinessException("ReceptionWork not found ");
            }
            
        } catch (ClassNotFoundException ex) {
            Log.logException(ex);
        } catch (SQLException sqlException) {
            throw new BusinessException("DataBase connection failed ", sqlException);
        }
        return receptionWork;
    }

      /*
        *@params receptionWork Trabajo recepcional del cual se busca su id
        *@return La id del trabajo recepcional
        *@throws Se cacho una excepción de tipo SQLException o si no es localizado el trabajo manda una excepción de tipo BusinessException
    */

    public int getId(ReceptionWork receptionWork) throws BusinessException {
        Integer id= 0;
        try{
            Connector connectorDataBase=new Connector();
            Connection connectionDataBase = connectorDataBase.getConnection();
            ResultSet resultSet;
            String selectId = "SELECT idTrabajoRecepcional from  TrabajoRecepcional where titulo=?  and tipo=? and fechaInicio=? and fechaFin=?;";
     
            PreparedStatement preparedStatement = connectionDataBase.prepareStatement(selectId);
            preparedStatement.setString(1, receptionWork.getTitle());
            preparedStatement.setString(2, receptionWork.getType());
            preparedStatement.setString(3, receptionWork.getDateStart());
            preparedStatement.setString(4, receptionWork.getDateEnd());      
            resultSet=preparedStatement.executeQuery();
           
            if(resultSet.next()){
                id=Integer.parseInt(resultSet.getString("idTrabajoRecepcional"));
            }else{
                throw new BusinessException("ReceptionWork not found");
            }
            connectorDataBase.disconnect();
        }catch(SQLException sqlException) {
            throw new BusinessException("DataBase connection failed ", sqlException);
        } catch (ClassNotFoundException ex) {
            Log.logException(ex);
        }
        return id;
    }
    
     /*
        *@params keyGroupAcademic Clave del cuerpo académico para filtrar los trabajos recepcionales que le corresponden
        *@return Trabajos recepcionales relacionados a un cuerpo académico
        *@throws Se cacho una excepción de tipo SQLException
    */
    
    public ArrayList<ReceptionWork> getReceptionWorks(String keyGroupAcademic) throws BusinessException {
        ArrayList<ReceptionWork> receptionWorkList = new ArrayList<ReceptionWork>();
        try{
            Connector connectorDataBase = new Connector();
            Connection connectionDataBase = connectorDataBase.getConnection();
            String queryReceptionWork="SELECT * FROM TrabajoRecepcional where clave_CA=?";

               PreparedStatement preparedStatement;
               preparedStatement = connectionDataBase.prepareStatement(queryReceptionWork);
               preparedStatement.setString(1, keyGroupAcademic);
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
                    int idProject = resultSet.getInt("idProyecto");
                     String keyGroup = resultSet.getString("clave_CA");
                   
                    ReceptionWork receptionWorkAuxiliar = new ReceptionWork(title,type, description, dateStart, dateEnd, actualState, keyGroup);
                    receptionWorkAuxiliar.setKey(key);
                    ProjectDAO projectDAO = new ProjectDAO();
                    receptionWorkAuxiliar.setProject(projectDAO.getProjectById(idProject));
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
    
     /*
        *@params receptionWork Trabajo recepcional al cual se le añaden los colaboradores
        *@return Si el trabajo pudo ser guardar sus colaboradores  (true) o no (false) en la base de datos 
        *@throws Se cacho una excepción de tipo SQLException 
        *@see ReceptionWork
    */
    
    public boolean addedSucessfulColaborators(ReceptionWork receptionWork) throws BusinessException {
        boolean addColaboratorSuccess=false;
        int idReceptionWork=receptionWork.getKey();
        ArrayList<Member> colaborators= receptionWork.getMembers();
         try {
                Connector connectorDataBase=new Connector();
                Connection connectionDataBase = connectorDataBase.getConnection();
                String insertColaborators = "INSERT INTO Dirige(idTrabajoRecepcional,cedula,rol) VALUES (?,?,?)";
                int i=0;
                PreparedStatement preparedStatement = connectionDataBase.prepareStatement(insertColaborators);
                while(i< colaborators.size()){
                    
                    
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

    /*
        *@params receptionWork Trabajo recepcional al cual se le añaden los estudiantes
        *@return Si el trabajo recepcional pudo ser guardar sus estudiantes (true) o no (false) en la base de datos 
        *@throws Se cacho una excepción de tipo SQLException
        *@see ReceptionWork
    */
    
    public boolean addedSucessfulStudents(ReceptionWork receptionWork) throws BusinessException {
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

     /*
        *@params idReceptionWork Identificador del trabajo recepcional del cual se buscan sus colaboradores 
        *@return Listado de colaboradores del trabajo recepcional
        *@throws Se cacho una excepción de tipo SQLException
    */
    @Override
    public ArrayList<Member> getColaborators(int idReceptionWork) throws BusinessException {
        ArrayList<Member> colaborators= new ArrayList<Member> ();
        MemberDAO memberDAO= new MemberDAO();
        try{
            Connector connectorDataBase = new Connector();
            Connection connectionDataBase = connectorDataBase.getConnection();
            String query = "SELECT cedula,rol FROM Dirige where idTrabajoRecepcional=?";

               PreparedStatement preparedStatement = connectionDataBase.prepareStatement(query);
               preparedStatement.setInt(1, idReceptionWork);
               ResultSet resultSet;
               resultSet = preparedStatement.executeQuery();
               while(resultSet.next()){
                    int key= resultSet.getInt(1);
                    String professionalLicense= resultSet.getString("cedula");
                    String role = resultSet.getString("rol");
                    Member member = memberDAO.getMemberByLicense(professionalLicense);
                    member.setRole(role);
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
    
     /*
        *@params receptionWork Trabajo recepcional al cual se le eliminan los colaboradores
        *@return Si fue posible eliminar los colaboradores de un trabajo recepcional (true) o no (false) en la base de datos 
        *@throws Se cacho una excepción de tipo SQLException 
        *@see ReceptionWork
    */
    
    @Override
    public boolean deletedSucessfulColaborators(ReceptionWork receptionWork) throws BusinessException {
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
    
      /*
        *@params idReceptionWork Identificador del trabajo recepcional del cual se buscan sus estudiantes
        *@return Listado de estudiantes del trabajo recepcional
        *@throws Se cacho una excepción de tipo SQLException
    */

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

    
      /*
        *@params receptionWork Trabajo recepcional al cual se le eliminan los estudiantes
        *@return Si fue posible eliminar los estudiantes de un trabajo recepcional (true) o no (false) en la base de datos 
        *@throws Se cacho una excepción de tipo SQLException
        *@see ReceptionWork
    */
    
    @Override
    public boolean deletedSucessfulStudents(ReceptionWork receptionWork) throws BusinessException {
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
