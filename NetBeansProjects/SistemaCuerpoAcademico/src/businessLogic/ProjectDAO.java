package businessLogic;

import dataaccess.Connector;
import domain.Project;
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
}