/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businessLogic;

import dataaccess.Connector;
import domain.WorkPlan;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import log.BusinessException;
import log.Log;

/**
 *
 * @author gustavor
 */
public class WorkPlanDAO implements IWorkPlanDAO {

    @Override
    public WorkPlan getWorkPlan(int idWorkPlan) throws BusinessException {
        WorkPlan workPlan = null;
        try{
            Connector connectorDataBase = new Connector();
            Connection connectionDataBase = connectorDataBase.getConnection();
            try{
                PreparedStatement getWorkPlanStatment;
                getWorkPlanStatment = connectionDataBase.prepareStatement("SELECT * FROM PlanTrabajo WHERE idPlanTrabajo = ?;");
                getWorkPlanStatment.setInt(1, idWorkPlan);          
                ResultSet workPlanResultSet;
                workPlanResultSet = getWorkPlanStatment.executeQuery();
                if (workPlanResultSet.next()) {
                    int id = workPlanResultSet.getInt("idPlanTrabajo");
                    String objetivoGeneral = workPlanResultSet.getString("objetivo");
                    String timePeriod = workPlanResultSet.getString("periodo");
                    workPlan = new WorkPlan(objetivoGeneral, timePeriod, null);
                    workPlan.setId(id);
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
        return workPlan;
    }
    
     public ArrayList <WorkPlan> getWorkPlans() throws BusinessException {
        ArrayList <WorkPlan> workPlanList = new ArrayList<WorkPlan>();
        try{
            Connector connectorDataBase = new Connector();
            Connection connectionDataBase = connectorDataBase.getConnection();
            try{
                PreparedStatement getGoalsStatment;
                getGoalsStatment = connectionDataBase.prepareStatement("SELECT * FROM PlanTrabajo");
                ResultSet workPlanResultSet;
                workPlanResultSet = getGoalsStatment.executeQuery();
                while(workPlanResultSet.next()){
                    int id = workPlanResultSet.getInt("idPlanTrabajo");
                    String objetiveGeneral = workPlanResultSet.getString("objetivo");
                    String timePeriod = workPlanResultSet.getString("periodo");
                    WorkPlan workPlan = new WorkPlan(id,objetiveGeneral, timePeriod);
                    workPlanList.add(workPlan);
                }

                connectorDataBase.disconnect();

            }catch(SQLException sqlException) {
                throw new BusinessException("Parameter index ", sqlException);
            }

        }catch(ClassNotFoundException ex) {
            Log.logException(ex);
        }

        return workPlanList;  
    }
     
    public boolean updateWorkPlan(WorkPlan workPlan) throws BusinessException{
        boolean updateSuccess = false;
        try{
            Connector connectorDataBase = new Connector();
            Connection connectionDataBase = connectorDataBase.getConnection();
            PreparedStatement preparedStatement = connectionDataBase.prepareStatement("UPDATE PlanTrabajo set objetivo = ?, periodo = ? WHERE idPlanTrabajo = ?");
            preparedStatement.setString(1, workPlan.getObjetiveGeneral());
            preparedStatement.setString(2, workPlan.getTimePeriod());
            preparedStatement.setInt(3, workPlan.getId());
            preparedStatement.executeUpdate();
            updateSuccess = true;
            connectorDataBase.disconnect();
        }catch(SQLException sqlException) {
                throw new BusinessException("Parameter index ", sqlException);
         } catch (ClassNotFoundException ex) {
            Log.logException(ex);
        }
        return updateSuccess;
    }

    @Override
    public boolean saveSuccesful(WorkPlan workPlan) throws BusinessException {
         boolean saveSuccess = false;
        try{
             Connector connectorDataBase = new Connector();
            Connection connectionDataBase = connectorDataBase.getConnection();
              PreparedStatement insertWorkPlanStatment;
                insertWorkPlanStatment = connectionDataBase.prepareStatement("INSERT INTO PlanTrabajo(objetivo, claveCuerpoAcademico, periodo) VALUES(?,?,?) ");
                 insertWorkPlanStatment.setString(1, workPlan.getObjetiveGeneral());
                 insertWorkPlanStatment.setString(2, workPlan.getGroupAcademicKey());
                insertWorkPlanStatment.setString(3, workPlan.getTimePeriod());
                insertWorkPlanStatment.executeUpdate();
                connectorDataBase.disconnect();
                saveSuccess = true;
        }catch(SQLException sqlException) {
                throw new BusinessException("Parameter index ", sqlException);
         } catch (ClassNotFoundException ex) {
            Log.logException(ex);
        }
        
        return saveSuccess;
    }
    
     public int getId (WorkPlan workPlan) throws BusinessException{
         Integer id=0;
         try{
            Connector connectorDataBase = new Connector();
            Connection connectionDataBase = connectorDataBase.getConnection();
            try{
                PreparedStatement getWorkPlanStatment;
                getWorkPlanStatment = connectionDataBase.prepareStatement("SELECT idPlanTrabajo FROM PlanTrabajo WHERE periodo=? and objetivo= ?;");
                getWorkPlanStatment.setString(1, workPlan.getTimePeriod()); 
                getWorkPlanStatment.setString(2,workPlan.getObjetiveGeneral() );   
                ResultSet workPlanResultSet;
                workPlanResultSet = getWorkPlanStatment.executeQuery();
                if (workPlanResultSet.next()) {
                    id=Integer.parseInt(workPlanResultSet.getString("idPlanTrabajo"));
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
