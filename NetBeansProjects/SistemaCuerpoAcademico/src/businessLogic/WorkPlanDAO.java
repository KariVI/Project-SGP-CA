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
    
}
