
package businessLogic;

import dataaccess.Connector;
import domain.Agreement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import log.BusinessException;
import log.Log;

public class AgreementDAO implements IAgreement{

    @Override
    public boolean savedSucessfulAgreement(Agreement agreement) throws BusinessException {
        boolean saveSuccess = false;
        try{
            Connector connectorDataBase = new Connector();
            Connection connectionDataBase = connectorDataBase.getConnection();
            try{
                PreparedStatement insertAgreementStatment;
                insertAgreementStatment = connectionDataBase.prepareStatement("INSERT INTO Acuerdo(periodo,descripcion,idMinuta,cedula) VALUES(?,?,?,?) ");
                insertAgreementStatment.setString(1, agreement.getPeriod());
                insertAgreementStatment.setString(2,  agreement.getDescription());
                insertAgreementStatment.setInt(3, agreement.getIdMinute());
                insertAgreementStatment.setString(4, agreement.getProfessionalLicense());
                insertAgreementStatment.executeUpdate();
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
    public ArrayList<Agreement> getAgreementsMinute(int idMinute) throws BusinessException {
        ArrayList<Agreement> agreementList = new ArrayList<>();
        try{
            Connector connectorDataBase = new Connector();
            Connection connectionDataBase = connectorDataBase.getConnection();
            try{
                PreparedStatement preparedStatement;
                preparedStatement = connectionDataBase.prepareStatement("SELECT * FROM Acuerdo where idMinuta = ?");
                preparedStatement.setInt(1, idMinute);          
                ResultSet agreementResultSet;
                agreementResultSet = preparedStatement.executeQuery();
                while(agreementResultSet.next()){
                    int idAcuerdo = agreementResultSet.getInt("idAcuerdo");
                    String period = agreementResultSet.getString("periodo");
                    String description = agreementResultSet.getString("descripcion");
                    String professionalLicense = agreementResultSet.getString("cedula");
                    Agreement agreementData = new Agreement( period, description,idAcuerdo,idMinute, professionalLicense);
                    agreementList.add(agreementData);
                }
                              
                connectorDataBase.disconnect();
                           
            }catch(SQLException sqlException) {
                throw new BusinessException("Parameter index ", sqlException);
            }
        }catch(ClassNotFoundException ex) {
            Log.logException(ex);
        }
                    
        return agreementList;  
    }
    
    @Override
    public boolean updatedSucessful(Agreement newAgreement) throws BusinessException {
        boolean updateSucess = false;
        Connector connectorDataBase=new Connector();
        try {
            Connection connectionDataBase = connectorDataBase.getConnection();
            PreparedStatement preparedStatement = connectionDataBase.prepareStatement("UPDATE Acuerdo set periodo = ?, descripcion = ?, idMinuta = ?, cedula = ? WHERE idAcuerdo = ? ");
            preparedStatement.setString(1, newAgreement.getPeriod());
            preparedStatement.setString(2,  newAgreement.getDescription());
            preparedStatement.setInt(3, newAgreement.getIdMinute());
            preparedStatement.setString(4, newAgreement.getProfessionalLicense());
            preparedStatement.setInt(5, newAgreement.getIdAgreement());     
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
    public boolean deletedSucessful(Agreement agreement) throws BusinessException{
        boolean deleteSucess=false;
        try{
            Connector connectorDataBase=new Connector();
            Connection connectionDataBase = connectorDataBase.getConnection();
            PreparedStatement preparedStatement = connectionDataBase.prepareStatement("DELETE FROM Acuerdo where idAcuerdo = ? and idMinuta = ? ");
            preparedStatement.setInt(1, agreement.getIdAgreement());
            preparedStatement.setInt(2, agreement.getIdMinute());
            preparedStatement.executeUpdate();
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
