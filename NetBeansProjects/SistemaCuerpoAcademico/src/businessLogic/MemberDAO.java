package businessLogic;
import domain.Member;
import domain.GroupAcademic;
import dataaccess.Connector;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class MemberDAO implements IMemberDAO{

    @Override
    public void saveMember(Member member, GroupAcademic groupAcademic){
        try {
            
            Connector connectorDataBase = new Connector();
            Connection connectionDataBase = connectorDataBase.getConnection();
            String insertMember = "INSERT INTO Miembro(cedula, nombre, rol, gradoMaximo, correo, clave_CA) VALUES (?,?,?,?,?,?)";
            PreparedStatement preparedStatement= connectionDataBase.prepareStatement(insertMember);
            preparedStatement.setString(1, member.getProfessionalLicense());
            preparedStatement.setString(2, member.getName());
            preparedStatement.setString(3, member.getRole());
            preparedStatement.setString(4, member.getMaxDegreeStudy());
            preparedStatement.setString(5, member.getEmail());
            preparedStatement.setString(6, groupAcademic.getKey());
            preparedStatement.executeUpdate();
            connectorDataBase.disconnect();
            
        } catch (SQLException sqlException){
            throw new IllegalStateException("DataBase connection failed ", sqlException);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MemberDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String searchProfessionalLicense(Member member) {
        String professionalLicenseAuxiliar = "";
        
        try{
            Connector connectorDataBase=new Connector();
            Connection connectionDataBase = connectorDataBase.getConnection();
            ResultSet resultSet;
            String selectLicenseMember = "SELECT cedula from  Miembro where nombre=? and rol=? and gradoMaximo=? and correo=?";
     
            PreparedStatement preparedStatement = connectionDataBase.prepareStatement(selectLicenseMember);
            preparedStatement.setString(1, member.getName());          
            preparedStatement.setString(2, member.getRole());
            preparedStatement.setString(3, member.getMaxDegreeStudy());
            preparedStatement.setString(4, member.getEmail());
            
            resultSet=preparedStatement.executeQuery();
            if(resultSet.next()){
                professionalLicenseAuxiliar = resultSet.getString("cedula");
            }
                connectorDataBase.disconnect();
                
        }catch(SQLException sqlException) {
            throw new IllegalStateException("DataBase connection failed ", sqlException);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MemberDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return professionalLicenseAuxiliar;
    }

    @Override
    public Member getMemberByLicense(String professionalLicenseMember) {
        Member memberAuxiliar = null;
        try{
            Connector connectorDataBase=new Connector();
            Connection connectionDataBase = connectorDataBase.getConnection();
            ResultSet resultSet;
            String selectLicenseMember = "SELECT * from Miembro where cedula=?";
     
            PreparedStatement preparedStatement = connectionDataBase.prepareStatement(selectLicenseMember);
            preparedStatement.setString(1, professionalLicenseMember);        
            resultSet=preparedStatement.executeQuery();
           
            if(resultSet.next()){
                String professionalLicense = resultSet.getString("cedula");
                String name = resultSet.getString("nombre");
                String role = resultSet.getString("rol");
                String maxDegreeStudy = resultSet.getString("gradoMaximo");
                String email =resultSet.getString("correo");
               
                memberAuxiliar = new Member(professionalLicense, name, role, maxDegreeStudy, email);
            }
                connectorDataBase.disconnect();
                
        }catch(SQLException sqlException) {
            throw new IllegalStateException("DataBase connection failed ", sqlException);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MemberDAO.class.getName()).log(Level.SEVERE, null, ex);
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