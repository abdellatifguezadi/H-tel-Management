package Model;

public class Hotel {
    private String hotelId;
    private String name;
    private String address;
    private int availableRooms;
    private double rating;

    public Hotel(String hotelId, String name, String address, int availableRooms, double rating) {
        this.hotelId = hotelId;
        this.name = name;
        this.address = address;
        this.availableRooms = availableRooms;
        this.rating = rating;
    }

    public String getHotelId() {
        return hotelId;
    }

    public void setHotelId(String hotelId) {
        this.hotelId = hotelId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getAvailableRooms() {
        return availableRooms;
    }

    public void setAvailableRooms(int availableRooms) {
        this.availableRooms = availableRooms;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {

        this.address = address;
    }

    public boolean hasAvailableRooms() {
        return availableRooms > 0;
    }

    ;

    public void reserveRoom() {
        if (availableRooms > 0) {
            availableRooms--;
        }
    }

    public void releaseRoom() {
        availableRooms++;
    }

    @Override
    public String toString() {
        return "Hotel{" +
                "hotelId='" + hotelId + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", availableRooms=" + availableRooms +
                ", rating=" + rating +
                '}';
    }
}
