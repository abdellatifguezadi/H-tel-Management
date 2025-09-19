package repository;

import Model.Reservation;

import java.util.*;
import java.util.stream.Collectors;

public class MemoryReservation implements ReservationRepository{

    private final Map<UUID, Reservation> reservations = new HashMap<>();

    @Override
    public Optional<Reservation> findById(UUID id) {
        return Optional.ofNullable(reservations.get(id));
    }

    @Override
    public Reservation save(Reservation reservation) {
        reservations.put(reservation.getId(), reservation);
        return reservation;
    }

    @Override
    public List<Reservation> findAll() {
        return new ArrayList<>(reservations.values());
    }

    @Override
    public void delete(UUID id) {
        reservations.remove(id);
    }

    @Override
    public List<Reservation> findByClientId(UUID clientId) {
        return reservations.values().stream()
                .filter(reservation -> reservation.getClientId().equals(clientId))
                .collect(Collectors.toList());
    }

    @Override
    public List<Reservation> findByHotelId(String hotelId) {
        return reservations.values().stream()
                .filter(reservation -> reservation.getHotelId().equals(hotelId))
                .collect(Collectors.toList());
    }

    @Override
    public boolean existById(UUID id) {
        return reservations.containsKey(id);
    }
}
