package osselin.diagnosisapi.model;

/**
 * <p>PatFamilyDto is a DTO to communicate with the Post Method assess by names.</p>
 *
 * <p>It is a requirement from the client.</p>
 *
 */
public class PatFamilyDto {

    private String firstName;
    private String familyName;

    public PatFamilyDto() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }
}
