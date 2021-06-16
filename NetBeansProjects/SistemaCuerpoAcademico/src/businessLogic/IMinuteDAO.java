package businessLogic;

import domain.Member;
import domain.Minute;
import domain.MinuteComment;
import java.util.ArrayList;
import log.BusinessException;

public interface IMinuteDAO {
    public boolean saveMinute(Minute minute)throws BusinessException;
    public int getIdMinute(Minute minute)throws BusinessException;
    public void approveMinute(int idMinute, String professionalLicense)throws BusinessException;
    public void disapproveMinute(MinuteComment minuteComment)throws BusinessException;
    public ArrayList<Minute>  getMinutes()throws BusinessException;
    public ArrayList<MinuteComment>  getMinutesComments(int idMinute) throws BusinessException;
    public boolean deleteMinuteComment(MinuteComment minuteComment) throws BusinessException;
    public boolean deleteMinutesComments(int idMinute) throws BusinessException;
    public boolean update(Minute newMinute) throws BusinessException;
    public ArrayList<Member> getMembersApprove(Minute minute) throws BusinessException;
    public Minute getMinute(int idMeeting) throws BusinessException;
}
