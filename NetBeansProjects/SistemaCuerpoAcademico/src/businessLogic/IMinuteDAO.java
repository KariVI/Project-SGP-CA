package businessLogic;

import domain.Minute;
import domain.MinuteComment;
import java.util.ArrayList;
import log.BusinessException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Mariana
 */
public interface IMinuteDAO {
    public boolean saveMinute(Minute minute)throws BusinessException;
    public void approveMinute(int idMinute, String professionalLicense)throws BusinessException;
    public void disapproveMinute(int idMinute, String professionalLicense, String comments)throws BusinessException;
    public ArrayList<Minute>  getMinutes()throws BusinessException;
    public ArrayList<MinuteComment>  getMinutesComments(int idMinute) throws BusinessException;
    public boolean update(Minute newMinute) throws BusinessException;
}
