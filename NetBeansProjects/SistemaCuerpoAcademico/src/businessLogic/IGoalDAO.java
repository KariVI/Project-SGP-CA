/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businessLogic;

import domain.Goal;
import java.util.ArrayList;
import log.BusinessException;

/**
 *
 * @author Mariana
 */
public interface IGoalDAO {
    public ArrayList <Goal> getGoalsByWorkPlanId(int id) throws BusinessException;
    public boolean deletedGoalById(int id) throws BusinessException;
   public boolean updatedGoalById(int idWorkPlan, Goal goal) throws BusinessException;
}
