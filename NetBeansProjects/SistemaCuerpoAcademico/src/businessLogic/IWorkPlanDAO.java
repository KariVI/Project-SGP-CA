package businessLogic;

import domain.WorkPlan;
import java.util.ArrayList;
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
    public ArrayList <WorkPlan> getWorkPlans() throws BusinessException;
    public boolean saveSuccesful(WorkPlan workPlan) throws BusinessException;
    public int getId (WorkPlan workPlan) throws BusinessException;
}
