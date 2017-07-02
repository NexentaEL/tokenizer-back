package pro.hungrydev.tokenizer.back.repository.impl;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import pro.hungrydev.tokenizer.back.model.User;
import pro.hungrydev.tokenizer.back.repository.ConnectionHolder;
import pro.hungrydev.tokenizer.back.repository.UserRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final static Logger LOGGER = Logger.getLogger(UserRepositoryImpl.class);

    @Override
    public boolean createUser(User user) {
        try (Connection cn = ConnectionHolder.INSTANCE.getConnection()) {

            PreparedStatement preparedStatement = cn.prepareStatement("insert into users (uuid, telegram_id, chat_id, username, "
                    + "first_name, last_name, language_id) values ('"
                    + user.getUuid() + "', " + user.getTelegramId() + ", " + user.getChatId() + ", '" + user.getUsername() + "', '"
                    + user.getFirstName() + "', '" + user.getLastName() + "', " + user.getLanguageId() + ");");
            preparedStatement.execute();

            preparedStatement.close();
        } catch (SQLException e) {
            LOGGER.error("", e);
        }
        return true;
    }

    @Override
    public User getByTelegramId(Long telegramId) {
        User user = null;

        try (Connection cn = ConnectionHolder.INSTANCE.getConnection()) {

            PreparedStatement preparedStatement = cn.prepareStatement("select uuid, telegram_id, chat_id, "
                    + "username, first_name, last_name, language_id from users where users.telegram_id = "
                    + telegramId + ";");
            ResultSet userSet = preparedStatement.executeQuery();

            if (userSet.isBeforeFirst()) {
                userSet.next();
                user = new User(UUID.fromString(userSet.getString("uuid")), userSet.getLong("telegram_id"),
                        userSet.getLong("chat_id"), userSet.getString("username"),
                        userSet.getString("first_name"), userSet.getString("last_name"),
                        userSet.getShort("language_id"));
            }

            preparedStatement.close();
        } catch (SQLException e) {
            LOGGER.error("", e);
        }
        return user;
    }

    @Override
    public User getByUuid(UUID uuid) {
        User user = null;

        try (Connection cn = ConnectionHolder.INSTANCE.getConnection()) {

            PreparedStatement preparedStatement = cn.prepareStatement("select uuid, telegram_id, chat_id, "
                    + "username, first_name, last_name, language_id from users where users.uuid = '"
                    + uuid + "';");
            ResultSet userSet = preparedStatement.executeQuery();

            if (userSet.isBeforeFirst()) {
                userSet.next();
                user = new User(UUID.fromString(userSet.getString("uuid")), userSet.getLong("telegram_id"),
                        userSet.getLong("chat_id"), userSet.getString("username"),
                        userSet.getString("first_name"), userSet.getString("last_name"),
                        userSet.getShort("language_id"));
            }

            preparedStatement.close();
        } catch (SQLException e) {
            LOGGER.error("", e);
        }
        return user;
    }
}
