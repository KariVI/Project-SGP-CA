
package businessLogic;

import dataaccess.Connector;
import domain.LGCA;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import log.BusinessException;
import log.Log;


public class LGCADAO implements ILGCADAO {
    
    public boolean save(LGCA lgac) throws BusinessException{
        boolean value=false;
        Connector connectorDataBase=new Connector();
        try {
            Connection connectionDataBase = connectorDataBase.getConnection();
            String insertLgac = "INSERT INTO LGAC(nombre,descripcion) VALUES (?,?)";
            
            PreparedStatement preparedStatement = connectionDataBase.prepareStatement(insertLgac);
            preparedStatement.setString(1, lgac.getName());
            preparedStatement.setString(2, lgac.getDescription());
                
            preparedStatement.executeUpdate();
            value=true;  
            connectorDataBase.disconnect();
        }catch (ClassNotFoundException ex) {
            Log.logException(ex);
        } catch (SQLException sqlException) {
           throw new BusinessException("DataBase connection failed ", sqlException);
            
        }
        return value;
    }

    @Override
    public LGCA getLgacByName(String name) throws BusinessException {
        LGCA lgacAuxiliar=null;
        Connector connectorDataBase=new Connector();
        try {
            Connection connectionDataBase = connectorDataBase.getConnection();
            String query="SELECT * from LGAC where nombre=?";
            PreparedStatement preparedStatement = connectionDataBase.prepareStatement(query);
            preparedStatement.setString(1, name);
            ResultSet resultSet;
            resultSet=preparedStatement.executeQuery();
            
            if(resultSet.next()){   
                String nameLgac= resultSet.getString("nombre");
                String description=resultSet.getString("descripcion");
                lgacAuxiliar=new LGCA(nameLgac, description);
            }
            
        } catch (ClassNotFoundException ex) {
            Log.logException(ex);
        } catch (SQLException sqlException) {
            throw new BusinessException("DataBase connection failed ", sqlException);
        }
        return lgacAuxiliar;
    }

   
    public boolean update(String beforeName, LGCA lgac) throws BusinessException {
        boolean updateSucess=false;
        Connector connectorDataBase=new Connector();
        try {
            Connection connectionDataBase = connectorDataBase.getConnection();
            String updateLgac = "UPDATE LGAC set nombre=?,descripcion=? where nombre=?";
            
            PreparedStatement preparedStatement = connectionDataBase.prepareStatement(updateLgac);
            preparedStatement.setString(1, lgac.getName());
            preparedStatement.setString(2, lgac.getDescription());
            preparedStatement.setString(3,beforeName);
                
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
    
    public ArrayList<LGCA>  getLGACs(){
        ArrayList<LGCA> lgacList = new ArrayList<>();
        try{
            Connector connectorDataBase = new Connector();
            Connection connectionDataBase = connectorDataBase.getConnection();
            String queryLgac="SELECT * FROM LGAC";

               PreparedStatement preparedStatement;
               preparedStatement = connectionDataBase.prepareStatement(queryLgac);
               ResultSet resultSet;
               resultSet = preparedStatement.executeQuery();
               while(resultSet.next()){
                    String name = resultSet.getString("nombre");
                    String description = resultSet.getString("descripcion");
                    LGCA lgacAuxiliar = new LGCA(name,description);
                    lgacList.add(lgacAuxiliar);
                }
                connectorDataBase.disconnect();
            }catch(SQLException sqlException) {
                   throw new IllegalStateException("Parameter index ", sqlException);         
            }catch(ClassNotFoundException ex) {
                        Log.logException(ex);
            }
            return lgacList;
        }
}