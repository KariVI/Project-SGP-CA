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
    
}
