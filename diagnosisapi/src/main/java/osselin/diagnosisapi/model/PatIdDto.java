package osselin.diagnosisapi.model;

/**
 * <p>PatIdDto is a DTO to communicate with the Post Method assess by id.</p>
 *
 * <p>It is a requirement from the client.</p>
 *
 */
public class PatIdDto {

    public Integer patId;

    public PatIdDto() {
    }

    public Integer getPatId() {
        return patId;
    }

    public void setPatId(Integer patId) {
        this.patId = patId;
    }
}
