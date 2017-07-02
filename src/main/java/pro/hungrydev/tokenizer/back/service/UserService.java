package pro.hungrydev.tokenizer.back.service;

import pro.hungrydev.tokenizer.back.model.User;

import java.util.UUID;

public interface UserService {

    boolean save(User user);

    User getByTelegramId(Long telegramId);

    User getByUuid(UUID uuid);
}
