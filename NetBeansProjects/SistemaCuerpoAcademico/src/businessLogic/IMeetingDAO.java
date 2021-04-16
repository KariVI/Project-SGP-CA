
package businessLogic;

import domain.Meeting;
import domain.Member;
import log.BusinessException;



public interface IMeetingDAO {
    public boolean save(Meeting meeting )throws BusinessException ;
    public int searchId(Meeting meeting) throws BusinessException;
    public boolean findMeetingById(int id);
    public Meeting getMeetingById(int id) throws BusinessException;
    public boolean addAssistant(int idMeeting, String enrollment, String role) throws BusinessException;
    public Member getAssistant(int idMeeting, String profesionalLicense) throws BusinessException;
    public boolean update(Meeting meeting) throws BusinessException;
}
