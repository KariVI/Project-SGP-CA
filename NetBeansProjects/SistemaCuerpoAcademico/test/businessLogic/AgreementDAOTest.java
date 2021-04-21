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
        String professionalLicense = "4065161";
        agreementAuxiliar = new Agreement("Septiembre 2020","Realizar reunión para evaliación", idMinute,professionalLicense);
        assertTrue(agreementDAO.saveAgreement(agreementAuxiliar));
    }
    
    @Test
    public void testGetAgreements() throws BusinessException {
        AgreementDAO agreementDAO = new AgreementDAO();
        ArrayList<Agreement> result = agreementDAO.getAgreements();
        assertNotNull(result);
    }
    
    @Test 
    public void testUpdate() throws BusinessException {
        System.out.println("update");
        int idMinute = 1;
        String professionalLicense = "4065161";
        Agreement newAgreement;
        newAgreement = new Agreement("Septiembre 2021","Realizar reunión para evaliación",1,idMinute,professionalLicense);
        AgreementDAO agreementDAO = new AgreementDAO();
        assertTrue(agreementDAO.update(newAgreement));
    }
    
    @Test 
    public void testDelete() throws BusinessException {
        System.out.println("delete");
        int idMinute = 1;
        String professionalLicense = "4065161";
        Agreement agreement = new Agreement("Septiembre 2021","Realizar reunión para evaliación",1,idMinute,professionalLicense);
        AgreementDAO agreementDAO = new AgreementDAO();
        assertTrue(agreementDAO.delete(agreement));
    }
}
