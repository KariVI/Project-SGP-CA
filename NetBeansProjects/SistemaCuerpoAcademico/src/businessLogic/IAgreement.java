
package businessLogic;

import domain.Agreement;
import java.util.ArrayList;
import log.BusinessException;

public interface IAgreement {
    public boolean saveAgreement(Agreement agreement, int idMinute, String professionalLicense)throws BusinessException;
     public ArrayList<Agreement>  getAgreements()throws BusinessException;
}
