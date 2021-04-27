package businessLogic;

import domain.Project;
import java.util.ArrayList;

import log.BusinessException;

public interface IProjectDAO {
      public boolean save(Project project) throws BusinessException;
      public ArrayList<Project>  getProjects() throws BusinessException;
      public int searchId(Project project) throws BusinessException;
      public boolean findProjectById(int idProject);
      public Project getProjectById(int idProject) throws BusinessException;
      public boolean update(Project newProject) throws BusinessException;
}
