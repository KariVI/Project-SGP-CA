
package businessLogic;

import domain.Meeting;
import domain.Member;
import java.util.ArrayList;
import log.BusinessException;



public interface IMeetingDAO {
    public boolean save(Meeting meeting )throws BusinessException ;
    public int searchId(Meeting meeting) throws BusinessException;
    public boolean findMeetingById(int id);
    public Meeting getMeetingById(int id) throws BusinessException;
    public boolean addAssistants(Meeting meeting) throws BusinessException;
    //public Member getAssistant(int idMeeting, String profesionalLicense) throws BusinessException;
    public ArrayList<Member> getAssistants(int idMeeting) throws BusinessException;
    public boolean update(Meeting meeting) throws BusinessException;
    public boolean changeState(Meeting meeting) throws BusinessException;
    public boolean deleteAssistants(Meeting meeting) throws BusinessException;
    public ArrayList<Meeting>  getMeetings();
}
