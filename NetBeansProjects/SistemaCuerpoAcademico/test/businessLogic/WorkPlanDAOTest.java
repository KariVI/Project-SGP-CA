/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businessLogic;

import domain.Member;
import domain.WorkPlan;
import log.BusinessException;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author gustavor
 */
public class WorkPlanDAOTest {
    
     @Test
    public void getWorkPlan() throws BusinessException {
        System.out.println("getWorkPlan");
        WorkPlanDAO workPlanDAO = new WorkPlanDAO();
        int workPlanId = 1;
        WorkPlan workPlan = workPlanDAO.getWorkPlan(workPlanId);
        WorkPlan workPlanExpected = new WorkPlan("Mantener el grado en consolidación del cuerpo academico","2020-2023",null);
        workPlanExpected.setId(workPlanId);
        assertTrue(workPlan.equals(workPlanExpected));
    }
    
}
