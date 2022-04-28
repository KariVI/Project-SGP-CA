/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businessLogic;

import domain.Action;
import java.util.ArrayList;
import log.BusinessException;

/**
 *
 * @author david
 */
public interface IActionDAO {
   public ArrayList <Action> getActionsByGoalId(int id) throws BusinessException;
   public boolean deletedActionById(int id) throws BusinessException;
   public boolean updatedActionById(int idGoal, Action action) throws BusinessException;
    public boolean saveSuccesful(Action action, int idGoal ) throws BusinessException;
   public boolean deleteAllActionByIdGoal(int idGoal) throws BusinessException;
}
