
package businessLogic;

import domain.LGCA;
import java.util.ArrayList;
import log.BusinessException;

public interface ILGCADAO {
    public boolean save(LGCA lgac)  throws BusinessException;
    public LGCA getLgacByName(String name) throws BusinessException;
    public boolean update(String beforeName,LGCA lgac) throws BusinessException;
    public ArrayList<LGCA> getLGACs() throws BusinessException;
}
