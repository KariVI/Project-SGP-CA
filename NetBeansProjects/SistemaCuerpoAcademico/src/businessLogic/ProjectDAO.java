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

/*
        *@author Mariana Vargas
*/
public class ProjectDAO implements IProjectDAO {
    
    /*
        *@param project Proyecto a guardar 
        *@return Si el proyecto pudo ser guardada (true) o no (false) en la base de datos 
        *@throws BusinessException Se cacho una excepción de tipo SQLException
    */
    @Override
    public boolean savedSucessful(Project project) throws BusinessException{
        boolean saveSuccess = false;
        try{
            Connector connectorDataBase = new Connector();
            Connection connectionDataBase = connectorDataBase.getConnection();
            try{
                PreparedStatement insertProjectStatment;
                insertProjectStatment = connectionDataBase.prepareStatement("INSERT INTO Proyecto(titulo,descripcion,fechaInicio,fechaFin,clave) VALUES(?,?,?,?,?) ");
                insertProjectStatment.setString(1, project.getTitle());
                insertProjectStatment.setString(2,  project.getDescription());
                insertProjectStatment.setString(3, project.getStartDate());
                insertProjectStatment.setString(4, project.getFinishDate());
                insertProjectStatment.setString(5, project.getGroupAcademicKey());
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
    
     /*
        *@return Todos los proyectos de la base de datos (ArrayList<Project>)
        *@throws BusinessException Se cacho una excepción de tipo SQLException
    */       
    @Override
    public ArrayList<Project>  getProjects() throws BusinessException{
        ArrayList<Project> projectList = new ArrayList<Project>();
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
                    String groupAcademicKey = projectResultSet.getString("clave");
                    Project projectData = new Project(idProject,title, description, startDate, finishDate,groupAcademicKey);
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
    
    /*
        *@param project Proyecto del cual se busca obtener el ID
        *@return Si se encuentra el proyecto, su ID (int)
        *@throws BusinessException Se cacho una excepción de tipo SQLException o si no se encontro el proyecto a buscar
    */                    
    @Override
    public int searchId(Project project) throws BusinessException{
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
                }else{
                    throw new BusinessException("Project not found");
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
    
   /*
        *@params idProject ID del proyecto a buscar
        *@return El proyecto con el id (Project)
        *@throws BusinessException Se cacho una excepción de tipo SQLException o si no se encontro el proyecto a a buscar
    */         
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
                    String groupAcademicKey = projectResultSet.getString("Clave");
                    project = new Project(id, title, description, startDate, finishDate, groupAcademicKey);
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
    
    /*
        *@param newProject proyecto con los datos nuevos a actualizar
        *@return Si el proyecto pudo ser actualizado (true) o no (false) en la base de datos
        *@throws BusinessException Se cacho una excepción de tipo SQLException
    */          
    @Override
    public boolean updatedSucessful(Project newProject) throws BusinessException {
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
    
    /*
        *@param project Proyecto al cual se añaden los estudiantes
        *@return Si los estudiantes pueden ser guardados (true) o no (false) en la base de datos
        *@throws BusinessException Se cacho una excepción de tipo SQLException
    */
    @Override
    public boolean addStudents(Project project) throws BusinessException {
        boolean addStudentsSuccess = false;
         try {
                Connector connectorDataBase = new Connector();
                Connection connectionDataBase = connectorDataBase.getConnection();
                int i=0;
                int idProject = project.getIdProject();
                 ArrayList<Student> students = project.getStudents();
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
    
    /*
        *@param project Proyecto al cual se añaden los colaboradores
        *@return Si los colaboradores pueden ser guardados (true) o no (false) en la base de datos
        *@throws BusinessException Se cacho una excepción de tipo SQLException
    */
    @Override
    public boolean addColaborators(Project project) throws BusinessException {
        boolean addColaboratorsSuccess = false;
         try {
                Connector connectorDataBase = new Connector();
                Connection connectionDataBase = connectorDataBase.getConnection();
                int i=0;
                int idProject = project.getIdProject();
                ArrayList<Member> members = project.getMembers();
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
    
    /*
        *@param project Proyecto al cual se añaden las LGACs
        *@return Si las LGACs pueden ser guardadas (true) o no (false) en la base de datos
        *@throws BusinessException Se cacho una excepción de tipo SQLException
    */
    @Override
    public boolean addLGAC(Project project) throws BusinessException {
        boolean addLGACSucces = false;
         try {
                Connector connectorDataBase = new Connector();
                Connection connectionDataBase = connectorDataBase.getConnection();
                ArrayList<LGAC> lgacs = project.getLGACs();
                int idProject = project.getIdProject();
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
    
    /*
        *@param project Proyecto del cual se recuperarán los colaboradores
        *@return Colaboradores del proyecto relacionado (ArrayList<Member>)
        *@throws BusinessException Se cacho una excepción de tipo SQLException
    */
    @Override
    public ArrayList<Member> getColaborators(Project project) throws BusinessException {
        ArrayList<Member> members = new ArrayList<Member>();
        try{
            Connector connectorDataBase = new Connector();
            Connection connectionDataBase = connectorDataBase.getConnection();
               PreparedStatement preparedStatement = connectionDataBase.prepareStatement("SELECT cedula FROM DesarrollaProyecto where idProyecto = ?");
               preparedStatement.setInt(1, project.getIdProject());
               ResultSet resultSet;
               resultSet = preparedStatement.executeQuery();
               MemberDAO memberDAO= new MemberDAO();
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
        *@param project Proyecto del cual se recuperarán los estudiantes
        *@return Estudiantes del proyecto relacionado (ArrayList<Student>)
        *@throws BusinessException Se cacho una excepción de tipo SQLException
    */
    @Override
    public ArrayList<Student> getStudents(Project project) throws BusinessException {
        ArrayList<Student> students= new ArrayList<Student>();
        try{
            Connector connectorDataBase = new Connector();
            Connection connectionDataBase = connectorDataBase.getConnection();
               PreparedStatement preparedStatement = connectionDataBase.prepareStatement("SELECT matricula FROM ParticipaProyecto where idProyecto = ?");
               preparedStatement.setInt(1, project.getIdProject());
               ResultSet resultSet;
               resultSet = preparedStatement.executeQuery();
               StudentDAO studentDAO= new StudentDAO();
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
        *@param project Proyecto del cual se recuperarán las LGACs
        *@return LGACs del proyecto relacionado (ArrayList<LGAC>)
        *@throws BusinessException Se cacho una excepción de tipo SQLException
    */
    @Override
    public ArrayList<LGAC> getLGACs(Project project) throws BusinessException {
        ArrayList<LGAC> lgacs = new ArrayList<LGAC>();
        
        try{
            Connector connectorDataBase = new Connector();
            Connection connectionDataBase = connectorDataBase.getConnection();
               PreparedStatement preparedStatement = connectionDataBase.prepareStatement("SELECT nombreLGAC FROM CultivaProyecto where idProyecto = ?");
               preparedStatement.setInt(1, project.getIdProject());
               ResultSet resultSet;
               resultSet = preparedStatement.executeQuery();
               LGACDAO lgacDAO= new LGACDAO();
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

    /*
        *@param project Proyecto al cual se añaden los trabajos recepcionales
        *@return Si los trabajos recepcionales pueden ser guardadas (true) o no (false) en la base de datos
        *@throws BusinessException Se cacho una excepción de tipo SQLException
    */
    @Override
    public boolean addReceptionWork(Project project) throws BusinessException {
        boolean addReceptionWorkSucces = false;
        int idProject = project.getIdProject();
       
         try {
                Connector connectorDataBase = new Connector();
                Connection connectionDataBase = connectorDataBase.getConnection();
                ArrayList<ReceptionWork> receptionWorks = project.getReceptionWorks();
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
    
    /*
        *@param project Proyecto del cual se recuperarán los trabajos recepcionales
        *@return Trabajos recepcionales del proyecto relacionado (ArrayList<ReceptionWork>)
        *@throws BusinessException Se cacho una excepción de tipo SQLException
    */
    @Override
    public ArrayList<ReceptionWork> getReceptionWorks(Project project) throws BusinessException {
        ArrayList<ReceptionWork> receptionWorks = new ArrayList<ReceptionWork>();
        try{
            Connector connectorDataBase = new Connector();
            Connection connectionDataBase = connectorDataBase.getConnection();
               PreparedStatement preparedStatement = connectionDataBase.prepareStatement("SELECT idTrabajoRecepcional FROM ProyectoTrabajoRecepcional where idProyecto = ?");
               preparedStatement.setInt(1, project.getIdProject());
               ResultSet resultSet;
               resultSet = preparedStatement.executeQuery();
               ReceptionWorkDAO receptionWorkDAO= new ReceptionWorkDAO();
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
    
    /*
        *@param project Proyecto del cual se eliminarán los colaboradores
        *@return Si los colaboradores puedieron ser eliminados (true) o no (false) en la base de datos
        *@throws BusinessException Se cacho una excepción de tipo SQLException
    */    
    @Override
    public boolean deletedSucessfulColaborators(Project project) throws BusinessException {
        boolean deleteSucess = false;
        int idProject = project.getIdProject();
        try{
            Connector connectorDataBase = new Connector();
            Connection connectionDataBase = connectorDataBase.getConnection();
            PreparedStatement preparedStatement = connectionDataBase.prepareStatement("delete from DesarrollaProyecto where idProyecto = ? ");
                 
            preparedStatement.setInt(1, idProject);
            preparedStatement.executeUpdate();           
            
            deleteSucess = true;
            connectorDataBase.disconnect();         
        }catch(SQLException sqlException) {
            throw new BusinessException("DataBase connection failed ", sqlException);
        } catch (ClassNotFoundException ex) {
            Log.logException(ex);
        }
        return deleteSucess;
    }
    
    /*
        *@param project Proyecto del cual se eliminarán los trabajos recepcionales
        *@return Si los trabajos recepcionales puedieron ser eliminados (true) o no (false) en la base de datos
        *@throws BusinessException Se cacho una excepción de tipo SQLException
    */
    @Override
    public boolean deletedSucessfulReceptionWorks(Project project) throws BusinessException {
        boolean deleteSucess = false;
        int idProject = project.getIdProject();
        try{
            Connector connectorDataBase = new Connector();
            Connection connectionDataBase = connectorDataBase.getConnection();
            PreparedStatement preparedStatement = connectionDataBase.prepareStatement("Delete from ProyectoTrabajoRecepcional where idProyecto = ? ");
                 
            preparedStatement.setInt(1, idProject);
            preparedStatement.executeUpdate();           
            
            deleteSucess = true;
            connectorDataBase.disconnect();         
        }catch(SQLException sqlException) {
            throw new BusinessException("DataBase connection failed ", sqlException);
        } catch (ClassNotFoundException ex) {
            Log.logException(ex);
        }
        return deleteSucess;
    }
    
    /*
        *@param project Proyecto del cual se eliminarán los estudiantes
        *@return Si los estudiantes puedieron ser eliminados (true) o no (false) en la base de datos
        *@throws BusinessException Se cacho una excepción de tipo SQLException
    */
    @Override
    public boolean deletedSucessfulStudents(Project project) throws BusinessException {
       boolean deleteSucess = false;
        int idProject = project.getIdProject();
        try{
            Connector connectorDataBase = new Connector();
            Connection connectionDataBase = connectorDataBase.getConnection();
            PreparedStatement preparedStatement = connectionDataBase.prepareStatement("Delete from ParticipaProyecto where idProyecto = ? ");
                 
            preparedStatement.setInt(1, idProject);
            preparedStatement.executeUpdate();           
            
            deleteSucess = true;
            connectorDataBase.disconnect();         
        }catch(SQLException sqlException) {
            throw new BusinessException("DataBase connection failed ", sqlException);
        } catch (ClassNotFoundException ex) {
            Log.logException(ex);
        }
        return deleteSucess;
    }
    
    /*
        *@param project Proyecto del cual se eliminarán las LGACs
        *@return Si las LGACs puedieron ser eliminadas (true) o no (false) en la base de datos
        *@throws BusinessException Se cacho una excepción de tipo SQLException
    */
    @Override
    public boolean deletedSucessfulLGACS(Project project) throws BusinessException {
        boolean deleteSucess = false;
        int idProject = project.getIdProject();
        try{
            Connector connectorDataBase = new Connector();
            Connection connectionDataBase = connectorDataBase.getConnection();
            PreparedStatement preparedStatement = connectionDataBase.prepareStatement("Delete from CultivaProyecto where idProyecto = ? ");
                 
            preparedStatement.setInt(1, idProject);
            preparedStatement.executeUpdate();           
            
            deleteSucess = true;
            connectorDataBase.disconnect();         
        }catch(SQLException sqlException) {
            throw new BusinessException("DataBase connection failed ", sqlException);
        } catch (ClassNotFoundException ex) {
            Log.logException(ex);
        }
        return deleteSucess;
    }
    
}