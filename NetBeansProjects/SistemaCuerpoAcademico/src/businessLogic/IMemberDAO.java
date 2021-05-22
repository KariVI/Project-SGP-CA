package businessLogic;
import domain.Member;
import java.util.ArrayList;
import log.BusinessException;

public interface IMemberDAO {
    public boolean saveMember(Member member) throws BusinessException;
    public String searchProfessionalLicense(String memberName) throws BusinessException;
    public Member getMemberByLicense(String license) throws BusinessException;
    public boolean memberExists(String license);  
    public boolean update( Member newMember) throws BusinessException;
    public boolean desactivateMember( Member newMember) throws BusinessException;
    public boolean activateMember( Member newMember) throws BusinessException;
    public ArrayList<Member> getMembers() throws BusinessException;
}