
package businessLogic;

import domain.Member;
import domain.ReceptionWork;
import domain.Student;
import java.util.ArrayList;
import log.BusinessException;

public interface IReceptionWorkDAO {
    public boolean savedSucessful(ReceptionWork receptionWork)  throws BusinessException;
    public boolean updatedSucessful(int id, ReceptionWork receptionWork)  throws BusinessException;
    public ReceptionWork getReceptionWorkById(int id) throws BusinessException;
    public ArrayList<ReceptionWork> getReceptionWorks() throws BusinessException;
    public boolean addedSucessfulColaborators(ReceptionWork receptionWork)throws BusinessException;
    public boolean addedSucessfulStudents (ReceptionWork receptionWork) throws BusinessException;
    public int getId(ReceptionWork receptionWork) throws BusinessException;
    public ArrayList<Member> getColaborators(int idReceptionWork) throws BusinessException;
    public boolean deletedSucessfulColaborators(ReceptionWork receptionWork) throws BusinessException;
    public ArrayList<Student> getStudents(int idReceptionWork) throws BusinessException;
    public boolean deletedSucessfulStudents(ReceptionWork receptionWork) throws BusinessException;
}
