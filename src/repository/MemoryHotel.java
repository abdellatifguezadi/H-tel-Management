package repository;

import Model.Hotel;

import java.util.*;
import java.util.stream.Collectors;

public class MemoryHotel implements HotelRepository {
    private final Map<String, Hotel> hotels = new HashMap<>();

    public Hotel save(Hotel hotel){
        hotels.put(hotel.getHotelId(),hotel);
        return hotel;
    }

    @Override
    public Optional<Hotel> findById(String hotelId) {
                   return Optional.ofNullable(hotels.get(hotelId));
    }

    @Override
    public List<Hotel> findAll() {
        return new ArrayList<>(hotels.values());
    }

    @Override
    public void delete(String hotelId) {
        hotels.remove(hotelId);
    }

    @Override
    public List<Hotel> findHotelsAvailable() {
        return hotels.values().stream()
                .filter(Hotel::hasAvailableRooms)
                .collect(Collectors.toList());
    }

    @Override
    public boolean existsById(String hotelId) {
        return hotels.containsKey(hotelId);
    }


}
