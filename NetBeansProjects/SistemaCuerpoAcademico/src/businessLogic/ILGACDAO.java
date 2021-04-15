
package businessLogic;

import domain.LGAC;

public interface ILGACDAO {
    public boolean save(LGAC lgac);
    public LGAC getLgacByName(String name);
}
