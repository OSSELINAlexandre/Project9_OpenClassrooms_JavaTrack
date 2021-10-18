package osselin.patientnotes.model;

import org.springframework.data.annotation.Id;

public class PatientNote {

    @Id
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

    public String getSqlId() {
        return sqlId;
    }

    public void setSqlId(String sqlID) {
        this.sqlId = sqlID;
    }

    @Override
    public String toString() {
        return "PatientNote{" +
                "id='" + id + '\'' +
                ", sqlId='" + sqlId + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", observation='" + observation + '\'' +
                '}';
    }
}
