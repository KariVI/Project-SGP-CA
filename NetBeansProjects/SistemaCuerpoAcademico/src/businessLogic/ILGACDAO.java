
package businessLogic;

import domain.LGAC;

public interface ILGACDAO {
    public void save(LGAC lgac);
    public LGAC getGroupAcademicByName(String name);
}
