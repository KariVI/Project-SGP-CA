/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businessLogic;
import domain.Action;
import domain.Goal;
import domain.LoginCredential;
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
    public void getActionsByGoalId() throws BusinessException {
        System.out.println("getActions");
        int idGoal = 1;
        Action action = new Action(1,"Obtener una lista de contactos de los CA","Diciembre 2020","KVC","Lista de cuerpos académicos de PRODEP");
        ArrayList<Action> resultExpected = new ArrayList<Action>();
        resultExpected.add(action);
        GoalDao goalDao = new GoalDao();
        ArrayList<Action> result = goalDao.getActionsByGoalId(idGoal);   
        assertEquals(result,resultExpected);
    }
    
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
}
