
package businessLogic;

import domain.Student;
import log.BusinessException;

public interface IStudentDAO {
    public boolean savedSucessful(Student student) throws BusinessException;
    public Student getByEnrollment(String enrollment)throws BusinessException;
}
