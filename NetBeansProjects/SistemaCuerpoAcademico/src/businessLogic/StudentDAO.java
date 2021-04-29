
package businessLogic;

import dataaccess.Connector;
import domain.Student;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import log.BusinessException;
import log.Log;

public class StudentDAO implements IStudentDAO{
    public boolean save(Student student) throws BusinessException{
         boolean saveSuccess=false;
            try {
                Connector connectorDataBase=new Connector();
                Connection connectionDataBase = connectorDataBase.getConnection();
                String insertStudent = "INSERT INTO Estudiante(matricula, nombre) VALUES (?,?)";
            
                PreparedStatement preparedStatement;
                preparedStatement = connectionDataBase.prepareStatement(insertStudent);
                preparedStatement.setString(1, student.getEnrollment());
                preparedStatement.setString(2, student.getName());
                
                preparedStatement.executeUpdate();
                saveSuccess=true;
                connectorDataBase.disconnect();
            } catch (SQLException sqlException) {
                throw new BusinessException("DataBase connection failed ", sqlException);
            } catch (ClassNotFoundException ex) {
                Log.logException(ex);
            }
        return saveSuccess;
    }
    
    public Student getByEnrollment(String enrollment)throws BusinessException{
        Student studentAuxiliar = null;
        try{
            Connector connectorDataBase=new Connector();
            Connection connectionDataBase = connectorDataBase.getConnection();
            ResultSet resultSet;
            String selectStudent = "SELECT * from  Estudiante where matricula=?";
     
            PreparedStatement preparedStatement = connectionDataBase.prepareStatement(selectStudent);
            preparedStatement.setString(1, enrollment);        
            resultSet=preparedStatement.executeQuery();
           
            if(resultSet.next()){
                String enrollmentResult= resultSet.getString("matricula");
                String name= resultSet.getString("nombre");
               
                studentAuxiliar=new Student(enrollmentResult, name);
            }
                connectorDataBase.disconnect();
        }catch(SQLException sqlException) {
            throw new BusinessException("DataBase connection failed ", sqlException);
        } catch (ClassNotFoundException ex) {
            Log.logException(ex);
        }
        return studentAuxiliar;
    }
}
