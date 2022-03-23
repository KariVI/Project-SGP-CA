/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businessLogic;

import domain.Action;
import domain.Goal;
import java.util.ArrayList;
import log.BusinessException;

/**
 *
 * @author Mariana
 */
public interface IGoalDAO {
    public ArrayList <Action> getActionsByGoalId(int id) throws BusinessException;
    public ArrayList <Goal> getGoalsByWorkPlanId(int id) throws BusinessException;
    public boolean saveSuccesful(Goal goal, int idWorkPlan) throws BusinessException;
    public int getId(Goal goal, int idWorkPlan) throws BusinessException;
}
