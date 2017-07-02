package pro.hungrydev.tokenizer.back;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@SpringBootApplication
public class Application {
    public static void main(String[] args) throws IOException {
        SpringApplication.run(Application.class, args);
        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost("http://192.168.188.14:8081/createholder");
        HttpResponse response = client.execute(post);
        BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        Config.getInstance().setAddress(rd.readLine());
    }
}
