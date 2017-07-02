package pro.hungrydev.tokenizer.back.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pro.hungrydev.tokenizer.back.model.User;
import pro.hungrydev.tokenizer.back.repository.UserRepository;
import pro.hungrydev.tokenizer.back.service.UserService;

import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean save(User user) {
        return userRepository.createUser(user);
    }

    @Override
    public User getByTelegramId(Long telegramId) {
        return userRepository.getByTelegramId(telegramId);
    }

    @Override
    public User getByUuid(UUID uuid) {
        return userRepository.getByUuid(uuid);
    }
}
