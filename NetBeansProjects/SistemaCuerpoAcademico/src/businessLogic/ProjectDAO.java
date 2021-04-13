package businessLogic;

import dataaccess.Connector;
import domain.Project;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import log.Log;

/**
 *
 * @author Mariana
 */
public class ProjectDAO implements IProjectDAO {

        @Override
        public boolean save(Project project) {
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
                            throw new IllegalStateException("Parameter index ", sqlException);
                        }
                    }catch(ClassNotFoundException ex) {
                        Logger.getLogger(ProjectDAO.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    return saveSuccess;
        }
        
        @Override
        public ArrayList<Project>  getProjects(){
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
                            throw new IllegalStateException("Parameter index ", sqlException);
                        }
                    }catch(ClassNotFoundException ex) {
                        Logger.getLogger(ProjectDAO.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    return projectList;  
        }
        
        @Override
        public int  searchId(Project project) {
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
                            throw new IllegalStateException("Parameter index ", sqlException);
                        }
                    }catch(ClassNotFoundException ex) {
                        Logger.getLogger(ProjectDAO.class.getName()).log(Level.SEVERE, null, ex);
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
        public Project getProjectById(int idProject){
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
                            throw new IllegalStateException("Parameter index ", sqlException);
                        }
                    }catch(ClassNotFoundException ex) {
                        Logger.getLogger(ProjectDAO.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    return project;  
        }
}