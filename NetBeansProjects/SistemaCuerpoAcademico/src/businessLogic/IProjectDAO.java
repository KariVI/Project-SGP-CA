package businessLogic;

import domain.Project;
import java.util.ArrayList;
import java.sql.SQLException;

/**
 *
 * @author Mariana
 */
public interface IProjectDAO {
      public boolean save(Project project);
      public ArrayList<Project>  getProjects();
      public int searchId(Project project);
      public boolean findProjectById(int idProject);
      public Project getProjectById(int idProject);
}
