package domain;

/**
 *
 * @author Mariana
 */
public class Agreement {
    private String period;
    private String description;
    private int idAgreement;
    private int idMinute;
    private String professionalLicense;

    public Agreement(String period, String description,int idAgreement,int idMinute, String professionalLicense ){
        this.period = period;
        this.description = description;
        this.idMinute = idMinute;
        this.idAgreement = idAgreement;
        this.professionalLicense = professionalLicense;
    }
    
    public Agreement(String period, String description){
        this.period = period;
        this.description = description;
    }
    public int getIdAgreement(){
        return idAgreement;
    }

    public void setIdAgreement(int idAgreement){
        this.idAgreement = idAgreement;
    }
    public String getPeriod(){
        return period;
    }

    public void setPeriod(String period){
        this.period = period;
    }

    public String getDescription(){
        return description;
    }

    public void setDescription(String description){
        this.description = description;
    }

}