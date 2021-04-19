package businessLogic;
import domain.Member;
import log.BusinessException;

public interface IMemberDAO {
    public boolean saveMember(Member member, String KeyGroupAcademic) throws BusinessException;
    public String searchProfessionalLicense(String memberName) throws BusinessException;
    public Member getMemberByLicense(String license) throws BusinessException;
    public boolean findMemberByLicense(String license);  
}