import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Hotel {

    Scanner scan = new Scanner(System.in);
    boolean deleteFlag = false; //Used to prevent Scanner buffer overflow

    private final ArrayList<Room> hotelRooms = new ArrayList<>();
    private final ArrayList<Room> hotelQueueRooms = new ArrayList<>();

    private int front = -1;
    private int rear = -1;      //used for traversal of circular queue
    private int count = 0;

    public void addRoom(Room room) {   //ARRAYLIST OF ROOM OBJECTS
        hotelRooms.add(room);
    }

    public void addToCircularQueue(Room queueRoom) {    //arraylist of queued rooms objects
        hotelQueueRooms.add(queueRoom);
    }

    public boolean isHotelFull() {          //used in add method to check guest goes to hotel or circular queue
        boolean hotelFull = true;
        for (int x = 0; x < 8; x++) {
            if (hotelRooms.get(x).getPayingGuest().isEmptyRoom()) {
                hotelFull = false;
                break;
            }
        }
        return hotelFull;
    }

    public String validChoice (String text) {   //validates if choice was Y or N
        boolean choiceValid = false;
        String queueChoice;

        do {
            System.out.println(text);
            queueChoice = scan.nextLine();

            if (queueChoice.equals("Y") || queueChoice.equals("N")) {
                choiceValid = true;
            } else {
                System.out.println("Invalid Input, Please Enter Again");
            }
        } while (!choiceValid);

        return queueChoice;
    }

    public int validRoomNum (String text) {     //validates if room number entered is an integer and is between 0 to 7
        boolean numValid = false;
        int roomNum = 0;

        do {
            try {
                System.out.println(text);
                roomNum = scan.nextInt();
            } catch (Exception e) {
                System.out.println("Invalid Input, Please Enter Again");
                scan.next();
                continue;
            }

            if (roomNum<0 || roomNum>8) {
                System.out.println("Invalid Number Input, Please Enter Again");
                continue;
            }
            numValid = true;

        } while (!numValid);

        return roomNum;
    }

    public int validCreditAndGuests(String text) {  //validates if user entered an integer for the credit card an number of extra guests
        boolean intValid = false;
        int creditOrNumGuest = 0;

        do {
            try {
                System.out.println(text);
                creditOrNumGuest = scan.nextInt();
            } catch (Exception e) {
                System.out.println("Input was not a Number, Please Enter Again");
                scan.next();
                continue;
            }
            intValid = true;
        } while (!intValid);

        return creditOrNumGuest;
    }

    public void viewRooms() {       //displays every room as well as if each room is empty or occupied by a certain guest
        for (int x = 0; x < 8; x++) {
            if (hotelRooms.get(x).getPayingGuest().isEmptyRoom()) {
                System.out.println("Room " + x + " is empty");
            } else {
                System.out.println("Room " + x + " is occupied by " + hotelRooms.get(x).getPayingGuest().getFirstName() + " " + hotelRooms.get(x).getPayingGuest().getLastName() + " with " + hotelRooms.get(x).getNumOfGuests() + " other guests in the room");
            }
        }
        System.out.print("\n");
    }

    public void addGuest() {        //adds a guest to the room number entered (saves guest to circular queue if hotel is full)
        boolean endLoop = false;

        do {
            if (!isHotelFull()) {
                String text = "Enter room number (0-7) or 8 to stop: ";
                int roomNum = validRoomNum(text);

                if (roomNum == 8) {
                    System.out.println("Cancelled Adding Guest");
                    endLoop = true;
                } else {
                    if (!hotelRooms.get(roomNum).getPayingGuest().isEmptyRoom()) {
                        System.out.println("Room is already occupied by " + hotelRooms.get(roomNum).getPayingGuest().getFirstName() + " " + hotelRooms.get(roomNum).getPayingGuest().getLastName());
                    } else {
                        scan.nextLine();
                        System.out.println("Enter First Name of Paying Guest for room " + roomNum);
                        hotelRooms.get(roomNum).getPayingGuest().setFirstName(scan.nextLine());
                        System.out.println("Enter Last Name of Paying Guest for room " + roomNum);
                        hotelRooms.get(roomNum).getPayingGuest().setLastName(scan.nextLine());
                        String creditString = "Enter Paying Guest's Credit Card Number for room " + roomNum;
                        hotelRooms.get(roomNum).getPayingGuest().setCreditCardNum(validCreditAndGuests(creditString));
                        String numOfGuestsString = "Enter Number of Extra Guests for room " + roomNum;
                        hotelRooms.get(roomNum).setNumOfGuests(validCreditAndGuests(numOfGuestsString));
                        System.out.print("\n" + hotelRooms.get(roomNum).getPayingGuest().getFirstName() + " " + hotelRooms.get(roomNum).getPayingGuest().getLastName() + " and " + hotelRooms.get(roomNum).getNumOfGuests() + " other guests have been added to room " + roomNum);
                        hotelRooms.get(roomNum).getPayingGuest().setEmptyRoom(false);
                        endLoop = true;
                    }
                }
            } else {
                if (deleteFlag) {
                    scan.nextLine();
                    deleteFlag = false;
                }
                String queueChoice = validChoice("Hotel is Currently Full. Would you like to be added to the Waiting List? Answer Y/N");
                if (queueChoice.equals("N")) {
                    System.out.println("Cancelled Adding Guest");
                    endLoop = true;
                } else if (queueChoice.equals("Y") && !isFull()) {
                    scan.nextLine();
                    System.out.println("Enter First Name of Paying Guest ");
                    hotelQueueRooms.get(count).getPayingGuest().setFirstName(scan.nextLine());
                    System.out.println("Enter Last Name of Paying Guest ");
                    hotelQueueRooms.get(count).getPayingGuest().setLastName(scan.nextLine());
                    String creditString = "Enter Paying Guest's Credit Card Number ";
                    hotelQueueRooms.get(count).getPayingGuest().setCreditCardNum(validCreditAndGuests(creditString));
                    String numOfGuestsString = "Enter Number of Extra Guests ";
                    hotelQueueRooms.get(count).setNumOfGuests(validCreditAndGuests(numOfGuestsString));
                    System.out.print("\n" + hotelQueueRooms.get(count).getPayingGuest().getFirstName() + " " + hotelQueueRooms.get(count).getPayingGuest().getLastName() + " and " + hotelQueueRooms.get(count).getNumOfGuests() + " other guests have been added to the Waiting List ");
                    hotelQueueRooms.get(count).getPayingGuest().setEmptyRoom(false);
                    endLoop = true;
                    addDataToQueue(hotelQueueRooms.get(count));
                    if (count < 2) {
                        count++;
                    } else {
                        count = 0;
                    }
                } else {
                    System.out.println("Queue is Currently Full");
                    endLoop = true;
                }
            }

        } while (!endLoop);

        System.out.println("\n");
    }

    public void emptyRooms() {      //displays every empty room
        boolean noRooms = true;

        for (int x = 0; x < 8; x++ ) {
            if (hotelRooms.get(x).getPayingGuest().isEmptyRoom()) {
                System.out.println("Room " + x + " is empty");
                noRooms = false;
            }
        }

        if (noRooms) {
            System.out.println("No Rooms Available");
        }

        System.out.print("\n");
    }

    public void deleteGuest() {     //removes the guest from the specified room number (and adds the front guest from the circular queue to the freed up room)
        String text = "Which room would you like to vacate? ";
        int roomNum = validRoomNum(text);

        for (int x = 0; x < 8; x++) {
            if (x == roomNum) {
                if (hotelRooms.get(roomNum).getPayingGuest().isEmptyRoom()) {
                    System.out.println("\nRoom " + roomNum + " is already empty");
                } else {
                    System.out.println("\n" + hotelRooms.get(roomNum).getPayingGuest().getFirstName() + " " + hotelRooms.get(roomNum).getPayingGuest().getLastName() + " and " + hotelRooms.get(roomNum).getNumOfGuests() + " other guests have been removed from room " + roomNum);
                    if (front == -1) {
                        hotelRooms.get(roomNum).getPayingGuest().setEmptyRoom(true);
                        hotelRooms.get(roomNum).getPayingGuest().setFirstName("");
                        hotelRooms.get(roomNum).getPayingGuest().setLastName("");
                        hotelRooms.get(roomNum).getPayingGuest().setCreditCardNum(0);
                        hotelRooms.get(roomNum).setNumOfGuests(0);
                    } else {
                        Room tempRoom = removeFromQueue();
                        hotelRooms.get(roomNum).getPayingGuest().setEmptyRoom(tempRoom.getPayingGuest().isEmptyRoom());
                        hotelRooms.get(roomNum).getPayingGuest().setFirstName(tempRoom.getPayingGuest().getFirstName());
                        hotelRooms.get(roomNum).getPayingGuest().setLastName(tempRoom.getPayingGuest().getLastName());
                        hotelRooms.get(roomNum).getPayingGuest().setCreditCardNum(tempRoom.getPayingGuest().getCreditCardNum());
                        hotelRooms.get(roomNum).setNumOfGuests(tempRoom.getNumOfGuests());

                        System.out.println("\n" + hotelRooms.get(roomNum).getPayingGuest().getFirstName() + " " + hotelRooms.get(roomNum).getPayingGuest().getLastName() + " and " + hotelRooms.get(roomNum).getNumOfGuests() + " other guests have been added from the waiting list to room " + roomNum);
                    }

                }
            }
        }
        deleteFlag = true;
        System.out.print("\n");

    }

    public void findGuest() {   //locates guests room number by searching the guests name
        boolean noFoundGuest = true;

        if (deleteFlag) {
            scan.nextLine();
            deleteFlag = false;
        }

        System.out.println("Enter Paying Guest's First Name: ");
        String name = scan.nextLine();

        for (int x = 0; x < 8; x++) {
            if (hotelRooms.get(x).getPayingGuest().getFirstName().equals(name)) {
                System.out.println(name + " " + hotelRooms.get(x).getPayingGuest().getLastName() + " is in room " + x);
                noFoundGuest = false;
            }
        }
        if (noFoundGuest) {
            System.out.println("No Guest with the name " + name + " has been found");
        }
        System.out.print("\n");
    }

    public void writeFile() {       //saves array of room object's data to a text file (will not save the waiting list)
        String fileName = "Hotel Room Data.txt";

        try {
            PrintWriter outStream = new PrintWriter(fileName);

            for (int x = 0; x < 8; x++) {
                outStream.println(hotelRooms.get(x).getPayingGuest().getFirstName());
                outStream.println(hotelRooms.get(x).getPayingGuest().getLastName());
                outStream.println(hotelRooms.get(x).getPayingGuest().getCreditCardNum());
                outStream.println(hotelRooms.get(x).getNumOfGuests());
                outStream.println(hotelRooms.get(x).getPayingGuest().isEmptyRoom());
            }

            outStream.close();
            System.out.println("Program data has been saved to file");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        System.out.print("\n");

    }

    public void readFile() {        //loads the previously saved array of room object's data from the text file
        File fileName = new File("Hotel Room Data.txt");

        try {
            Scanner scanFile = new Scanner(fileName);

            for (int x = 0; x < 8; x++) {
                hotelRooms.get(x).getPayingGuest().setFirstName(scanFile.nextLine());
                hotelRooms.get(x).getPayingGuest().setLastName(scanFile.nextLine());
                hotelRooms.get(x).getPayingGuest().setCreditCardNum(Integer.parseInt(scanFile.nextLine()));
                hotelRooms.get(x).setNumOfGuests(Integer.parseInt(scanFile.nextLine()));
                hotelRooms.get(x).getPayingGuest().setEmptyRoom(Boolean.parseBoolean(scanFile.nextLine()) );
            }

            System.out.println("Program Data has been loaded:\n");

            viewRooms();

        } catch (FileNotFoundException e) {
            System.out.println("No Saved File Detected");
        }

        System.out.print("\n");

    }

    public void sortGuests() {      //sorts and displays guest names sorted alphabetically (bubble sort)
        String temp;
        boolean addedToArray = false;
        String [] sortedGuests = new String[8];

        for (int w = 0; w < 8; w++) {
            if (!hotelRooms.get(w).getPayingGuest().isEmptyRoom()) {
                sortedGuests[w] = hotelRooms.get(w).getPayingGuest().getFirstName().toLowerCase();
                addedToArray = true;
            } else {
                sortedGuests[w] = "placeholder";
            }
        }

        if (addedToArray) {
            for (int x = 0; x < sortedGuests.length; x++) {
                for (int y = x + 1; y < sortedGuests.length; y++) {
                    if (sortedGuests[x].compareTo(sortedGuests[y]) > 0) {
                        temp = sortedGuests[x];
                        sortedGuests[x] = sortedGuests[y];
                        sortedGuests[y] = temp;
                    }
                }
            }

            System.out.println("Guest Names Sorted:\n");

            for (int z = 0; z < 8; z++) {
                if (!sortedGuests[z].equals("placeholder")) {
                    System.out.println(sortedGuests[z]);
                }
            }
        } else {
            System.out.println("No Guests available to sort");
        }

        System.out.print("\n");

    }

    //CIRCULAR QUEUE METHODS

    public boolean isEmpty() {  //checks if the circular queue is empty
        return front == -1;
    }

    public boolean isFull() {   //checks if the circular queue is full
        return (rear + 1) % hotelQueueRooms.size() == front;
    }

    public void addDataToQueue(Room room) {     //adds guest to circular queue (enqueue method)
        if (isFull()) {
            System.out.println("Queue is full");
        } else {
            if (isEmpty()) {
                front++;
            }
            rear = (rear + 1) % hotelQueueRooms.size();
            hotelQueueRooms.set(rear, room);
        }
    }

    public Room removeFromQueue() {             //returns the front guest from the circular queue (dequeue method)
        Room temp = hotelQueueRooms.get(front);
        if (front == rear) {
            front = rear = -1;
        } else {
            front = (front+1) % hotelQueueRooms.size();
        }
        return temp;

    }

}