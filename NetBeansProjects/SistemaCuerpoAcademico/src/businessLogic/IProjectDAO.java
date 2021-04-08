package businessLogic;

import domain.Project;
import java.util.ArrayList;
import java.sql.SQLException;

/**
 *
 * @author Mariana
 */
public interface IProjectDAO {
      public void save(Project project) throws SQLException;
      public ArrayList<Project>  getProjects();
      public int searchId(Project project);
      public boolean findProjectById(int idProject);
      public Project getProjectById(int idProject);
}
