package businessLogic;

import domain.Project;
import java.util.ArrayList;

/**
 *
 * @author Mariana
 */
public interface IProjectDAO {
      public void save(Project project);
      public ArrayList<Project>  getProjects();
      public int searchId(Project project);
      public boolean findProjectById(int idProject);
      public Project getProjectById(int idProject);
}
