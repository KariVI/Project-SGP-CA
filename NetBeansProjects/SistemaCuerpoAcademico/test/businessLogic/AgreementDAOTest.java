/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businessLogic;

import domain.Agreement;
import java.util.ArrayList;
import log.BusinessException;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Mariana
 */
public class AgreementDAOTest {
    

    @Test
    public void testSaveAgreement() throws BusinessException{
        AgreementDAO agreementDAO= new AgreementDAO();
        Agreement agreementAuxiliar;
        int idMinute = 1;
        String professionaLicense = "4065161";
        agreementAuxiliar = new Agreement("Septiembre 2020","Realizar reunión para evaliación");
        assertTrue(agreementDAO.saveAgreement(agreementAuxiliar, idMinute,professionaLicense));
    }
    
    @Test
    public void testGetAgreements() throws BusinessException {
        AgreementDAO agreementDAO = new AgreementDAO();
        ArrayList<Agreement> result = agreementDAO.getAgreements();
        assertNotNull(result);
    }
 
}
