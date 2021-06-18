/*
        *@author Karina Valdes
        *@see IReceptionWorkDAO
        *@see Member
        *@see Student
        *@see PreliminarProject
        *@see LGAC
    */
package businessLogic;

import dataaccess.Connector;
import domain.LGAC;
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

    public boolean savedSucessful(ReceptionWork receptionWork) throws BusinessException {
        boolean value=false;
        int idPreliminarProject= receptionWork.getPreliminarProject().getKey();
            try {
                Connector connectorDataBase=new Connector();
                Connection connectionDataBase = connectorDataBase.getConnection();
                String insertPreliminarProject = "INSERT INTO TrabajoRecepcional(titulo, tipo, descripcion, fechaInicio,fechaFin, estadoActual, idAnteproyecto ,clave_CA) VALUES (?,?,?,?,?,?,?,?)";
            
                PreparedStatement preparedStatement = connectionDataBase.prepareStatement(insertPreliminarProject );
                preparedStatement.setString(1, receptionWork.getTitle());
                preparedStatement.setString(2, receptionWork.getType());
                preparedStatement.setString(3, receptionWork.getDescription());
                preparedStatement.setString(4, receptionWork.getDateStart());
                preparedStatement.setString(5, receptionWork.getDateEnd());
                preparedStatement.setString(6, receptionWork.getActualState());
                preparedStatement.setInt(7,idPreliminarProject );
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

    public boolean updatedSucessful(int id, ReceptionWork receptionWork) throws BusinessException {
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
                int idPreliminarProject = resultSet.getInt("idAnteproyecto");
                String keyGroupAcademic = resultSet.getString("clave_CA");
                receptionWork=new ReceptionWork(title,type, description, dateStart, dateEnd, actualState,keyGroupAcademic);
                receptionWork.setKey(idReceptionWork);
                PreliminarProjectDAO preliminarProjectDAO = new PreliminarProjectDAO();
                receptionWork.setPreliminarProject(preliminarProjectDAO.getById(idPreliminarProject));
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
                    int idPreliminarProject = resultSet.getInt("idAnteproyecto");
                     String keyGroup = resultSet.getString("clave_CA");
                   
                    ReceptionWork receptionWorkAuxiliar = new ReceptionWork(title,type, description, dateStart, dateEnd, actualState, keyGroup);
                    receptionWorkAuxiliar.setKey(key);
                   PreliminarProjectDAO preliminarProjectDAO = new PreliminarProjectDAO();
                   receptionWorkAuxiliar.setPreliminarProject(preliminarProjectDAO.getById(idPreliminarProject));
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

    @Override
    public ArrayList<Member> getColaborators(int idPreliminarProject) throws BusinessException {
        ArrayList<Member> colaborators= new ArrayList<Member> ();
        MemberDAO memberDAO= new MemberDAO();
        try{
            Connector connectorDataBase = new Connector();
            Connection connectionDataBase = connectorDataBase.getConnection();
            String query = "SELECT cedula,rol FROM Dirige where idTrabajoRecepcional=?";

               PreparedStatement preparedStatement = connectionDataBase.prepareStatement(query);
               preparedStatement.setInt(1, idPreliminarProject);
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

    public boolean addedSucessfulLGACs(ReceptionWork receptionWork) throws BusinessException {
        boolean addLGACSucces = false;
        int idReceptionWork = receptionWork.getKey();
        ArrayList<LGAC> lgacs = receptionWork.getLGACs();
         try {
                Connector connectorDataBase = new Connector();
                Connection connectionDataBase = connectorDataBase.getConnection();
                int i=0;
                while(i< lgacs.size()){
                    PreparedStatement preparedStatement = connectionDataBase.prepareStatement("INSERT INTO CultivaTrabajoRecepcional(idTrabajoRecepcional,nombreLGAC) VALUES (?,?)");
                    preparedStatement.setInt(1, idReceptionWork);
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

    public ArrayList<LGAC> getLGACs(int idReceptionWork) throws BusinessException {
        ArrayList<LGAC> lgacs = new ArrayList<LGAC>();
        LGACDAO lgacDAO= new LGACDAO();
        try{
            Connector connectorDataBase = new Connector();
            Connection connectionDataBase = connectorDataBase.getConnection();
               PreparedStatement preparedStatement = connectionDataBase.prepareStatement("SELECT nombreLGAC FROM CultivaTrabajoRecepcional where idTrabajoRecepcional = ?");
               preparedStatement.setInt(1, idReceptionWork);
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
    public boolean deletedSucessfulLGACs(ReceptionWork receptionWork) throws BusinessException {
        boolean deleteSucess=false;
        int idReceptionWork=receptionWork.getKey();
        ArrayList<LGAC> lgacs= receptionWork.getLGACs();
        try{
            Connector connectorDataBase=new Connector();
            Connection connectionDataBase = connectorDataBase.getConnection();
            String delete = "delete from CultivaTrabajoRecepcional where nombreLGAC=? and idTrabajoRecepcional=?";
     
            PreparedStatement preparedStatement = connectionDataBase.prepareStatement(delete);
            int i=0;
            while(i< lgacs.size()){
               preparedStatement.setString(1, lgacs.get(i).getName());
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
