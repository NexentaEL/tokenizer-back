package pro.hungrydev.tokenizer.back.service.net;

import java.io.IOException;

public interface CrowdSaleNet {

    int getSoldTokens(String address) throws IOException;

    String createTokenDeal(String ownerAddress, String fullPayment, int nightsNumber, String tokenPrice) throws IOException;
}
