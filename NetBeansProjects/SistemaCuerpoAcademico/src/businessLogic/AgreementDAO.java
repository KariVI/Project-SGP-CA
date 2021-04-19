/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
    public boolean saveAgreement(Agreement agreement, int idMinute, String professionalLicense) throws BusinessException {
                    boolean saveSuccess = false;
                    try{
                        Connector connectorDataBase = new Connector();
                        Connection connectionDataBase = connectorDataBase.getConnection();
                        try{
                            
                            PreparedStatement insertAgreementStatment;
                            insertAgreementStatment = connectionDataBase.prepareStatement("INSERT INTO Acuerdo(periodo,descripcion,idMinuta,cedula) VALUES(?,?,?,?) ");
                            insertAgreementStatment.setString(1, agreement.getPeriod());
                            insertAgreementStatment.setString(2,  agreement.getDescription());
                            insertAgreementStatment.setInt(3, idMinute);
                            insertAgreementStatment.setString(4, professionalLicense);
                            
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
    public ArrayList<Agreement> getAgreements() throws BusinessException {
                     ArrayList<Agreement> agreementList = new ArrayList<>();
                     try{
                        Connector connectorDataBase = new Connector();
                        Connection connectionDataBase = connectorDataBase.getConnection();
                        try{
                            
                            PreparedStatement getProjectsStatment;
                            getProjectsStatment = connectionDataBase.prepareStatement("SELECT * FROM Acuerdo");
                            ResultSet agreementResultSet;
                            
                            agreementResultSet = getProjectsStatment.executeQuery();
                            
                            while(agreementResultSet.next()){
                                int idAcuerdo = agreementResultSet.getInt("idAcuerdo");
                                String period = agreementResultSet.getString("periodo");
                                String description = agreementResultSet.getString("descripcion");
                                int idMinute = agreementResultSet.getInt("idMinuta");
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
    
}
