
package businessLogic;

import domain.LGAC;
import domain.Member;
import domain.PreliminarProject;
import domain.Student;
import java.util.ArrayList;
import log.BusinessException;

public interface IPreliminarProjectDAO {
    public boolean savedSucessful(PreliminarProject preliminarProject) throws BusinessException;
    public int getId(PreliminarProject preliminarProject)throws BusinessException;
    public boolean updatedSucessful(int id, PreliminarProject preliminarProject) throws BusinessException;
    public ArrayList<PreliminarProject> getPreliminarProjects() throws BusinessException;
    public PreliminarProject getById(int id) throws BusinessException ;
    public boolean addedSucessfulColaborators(PreliminarProject preliminarProject)throws BusinessException;
    public boolean addedSucessfulStudents(PreliminarProject preliminarProject) throws BusinessException;
    public ArrayList<Member> getColaborators(int idPreliminarProject) throws BusinessException;
    public boolean deletedSucessfulColaborators(PreliminarProject preliminarProject) throws BusinessException;
    public ArrayList<Student> getStudents(int idPreliminarProject) throws BusinessException;
    public boolean deletedSucessfulStudents(PreliminarProject preliminarProject) throws BusinessException;
    public boolean addedSucessfulLGAC(PreliminarProject preliminarProject) throws BusinessException;
     public ArrayList<LGAC> getLGACs(int idPreliminarProject) throws BusinessException;
     public boolean deletedSucessfulLGACS(PreliminarProject preliminarProject) throws BusinessException;
}

