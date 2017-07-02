package pro.hungrydev.tokenizer.back.repository.impl;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import pro.hungrydev.tokenizer.back.model.Ad;
import pro.hungrydev.tokenizer.back.repository.AdRepository;
import pro.hungrydev.tokenizer.back.repository.ConnectionHolder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class AdRepositoryImpl implements AdRepository {

    private final static Logger LOGGER = Logger.getLogger(AdRepositoryImpl.class);

    @Override
    public Boolean checkAdsByUserUuid(UUID userUuid) {
        Boolean adsExists = false;
        try (Connection cn = ConnectionHolder.INSTANCE.getConnection()) {

            PreparedStatement preparedStatement = cn.prepareStatement("select uuid from ads where ads.user_uuid = '" + userUuid + "';");
            ResultSet adsUuidsForUser = preparedStatement.executeQuery();
            if (adsUuidsForUser.isBeforeFirst()) {
                adsExists = true;
            }

            preparedStatement.close();
        } catch (SQLException e) {
            LOGGER.error("", e);
        }
        return adsExists;
    }

    @Override
    public List<Ad> getByUserUuid(UUID userUuid) {
        List<Ad> ads = new ArrayList<>();

        try (Connection cn = ConnectionHolder.INSTANCE.getConnection()) {

            PreparedStatement preparedStatement = cn.prepareStatement("select uuid, status_id, longitude, latitude, address, rooms_number, "
                + "area, price_per_night, photos from ads where ads.user_uuid = '" + userUuid + "';");
            ResultSet adsByUser = preparedStatement.executeQuery();
            while (adsByUser.next()) {
                Ad ad = new Ad(UUID.fromString(adsByUser.getString("uuid")), adsByUser.getShort("status_id"), adsByUser.getDouble("longitude"),
                        adsByUser.getDouble("latitude"), adsByUser.getString("address"), adsByUser.getShort("rooms_number"),
                        adsByUser.getInt("area"), adsByUser.getString("price_per_night"), (String[])adsByUser.getArray("photos").getArray());
                ads.add(ad);
            }

            preparedStatement.close();
        } catch (SQLException e) {
            LOGGER.error("", e);
        }
        return ads;
    }

    @Override
    public Boolean create(Ad ad) {
        try (Connection cn = ConnectionHolder.INSTANCE.getConnection()) {

            PreparedStatement preparedStatement = cn.prepareStatement("insert into ads (uuid, user_uuid, status_id, longitude, latitude, "
                    + "address, rooms_number, area, price_per_night, photos) values ('"
                    + ad.getUuid() + "', '" + ad.getUserUuid() + "', " + ad.getStatusId() + ", " + ad.getLongitude() + ", " + ad.getLatitude()+ ", '"
                    + ad.getAddress() + "', " + ad.getRoomsNumber() + ", " + ad.getArea() + ", " + ad.getPricePerNight() + ", '"
                    + cn.createArrayOf("text", ad.getPhotos()) + "');");
            preparedStatement.execute();

            preparedStatement.close();
        } catch (SQLException e) {
            LOGGER.error("", e);
        }
        return true;
    }

    @Override
    public Boolean changeStatus(UUID adUuid, short newStatus) {
        try (Connection cn = ConnectionHolder.INSTANCE.getConnection()) {

            PreparedStatement preparedStatement = cn.prepareStatement("update ads set status_id = " + newStatus + " where ads.uuid = '" + adUuid + "';");
            preparedStatement.execute();

            preparedStatement.close();
        } catch (SQLException e) {
            LOGGER.error("", e);
        }
        return true;
    }

    @Override
    public Ad getByUuid(UUID uuid) {
        Ad ad = null;

        try (Connection cn = ConnectionHolder.INSTANCE.getConnection()) {

            PreparedStatement preparedStatement = cn.prepareStatement("select uuid, status_id, longitude, latitude, address, rooms_number, "
                    + "area, price_per_night, photos from ads where ads.uuid = '" + uuid + "';");
            ResultSet adByUuid = preparedStatement.executeQuery();
            if (adByUuid.isBeforeFirst()) {
                adByUuid.next();
                ad = new Ad(UUID.fromString(adByUuid.getString("uuid")), adByUuid.getShort("status_id"), adByUuid.getDouble("longitude"),
                        adByUuid.getDouble("latitude"), adByUuid.getString("address"), adByUuid.getShort("rooms_number"),
                        adByUuid.getInt("area"), adByUuid.getString("price_per_night"), (String[])adByUuid.getArray("photos").getArray());
            }

            preparedStatement.close();
        } catch (SQLException e) {
            LOGGER.error("", e);
        }
        return ad;
    }

    private void writeAdsToList(ResultSet adsSet, List<Ad> ads) {
        try {
            while (adsSet.next()) {
                Ad ad = new Ad(UUID.fromString(adsSet.getString("uuid")), adsSet.getShort("status_id"), adsSet.getDouble("longitude"),
                        adsSet.getDouble("latitude"), adsSet.getString("address"), adsSet.getShort("rooms_number"),
                        adsSet.getInt("area"), adsSet.getString("price_per_night"), (String[])adsSet.getArray("photos").getArray());
                ads.add(ad);
            }
        } catch (SQLException e) {
            LOGGER.error("", e);
        }
    }

    @Override
    public List<Ad> getAll() {
        List<Ad> ads = new ArrayList<>();
        try (Connection cn = ConnectionHolder.INSTANCE.getConnection()) {

            PreparedStatement preparedStatement = cn.prepareStatement("select * from ads where ads.status_id = 2;");
            writeAdsToList(preparedStatement.executeQuery(), ads);

            preparedStatement.close();
        } catch (SQLException e) {
            LOGGER.error("", e);
        }
        return ads;
    }

    @Override
    public List<Ad> filterByPriceRange(int priceLow, int priceHigh) {
        List<Ad> ads = new ArrayList<>();
        try (Connection cn = ConnectionHolder.INSTANCE.getConnection()) {

            PreparedStatement preparedStatement = cn.prepareStatement("select * from ads where ads.status_id = 2 and ads.price_per_night >= " + priceLow + " and ads.price_per_night <= " + priceHigh + " ;");
            writeAdsToList(preparedStatement.executeQuery(), ads);

            preparedStatement.close();
        } catch (SQLException e) {
            LOGGER.error("", e);
        }
        return ads;
    }

    @Override
    public List<Ad> filterByRoomsNumber(short roomsNumber) {
        List<Ad> ads = new ArrayList<>();
        try (Connection cn = ConnectionHolder.INSTANCE.getConnection()) {

            PreparedStatement preparedStatement = cn.prepareStatement("select * from ads where ads.status_id = 2 and ads.rooms_number = " + roomsNumber + ";");
            writeAdsToList(preparedStatement.executeQuery(), ads);

            preparedStatement.close();
        } catch (SQLException e) {
            LOGGER.error("", e);
        }
        return ads;
    }

    @Override
    public List<Ad> filterByLocation(double longitude, double latitude) {
        List<Ad> ads = new ArrayList<>();
        try (Connection cn = ConnectionHolder.INSTANCE.getConnection()) {

            PreparedStatement preparedStatement = cn.prepareStatement("select uuid, status_id, longitude, latitude, address, rooms_number, "
                    + "area, price_per_night, photos, (sqrt(power((longitude - " + longitude + "),2) + power((latitude - " + latitude + "),2))) as order_condition "
                    + "from ads where ads.status_id = 2 order by order_condition limit 100;");
            writeAdsToList(preparedStatement.executeQuery(), ads);

            preparedStatement.close();
        } catch (SQLException e) {
            LOGGER.error("", e);
        }
        return ads;
    }

    @Override
    public List<Ad> filterByPriceRangeAndRoomsNumber(int priceLow, int priceHigh, short roomsNumber) {
        List<Ad> ads = new ArrayList<>();
        try (Connection cn = ConnectionHolder.INSTANCE.getConnection()) {

            PreparedStatement preparedStatement = cn.prepareStatement("select uuid, status_id, longitude, latitude, address, rooms_number, "
                    + "area, price_per_night, photos from ads where ads.status_id = 2 and ads.price_per_night >= " + priceLow + " and ads.price_per_night <= " + priceHigh + " "
                    + "and ads.rooms_number = " + roomsNumber + ";");
            writeAdsToList(preparedStatement.executeQuery(), ads);

            preparedStatement.close();
        } catch (SQLException e) {
            LOGGER.error("", e);
        }
        return ads;
    }

    @Override
    public List<Ad> filterByRoomsNumberAndLocation(short roomsNumber, double longitude, double latitude) {
        List<Ad> ads = new ArrayList<>();
        try (Connection cn = ConnectionHolder.INSTANCE.getConnection()) {

            PreparedStatement preparedStatement = cn.prepareStatement("select uuid, status_id, longitude, latitude, address, rooms_number, "
                    + "area, price_per_night, photos, (sqrt(power((longitude - " + longitude + "),2) + power((latitude - " + latitude + "),2))) as order_condition "
                    + "from ads where ads.status_id = 2 and ads.rooms_number = " + roomsNumber + " order by order_condition limit 100;");
            writeAdsToList(preparedStatement.executeQuery(), ads);

            preparedStatement.close();
        } catch (SQLException e) {
            LOGGER.error("", e);
        }
        return ads;
    }

    @Override
    public List<Ad> filterByPriceRangeAndLocation(int priceLow, int priceHigh, double longitude, double latitude) {
        List<Ad> ads = new ArrayList<>();
        try (Connection cn = ConnectionHolder.INSTANCE.getConnection()) {

            PreparedStatement preparedStatement = cn.prepareStatement("select uuid, status_id, longitude, latitude, address, rooms_number, "
                    + "area, price_per_night, photos, (sqrt(power((longitude - " + longitude + "),2) + power((latitude - " + latitude + "),2))) as order_condition "
                    + "from ads where ads.status_id = 2 and ads.price_per_night >= " + priceLow + " and ads.price_per_night <= " + priceHigh + " order by order_condition limit 100;");
            writeAdsToList(preparedStatement.executeQuery(), ads);

            preparedStatement.close();
        } catch (SQLException e) {
            LOGGER.error("", e);
        }
        return ads;
    }

    @Override
    public List<Ad> filterByPriceRangeAndRoomsNumberAndLocation(int priceLow, int priceHigh, short roomsNumber, double longitude, double latitude) {
        List<Ad> ads = new ArrayList<>();
        try (Connection cn = ConnectionHolder.INSTANCE.getConnection()) {

            PreparedStatement preparedStatement = cn.prepareStatement("select uuid, status_id, longitude, latitude, address, rooms_number, "
                    + "area, price_per_night, photos, (sqrt(power((longitude - " + longitude + "),2) + power((latitude - " + latitude + "),2))) as order_condition "
                    + "from ads where ads.status_id = 2 and ads.price_per_night >= " + priceLow + " and ads.price_per_night <= " + priceHigh + " "
                    + "and ads.rooms_number = " + roomsNumber + " order by order_condition limit 100;");
            writeAdsToList(preparedStatement.executeQuery(), ads);

            preparedStatement.close();
        } catch (SQLException e) {
            LOGGER.error("", e);
        }
        return ads;
    }
}
