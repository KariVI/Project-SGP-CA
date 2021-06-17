package businessLogic;

import domain.Agreement;
import java.util.ArrayList;
import log.BusinessException;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author Mariana
 */
public class AgreementDAOTest {
    

    @Test
    public void testSaveAgreement() throws BusinessException{
        AgreementDAO agreementDAO= new AgreementDAO();     
        int idMinute = 1;
        String professionalLicense = "4065161";
        Agreement agreementAuxiliar = new Agreement("Septiembre 2020","Realizar reunión para evaliación", idMinute,professionalLicense);
        assertTrue(agreementDAO.savedSucessfulAgreement(agreementAuxiliar));
    }
    
    @Test
    public void testGetAgreements() throws BusinessException {
        AgreementDAO agreementDAO = new AgreementDAO();
        ArrayList<Agreement> resultExpected = new ArrayList<Agreement>();
        int idMinute = 1;
        String professionalLicense = "4065161";
        Agreement agreementAuxiliar = new Agreement("Septiembre 2020","Realizar reunión para evaliación", idMinute,professionalLicense);
        resultExpected.add(agreementAuxiliar);
        ArrayList<Agreement> result = agreementDAO.getAgreements();
        assertNotNull(result);
    }
    
    @Test 
    public void testUpdate() throws BusinessException {
        System.out.println("update");
        int idMinute = 1;
        String professionalLicense = "4065161";
        Agreement newAgreement = new Agreement("Septiembre 2021","Realizar reunión para evaliación",1,idMinute,professionalLicense);
        AgreementDAO agreementDAO = new AgreementDAO();
        assertTrue(agreementDAO.updatedSucessful(newAgreement));
    }
    
    @Test 
    public void testDelete() throws BusinessException {
        System.out.println("delete");
        int idMinute = 1;
        String professionalLicense = "4065161";
        Agreement agreement = new Agreement("Septiembre 2021","Realizar reunión para evaliación",1,idMinute,professionalLicense);
        AgreementDAO agreementDAO = new AgreementDAO();
        assertTrue(agreementDAO.deletedSucessful(agreement));
    }
}
