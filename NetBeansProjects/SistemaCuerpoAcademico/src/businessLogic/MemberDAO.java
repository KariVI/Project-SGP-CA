package businessLogic;
import domain.Member;
import domain.GroupAcademic;
import dataaccess.Connector;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import log.BusinessException;
import log.Log;

public class MemberDAO implements IMemberDAO{

    @Override
    public boolean saveMember(Member member) throws BusinessException{
        boolean saveSuccess = false;
        try {
            
            Connector connectorDataBase = new Connector();
            Connection connectionDataBase = connectorDataBase.getConnection();
            String insertMember = "INSERT INTO Miembro(cedula, nombre, rol, grado, nombreGrado, universidad, anio, estado, clave) VALUES (?,?,?,?,?,?,?,?,?)";
            PreparedStatement preparedStatement= connectionDataBase.prepareStatement(insertMember);
            preparedStatement.setString(1, member.getProfessionalLicense());
            preparedStatement.setString(2, member.getName());
            preparedStatement.setString(3, member.getRole());
            preparedStatement.setString(4, member.getDegree());
            preparedStatement.setString(5, member.getNameDegree());
            preparedStatement.setString(6, member.getUniversityName());
            preparedStatement.setInt(7, member.getDegreeYear());
            preparedStatement.setString(8, "Activo");
            preparedStatement.setString(9, member.getKeyGroupAcademic());
            preparedStatement.executeUpdate();
            connectorDataBase.disconnect();
            
            saveSuccess = true;
            
        } catch (SQLException sqlException){
            throw new BusinessException("DataBase connection failed ", sqlException);
        } catch (ClassNotFoundException ex) {
            Log.logException(ex);
        }
        
        return saveSuccess;
    }

    @Override
    public String searchProfessionalLicense(String memberName) throws BusinessException{
        String professionalLicenseAuxiliar = "";
        
        try{
            Connector connectorDataBase=new Connector();
            Connection connectionDataBase = connectorDataBase.getConnection();
            ResultSet resultSet;
            String selectLicenseMember = "SELECT cedula from  Miembro where nombre=? ";
     
            PreparedStatement preparedStatement = connectionDataBase.prepareStatement(selectLicenseMember);
            preparedStatement.setString(1, memberName);          
            
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                professionalLicenseAuxiliar = resultSet.getString("cedula");
            }
                connectorDataBase.disconnect();
                
        }catch(SQLException sqlException) {
            throw new BusinessException("DataBase connection failed ", sqlException);
        } catch (ClassNotFoundException ex) {
            Log.logException(ex);
        }
        
        return professionalLicenseAuxiliar;
    }

    @Override
    public Member getMemberByLicense(String professionalLicenseMember) throws BusinessException {
        Member memberAuxiliar = null;
        try{
            Connector connectorDataBase=new Connector();
            Connection connectionDataBase = connectorDataBase.getConnection();
            ResultSet resultSet;
            String selectLicenseMember = "SELECT * from Miembro where cedula = ?";
     
            PreparedStatement preparedStatement = connectionDataBase.prepareStatement(selectLicenseMember);
            preparedStatement.setString(1, professionalLicenseMember);        
            resultSet=preparedStatement.executeQuery();
           
            if(resultSet.next()){
                String professionalLicense = resultSet.getString("cedula");
                String name = resultSet.getString("nombre");
                String role = resultSet.getString("rol");
                String degree = resultSet.getString("grado");
                String nameDegree = resultSet.getString("nombreGrado");
                String nameUniversity = resultSet.getString("universidad");
                int year = resultSet.getInt("anio");
                String state = resultSet.getString("estado");
                String key = resultSet.getString("clave");
                
                memberAuxiliar = new Member(professionalLicense, name, role, degree, nameDegree, nameUniversity, year, state, key);
            }
                connectorDataBase.disconnect();
                
        }catch(SQLException sqlException) {
            throw new BusinessException("DataBase connection failed ", sqlException);
        } catch (ClassNotFoundException ex) {
            Log.logException(ex);
        }
        return memberAuxiliar;
    }
    
    @Override
    public boolean findMemberByLicense(String professionalLicense) {
        boolean value=true;
       if (professionalLicense == null){
           value=false;
       }
       return value;
    }
}