
package businessLogic;

import domain.Agreement;
import java.util.ArrayList;
import log.BusinessException;

public interface IAgreementDAO {
    public boolean savedSucessfulAgreement(Agreement agreement)throws BusinessException;
     public ArrayList<Agreement>  getAgreementsMinute(int idMinute) throws BusinessException;
     public boolean updatedSucessful(Agreement newAgreement) throws BusinessException;
     public boolean deletedSucessful(Agreement agreement) throws BusinessException;
}
