package businessLogic;

import domain.WorkPlan;
import log.BusinessException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author gustavor
 */
public interface IWorkPlanDAO {
    public WorkPlan getWorkPlan(int idWorkPlan) throws BusinessException;
}
