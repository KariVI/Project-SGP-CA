
package businessLogic;

import domain.LGAC;
import java.util.ArrayList;
import log.BusinessException;

public interface ILGACDAO {
    public boolean savedSucessful(LGAC lgac)  throws BusinessException;
    public LGAC getLgacByName(String name) throws BusinessException;
    public boolean updatedSucessful(String beforeName,LGAC lgac) throws BusinessException;
    public ArrayList<LGAC> getLGACs() throws BusinessException;
}
