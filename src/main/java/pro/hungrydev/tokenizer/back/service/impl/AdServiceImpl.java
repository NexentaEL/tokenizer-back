package pro.hungrydev.tokenizer.back.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pro.hungrydev.tokenizer.back.model.Ad;
import pro.hungrydev.tokenizer.back.repository.AdRepository;
import pro.hungrydev.tokenizer.back.service.AdService;

import java.util.List;
import java.util.UUID;

@Service
public class AdServiceImpl implements AdService {

    @Autowired
    private AdRepository adRepository;

    @Override
    public Boolean checkAdsByUserUuid(UUID userUuid) {
        return adRepository.checkAdsByUserUuid(userUuid);
    }

    @Override
    public List<Ad> getByUserUuid(UUID userUuid) {
        return adRepository.getByUserUuid(userUuid);
    }

    @Override
    public Boolean create(Ad ad) {
        return adRepository.create(ad);
    }

    @Override
    public Boolean changeStatus(UUID adUuid, short newStatus) {
        return adRepository.changeStatus(adUuid, newStatus);
    }

    @Override
    public Ad getByUuid(UUID uuid) {
        return adRepository.getByUuid(uuid);
    }

    @Override
    public List<Ad> getAll() {
        return adRepository.getAll();
    }

    @Override
    public List<Ad> filterByPriceRange(int priceLow, int priceHigh) {
        return adRepository.filterByPriceRange(priceLow, priceHigh);
    }

    @Override
    public List<Ad> filterByRoomsNumber(short roomsNumber) {
        return adRepository.filterByRoomsNumber(roomsNumber);
    }

    @Override
    public List<Ad> filterByLocation(double longitude, double latitude) {
        return adRepository.filterByLocation(longitude, latitude);
    }

    @Override
    public List<Ad> filterByPriceRangeAndRoomsNumber(int priceLow, int priceHigh, short roomsNumber) {
        return adRepository.filterByPriceRangeAndRoomsNumber(priceLow, priceHigh, roomsNumber);
    }

    @Override
    public List<Ad> filterByRoomsNumberAndLocation(short roomsNumber, double longitude, double latitude) {
        return adRepository.filterByRoomsNumberAndLocation(roomsNumber, longitude, latitude);
    }

    @Override
    public List<Ad> filterByPriceRangeAndLocation(int priceLow, int priceHigh, double longitude, double latitude) {
        return adRepository.filterByPriceRangeAndLocation(priceLow, priceHigh, longitude, latitude);
    }

    @Override
    public List<Ad> filterByPriceRangeAndRoomsNumberAndLocation(int priceLow, int priceHigh, short roomsNumber, double longitude, double latitude) {
        return adRepository.filterByPriceRangeAndRoomsNumberAndLocation(priceLow, priceHigh, roomsNumber, longitude, latitude);
    }
}
