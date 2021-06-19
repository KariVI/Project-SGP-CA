package businessLogic;

import domain.Member;
import domain.Minute;
import domain.MinuteComment;
import java.util.ArrayList;
import log.BusinessException;

public interface IMinuteDAO {
    public boolean savedSucessfulMinute(Minute minute)throws BusinessException;
    public int getIdMinute(Minute minute)throws BusinessException;
    public boolean approveMinute(int idMinute, String professionalLicense)throws BusinessException;
    public boolean disapproveMinute(MinuteComment minuteComment)throws BusinessException;
    public ArrayList<MinuteComment>  getMinutesComments(int idMinute) throws BusinessException;
    public boolean deletedSucessfulMinuteComment(MinuteComment minuteComment) throws BusinessException;
    public boolean deletedSucessfulMinutesComments(int idMinute) throws BusinessException;
    public boolean updatedSucessful(Minute newMinute) throws BusinessException;
    public ArrayList<Member> getMembersApprove(Minute minute) throws BusinessException;
    public Minute getMinute(int idMeeting) throws BusinessException;
}
