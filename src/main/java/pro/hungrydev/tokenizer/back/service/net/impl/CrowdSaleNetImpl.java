package pro.hungrydev.tokenizer.back.service.net.impl;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.stereotype.Service;
import pro.hungrydev.tokenizer.back.service.net.CrowdSaleNet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Service
public class CrowdSaleNetImpl implements CrowdSaleNet {

    @Override
    public int getSoldTokens(String address) throws IOException {
        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost("http://192.168.188.14:8081/checktotalsupply");
        post.setEntity(new StringEntity("address", address));
        HttpResponse response = client.execute(post);
        BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        return Integer.parseInt(rd.readLine());
    }

    @Override
    public String createTokenDeal(String ownerAddress, String fullPayment, int nightsNumber, String tokenPrice) throws IOException {
        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost("http://192.168.188.14:8081/createtokendeal");
        post.setEntity(new StringEntity("landlord", ownerAddress));
        post.setEntity(new StringEntity("fullpayment", fullPayment));
        post.setEntity(new StringEntity("nights", nightsNumber + ""));
        post.setEntity(new StringEntity("tokenPrice", tokenPrice));
        HttpResponse response = client.execute(post);
        BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        return rd.readLine();
    }
}
