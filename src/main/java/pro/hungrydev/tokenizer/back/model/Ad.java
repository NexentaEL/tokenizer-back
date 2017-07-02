package pro.hungrydev.tokenizer.back.model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.UUID;

// объявление о сдаче квартиры
public class Ad {

    private UUID uuid;
    private UUID userUuid;
    private short statusId;
    private double longitude;
    private double latitude;
    private String address;
    private short roomsNumber;
    private int area;
    private String pricePerNight;
    private String [] photos;

    public Ad() {}

    public Ad(UUID uuid, short statusId, double longitude, double latitude, String address, short roomsNumber, int area, String pricePerNight, String[] photos) {
        this.uuid = uuid;
        this.statusId = statusId;
        this.longitude = longitude;
        this.latitude = latitude;
        this.address = address;
        this.roomsNumber = roomsNumber;
        this.area = area;
        this.pricePerNight = pricePerNight;
        this.photos = photos;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getUserUuid() {
        return userUuid;
    }

    public void setUserUuid(UUID userUuid) {
        this.userUuid = userUuid;
    }

    public short getStatusId() {
        return statusId;
    }

    public void setStatusId(short statusId) {
        this.statusId = statusId;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public short getRoomsNumber() {
        return roomsNumber;
    }

    public void setRoomsNumber(short roomsNumber) {
        this.roomsNumber = roomsNumber;
    }

    public int getArea() {
        return area;
    }

    public void setArea(int area) {
        this.area = area;
    }

    public String getPricePerNight() {
        return new BigDecimal(pricePerNight).divide(BigDecimal.valueOf(1L, -18)).toString();
    }

    public void setPricePerNight(double ethers) {
        this.pricePerNight = BigDecimal.valueOf(ethers).multiply(BigDecimal.valueOf(1L, -18)).toBigInteger().toString();
    }

    public String[] getPhotos() {
        return photos;
    }

    public void setPhotos(String[] photos) {
        this.photos = photos;
    }
}
