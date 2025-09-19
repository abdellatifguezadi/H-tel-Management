package Services;

import Model.Client;
import Model.Hotel;
import Model.Reservation;
import repository.ClientRepository;
import repository.HotelRepository;
import repository.ReservationRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final HotelRepository hotelRepository;
    private final ClientRepository clientRepository;

    public ReservationService(ReservationRepository reservationRepository, HotelRepository hotelRepository, ClientRepository clientRepository) {
        this.reservationRepository = reservationRepository;
        this.hotelRepository = hotelRepository;
        this.clientRepository = clientRepository;
    }


    public Reservation createReservation(Client client, String hotelId, int nights) {
        if (client == null) {
            throw new IllegalArgumentException("Client requis");
        }


        if (hotelId == null || hotelId.trim().isEmpty()) {
            throw new IllegalArgumentException("ID d'hôtel requis");
        }
        if (nights <= 0) {
            throw new IllegalArgumentException("Le nombre de nuits doit être supérieur à 0");
        }


        Optional<Hotel> hotelOptional = hotelRepository.findById(hotelId);
        if (hotelOptional.isEmpty()) {
            throw new IllegalArgumentException("Hôtel introuvable");
        }

        Hotel hotel = hotelOptional.get();

        if (!hotel.hasAvailableRooms()) {
            throw new IllegalArgumentException("Aucune chambre disponible dans cet hôtel");
        }

        Reservation reservation = new Reservation(hotelId, client.getId(), nights);


        hotel.reserveRoom();
        hotelRepository.save(hotel);


        return reservationRepository.save(reservation);
    }


    public boolean cancelReservation(UUID reservationId, Client client) {
        if (client == null) {
            throw new IllegalArgumentException("Client requis");
        }
        Optional<Reservation> reservationOptional = reservationRepository.findById(reservationId);
        if (reservationOptional.isEmpty()) {
            throw new IllegalArgumentException("Réservation introuvable");
        }
        Reservation reservation = reservationOptional.get();

        if (!client.isAdmin() && !reservation.getClientId().equals(client.getId())) {
            throw new IllegalArgumentException("Vous ne pouvez annuler que vos propres réservations");
        }
        Optional<Hotel> hotelOptional = hotelRepository.findById(reservation.getHotelId());
        if (hotelOptional.isPresent()) {
            Hotel hotel = hotelOptional.get();
            hotel.releaseRoom();
            hotelRepository.save(hotel);
        }
        reservationRepository.delete(reservationId);
        return true;
    }


    public List<Reservation> getReservationsByClient(Client client) {
        if (client == null) {
            throw new IllegalArgumentException("Client requis");
        }
        return reservationRepository.findByClientId(client.getId());
    }


    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    public List<Reservation> getReservationsByHotel(String hotelId) {
        if (hotelId == null || hotelId.trim().isEmpty()) {
            throw new IllegalArgumentException("ID d'hôtel requis");
        }
        return reservationRepository.findByHotelId(hotelId);
    }

    public String getClientNameById(UUID clientId) {
        if (clientId == null) return "Inconnu";
        return clientRepository.findById(clientId)
                .map(Client::getFullName)
                .orElse("Inconnu");
    }
}
