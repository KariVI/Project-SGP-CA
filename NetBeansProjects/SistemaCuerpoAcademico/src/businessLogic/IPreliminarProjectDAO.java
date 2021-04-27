
package businessLogic;

import domain.PreliminarProject;
import java.util.ArrayList;
import log.BusinessException;

public interface IPreliminarProjectDAO {
    public boolean save(PreliminarProject preliminarProject) throws BusinessException;
    public int getId(PreliminarProject preliminarProject)throws BusinessException;
    public boolean update(int id, PreliminarProject preliminarProject) throws BusinessException;
    public ArrayList<PreliminarProject> getPreliminarProjects() throws BusinessException;
    public PreliminarProject getById(int id) throws BusinessException ;
    public boolean addColaborators(PreliminarProject preliminarProject)throws BusinessException;
}
