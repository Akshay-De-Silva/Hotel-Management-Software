public class Person {
    private String firstName = "";
    private String lastName = "";
    private int creditCardNum;
    private boolean emptyRoom = true;

    public Person() {       //basic constructor
    }

    public boolean isEmptyRoom() {
        return emptyRoom;               //getter and setter for empty room check
    }

    public void setEmptyRoom(boolean emptyRoom) {
        this.emptyRoom = emptyRoom;
    }

    public String getFirstName() {
        return firstName;               //getter and setter for first name
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;                //getter and setter for last name
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getCreditCardNum() {
        return creditCardNum;          //getter and setter for credit card number
    }

    public void setCreditCardNum(int creditCardNum) {
        this.creditCardNum = creditCardNum;
    }

    @Override
    public String toString() {
        return "{Person: " + firstName + ", " + lastName + ", " + creditCardNum + "}";
    }

}
