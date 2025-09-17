import Model.Client;
import Model.Hotel;
import Model.Reservation;

import java.time.Instant;
import java.util.UUID;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        // Create a new Client object
        Client client = new Client(
                UUID.randomUUID(),             // generate unique ID
                "John Doe",                    // full name
                "john@example.com",            // email
                "securePass123"                // password
        );

        Client client2 = new Client(
                UUID.randomUUID(),             // generate unique ID
                "guezadi",                    // full name
                "guezadi@gmail.com",            // email
                "Pass1234"                // password
        );

        Hotel hotel = new Hotel(
                "hotel1" , "test" , "mazola rue 3 n 60" , 5 , 2.12
        );

        Reservation res1 = new Reservation(
                UUID.randomUUID(),
                Instant.now(),
                hotel.getHotelId(),
                client2.getId(),
                5
        );

        Reservation res2 = new Reservation(
                UUID.randomUUID(),      // reservation id
                Instant.now(),          // current timestamp
                hotel.getHotelId(),     // hotel ID
                client.getId(),         // client ID
                3                       // number of nights
        );

        // Print client details
        System.out.println(client.toString());
        System.out.println(hotel.toString());
        System.out.println(res1.toString());
        System.out.println(res2.toString());

    }
}