package osselin.diagnosisapi.model;

public class PatientNote {

    public String id;
    public String sqlId;
    public String firstName;
    public String lastName;
    public String observation;

    public PatientNote() {
    }

    public PatientNote(String sqlId, String firstName, String lastName, String observation) {
        this.sqlId = sqlId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.observation = observation;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSqlId() {
        return sqlId;
    }

    public void setSqlId(String sqlId) {
        this.sqlId = sqlId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }
}
