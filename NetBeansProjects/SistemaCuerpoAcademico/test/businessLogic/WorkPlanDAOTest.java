/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businessLogic;

import domain.Member;
import domain.WorkPlan;
import businessLogic.WorkPlanDAO;
import domain.Student;

import java.util.ArrayList;
import log.BusinessException;
import static org.junit.Assert.assertEquals;
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
    @Test
    public void getWorkPlansTest() throws BusinessException {
        System.out.println("getWorkPlans");
        WorkPlan workPlan = new WorkPlan(1,"Mantener el grado en consolidación del cuerpo academico","2020-2023");
        WorkPlan workPlan1 = new WorkPlan(2,"Mantener el grado en consolidación del cuerpo academico","2018-2020");
        ArrayList<WorkPlan> resultExpected = new ArrayList<WorkPlan>();
        resultExpected.add(workPlan);
        resultExpected.add(workPlan1);
        WorkPlanDAO workPlanDao = new WorkPlanDAO();
        ArrayList<WorkPlan> result = workPlanDao.getWorkPlans();  
        System.out.println(resultExpected.size());
        System.out.println(result.size());
        assertEquals(result.get(0),resultExpected.get(0));
    }
    
    @Test
     public void testSave() throws BusinessException {
        System.out.println("savedSucessful");
        WorkPlan workPlan= new WorkPlan("Establecer el cuerpo academico a grado de consolidado", "Enero 2022- Agosto 2023",null);
        WorkPlanDAO workPlanDAO = new WorkPlanDAO();
        assertTrue(workPlanDAO.saveSuccesful(workPlan));
    }
     
    @Test
     public void getID() throws BusinessException {
        System.out.println("getId");
        WorkPlan workPlan= new WorkPlan("Establecer el cuerpo academico a grado de consolidado", "Enero 2022- Agosto 2023",null);
        WorkPlanDAO workPlanDAO = new WorkPlanDAO();
        System.out.println(workPlanDAO.getId(workPlan));
        assertTrue( (workPlanDAO.getId(workPlan) > 0));
    }
}
