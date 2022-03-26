import java.util.Scanner;

public class MainHotelTest {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        String selection;
        boolean endLoop = false;

        Hotel hotel = new Hotel();

        Person [] payingGuests = new Person[8];
        Room [] rooms = new Room[8];
        for (int x = 0; x < payingGuests.length; x++) {     //initializes every payingGuest
            payingGuests[x] = new Person();                 //adds all declared payingGuests to each room object
            rooms[x] = new Room(payingGuests[x]);           // adds all declared rooms to the arraylist of room objects
            hotel.addRoom(rooms[x]);
        }

        Person [] queuePayingGuests = new Person[3];
        Room [] queueRooms = new Room[3];
        for (int y =0; y < queueRooms.length; y++) {        //same as above but initialising the 3 indexes in the circular queue
            queuePayingGuests[y] = new Person();
            queueRooms[y] = new Room(queuePayingGuests[y]);
            hotel.addToCircularQueue(queueRooms[y]);
        }

        while (!endLoop)
        {
            System.out.println("Select one of the following options:\n\nV to view all rooms\nA to add guests to a room\nE to view all empty rooms" +
                    "\nD to delete guests from a specified room\nF to find room using paying guest's first name\nS to store program data in file" +
                    "\nL to load program data from file\nO to view the paying guests ordered alphabetically by their first name\nQ to quit");
            selection = scan.nextLine();

            switch (selection) {    //loops till user types Q to quit
                case "V":
                    hotel.viewRooms();
                    break;
                case "A":
                    hotel.addGuest();
                    break;
                case "E":
                    hotel.emptyRooms();
                    break;
                case "D":
                    hotel.deleteGuest();
                    break;
                case "F":
                    hotel.findGuest();
                    break;
                case "S":
                    hotel.writeFile();
                    break;
                case "L":
                    hotel.readFile();
                    break;
                case "O":
                    hotel.sortGuests();
                    break;
                case "Q":
                    System.out.println("Thank you and Goodbye!");
                    endLoop = true;
                    break;
                default:
                    System.out.println("Invalid Option Selected");
                    break;
            }
        }
    }
}
