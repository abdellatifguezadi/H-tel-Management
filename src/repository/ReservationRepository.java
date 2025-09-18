package repository;


import Model.Reservation;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ReservationRepository {

    Reservation save(Reservation reservation);

    Optional<Reservation> findById(UUID id);

    List<Reservation> findAll();

    List<Reservation> findByClientId(UUID clientId);

    List<Reservation> findByHotelId(String hotelId);

    void delete(UUID id);

    boolean existById(UUID id);
}