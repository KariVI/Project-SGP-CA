package businessLogic;

import domain.LGAC;
import domain.Member;
import domain.Project;
import domain.ReceptionWork;
import domain.Student;
import java.util.ArrayList;

import log.BusinessException;

public interface IProjectDAO {
      public boolean savedSucessful(Project project) throws BusinessException;
      public ArrayList<Project>  getProjects() throws BusinessException;
      public int  searchId(Project project) throws BusinessException;
      public Project getProjectById(int idProject) throws BusinessException;
      public boolean updatedSucessful(Project newProject) throws BusinessException;
      public boolean addStudents(Project project) throws BusinessException;
      public boolean addColaborators(Project project) throws BusinessException;
      public boolean addLGAC(Project project) throws BusinessException;
      public boolean addReceptionWork(Project project) throws BusinessException;
      public ArrayList <Member> getColaborators(Project  project) throws BusinessException;
      public ArrayList <Student> getStudents(Project project) throws BusinessException;
      public ArrayList <LGAC> getLGACs(Project project) throws BusinessException;
      public ArrayList <ReceptionWork> getReceptionWorks(Project project) throws BusinessException;
      public boolean deletedSucessfulReceptionWorks(Project project) throws BusinessException;
      public boolean deletedSucessfulColaborators(Project project) throws BusinessException;
      public boolean deletedSucessfulStudents(Project project) throws BusinessException;
      public boolean deletedSucessfulLGACS(Project project) throws BusinessException;
      
}
