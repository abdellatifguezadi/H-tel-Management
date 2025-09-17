package repository ;


import Model.Hotel;

import java.util.List;
import java.util.Optional;

public interface HotelRepository {

    Hotel save(Hotel hotel);

    Optional<Hotel> findById(String hotelId);

    List<Hotel> findAll();

    void delete(String hotelId);

    List<Hotel> findHotelsAvailable();
}


