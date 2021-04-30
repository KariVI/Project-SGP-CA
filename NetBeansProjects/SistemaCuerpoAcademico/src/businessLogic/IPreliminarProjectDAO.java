
package businessLogic;

import domain.LGAC;
import domain.Member;
import domain.PreliminarProject;
import domain.Student;
import java.util.ArrayList;
import log.BusinessException;

public interface IPreliminarProjectDAO {
    public boolean save(PreliminarProject preliminarProject) throws BusinessException;
    public int getId(PreliminarProject preliminarProject)throws BusinessException;
    public boolean update(int id, PreliminarProject preliminarProject) throws BusinessException;
    public ArrayList<PreliminarProject> getPreliminarProjects() throws BusinessException;
    public PreliminarProject getById(int id) throws BusinessException ;
    public boolean addColaborators(PreliminarProject preliminarProject)throws BusinessException;
    public boolean addStudents(PreliminarProject preliminarProject) throws BusinessException;
    public ArrayList<Member> getColaborators(int idPreliminarProject) throws BusinessException;
    public boolean deleteColaborators(PreliminarProject preliminarProject) throws BusinessException;
    public ArrayList<Student> getStudents(int idPreliminarProject) throws BusinessException;
    public boolean deleteStudents(PreliminarProject preliminarProject) throws BusinessException;
    public boolean addLGAC(PreliminarProject preliminarProject) throws BusinessException;
     public ArrayList<LGAC> getLGACs(PreliminarProject preliminarProject) throws BusinessException;
}

