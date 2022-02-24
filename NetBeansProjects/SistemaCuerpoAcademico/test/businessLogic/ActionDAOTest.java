/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businessLogic;

import domain.Action;
import java.util.ArrayList;
import log.BusinessException;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author david
 */
public class ActionDAOTest {
    @Test
    public void getActionsByGoalId() throws BusinessException {
        System.out.println("getActions");
        int idGoal = 1;
        Action action = new Action(2,"Obtener una lista de contactos de los CA","Diciembre 2020","KVC","Lista de cuerpos académicos de PRODEP");
        ArrayList<Action> resultExpected = new ArrayList<Action>();
        resultExpected.add(action);
        ActionDAO actionDAO = new ActionDAO();
        ArrayList<Action> result = actionDAO.getActionsByGoalId(idGoal);   
        assertEquals(result,resultExpected);
    }
    
    @Test
    public void testDeleteAction() throws BusinessException {
        System.out.println("delete");
        ActionDAO actionDAO = new ActionDAO();
        assertTrue(actionDAO.deletedActionById(5));
    }
    
    @Test
    public void testUpdateAction() throws BusinessException {
        System.out.println("update");
        Action newAction = new Action(2, "Obtener una lista de contactos de los CAs","Diciembre 2020","KVC","Lista de cuerpos académicos de PRODEP");
        ActionDAO actionDAO = new ActionDAO();
        int idGoal = 1;
        assertTrue(actionDAO.updatedActionById(idGoal, newAction));
    }
}
