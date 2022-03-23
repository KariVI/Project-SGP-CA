/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businessLogic;

import dataaccess.Connector;
import domain.Action;
import domain.Goal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import log.BusinessException;
import log.Log;

/**
 *
 * @author Mariana
 */
public class GoalDao implements IGoalDAO{

      @Override
      public ArrayList <Goal> getGoalsByWorkPlanId(int id) throws BusinessException {
        ArrayList <Goal> goalList = new ArrayList<Goal>();
        try{
            Connector connectorDataBase = new Connector();
            Connection connectionDataBase = connectorDataBase.getConnection();
            try{
                PreparedStatement getGoalsStatment;
                getGoalsStatment = connectionDataBase.prepareStatement("SELECT * FROM Meta where idPlanTrabajo = ?");
                getGoalsStatment.setInt(1,id);
                ResultSet goalResultSet;
                goalResultSet = getGoalsStatment.executeQuery();
                while(goalResultSet.next()){
                    int idGoal = goalResultSet.getInt("idMeta");
                    String description = goalResultSet.getString("descripcion");
                    Goal goalData = new Goal(idGoal,description);
                    goalList.add(goalData);
                }

                connectorDataBase.disconnect();

            }catch(SQLException sqlException) {
                throw new BusinessException("Parameter index ", sqlException);
            }

        }catch(ClassNotFoundException ex) {
            Log.logException(ex);
        }

        return goalList;  
    }

     @Override
    public boolean deletedGoalById(int id) throws BusinessException {
         boolean deletedSuccess = false;
        try{
            Connector connectorDataBase = new Connector();
            Connection connectionDataBase = connectorDataBase.getConnection();
            try{
                PreparedStatement preparedStatement = connectionDataBase.prepareStatement("DELETE FROM Meta Where idMeta = ?");
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
    public boolean updatedGoalById(int idWorkPlan, Goal goal) throws BusinessException {
        boolean updatedSucess = false;
        try{
            Connector connectorDataBase = new Connector();
            Connection connectionDataBase = connectorDataBase.getConnection();
            try{
                String sql = "UPDATE Meta set idPlanTrabajo = ?, descripcion = ? WHERE idMeta = ?";
                PreparedStatement preparedStatement = connectionDataBase.prepareStatement(sql);
                preparedStatement.setInt(1, idWorkPlan);
                preparedStatement.setString(2, goal.getDescription());
                preparedStatement.setInt(3, goal.getId());
                preparedStatement.executeUpdate();
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

    @Override
    public boolean saveSuccesful(Goal goal, int idWorkPlan) throws BusinessException {
        boolean saveSuccess=false;
        try {
                Connector connectorDataBase=new Connector();
                Connection connectionDataBase = connectorDataBase.getConnection();
                String insertGoal = "INSERT INTO Meta(idPlanTrabajo, descripcion) VALUES (?, ?)";
            
                PreparedStatement preparedStatement = connectionDataBase.prepareStatement(insertGoal);
                
                preparedStatement.setInt(1, idWorkPlan);
                preparedStatement.setString(2, goal.getDescription());
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

    @Override
    public ArrayList<Action> getActionsByGoalId(int id) throws BusinessException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getId(Goal goal, int idWorkPlan) throws BusinessException {
        Integer  id=0;
        try{
            Connector connectorDataBase = new Connector();
            Connection connectionDataBase = connectorDataBase.getConnection();
            try{
                PreparedStatement getWorkPlanStatment;
                getWorkPlanStatment = connectionDataBase.prepareStatement("SELECT idMeta FROM Meta WHERE descripcion=? and idPlanTrabajo= ?;");
                getWorkPlanStatment.setString(1, goal.getDescription()); 
                getWorkPlanStatment.setInt(2,idWorkPlan );   
                ResultSet workPlanResultSet;
                workPlanResultSet = getWorkPlanStatment.executeQuery();
                if (workPlanResultSet.next()) {
                    id=Integer.parseInt(workPlanResultSet.getString("idMeta"));
                } else {
                    throw new BusinessException("Work plan not found");
                }
                connectorDataBase.disconnect();
            }catch(SQLException sqlException) {
                throw new BusinessException("Parameter index ", sqlException);
            }
        }catch(ClassNotFoundException ex) {
            Log.logException(ex);
        }
        return id;
    }
    
}
