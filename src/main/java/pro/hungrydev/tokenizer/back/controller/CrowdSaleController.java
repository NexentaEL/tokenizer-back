package pro.hungrydev.tokenizer.back.controller;

import javafx.util.Pair;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pro.hungrydev.tokenizer.back.model.CrowdSale;
import pro.hungrydev.tokenizer.back.service.CrowdSaleService;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/crowdSale")
public class CrowdSaleController {

    private final static Logger LOGGER = Logger.getLogger(CrowdSaleController.class);

    @Autowired
    private CrowdSaleService crowdSaleService;

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public CrowdSale create(@RequestBody UUID uuid, @RequestBody UUID adUuid) {
        return crowdSaleService.create(uuid, adUuid);
    }

    // выдаются только те, которые is_published
    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    public List<CrowdSale> getAll() throws IOException {
        return crowdSaleService.getAll();
    }

    @RequestMapping(value = "publish", method = RequestMethod.POST)
    // адрес владельца, uuid crowd sale - возвращает адрес контракта
    // createtokendeal/ адрес владельца, кол-во токенов*цену токенов, 30 - возрв адрес контракта
    // меняю isPublished + сохр адрес контракта
    public String publish(@RequestBody Pair<UUID, String> uuidAndOwnerAddress) throws IOException {
        return crowdSaleService.publish(uuidAndOwnerAddress);
    }

    @RequestMapping(value = "/applyCrowdSale", method = RequestMethod.POST)
    public String applyCrowdSale(@RequestBody Pair<UUID, UUID> uuids) {
        // возвра адрес контракта по uuid, связывает пользователя и crowd_sale третьей таблицей
        return null;
    }

    // TODO удалять чз 30 дней
}
