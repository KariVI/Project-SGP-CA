
package businessLogic;

import domain.LGAC;
import java.util.ArrayList;
import log.BusinessException;

public interface ILGCADAO {
    public boolean save(LGAC lgac)  throws BusinessException;
    public LGAC getLgacByName(String name) throws BusinessException;
    public boolean update(String beforeName,LGAC lgac) throws BusinessException;
    public ArrayList<LGAC> getLGACs() throws BusinessException;
}
