package pro.hungrydev.tokenizer.back.repository.impl;

import javafx.util.Pair;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pro.hungrydev.tokenizer.back.model.CrowdSale;
import pro.hungrydev.tokenizer.back.repository.ConnectionHolder;
import pro.hungrydev.tokenizer.back.repository.CrowdSaleRepository;
import pro.hungrydev.tokenizer.back.service.net.CrowdSaleNet;

import java.io.IOException;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Repository
public class CrowdSaleRepositoryImpl implements CrowdSaleRepository {

    private final static Logger LOGGER = Logger.getLogger(CrowdSaleRepositoryImpl.class);

    @Autowired
    private CrowdSaleNet crowdSaleNet;

    @Override
    public CrowdSale create(UUID uuid, UUID adUuid) {
        CrowdSale crowdSale = null;

        try (Connection cn = ConnectionHolder.INSTANCE.getConnection()) {

            // проверка на наличие уже созданного crowd sale у объявления и его возврат в случае существования
            PreparedStatement preparedStatement = cn.prepareStatement("select tokens_number, token_price, "
                    + "sold_tokens, is_published, create_date, address from crowd_sales where crowd_sales.ad_uuid = '"
                    + adUuid + "';");
            ResultSet crowdSaleSet = preparedStatement.executeQuery();

            if (crowdSaleSet.isBeforeFirst()) {
                crowdSaleSet.next();
                crowdSale = new CrowdSale(UUID.fromString(crowdSaleSet.getString("uuid")), UUID.fromString(crowdSaleSet.getString("ad_uuid")),
                        crowdSaleSet.getInt("tokens_number"), crowdSaleSet.getString("token_price"),
                        crowdSaleSet.getInt("sold_tokens"), crowdSaleSet.getBoolean("is_published"),
                        crowdSaleSet.getDate("create_date"), crowdSaleSet.getString("address"));
                return crowdSale;
            }

            preparedStatement = cn.prepareStatement("select price_per_night from ads where ads.uuid = '"
                    + adUuid + "';");
            ResultSet pricePerNightByAd = preparedStatement.executeQuery();
            pricePerNightByAd.next();
            String pricePerNight = pricePerNightByAd.getString("price_per_night");

            // кол-во токенов = 1000, цена за токен pricePerNight * 30 * 0,88 / 1000, проданные токены = 0,
            // isPublished, createDate
            int tokensNumber = 1000;
            String tokenPrice = new BigInteger(pricePerNight).multiply(new BigInteger("30")).multiply(new BigInteger("0.88")).divide(new BigInteger("1000")).toString();
            int soldTokens = 0;
            boolean isPublished = false;
            Date createDate = new Date();
            preparedStatement = cn.prepareStatement("insert into crowd_sales (uuid, ad_uuid, tokens_number, token_price, sold_tokens, "
                    + "is_published, create_date) values ('" + uuid + "', '" + adUuid + "', " + tokensNumber + ", " + tokenPrice + ", " + soldTokens + ", "
                    + isPublished + ", '" + createDate+ "');");
            preparedStatement.execute();

            preparedStatement.close();
        } catch (SQLException e) {
            LOGGER.error("", e);
        }
        return crowdSale;
    }

    private void writeCrowdSalesToList(ResultSet crowdSalesSet, List<CrowdSale> crowdSales) {
        try {
            while (crowdSalesSet.next()) {
                CrowdSale crowdSale = new CrowdSale(UUID.fromString(crowdSalesSet.getString("uuid")), UUID.fromString(crowdSalesSet.getString("ad_uuid")),
                        crowdSalesSet.getInt("tokens_number"), crowdSalesSet.getString("token_price"), crowdSalesSet.getInt("sold_tokens"),
                        crowdSalesSet.getBoolean("is_published"), crowdSalesSet.getDate("create_date"), crowdSalesSet.getString("address"));
                crowdSales.add(crowdSale);
            }
        } catch (SQLException e) {
            LOGGER.error("", e);
        }
    }

    @Override
    public List<CrowdSale> getAll() throws IOException {
        List<CrowdSale> crowdSales = new ArrayList<>();
        try (Connection cn = ConnectionHolder.INSTANCE.getConnection()) {

            PreparedStatement preparedStatement = cn.prepareStatement("select uuid, address from crowd_sales where crowd_sales.is_published = true;");
            ResultSet addresses = preparedStatement.executeQuery();
            while (addresses.next()) {
                int soldTokens = crowdSaleNet.getSoldTokens(addresses.getString("address"));
                preparedStatement = cn.prepareStatement("update crowd_sales set sold_tokens = " + soldTokens + ";");
                preparedStatement.execute();
            }

            preparedStatement = cn.prepareStatement("select * from crowd_sales where crowd_sales.is_published = true;");
            writeCrowdSalesToList(preparedStatement.executeQuery(), crowdSales);

            preparedStatement.close();
        } catch (SQLException e) {
            LOGGER.error("", e);
        }
        return crowdSales;
    }

    @Override
    public String publish(Pair<UUID, String> uuidAndOwnerAddress) throws IOException {
        // createtokendeal/ адрес владельца, кол-во токенов*цену токенов, 30 - возрв адрес контракта
        // меняю isPublished + сохр адрес контракта
        CrowdSale crowdSale = getCrowdSaleByUuid(uuidAndOwnerAddress.getKey());
        String fullPayment = new BigInteger(crowdSale.getTokenPriceInWei()).multiply(new BigInteger(crowdSale.getTokensNumber().toString())).toString();
        String address = crowdSaleNet.createTokenDeal(uuidAndOwnerAddress.getValue(), fullPayment, 30, crowdSale.getTokenPriceInWei());

        try (Connection cn = ConnectionHolder.INSTANCE.getConnection()) {

            PreparedStatement preparedStatement = cn.prepareStatement("update crowd_sales set is_published = 'true', address = '" + address + "';");
            preparedStatement.execute();

            preparedStatement.close();
        } catch (SQLException e) {
            LOGGER.error("", e);
        }
        return address;
    }

    private CrowdSale getCrowdSaleByUuid(UUID uuid) {
        CrowdSale crowdSale = null;

        try (Connection cn = ConnectionHolder.INSTANCE.getConnection()) {

            PreparedStatement preparedStatement = cn.prepareStatement("select * from crowd_sales where crowd_sales.uuid = '" + uuid + "';");
            ResultSet crowdSaleSet = preparedStatement.executeQuery();
            if (crowdSaleSet.isBeforeFirst()) {
                crowdSaleSet.next();
                crowdSale = new CrowdSale(UUID.fromString(crowdSaleSet.getString("uuid")), UUID.fromString(crowdSaleSet.getString("ad_uuid")),
                        crowdSaleSet.getInt("tokens_number"), crowdSaleSet.getString("token_price"), crowdSaleSet.getInt("sold_tokens"),
                        crowdSaleSet.getBoolean("is_published"), crowdSaleSet.getDate("create_date"), crowdSaleSet.getString("address"));
            }

            preparedStatement.close();
        } catch (SQLException e) {
            LOGGER.error("", e);
        }
        return crowdSale;
    }
}
