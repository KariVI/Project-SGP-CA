/*
        *@author Karina Valdes
 
    */
package businessLogic;

import dataaccess.Connector;
import domain.Member;
import domain.PreliminarProject;
import domain.Student;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import log.BusinessException;
import log.Log;

public class PreliminarProjectDAO implements IPreliminarProjectDAO {

      /*
        *@params preliminarProject El anteproyecto a guardar 
        *@return Si el anteproyecto pudo ser guardado (true) o no (false) en la base de datos 
        *@throws Se cacho una excepción de tipo SQLException
    */
    @Override
    public boolean savedSucessful(PreliminarProject preliminarProject) throws BusinessException {
         boolean value=false;
            try {
                Connector connectorDataBase=new Connector();
                Connection connectionDataBase = connectorDataBase.getConnection();
                String insertPreliminarProject = "INSERT INTO Anteproyecto(titulo,descripcion, fechaInicio,fechaFin, clave_CA ) VALUES (?,?,?,?,?)";
            
                PreparedStatement preparedStatement = connectionDataBase.prepareStatement(insertPreliminarProject );
                preparedStatement.setString(1, preliminarProject.getTitle());
                preparedStatement.setString(2, preliminarProject.getDescription());
                preparedStatement.setString(3, preliminarProject.getDateStart());
                preparedStatement.setString(4, preliminarProject.getDateEnd());
                 preparedStatement.setString(5, preliminarProject.getKeyGroupAcademic());
                
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
        *@params preliminarProject Anteproyecto del cual se busca su id
        *@return La id del anteproyecto
        *@throws Se cacho una excepción de tipo SQLException o si no es localizado el anteproyecto manda una excepción de tipo BusinessException
    */

    @Override
    public int getId(PreliminarProject preliminarProject) throws BusinessException {
        Integer id= 0;
        try{
            Connector connectorDataBase=new Connector();
            Connection connectionDataBase = connectorDataBase.getConnection();
            ResultSet resultSet;
            String selectId = "SELECT idAnteproyecto from  Anteproyecto where titulo=?  and fechaInicio=? and fechaFin=? and clave_CA=?;";
     
            PreparedStatement preparedStatement = connectionDataBase.prepareStatement(selectId);
            preparedStatement.setString(1, preliminarProject.getTitle());
            preparedStatement.setString(2, preliminarProject.getDateStart());
            preparedStatement.setString(3, preliminarProject.getDateEnd());  
            preparedStatement.setString(4, preliminarProject.getKeyGroupAcademic());      

            resultSet=preparedStatement.executeQuery();
           
            if(resultSet.next()){
                id=Integer.parseInt(resultSet.getString("idAnteproyecto"));
            }else{
                throw new BusinessException("PreliminarProject not found");
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
        *@params id Identificador del anteproyecto a modificar 
        *@params preliminarProject La nueva información del anteproyecto a actualizar  
        *@return Si el anteproyecto pudo ser actualizado (true) o no (false) en la base de datos 
        *@throws Se cacho una excepción de tipo SQLException 
    */

    @Override
    public boolean updatedSucessful(int id,PreliminarProject preliminarProject) throws BusinessException {
        boolean updateSucess=false;
        try{
            Connector connectorDataBase=new Connector();
            Connection connectionDataBase = connectorDataBase.getConnection();
            String updatePreliminarProject = "UPDATE Anteproyecto set titulo=? , descripcion=?, fechaInicio=?, fechaFin=? where idAnteproyecto=?";
            PreparedStatement preparedStatement = connectionDataBase.prepareStatement(updatePreliminarProject );
            preparedStatement.setString(1, preliminarProject.getTitle());
            preparedStatement.setString(2, preliminarProject.getDescription());
            preparedStatement.setString(3, preliminarProject.getDateStart());
            preparedStatement.setString(4, preliminarProject.getDateEnd());
            preparedStatement.setInt(5, id);
            
            preparedStatement.executeUpdate();
            connectorDataBase.disconnect();
            updateSucess=true;
        }catch(SQLException sqlException){
         throw new BusinessException("DataBase connection failed ", sqlException);
        }catch (ClassNotFoundException ex) {
            Log.logException(ex);
        }
        
        return updateSucess;
    }

    /*
        *@params keyGroupAcademic Clave del cuerpo académico para filtrar los anteproyectos que le corresponden
        *@return Anteproyectos relacionados a un cuerpo académico
        *@throws Se cacho una excepción de tipo SQLException
    */
    
    @Override
    public ArrayList<PreliminarProject> getPreliminarProjects(String keyGroupAcademic) throws BusinessException {
       ArrayList<PreliminarProject> preliminarProjectList = new ArrayList<PreliminarProject>();
        try{
            Connector connectorDataBase = new Connector();
            Connection connectionDataBase = connectorDataBase.getConnection();
            String queryPreliminarProject="SELECT * FROM Anteproyecto where clave_CA=?";

               PreparedStatement preparedStatement;
               preparedStatement = connectionDataBase.prepareStatement(queryPreliminarProject);
               preparedStatement.setString(1, keyGroupAcademic);
               ResultSet resultSet;
               resultSet = preparedStatement.executeQuery();
               while(resultSet.next()){
                   int key= resultSet.getInt(1);
                    String title = resultSet.getString("titulo");
                    String description = resultSet.getString("descripcion");
                    String dateStart=resultSet.getString("fechaInicio");
                    String dateEnd=resultSet.getString("fechaFin");
                    String groupAcademic= resultSet.getString("clave_CA");
                    PreliminarProject preliminarProjectAuxiliar = new PreliminarProject(title,description, dateStart, dateEnd, groupAcademic);
                    preliminarProjectAuxiliar.setKey(key);
                    preliminarProjectList.add(preliminarProjectAuxiliar);
                }
                connectorDataBase.disconnect();
            }catch(SQLException sqlException) {
                   throw new BusinessException("Database failed ", sqlException);         
            }catch(ClassNotFoundException ex) {
                        Log.logException(ex);
            }
            return preliminarProjectList;
    }
    
     /*
        *@params id Identificador del Anteproyecto que va a recuperarse 
        *@return El anteproyecto recuperado de acuerdo al id
        *@throws Se cacho una excepción de tipo SQLException o si no es localizado el anteproyecto manda una excepción de tipo BusinessException
    */

    @Override
    public PreliminarProject getById(int id) throws BusinessException {
        PreliminarProject preliminarProject=null;
        Connector connectorDataBase=new Connector();

        try {
            Connection connectionDataBase = connectorDataBase.getConnection();
            String query="SELECT * from Anteproyecto where idAnteproyecto=?";
            PreparedStatement preparedStatement = connectionDataBase.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet;
            resultSet=preparedStatement.executeQuery();
            if(resultSet.next()){   
                int idPreliminarProject= resultSet.getInt(1);
                String title= resultSet.getString("titulo");
                String description=resultSet.getString("descripcion");
                String dateStart= resultSet.getString("fechaInicio");
                String dateEnd= resultSet.getString("fechaFin");
                String groupAcademic= resultSet.getString("clave_CA");

                preliminarProject=new PreliminarProject(title, description, dateStart, dateEnd, groupAcademic);
                preliminarProject.setKey(idPreliminarProject);
            }
            
        } catch (ClassNotFoundException ex) {
            Log.logException(ex);
        } catch (SQLException sqlException) {
            throw new BusinessException("DataBase connection failed ", sqlException);
        }
        return preliminarProject;
    }

     /*
        *@params preliminarProject Anteproyecto al cual se le añaden los colaboradores
        *@return Si el anteproyecto pudo ser guardar sus colaboradores  (true) o no (false) en la base de datos 
        *@throws Se cacho una excepción de tipo SQLException 
        *@see PreliminarProject
    */
    public boolean addedSucessfulColaborators(PreliminarProject preliminarProject) throws BusinessException {
        boolean addColaboratorSuccess=false;
        int idPreliminarProject=preliminarProject.getKey();
        ArrayList<Member> colaborators= preliminarProject.getMembers();
         try {
                Connector connectorDataBase=new Connector();
                Connection connectionDataBase = connectorDataBase.getConnection();
                String insertColaborators = "INSERT INTO Colabora(idAnteproyecto,cedula,rol) VALUES (?,?,?)";
                int i=0;
                while(i< colaborators.size()){
                    PreparedStatement preparedStatement = connectionDataBase.prepareStatement(insertColaborators);
                    preparedStatement.setInt(1, idPreliminarProject);
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
        *@params preliminarProject El Anteproyecto al cual se le añaden los estudiantes
        *@return Si el anteproyecto pudo ser guardar sus estudiantes (true) o no (false) en la base de datos 
        *@throws Se cacho una excepción de tipo SQLException
        *@see PreliminarProject
    */
    public boolean addedSucessfulStudents(PreliminarProject preliminarProject) throws BusinessException {
        boolean addStudentsSuccess=false;
        int idPreliminarProject=preliminarProject.getKey();
        ArrayList<Student> students= preliminarProject.getStudents();
         try {
                Connector connectorDataBase=new Connector();
                Connection connectionDataBase = connectorDataBase.getConnection();
                String insertColaborators = "INSERT INTO Realiza(idAnteproyecto,matricula) VALUES (?,?)";
                int i=0;
                while(i< students.size()){
                    PreparedStatement preparedStatement = connectionDataBase.prepareStatement(insertColaborators);
                    preparedStatement.setInt(1, idPreliminarProject);
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
        *@params idPreliminarProject Identificador del anteproyecto del cual se buscan sus colaboradores 
        *@return Listado de colaboradores del anteproyecto
        *@throws Se cacho una excepción de tipo SQLException
    */
    public ArrayList<Member> getColaborators(int idPreliminarProject) throws BusinessException {
        ArrayList<Member> colaborators= new ArrayList<Member> ();
        MemberDAO memberDAO= new MemberDAO();
        try{
            Connector connectorDataBase = new Connector();
            Connection connectionDataBase = connectorDataBase.getConnection();
            String query="SELECT cedula,rol FROM Colabora where idAnteproyecto=?";

               PreparedStatement preparedStatement = connectionDataBase.prepareStatement(query);
               preparedStatement.setInt(1, idPreliminarProject);
               ResultSet resultSet;
               resultSet = preparedStatement.executeQuery();
               while(resultSet.next()){
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
        *@params preliminarProject Anteproyecto al cual se le eliminan los colaboradores
        *@return Si fue posible eliminar los colaboradores de un anteproyecto (true) o no (false) en la base de datos 
        *@throws Se cacho una excepción de tipo SQLException 
        *@see PreliminarProject
    */

    @Override
    public boolean deletedSucessfulColaborators(PreliminarProject preliminarProject) throws BusinessException {
        boolean deleteSucess=false;
        int idPreliminarProject=preliminarProject.getKey();
        ArrayList<Member> colaborators= preliminarProject.getMembers();
        try{
            Connector connectorDataBase=new Connector();
            Connection connectionDataBase = connectorDataBase.getConnection();
            String delete = "delete from Colabora where cedula=? and idAnteproyecto=?";
     
            PreparedStatement preparedStatement = connectionDataBase.prepareStatement(delete);
            int i=0;
            while(i < colaborators.size()){
               preparedStatement.setString(1, colaborators.get(i).getProfessionalLicense());
               preparedStatement.setInt(2, idPreliminarProject);
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
        *@params idPreliminarProject Identificador del anteproyecto del cual se buscan sus estudiantes
        *@return Listado de estudiantes del anteproyecto
        *@throws Se cacho una excepción de tipo SQLException
    */
    @Override
    public ArrayList<Student> getStudents(int idPreliminarProject) throws BusinessException {
        ArrayList<Student> students= new ArrayList<Student> ();
        StudentDAO studentDAO= new StudentDAO();
        try{
            Connector connectorDataBase = new Connector();
            Connection connectionDataBase = connectorDataBase.getConnection();
            String query="SELECT matricula FROM Realiza where idAnteproyecto=?";

               PreparedStatement preparedStatement = connectionDataBase.prepareStatement(query);
               preparedStatement.setInt(1, idPreliminarProject);
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
        *@params preliminarProject Anteproyecto al cual se le eliminan los estudiantes
        *@return Si fue posible eliminar los estudiantes de un anteproyecto (true) o no (false) en la base de datos 
        *@throws Se cacho una excepción de tipo SQLException
        *@see PreliminarProject
    */
    
    @Override
    public boolean deletedSucessfulStudents(PreliminarProject preliminarProject) throws BusinessException {
        boolean deleteSucess=false;
        int idPreliminarProject=preliminarProject.getKey();
        ArrayList<Student> students= preliminarProject.getStudents();
        try{
            Connector connectorDataBase=new Connector();
            Connection connectionDataBase = connectorDataBase.getConnection();
            String delete = "delete from Realiza where matricula=? and idAnteproyecto=?";
     
            PreparedStatement preparedStatement = connectionDataBase.prepareStatement(delete);
            int i=0;
            while(i< students.size()){
               preparedStatement.setString(1, students.get(i).getEnrollment());
               preparedStatement.setInt(2, idPreliminarProject);
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
