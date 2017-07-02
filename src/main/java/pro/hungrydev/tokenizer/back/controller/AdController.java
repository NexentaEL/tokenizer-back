package pro.hungrydev.tokenizer.back.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pro.hungrydev.tokenizer.back.model.Ad;
import pro.hungrydev.tokenizer.back.service.AdService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/ad")
public class AdController {

    private final static Logger LOGGER = Logger.getLogger(AdController.class);

    @Autowired
    private AdService adService;

    // есть ли объявления у пользователя
    @RequestMapping(value = "/checkAdsByUserUuid", method = RequestMethod.POST)
    public Boolean checkAdsByUserUuid(@RequestBody UUID uuid) {
        return adService.checkAdsByUserUuid(uuid);
    }

    // все объявления пользователя
    @RequestMapping(value = "/getByUserUuid", method = RequestMethod.POST)
    public List<Ad> getByUserUuid(@RequestBody UUID uuid) {
        return adService.getByUserUuid(uuid);
    }

    // создание объявления c адресом, кол-вом комнат, площадью, ценой за ночь, фотками
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public Boolean create(@RequestBody Ad ad) {
        return adService.create(ad);
    }

    // меняет статус объявления
    @RequestMapping(value = "/changeStatus", method = RequestMethod.GET)
    public Boolean changeStatus(@RequestParam UUID adUuid, @RequestParam short newStatus) {
        return adService.changeStatus(adUuid, newStatus);
    }

    // берёт объявление по uuid
    @RequestMapping(value = "/getByUuid", method = RequestMethod.POST)
    public Ad getByUuid(@RequestBody UUID uuid) {
        return adService.getByUuid(uuid);
    }

    // 8 фильтров объявлений: все, по диапазону цен, кол-ву комнат, на вход локация - вывести ближайшие 100 отсорт по дальности
    // - без, по одному, по парам, все

    // все объявления без фильтра
    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    public List<Ad> getAll() {
        return adService.getAll();
    }

    // по диапазону цен
    @RequestMapping(value = "/filterByPriceRange", method = RequestMethod.GET)
    public List<Ad> filterByPriceRange(@RequestParam int priceLow, @RequestParam int priceHigh) {
        return adService.filterByPriceRange(priceLow, priceHigh);
    }

    // по количеству комнат
    @RequestMapping(value = "/filterByRoomsNumber", method = RequestMethod.GET)
    public List<Ad> filterByRoomsNumber(@RequestParam short roomsNumber) {
        return adService.filterByRoomsNumber(roomsNumber);
    }

    // по локации
    @RequestMapping(value = "/filterByLocation", method = RequestMethod.GET)
    public List<Ad> filterByLocation(@RequestParam double longitude, @RequestParam double latitude) {
        return adService.filterByLocation(longitude, latitude);
    }

    // по диапазону цен и количеству комнат
    @RequestMapping(value = "/filterByPriceRangeAndRoomsNumber", method = RequestMethod.GET)
    public List<Ad> filterByPriceRangeAndRoomsNumber(@RequestParam int priceLow, @RequestParam int priceHigh, @RequestParam short roomsNumber) {
        return adService.filterByPriceRangeAndRoomsNumber(priceLow, priceHigh, roomsNumber);
    }

    // по количеству комнат и локации
    @RequestMapping(value = "/filterByRoomsNumberAndLocation", method = RequestMethod.GET)
    public List<Ad> filterByRoomsNumberAndLocation(@RequestParam short roomsNumber, @RequestParam double longitude, @RequestParam double latitude) {
        return adService.filterByRoomsNumberAndLocation(roomsNumber, longitude, latitude);
    }

    // по диапазону цен и локации
    @RequestMapping(value = "/filterByPriceRangeAndLocation", method = RequestMethod.GET)
    public List<Ad> filterByPriceRangeAndLocation(@RequestParam int priceLow, @RequestParam int priceHigh, @RequestParam double longitude, @RequestParam double latitude) {
        return adService.filterByPriceRangeAndLocation(priceLow, priceHigh, longitude, latitude);
    }

    // по всему
    @RequestMapping(value = "/filterByPriceRangeAndRoomsNumberAndLocation", method = RequestMethod.GET)
    public List<Ad> filterByPriceRangeAndRoomsNumberAndLocation(@RequestParam int priceLow, @RequestParam int priceHigh, @RequestParam short roomsNumber, @RequestParam double longitude, @RequestParam double latitude) {
        return adService.filterByPriceRangeAndRoomsNumberAndLocation(priceLow, priceHigh, roomsNumber, longitude, latitude);
    }
}
