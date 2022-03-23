/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businessLogic;

import dataaccess.Connector;
import domain.Action;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import log.BusinessException;
import log.Log;

/**
 *
 * @author david
 */
public class ActionDAO implements IActionDAO{

    @Override
    public ArrayList<Action> getActionsByGoalId(int id) throws BusinessException {
        ArrayList<Action> actionList = new ArrayList<Action>();
        try{
            Connector connectorDataBase = new Connector();
            Connection connectionDataBase = connectorDataBase.getConnection();
            try{
                PreparedStatement getActionsStatment;
                getActionsStatment = connectionDataBase.prepareStatement("SELECT * FROM Accion where idMeta = ?");
                getActionsStatment.setInt(1,id);
                ResultSet actionResultSet;
                actionResultSet = getActionsStatment.executeQuery();
                while(actionResultSet.next()){
                    int idAction = actionResultSet.getInt("idAccion");
                    String description = actionResultSet.getString("descripcion");
                    String dateFinish = actionResultSet.getString("fechaConclusion");
                    String memberInCharge = actionResultSet.getString("responsable");
                    String resource = actionResultSet.getString("recurso");
                    Action actionData = new Action(idAction, description,dateFinish,memberInCharge,resource);
                    actionList.add(actionData);
                }

                connectorDataBase.disconnect();

            }catch(SQLException sqlException) {
                throw new BusinessException("Parameter index ", sqlException);
            }

        }catch(ClassNotFoundException ex) {
            Log.logException(ex);
        }
        
        return actionList;  
    }

    @Override
    public boolean deletedActionById(int id) throws BusinessException {
        boolean deletedSuccess = false;
        try{
            Connector connectorDataBase = new Connector();
            Connection connectionDataBase = connectorDataBase.getConnection();
            try{
                PreparedStatement preparedStatement = connectionDataBase.prepareStatement("DELETE FROM Accion Where idAccion = ?");
                preparedStatement.setInt(1, id);
                preparedStatement.executeUpdate();
                deletedSuccess = true;
                connectorDataBase.disconnect();
            }catch(SQLException sqlException) {
                throw new BusinessException("DataBase connection failed", sqlException);
            }           
        }catch(ClassNotFoundException ex) {
            Log.logException(ex);
        }
        
        return deletedSuccess;
    }

    @Override
    public boolean updatedActionById(int idGoal, Action action) throws BusinessException {
        boolean updatedSucess = false;
        try{
            Connector connectorDataBase = new Connector();
            Connection connectionDataBase = connectorDataBase.getConnection();
            try{
                String sql = "UPDATE Accion set descripcion = ?, fechaConclusion = ?, responsable = ?, recurso = ?, idMeta = ? WHERE idAccion = ?";
                PreparedStatement preparedStatement = connectionDataBase.prepareStatement(sql);
                preparedStatement.setString(1, action.getDescription());
                preparedStatement.setString(2, action.getDateEnd());
                preparedStatement.setString(3, action.getMemberInCharge());
                preparedStatement.setString(4, action.getResource());
                preparedStatement.setInt(5, idGoal);
                preparedStatement.setInt(6, action.getId());
                updatedSucess = true;
                connectorDataBase.disconnect();
            }catch(SQLException sqlException){
                throw new BusinessException("DataBase connection failed", sqlException);
            }
        }catch(ClassNotFoundException ex) {
            Log.logException(ex);
        }
        return updatedSucess;
    }
    
    public boolean saveSuccesful(Action action, int idGoal ) throws BusinessException{   
        boolean saveSuccess=false;
        try {
                Connector connectorDataBase=new Connector();
                Connection connectionDataBase = connectorDataBase.getConnection();
                String insertGoal = "INSERT INTO Accion(idMeta, descripcion,fechaConclusion,responsable,recurso) VALUES (?,?,?,?,?)";
            
                PreparedStatement preparedStatement = connectionDataBase.prepareStatement(insertGoal);
                preparedStatement.setInt(1, idGoal);
                preparedStatement.setString(2, action.getDescription());
                preparedStatement.setString(3, action.getDateEnd());
                preparedStatement.setString(4, action.getMemberInCharge());
                preparedStatement.setString(5, action.getResource());
                preparedStatement.executeUpdate();
                connectorDataBase.disconnect();
                saveSuccess=true;
            } catch (SQLException sqlException) {
                throw new BusinessException("DataBase connection failed ", sqlException);
            } catch (ClassNotFoundException ex) {
               Log.logException(ex);
            }
        return saveSuccess; 
    }
    
     public boolean deleteAllActionByIdGoal(int idGoal) throws BusinessException{
        boolean deletedSuccess = false;
        try{
            Connector connectorDataBase = new Connector();
            Connection connectionDataBase = connectorDataBase.getConnection();
            try{
                PreparedStatement preparedStatement = connectionDataBase.prepareStatement("DELETE FROM Accion Where idMeta = ?");
                preparedStatement.setInt(1, idGoal);
                preparedStatement.executeUpdate();
                deletedSuccess = true;
                connectorDataBase.disconnect();
            }catch(SQLException sqlException) {
                throw new BusinessException("DataBase connection failed", sqlException);
            }           
        }catch(ClassNotFoundException ex) {
            Log.logException(ex);
        }
        
        return deletedSuccess;
    }
}
