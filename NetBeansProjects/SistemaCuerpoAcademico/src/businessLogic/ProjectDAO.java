package businessLogic;

import dataaccess.Connector;
import domain.LGAC;
import domain.Member;
import domain.Project;
import domain.ReceptionWork;
import domain.Student;
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
public class ProjectDAO implements IProjectDAO {

        @Override
        public boolean save(Project project) throws BusinessException{
                boolean saveSuccess = false;
                    try{
                        Connector connectorDataBase = new Connector();
                        Connection connectionDataBase = connectorDataBase.getConnection();
                        try{
                            
                            PreparedStatement insertProjectStatment;
                            insertProjectStatment = connectionDataBase.prepareStatement("INSERT INTO Proyecto(titulo,descripcion,fechaInicio,fechaFin) VALUES(?,?,?,?) ");
                            insertProjectStatment.setString(1, project.getTitle());
                            insertProjectStatment.setString(2,  project.getDescription());
                            insertProjectStatment.setString(3, project.getStartDate());
                            insertProjectStatment.setString(4, project.getFinishDate());
                            
                            insertProjectStatment.executeUpdate();
                            
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
        public ArrayList<Project>  getProjects() throws BusinessException{
                     ArrayList<Project> projectList = new ArrayList<>();
                     try{
                        Connector connectorDataBase = new Connector();
                        Connection connectionDataBase = connectorDataBase.getConnection();
                        try{
                            
                            PreparedStatement getProjectsStatment;
                            getProjectsStatment = connectionDataBase.prepareStatement("SELECT * FROM Proyecto");
                            ResultSet projectResultSet;
                            
                            projectResultSet = getProjectsStatment.executeQuery();
                            
                            while(projectResultSet.next()){
                                int idProject = projectResultSet.getInt("idProyecto");
                                String title = projectResultSet.getString("titulo");
                                String description = projectResultSet.getString("descripcion");
                                String startDate = projectResultSet.getString("fechaInicio");
                                String finishDate = projectResultSet.getString("fechaFin");
                                Project projectData = new Project(idProject,title, description, startDate, finishDate);
                                projectList.add(projectData);
                            }
                              
                            connectorDataBase.disconnect();
                           
                        }catch(SQLException sqlException) {
                            throw new BusinessException("Parameter index ", sqlException);
                        }
                    }catch(ClassNotFoundException ex) {
                        Log.logException(ex);
                    }
                    return projectList;  
        }
        
        @Override
        public int  searchId(Project project) throws BusinessException {
                int idProject = 0;
                try{
                        Connector connectorDataBase = new Connector();
                        Connection connectionDataBase = connectorDataBase.getConnection();
                        try{
                            
                            PreparedStatement projectStatment;
                            projectStatment = connectionDataBase.prepareStatement("SELECT idProyecto FROM Proyecto where titulo = ?");
                            projectStatment.setString(1,project.getTitle());
                            ResultSet projectResultSet;
                            
                            projectResultSet = projectStatment.executeQuery();
                            
                            if(projectResultSet.next()){
                                idProject = projectResultSet.getInt("idProyecto");
                            }
                              
                            connectorDataBase.disconnect();
                           
                        }catch(SQLException sqlException) {
                            throw new BusinessException("Parameter index ", sqlException);
                        }
                    }catch(ClassNotFoundException ex) {
                        Log.logException(ex);
                    }
                    return idProject;
        }
        
        @Override
        public boolean findProjectById(int idProject){
           boolean isRegistered = false;
           if(idProject != 0 ){
                isRegistered = true;
           }
           return isRegistered;
        }
        
        @Override
        public Project getProjectById(int idProject) throws BusinessException{
                     Project project = null;
                     try{
                        Connector connectorDataBase = new Connector();
                        Connection connectionDataBase = connectorDataBase.getConnection();
                        try{
                            
                            PreparedStatement getProjectStatment;
                            getProjectStatment = connectionDataBase.prepareStatement("SELECT * FROM Proyecto where idProyecto = ?");
                            getProjectStatment.setInt(1,idProject);
                            ResultSet projectResultSet;           
                            projectResultSet = getProjectStatment.executeQuery();
                            
                            if(projectResultSet.next()){
                                int id = projectResultSet.getInt("idProyecto");
                                String title = projectResultSet.getString("titulo");
                                String description = projectResultSet.getString("descripcion");
                                String startDate = projectResultSet.getString("fechaInicio");
                                String finishDate = projectResultSet.getString("fechaFin");
                                project = new Project(id, title, description, startDate, finishDate);
                            }
                              
                            connectorDataBase.disconnect();
                           
                        }catch(SQLException sqlException) {
                            throw new BusinessException("Parameter index ", sqlException);
                        }
                    }catch(ClassNotFoundException ex) {
                        Log.logException(ex);
                    }
                    return project;  
        }
        
    @Override
    public boolean update(Project newProject) throws BusinessException {
        boolean updateSucess = false;
        Connector connectorDataBase=new Connector();
        try {
            Connection connectionDataBase = connectorDataBase.getConnection();
            PreparedStatement preparedStatement = connectionDataBase.prepareStatement("UPDATE Proyecto set titulo = ?, descripcion = ?, fechaInicio = ?, fechaFin = ? WHERE idProyecto = ? ");
            preparedStatement.setString(1, newProject.getTitle());
            preparedStatement.setString(2, newProject.getDescription());
            preparedStatement.setString(3, newProject.getStartDate());
            preparedStatement.setString(4, newProject.getFinishDate());
            preparedStatement.setInt(5, newProject.getIdProject());
                
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
    public boolean addStudents(Project project) throws BusinessException {
        boolean addStudentsSuccess = false;
        int idProject = project.getIdProject();
        ArrayList<Student> students = project.getStudents();
         try {
                Connector connectorDataBase = new Connector();
                Connection connectionDataBase = connectorDataBase.getConnection();
                int i=0;
                while(i< students.size()){
                    PreparedStatement preparedStatement = connectionDataBase.prepareStatement("INSERT INTO ParticipaProyecto(idProyecto,matricula) VALUES (?,?)");
                    preparedStatement.setInt(1, idProject);
                    preparedStatement.setString(2, students.get(i).getEnrollment());
                    preparedStatement.executeUpdate();
                    i++;
                } 
                addStudentsSuccess = true; 
               connectorDataBase.disconnect();
            } catch (SQLException sqlException) {
                throw new BusinessException("DataBase connection failed ", sqlException);
            } catch (ClassNotFoundException ex) {
                Log.logException(ex);
            }
        return addStudentsSuccess;
    }

    @Override
    public boolean addColaborators(Project project) throws BusinessException {
        boolean addColaboratorsSuccess = false;
        int idProject = project.getIdProject();
        ArrayList<Member> members = project.getMembers();
         try {
                Connector connectorDataBase = new Connector();
                Connection connectionDataBase = connectorDataBase.getConnection();
                int i=0;
                while(i< members.size()){
                    PreparedStatement preparedStatement = connectionDataBase.prepareStatement("INSERT INTO DesarrollaProyecto(idProyecto,cedula) VALUES (?,?)");
                    preparedStatement.setInt(1, idProject);
                    preparedStatement.setString(2, members.get(i).getProfessionalLicense());
                    preparedStatement.executeUpdate();
                    i++;
                } 
                addColaboratorsSuccess = true; 
               connectorDataBase.disconnect();
            } catch (SQLException sqlException) {
                throw new BusinessException("DataBase connection failed ", sqlException);
            } catch (ClassNotFoundException ex) {
                Log.logException(ex);
            }
        return addColaboratorsSuccess;
    }

    @Override
    public boolean addLGAC(Project project) throws BusinessException {
        boolean addLGACSucces = false;
        int idProject = project.getIdProject();
        ArrayList<LGAC> lgacs = project.getLGACs();
         try {
                Connector connectorDataBase = new Connector();
                Connection connectionDataBase = connectorDataBase.getConnection();
                int i=0;
                while(i< lgacs.size()){
                    PreparedStatement preparedStatement = connectionDataBase.prepareStatement("INSERT INTO CultivaProyecto(idProyecto,nombreLGAC) VALUES (?,?)");
                    preparedStatement.setInt(1, idProject);
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

    @Override
    public ArrayList<Member> getColaborators(Project project) throws BusinessException {
        ArrayList<Member> members = new ArrayList<Member>();
        MemberDAO memberDAO= new MemberDAO();
        try{
            Connector connectorDataBase = new Connector();
            Connection connectionDataBase = connectorDataBase.getConnection();
               PreparedStatement preparedStatement = connectionDataBase.prepareStatement("SELECT cedula FROM DesarrollaProyecto where idProyecto = ?");
               preparedStatement.setInt(1, project.getIdProject());
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

    @Override
    public ArrayList<Student> getStudents(Project project) throws BusinessException {
        ArrayList<Student> students= new ArrayList<Student>();
        StudentDAO studentDAO= new StudentDAO();
        try{
            Connector connectorDataBase = new Connector();
            Connection connectionDataBase = connectorDataBase.getConnection();
               PreparedStatement preparedStatement = connectionDataBase.prepareStatement("SELECT matricula FROM ParticipaProyecto where idProyecto = ?");
               preparedStatement.setInt(1, project.getIdProject());
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
    public ArrayList<LGAC> getLGACs(Project project) throws BusinessException {
        ArrayList<LGAC> lgacs = new ArrayList<LGAC>();
        LGACDAO lgacDAO= new LGACDAO();
        try{
            Connector connectorDataBase = new Connector();
            Connection connectionDataBase = connectorDataBase.getConnection();
               PreparedStatement preparedStatement = connectionDataBase.prepareStatement("SELECT nombreLGAC FROM CultivaProyecto where idProyecto = ?");
               preparedStatement.setInt(1, project.getIdProject());
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
    public boolean addReceptionWork(Project project) throws BusinessException {
        boolean addReceptionWorkSucces = false;
        int idProject = project.getIdProject();
        ArrayList<ReceptionWork> receptionWorks = project.getReceptionWorks();
         try {
                Connector connectorDataBase = new Connector();
                Connection connectionDataBase = connectorDataBase.getConnection();
                int i=0;
                while(i< receptionWorks.size()){
                    PreparedStatement preparedStatement = connectionDataBase.prepareStatement("INSERT INTO ProyectoTrabajoRecepcional(idProyecto,idTrabajoRecepcional) VALUES (?,?)");
                    preparedStatement.setInt(1, idProject);
                    preparedStatement.setInt(2, receptionWorks.get(i).getKey());
                    preparedStatement.executeUpdate();
                    i++;
                } 
                addReceptionWorkSucces = true; 
               connectorDataBase.disconnect();
            } catch (SQLException sqlException) {
                throw new BusinessException("DataBase connection failed ", sqlException);
            } catch (ClassNotFoundException ex) {
                Log.logException(ex);
            }
        return addReceptionWorkSucces;
    }

    @Override
    public ArrayList<ReceptionWork> getReceptionWorks(Project project) throws BusinessException {
        ArrayList<ReceptionWork> receptionWorks = new ArrayList<ReceptionWork>();
        ReceptionWorkDAO receptionWorkDAO= new ReceptionWorkDAO();
        try{
            Connector connectorDataBase = new Connector();
            Connection connectionDataBase = connectorDataBase.getConnection();
               PreparedStatement preparedStatement = connectionDataBase.prepareStatement("SELECT idTrabajoRecepcional FROM ProyectoTrabajoRecepcional where idProyecto = ?");
               preparedStatement.setInt(1, project.getIdProject());
               ResultSet resultSet;
               resultSet = preparedStatement.executeQuery();
               while(resultSet.next()){
                    int idReceptionWork = resultSet.getInt("idTrabajoRecepcional");
                    ReceptionWork receptionWork = receptionWorkDAO.getReceptionWorkById(idReceptionWork);
                    receptionWorks.add(receptionWork);
                }
                connectorDataBase.disconnect();
            }catch(SQLException sqlException) {
                   throw new BusinessException("Database failed ", sqlException);         
            }catch(ClassNotFoundException ex) {
                        Log.logException(ex);
            }

        return receptionWorks;
    }
}