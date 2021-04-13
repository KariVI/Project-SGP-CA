
package businessLogic;

import domain.Member;
import domain.Prerequisite;

public interface IPrerequisiteDAO {
        public boolean savePrerequisites(Prerequisite prerequisite, int idMeeting);
        public String getPrerequisiteDescription(int id, int idMeeting);
        public int getId(Prerequisite prerequisite, int idMeeting);
        public boolean updatePrerequisite(int id, Prerequisite prerequisite);



    
}
