package pro.hungrydev.tokenizer.back;

public class Config {

    private static Config ourInstance = new Config();
    private String address;

    private Config() {
    }

    public static Config getInstance() {
        return ourInstance;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
