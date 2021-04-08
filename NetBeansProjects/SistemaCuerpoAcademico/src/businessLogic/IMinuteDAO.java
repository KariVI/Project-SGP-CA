package businessLogic;

import domain.Minute;

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
    public void saveMinute(Minute minute, int idMeeting);
}
