public class Room{

    private final Person payingGuest;
    private int numOfGuests;

    public Room(Person payingGuest) {       //constructor
        this.payingGuest = payingGuest;
    }

    public int getNumOfGuests() {
        return numOfGuests;
    }                           //getter and setter for number of guests

    public void setNumOfGuests(int numOfGuests) {
        this.numOfGuests = numOfGuests;
    }

    public Person getPayingGuest() {        //only getter for paying guest since setting it in the person class
        return payingGuest;
    }

    @Override
    public String toString() {
        return "{Room: " + payingGuest + ", " + numOfGuests + "}";
    }
}
