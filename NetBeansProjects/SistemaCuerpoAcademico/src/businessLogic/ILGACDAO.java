
package businessLogic;

import domain.LGAC;
import log.BusinessException;

public interface ILGACDAO {
    public boolean save(LGAC lgac)  throws BusinessException;
    public LGAC getLgacByName(String name) throws BusinessException;
    public boolean update(String beforeName,LGAC lgac) throws BusinessException;
}
