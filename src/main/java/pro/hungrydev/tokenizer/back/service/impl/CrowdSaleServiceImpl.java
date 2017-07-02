package pro.hungrydev.tokenizer.back.service.impl;

import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pro.hungrydev.tokenizer.back.model.CrowdSale;
import pro.hungrydev.tokenizer.back.repository.CrowdSaleRepository;
import pro.hungrydev.tokenizer.back.service.CrowdSaleService;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class CrowdSaleServiceImpl implements CrowdSaleService {

    @Autowired
    private CrowdSaleRepository crowdSaleRepository;

    @Override
    public CrowdSale create(UUID uuid, UUID adUuid) {
        return crowdSaleRepository.create(uuid, adUuid);
    }

    @Override
    public List<CrowdSale> getAll() throws IOException {
        return crowdSaleRepository.getAll();
    }

    @Override
    public String publish(Pair<UUID, String> uuidAndOwnerAddress) throws IOException {
        return crowdSaleRepository.publish(uuidAndOwnerAddress);
    }
}
