package businessLogic;
import domain.Member;
import java.util.ArrayList;
import log.BusinessException;

public interface IMemberDAO {
    public boolean savedSucessfulMember(Member member) throws BusinessException;
    public String searchProfessionalLicenseByName(String memberName) throws BusinessException;
    public Member getMemberByLicense(String license) throws BusinessException;
    public boolean updatedSucessful( Member newMember) throws BusinessException;
    public boolean desactivateMember( Member newMember) throws BusinessException;
    public boolean activateMember( Member newMember) throws BusinessException;
    public ArrayList<Member> getMembers(String groupAcademicKey) throws BusinessException ;

}