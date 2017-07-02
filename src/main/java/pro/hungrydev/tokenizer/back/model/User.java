package pro.hungrydev.tokenizer.back.model;

import java.util.UUID;

public class User {

    private UUID uuid;
    private long telegramId;
    private long chatId;
    private String username;
    private String firstName;
    private String lastName;
    private short languageId;

    public User() {}

    public User(UUID uuid, long telegramId, long chatId, String username, String firstName, String lastName, short languageId) {
        this.uuid = uuid;
        this.telegramId = telegramId;
        this.chatId = chatId;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.languageId = languageId;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public long getTelegramId() {
        return telegramId;
    }

    public void setTelegramId(long telegramId) {
        this.telegramId = telegramId;
    }

    public long getChatId() {
        return chatId;
    }

    public void setChatId(long chatId) {
        this.chatId = chatId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public short getLanguageId() {
        return languageId;
    }

    public void setLanguageId(short languageId) {
        this.languageId = languageId;
    }
}
