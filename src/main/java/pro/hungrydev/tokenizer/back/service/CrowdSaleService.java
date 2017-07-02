package pro.hungrydev.tokenizer.back.service;

import javafx.util.Pair;
import pro.hungrydev.tokenizer.back.model.CrowdSale;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface CrowdSaleService {

    CrowdSale create(UUID uuid, UUID adUuid);

    List<CrowdSale> getAll() throws IOException;

    String publish(Pair<UUID, String> uuidAndOwnerAddress) throws IOException;
}
