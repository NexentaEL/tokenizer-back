package pro.hungrydev.tokenizer.back.repository;

import pro.hungrydev.tokenizer.back.model.Ad;

import java.util.List;
import java.util.UUID;

public interface AdRepository {

    Boolean checkAdsByUserUuid(UUID userUuid);

    List<Ad> getByUserUuid(UUID userUuid);

    Boolean create(Ad ad);

    Boolean changeStatus(UUID adUuid, short newStatus);

    Ad getByUuid(UUID uuid);

    List<Ad> getAll();

    List<Ad> filterByPriceRange(int priceLow, int priceHigh);

    List<Ad> filterByRoomsNumber(short roomsNumber);

    List<Ad> filterByLocation(double longitude, double latitude);

    List<Ad> filterByPriceRangeAndRoomsNumber(int priceLow, int priceHigh, short roomsNumber);

    List<Ad> filterByRoomsNumberAndLocation(short roomsNumber, double longitude, double latitude);

    List<Ad> filterByPriceRangeAndLocation(int priceLow, int priceHigh, double longitude, double latitude);

    List<Ad> filterByPriceRangeAndRoomsNumberAndLocation(int priceLow, int priceHigh, short roomsNumber, double longitude, double latitude);
}
