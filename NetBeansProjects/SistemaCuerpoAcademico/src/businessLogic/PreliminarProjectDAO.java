
package businessLogic;

import dataaccess.Connector;
import domain.LGAC;
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

    @Override
    public boolean savedSucessful(PreliminarProject preliminarProject) throws BusinessException {
         boolean value=false;
            try {
                Connector connectorDataBase=new Connector();
                Connection connectionDataBase = connectorDataBase.getConnection();
                String insertPreliminarProject = "INSERT INTO Anteproyecto(titulo,descripcion, fechaInicio,fechaFin ) VALUES (?,?,?,?)";
            
                PreparedStatement preparedStatement = connectionDataBase.prepareStatement(insertPreliminarProject );
                preparedStatement.setString(1, preliminarProject.getTitle());
                preparedStatement.setString(2, preliminarProject.getDescription());
                preparedStatement.setString(3, preliminarProject.getDateStart());
                preparedStatement.setString(4, preliminarProject.getDateEnd());
                
                
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

    @Override
    public int getId(PreliminarProject preliminarProject) throws BusinessException {
        Integer id= 0;
        try{
            Connector connectorDataBase=new Connector();
            Connection connectionDataBase = connectorDataBase.getConnection();
            ResultSet resultSet;
            String selectId = "SELECT idAnteproyecto from  Anteproyecto where titulo=? and descripcion=? and fechaInicio=? and fechaFin=?;";
     
            PreparedStatement preparedStatement = connectionDataBase.prepareStatement(selectId);
            preparedStatement.setString(1, preliminarProject.getTitle());
            preparedStatement.setString(2, preliminarProject.getDescription());
            preparedStatement.setString(3, preliminarProject.getDateStart());
            preparedStatement.setString(4, preliminarProject.getDateEnd());      
            resultSet=preparedStatement.executeQuery();
           
            if(resultSet.next()){
                id=Integer.parseInt(resultSet.getString("idAnteproyecto"));
            }
            connectorDataBase.disconnect();
        }catch(SQLException sqlException) {
            throw new BusinessException("DataBase connection failed ", sqlException);
        } catch (ClassNotFoundException ex) {
            Log.logException(ex);
        }
        return id;
    }

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

    @Override
    public ArrayList<PreliminarProject> getPreliminarProjects() throws BusinessException {
       ArrayList<PreliminarProject> preliminarProjectList = new ArrayList<PreliminarProject>();
        try{
            Connector connectorDataBase = new Connector();
            Connection connectionDataBase = connectorDataBase.getConnection();
            String queryPreliminarProject="SELECT * FROM Anteproyecto";

               PreparedStatement preparedStatement;
               preparedStatement = connectionDataBase.prepareStatement(queryPreliminarProject);
               ResultSet resultSet;
               resultSet = preparedStatement.executeQuery();
               while(resultSet.next()){
                   int key= resultSet.getInt(1);
                    String title = resultSet.getString("titulo");
                    String description = resultSet.getString("descripcion");
                    String dateStart=resultSet.getString("fechaInicio");
                    String dateEnd=resultSet.getString("fechaFin");
                    PreliminarProject preliminarProjectAuxiliar = new PreliminarProject(title,description, dateStart, dateEnd);
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
                preliminarProject=new PreliminarProject(title, description, dateStart, dateEnd);
                preliminarProject.setKey(idPreliminarProject);
            }
            
        } catch (ClassNotFoundException ex) {
            Log.logException(ex);
        } catch (SQLException sqlException) {
            throw new BusinessException("DataBase connection failed ", sqlException);
        }
        return preliminarProject;
    }

  
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

    public ArrayList<Member> getColaborators(int idPreliminarProject) throws BusinessException {
        ArrayList<Member> colaborators= new ArrayList<Member> ();
        MemberDAO memberDAO= new MemberDAO();
        try{
            Connector connectorDataBase = new Connector();
            Connection connectionDataBase = connectorDataBase.getConnection();
            String query="SELECT cedula FROM Colabora where idAnteproyecto=?";

               PreparedStatement preparedStatement = connectionDataBase.prepareStatement(query);
               preparedStatement.setInt(1, idPreliminarProject);
               ResultSet resultSet;
               resultSet = preparedStatement.executeQuery();
               while(resultSet.next()){
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
            while(i< colaborators.size()){
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
    
    public boolean addedSucessfulLGAC(PreliminarProject preliminarProject) throws BusinessException {
        boolean addLGACSucces = false;
        int idPreliminarProject = preliminarProject.getKey();
        ArrayList<LGAC> lgacs =  preliminarProject.getLGACs();
         try {
                Connector connectorDataBase = new Connector();
                Connection connectionDataBase = connectorDataBase.getConnection();
                int i=0;
                while(i< lgacs.size()){
                    PreparedStatement preparedStatement = connectionDataBase.prepareStatement("INSERT INTO CultivaAnteproyecto(idAnteproyecto,nombreLGAC) VALUES (?,?)");
                    preparedStatement.setInt(1, idPreliminarProject);
                    preparedStatement.setString(2, lgacs.get(i).getName());
                    preparedStatement.executeUpdate();
                    i++;
                } 
                addLGACSucces = true; 
               connectorDataBase.disconnect();
            } catch (SQLException sqlException) {
                throw new BusinessException("DataBase connection failed ", sqlException);
            } catch (ClassNotFoundException ex) {
                Log.logException(ex);
            }
        return addLGACSucces;
    }
    
    public ArrayList<LGAC> getLGACs(int idPreliminarProject) throws BusinessException {
        ArrayList<LGAC> lgacs = new ArrayList<LGAC>();
        LGACDAO lgacDAO= new LGACDAO();
        try{
            Connector connectorDataBase = new Connector();
            Connection connectionDataBase = connectorDataBase.getConnection();
               PreparedStatement preparedStatement = connectionDataBase.prepareStatement("SELECT nombreLGAC FROM CultivaAnteproyecto where idAnteproyecto = ?");
               preparedStatement.setInt(1, idPreliminarProject);
               ResultSet resultSet;
               resultSet = preparedStatement.executeQuery();
               while(resultSet.next()){
                    String name = resultSet.getString("nombreLGAC");
                    LGAC lgac = lgacDAO.getLgacByName(name);
                    lgacs.add(lgac);
                }
                connectorDataBase.disconnect();
            }catch(SQLException sqlException) {
                   throw new BusinessException("Database failed ", sqlException);         
            }catch(ClassNotFoundException ex) {
                        Log.logException(ex);
            }

        return lgacs;
    }

    @Override
    public boolean deletedSucessfulLGACS(PreliminarProject preliminarProject) throws BusinessException {
       boolean deleteSucess=false;
        int idPreliminarProject=preliminarProject.getKey();
        ArrayList<LGAC> lgacs= preliminarProject.getLGACs();
        try{
            Connector connectorDataBase=new Connector();
            Connection connectionDataBase = connectorDataBase.getConnection();
            String delete = "delete from CultivaAnteproyecto where idAnteproyecto=? and nombreLGAC=?";
     
            PreparedStatement preparedStatement = connectionDataBase.prepareStatement(delete);
            int i=0;
            while(i< lgacs.size()){
               preparedStatement.setInt(1, idPreliminarProject);
               preparedStatement.setString(2, lgacs.get(i).getName());
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
