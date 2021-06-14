
package businessLogic;

import domain.Meeting;
import domain.Member;
import java.util.ArrayList;
import log.BusinessException;



public interface IMeetingDAO {
    public boolean savedSucessful(Meeting meeting )throws BusinessException ;
    public int getId(Meeting meeting) throws BusinessException;
    public boolean findMeetingById(int id);
    public Meeting getMeetingById(int id) throws BusinessException;
    public boolean addedSucessfulAssistants(Meeting meeting) throws BusinessException;
    public ArrayList<Member> getAssistants(int idMeeting) throws BusinessException;
    public boolean updatedSucessful(Meeting meeting) throws BusinessException;
    public boolean changedStateSucessful(Meeting meeting) throws BusinessException;
   public boolean deletedSucessfulAssistants(Meeting meeting, Member assistant) throws BusinessException;
    public ArrayList<Meeting>  getMeetings(String keyGroupAcademic);
}
