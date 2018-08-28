package phonebook;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Ádám
 *
 * Írtunk egy pojot amely az adatokkal fog foglalkozni a táblázatttal
 * kapcsolatban. SimpleStringProperty - be kell elmenteni az adatokat mert csak
 * az kompatibilis a TableView- val.
 *
 */
public class Person extends pdfGenerator {

    private final SimpleStringProperty firstName;
    private final SimpleStringProperty lastName;
    private final SimpleStringProperty emailAddress;
    private final SimpleStringProperty ID;

    public Person() {
        this.firstName = new SimpleStringProperty("");
        this.lastName = new SimpleStringProperty("");
        this.emailAddress = new SimpleStringProperty("");
        this.ID = new SimpleStringProperty();

    }

    public Person(String fName, String lName, String eAdress) {
        this.firstName = new SimpleStringProperty(fName);
        this.lastName = new SimpleStringProperty(lName);
        this.emailAddress = new SimpleStringProperty(eAdress);
        this.ID = new SimpleStringProperty("0");

    }
    
    public Person(Integer ID, String fName, String lName, String eAdress) {
        this.firstName = new SimpleStringProperty(fName);
        this.lastName = new SimpleStringProperty(lName);
        this.emailAddress = new SimpleStringProperty(eAdress);
        this.ID = new SimpleStringProperty(String.valueOf(ID));

    }

    public String getFirstName() {
        return firstName.get();
    }

    public void setFirstName(String fName) {
        this.firstName.set(fName);
    }

    public String getLastName() {
        return lastName.get();
    }

    public void setLastName(String lName) {
        this.lastName.set(lName);
    }

    public String getEmailAddress() {
        return emailAddress.get();
    }

    public void setEmailAddress(String eAdress) {
        this.emailAddress.set(eAdress);
    }

    public void setID(String ID) {

        this.ID.set(ID);
    }

    public String getID() {

        return ID.get();
    }

}
