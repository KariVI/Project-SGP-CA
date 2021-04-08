
package businessLogic;

import domain.Prerequisite;

public interface IPrerequisiteDAO {
        public void savePrerequisites(Prerequisite prerequisite, int idReunion);
        public String getPrerequisiteDescription(int id);


    
}
