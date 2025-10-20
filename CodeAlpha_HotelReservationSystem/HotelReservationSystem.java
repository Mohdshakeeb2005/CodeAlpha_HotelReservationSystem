import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// -------------------------
// ROOM CLASS
// -------------------------
class Room {
    int roomId;
    String category;
    double price;
    boolean isAvailable;

    Room(int roomId, String category, double price) {
        this.roomId = roomId;
        this.category = category;
        this.price = price;
        this.isAvailable = true;
    }

    @Override
    public String toString() {
        return "Room " + roomId + " - " + category + " - ₹" + price + "/night - " + (isAvailable ? "Available" : "Booked");
    }
}

// -------------------------
// BOOKING CLASS
// -------------------------
class Booking {
    int roomId;
    String guestName;
    String checkIn;
    String checkOut;
    double paymentAmount;

    Booking(int roomId, String guestName, String checkIn, String checkOut, double paymentAmount) {
        this.roomId = roomId;
        this.guestName = guestName;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.paymentAmount = paymentAmount;
    }

    @Override
    public String toString() {
        return "Room " + roomId + " | Guest: " + guestName + " | Check-in: " + checkIn +
               " | Check-out: " + checkOut + " | Payment: ₹" + paymentAmount;
    }
}

// -------------------------
// HOTEL CLASS
// -------------------------
class Hotel {
    String name;
    List<Room> rooms;
    List<Booking> bookings;

    Hotel(String name) {
        this.name = name;
        this.rooms = new ArrayList<>();
        this.bookings = new ArrayList<>();
    }

    void addRoom(Room room) {
        rooms.add(room);
    }

    List<Room> searchRooms(String category) {
        List<Room> availableRooms = new ArrayList<>();
        for (Room room : rooms) {
            if (room.isAvailable && (category == null || room.category.equalsIgnoreCase(category))) {
                availableRooms.add(room);
            }
        }
        return availableRooms;
    }

    String makeReservation(int roomId, String guestName, String checkIn, String checkOut, double paymentAmount) {
        for (Room room : rooms) {
            if (room.roomId == roomId && room.isAvailable) {
                room.isAvailable = false;
                bookings.add(new Booking(roomId, guestName, checkIn, checkOut, paymentAmount));
                return "\n✅ Reservation successful for " + guestName + " in Room " + roomId +
                       " from " + checkIn + " to " + checkOut + ".";
            }
        }
        return "\n❌ Room not available or invalid room ID.";
    }

    String cancelReservation(int roomId) {
        for (Booking booking : bookings) {
            if (booking.roomId == roomId) {
                bookings.remove(booking);
                for (Room room : rooms) {
                    if (room.roomId == roomId) {
                        room.isAvailable = true;
                        return "\n🔁 Booking for Room " + roomId + " has been cancelled successfully.";
                    }
                }
            }
        }
        return "\n⚠️ No active booking found for Room " + roomId + ".";
    }

    List<String> viewBookings() {
        List<String> bookingDetails = new ArrayList<>();
        for (Booking booking : bookings) {
            bookingDetails.add(booking.toString());
        }
        return bookingDetails;
    }
}

// -------------------------
// MAIN CLASS
// -------------------------
public class HotelReservationSystem {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Hotel hotel = new Hotel("Grand Stay Hotel");

        // Predefined Rooms
        hotel.addRoom(new Room(101, "Single", 1200));
        hotel.addRoom(new Room(102, "Double", 1800));
        hotel.addRoom(new Room(103, "Suite", 3000));

        System.out.println("🏨 Welcome to " + hotel.name + " Reservation System!");
        int choice;

        do {
            System.out.println("\n==============================");
            System.out.println("1. View Available Rooms");
            System.out.println("2. Make a Reservation");
            System.out.println("3. Cancel a Reservation");
            System.out.println("4. View All Bookings");
            System.out.println("5. Exit");
            System.out.println("==============================");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    System.out.println("\nAvailable Rooms:");
                    for (Room room : hotel.searchRooms(null)) {
                        System.out.println(room);
                    }
                    break;

                case 2:
                    System.out.print("Enter Room ID to Book: ");
                    int roomId = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Enter Guest Name: ");
                    String guestName = sc.nextLine();

                    System.out.print("Enter Check-In Date (YYYY-MM-DD): ");
                    String checkIn = sc.nextLine();

                    System.out.print("Enter Check-Out Date (YYYY-MM-DD): ");
                    String checkOut = sc.nextLine();

                    System.out.print("Enter Payment Amount: ₹");
                    double payment = sc.nextDouble();

                    System.out.println(hotel.makeReservation(roomId, guestName, checkIn, checkOut, payment));
                    break;

                case 3:
                    System.out.print("Enter Room ID to Cancel Booking: ");
                    int cancelId = sc.nextInt();
                    System.out.println(hotel.cancelReservation(cancelId));
                    break;

                case 4:
                    System.out.println("\n📋 Current Bookings:");
                    if (hotel.viewBookings().isEmpty()) {
                        System.out.println("No bookings available.");
                    } else {
                        for (String booking : hotel.viewBookings()) {
                            System.out.println(booking);
                        }
                    }
                    break;

                case 5:
                    System.out.println("\nThank you for using " + hotel.name + " Reservation System!");
                    break;

                default:
                    System.out.println("Invalid choice! Please try again.");
            }

        } while (choice != 5);

        sc.close();
    }
}
