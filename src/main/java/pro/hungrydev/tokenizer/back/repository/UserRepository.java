package pro.hungrydev.tokenizer.back.repository;

import pro.hungrydev.tokenizer.back.model.User;

import java.util.UUID;

public interface UserRepository {

    boolean createUser(User user);

    User getByTelegramId(Long telegramId);

    User getByUuid(UUID uuid);
}
