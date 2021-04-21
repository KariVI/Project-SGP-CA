
package businessLogic;

import domain.Agreement;
import java.util.ArrayList;
import log.BusinessException;

public interface IAgreement {
    public boolean saveAgreement(Agreement agreement)throws BusinessException;
     public ArrayList<Agreement>  getAgreements()throws BusinessException;
     public boolean update(Agreement newAgreement) throws BusinessException;
}
