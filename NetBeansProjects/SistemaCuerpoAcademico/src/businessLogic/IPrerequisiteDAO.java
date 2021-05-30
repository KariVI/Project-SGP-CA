
package businessLogic;

import domain.Member;
import domain.Prerequisite;
import java.util.ArrayList;
import log.BusinessException;

public interface IPrerequisiteDAO {
        public boolean savedSucessfulPrerequisites(Prerequisite prerequisite, int idMeeting) throws BusinessException;
        public int getId(Prerequisite prerequisite, int idMeeting) throws BusinessException;
        public boolean updatedSucessfulPrerequisite(int id, Prerequisite prerequisite) throws BusinessException;
        public boolean deletedSucessful(int idPrerequisite)throws BusinessException;
        public ArrayList<Prerequisite> getPrerequisites(int idMeeting) throws BusinessException;
       // public boolean deletedSucessfulPrerequisites(ArrayList<Prerequisite> prerequisites, int idMeeting ) throws BusinessException;


    
}
