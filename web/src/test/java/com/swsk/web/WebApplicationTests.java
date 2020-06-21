package com.swsk.web;

import lombok.val;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WebApplicationTests {


    String url = "https://api.sandbox.paypal.com/v1/oauth2/token";

    @Test
    public void contextLoads() {
        Map<String,Object> params = new HashMap<>();
        /*params.put("client_id","ATNuULnRo3dQONIyLtt9hirbIIgGYQnzcXOMrGlYQUKL3E_2uQ0JbvaetBOvKWpDqPZqB-ISi2ZIWWKQ");
        params.put("secret","EC8VdkJczzD2GdcFMGOyodfvL4TlgTpuDWOgtc5bO3tmJIQ4VF-YGwMmIhfNZ0itKaZjasKO7c0fO9YA");*/
        params.put("grant_type","client_credentials");
        String plainStr = "ATNuULnRo3dQONIyLtt9hirbIIgGYQnzcXOMrGlYQUKL3E_2uQ0JbvaetBOvKWpDqPZqB-ISi2ZIWWKQ:EC8VdkJczzD2GdcFMGOyodfvL4TlgTpuDWOgtc5bO3tmJIQ4VF-YGwMmIhfNZ0itKaZjasKO7c0fO9YA";
        byte[] plainStrBytes = plainStr.getBytes();
        byte[] base64StrBytes = Base64.encodeBase64(plainStrBytes);
        String base64Str = new String(base64StrBytes);

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization","Basic " +base64Str);
        httpHeaders.add("Content-Type", "application/x-www-form-urlencoded");
        HttpEntity<Map<String, Object>> r = new HttpEntity<>(params,httpHeaders);
        String m = restTemplate.postForObject(url,r, String.class);

        System.out.println(m);
        System.out.println("111111");
    }

    @Test
    public void test() throws Exception {
        String plainStr = "ATNuULnRo3dQONIyLtt9hirbIIgGYQnzcXOMrGlYQUKL3E_2uQ0JbvaetBOvKWpDqPZqB-ISi2ZIWWKQ:EC8VdkJczzD2GdcFMGOyodfvL4TlgTpuDWOgtc5bO3tmJIQ4VF-YGwMmIhfNZ0itKaZjasKO7c0fO9YA";
        byte[] plainStrBytes = plainStr.getBytes();
        byte[] base64StrBytes = Base64.encodeBase64(plainStrBytes);
        String base64Str = new String(base64StrBytes);

        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("Authorization","Basic " +base64Str);
        httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
        org.apache.http.HttpEntity entity = new StringEntity("grant_type=client_credentials");
        httpPost.setEntity(entity);
        CloseableHttpResponse response = httpClient.execute(httpPost);
        if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
            String content = EntityUtils.toString(response.getEntity(), "UTF-8");
            System.out.println(content);
        }
    }

}
