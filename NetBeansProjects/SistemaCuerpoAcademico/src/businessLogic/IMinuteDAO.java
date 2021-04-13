package businessLogic;

import domain.Minute;
import domain.MinuteComment;
import java.util.ArrayList;

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
    public boolean saveMinute(Minute minute, int idMeeting);
    public void approveMinute(int idMinute, String professionalLicense);
    public void disapproveMinute(int idMinute, String professionalLicense, String comments);
    public ArrayList<Minute>  getMinutes();
    public ArrayList<MinuteComment>  getMinutesComments(int idMinute);
}
