package Services;

import Model.Hotel;
import repository.HotelRepository;
import repository.MemoryHotel;

import java.util.List;
import java.util.Optional;

public class HotelService {
    private final HotelRepository hotelRepository;

    public HotelService(MemoryHotel hotelRepository) {
        this.hotelRepository = hotelRepository;
    }


    public Hotel createHotel(String hotelId, String name, String address, int availableRooms, double rating) {

        if (hotelId == null || hotelId.trim().isEmpty()) {
            throw new IllegalArgumentException("L'ID de l'hôtel ne peut pas être vide");
        }
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Le nom de l'hôtel ne peut pas être vide");
        }
        if (address == null || address.trim().isEmpty()) {
            throw new IllegalArgumentException("L'adresse ne peut pas être vide");
        }
        if (availableRooms < 0) {
            throw new IllegalArgumentException("Le nombre de chambres doit être supérieur ou égal à 0");
        }
        if (rating < 0 || rating > 5) {
            throw new IllegalArgumentException("La note doit être comprise entre 0 et 5");
        }


        if (hotelRepository.existsById(hotelId)) {
            throw new IllegalArgumentException("Un hôtel avec cet ID existe déjà");
        }

        Hotel hotel = new Hotel(hotelId.trim(), name.trim(), address.trim(), availableRooms, rating);
        return hotelRepository.save(hotel);
    }

    public boolean updateHotel(String hotelId, String name, String address, int availableRooms, double rating) {
        Optional<Hotel> hotelOptional = hotelRepository.findById(hotelId);
        if (hotelOptional.isEmpty()) {
            throw new IllegalArgumentException("Hôtel introuvable");
        }

        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Le nom de l'hôtel ne peut pas être vide");
        }
        if (address == null || address.trim().isEmpty()) {
            throw new IllegalArgumentException("L'adresse ne peut pas être vide");
        }
        if (availableRooms < 0) {
            throw new IllegalArgumentException("Le nombre de chambres doit être supérieur ou égal à 0");
        }
        if (rating < 0 || rating > 5) {
            throw new IllegalArgumentException("La note doit être comprise entre 0 et 5");
        }

        Hotel hotel = hotelOptional.get();
        hotel.setName(name.trim());
        hotel.setAddress(address.trim());
        hotel.setAvailableRooms(availableRooms);
        hotel.setRating(rating);
        hotelRepository.save(hotel);
        return true;
    }


    public boolean deleteHotel(String hotelId) {
        if (!hotelRepository.existsById(hotelId)) {
            throw new IllegalArgumentException("Hôtel introuvable");
        }
        hotelRepository.delete(hotelId);
        return true;
    }


    public List<Hotel> getAllHotels() {
        return hotelRepository.findAll();
    }


    public Optional<Hotel> getHotelById(String hotelId) {
        return hotelRepository.findById(hotelId);
    }


    public List<Hotel> getAvailableHotels() {
        return hotelRepository.findHotelsAvailable();
    }


    public boolean isHotelAvailable(String hotelId) {
        Optional<Hotel> hotelOptional = hotelRepository.findById(hotelId);
        return hotelOptional.isPresent() && hotelOptional.get().hasAvailableRooms();
    }

}
