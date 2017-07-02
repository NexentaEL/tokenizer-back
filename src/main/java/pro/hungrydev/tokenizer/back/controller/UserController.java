package pro.hungrydev.tokenizer.back.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pro.hungrydev.tokenizer.back.model.User;
import pro.hungrydev.tokenizer.back.service.UserService;

import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController {

    private final static Logger LOGGER = Logger.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Boolean save(@RequestBody User user) {
        return userService.save(user);
    }

    @RequestMapping(value = "/getByTelegramId", method = RequestMethod.POST)
    public User getByTelegramId(@RequestBody Long telegramId) {
        return userService.getByTelegramId(telegramId);
    }

    @RequestMapping(value = "/getByUuid", method = RequestMethod.POST)
    public User getByUuid(@RequestBody UUID uuid) {
        return userService.getByUuid(uuid);
    }
}
