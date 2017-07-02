package pro.hungrydev.tokenizer.back.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

public class CrowdSale {

    private UUID uuid;
    private UUID adUuid;
    private int tokensNumber;
    private String tokenPrice;
    private int soldTokens;
    private boolean isPublished;
    private Date createDate;
    private String address;

    public CrowdSale() {}

    public CrowdSale(UUID uuid, UUID adUuid, int tokensNumber, String tokenPrice, int soldTokens, boolean isPublished, Date createDate, String address) {
        this.uuid = uuid;
        this.adUuid = adUuid;
        this.tokensNumber = tokensNumber;
        this.tokenPrice = tokenPrice;
        this.soldTokens = soldTokens;
        this.isPublished = isPublished;
        this.createDate = createDate;
        this.address = address;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getAdUuid() {
        return adUuid;
    }

    public void setAdUuid(UUID adUuid) {
        this.adUuid = adUuid;
    }

    public Integer getTokensNumber() {
        return tokensNumber;
    }

    public void setTokensNumber(int tokensNumber) {
        this.tokensNumber = tokensNumber;
    }

    public String getTokenPrice() {
        return new BigDecimal(tokenPrice).divide(BigDecimal.valueOf(1L, -18)).toString();
    }

    public String getTokenPriceInWei() {
        return tokenPrice;
    }

    public void setTokenPrice(double ethers) {
        this.tokenPrice = BigDecimal.valueOf(ethers).multiply(BigDecimal.valueOf(1L, -18)).toBigInteger().toString();
    }

    public int getSoldTokens() {
        return soldTokens;
    }

    public void setSoldTokens(int soldTokens) {
        this.soldTokens = soldTokens;
    }

    public boolean isPublished() {
        return isPublished;
    }

    public void setPublished(boolean published) {
        isPublished = published;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
