package businessLogic;

import domain.Agreement;
import java.util.ArrayList;
import log.BusinessException;
import static org.junit.Assert.*;
import org.junit.Test;

public class AgreementDAOTest {
    

    @Test
    public void testSaveAgreement() throws BusinessException{
        AgreementDAO agreementDAO= new AgreementDAO();     
        int idMinute = 2;
        String professionalLicense = "7938268";
        Agreement agreementAuxiliar = new Agreement("Ago-Ene","Realizar reunión para revaliación", idMinute, professionalLicense);
        assertTrue(agreementDAO.savedSucessfulAgreement(agreementAuxiliar));
    }
    
    @Test
    public void testGetAgreementsMinute() throws BusinessException {
        AgreementDAO agreementDAO = new AgreementDAO();
        ArrayList<Agreement> resultExpected = new ArrayList<Agreement>();
        int idMinute = 1;
        String professionalLicense = "7938268";
        int idAgreement = 1;
        Agreement agreementAuxiliar = new Agreement("Ene-Jul", "Seminario con tematica de IA",idAgreement,idMinute, professionalLicense);
        resultExpected.add(agreementAuxiliar);
        ArrayList<Agreement> result = agreementDAO.getAgreementsMinute(idMinute);
        assertEquals(result,resultExpected);
    }
    
    @Test 
    public void testUpdate() throws BusinessException {
        System.out.println("update");
        int idMinute = 2;
        int idAgreement = 2;
        String professionalLicense = "7938268";
        Agreement newAgreement = new Agreement("Ago-Ene","Realizar reunión para revaliación y agenda",idAgreement, idMinute, professionalLicense);
        AgreementDAO agreementDAO = new AgreementDAO();
        assertTrue(agreementDAO.updatedSucessful(newAgreement));
    }
    
    @Test 
    public void testDelete() throws BusinessException {
        System.out.println("delete");
        int idMinute = 2;
        int idAgreement = 2;
        String professionalLicense = "7938268";
        Agreement agreement = new Agreement("Ago-Ene","Realizar reunión para revaliación y agenda",idAgreement, idMinute, professionalLicense);
        AgreementDAO agreementDAO = new AgreementDAO();
        assertTrue(agreementDAO.deletedSucessful(agreement));
    }
    
}
