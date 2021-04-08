
package businessLogic;

import domain.Meeting;
import domain.Member;
import domain.Prerequisite;

public interface IMeetingDAO {
    public void save(Meeting meeting );
    public int searchId(Meeting meeting);
    public boolean findMeetingById(int id);
    public Meeting getMeetingById(int id);
    public void addAssistant( Member member);
}
