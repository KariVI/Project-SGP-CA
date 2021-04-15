
package businessLogic;

import domain.Meeting;
import domain.Member;
import log.BusinessException;



public interface IMeetingDAO {
    public boolean save(Meeting meeting )throws BusinessException ;
    public int searchId(Meeting meeting);
    public boolean findMeetingById(int id);
    public Meeting getMeetingById(int id);
    public boolean addAssistant(int idMeeting, String enrollment, String role);
    public Member getAssistant(int idMeeting, String profesionalLicense);
}
