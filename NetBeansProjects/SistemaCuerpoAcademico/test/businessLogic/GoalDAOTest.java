/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businessLogic;
import domain.Goal;
import java.util.ArrayList;
import log.BusinessException;
import org.junit.Test;
import static org.junit.Assert.*;


/**
 *
 * @author Mariana
 */
public class GoalDAOTest {
  
    @Test
    public void getGoalsByWorkPlanId() throws BusinessException {
        System.out.println("getGoals");
        int idWorkPlan = 1;
        Goal goal = new Goal(1,"Lograr un acuerdo de colaboración con un CA externo");
        ArrayList<Goal> resultExpected = new ArrayList<Goal>();
        resultExpected.add(goal);
        GoalDao goalDao = new GoalDao();
        ArrayList<Goal> result = goalDao.getGoalsByWorkPlanId(idWorkPlan);   
        assertEquals(result,resultExpected);
    }
    
    @Test
    public void testDeleteGoal() throws BusinessException {
        System.out.println("delete");
        GoalDao goalDao = new GoalDao();
        assertTrue(goalDao.deletedGoalById(4));
    }
    
    @Test
    public void testUpdateGoal() throws BusinessException {
        System.out.println("update");
        int idWorkPlan = 1;
        Goal goal = new Goal(1,"Lograr un acuerdo de colaboración con un o varios CAs externos");
        GoalDao goalDao = new GoalDao();
        assertTrue(goalDao.updatedGoalById(idWorkPlan, goal));
    }
    
}
