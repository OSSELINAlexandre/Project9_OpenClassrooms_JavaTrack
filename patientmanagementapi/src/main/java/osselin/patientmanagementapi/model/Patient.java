package osselin.patientmanagementapi.model;

import javax.persistence.*;
import java.util.Date;

/**
 * <b>Patient is the class representing a medical patient.</b>
 *
 * <p>
 *     The given attributes are required by the client of our project.
 * </p>

 * <p>
 * The required attributes in database are the id, the first name, the last name, the birthdate and the gender.
 * </p>
 *
 * <p>The address and phone number can be null in the database (it was required as such by the client).</p>
 *
 */
@Entity
@Table(name="patientmanagement")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="id_person")
    private int id;

    @Column(name= "firstname")
    private String firstName;

    @Column(name= "lastname")
    private String lastName;

    @Column(name= "birthdate")
    private Date dateOfBirth;

    @Column(name= "gender")
    private Character gender;

    @Column(name= "address")
    private String address;

    @Column(name= "phonenumber")
    private String phoneNumber;

    public Patient() {
    }

    public Patient(String firstName, String lastName, Date dateOfBirth, Character gender, String address, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public Patient(int id, String firstName, String lastName, Date dateOfBirth, Character gender, String address, String phoneNumber) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Character getGender() {
        return gender;
    }

    public void setGender(Character gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }


}
