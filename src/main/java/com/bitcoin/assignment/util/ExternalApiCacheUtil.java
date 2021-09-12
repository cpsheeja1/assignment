package com.bitcoin.assignment.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.TimeUnit;

@Configuration
public class ExternalApiCacheUtil {

    @Autowired
    StringRedisTemplate primaryRedisTemplate;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    ObjectMapper mapper;

    @Bean
    public void getCoins(){
        String result = null;
        try {
            String url = "https://api.alternative.me/v2/listings/";

            HttpHeaders headers = new HttpHeaders();
            HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
            ResponseEntity<String> strResult = restTemplate.exchange(url, HttpMethod.GET,entity,String.class);
            result = strResult.getBody();

            System.out.println(" result"+result);
            Object responseObj = mapper.readValue(result,Object.class);
            JsonNode responseNode = mapper.valueToTree(responseObj);

            if(responseNode.has("data") && responseNode.get("data") != null){
                ArrayNode dataNode = (ArrayNode) responseNode.get("data");

                String dataStr = mapper.writeValueAsString(dataNode);
                primaryRedisTemplate.opsForValue().set("coins", dataStr);
            }

        }catch(Exception ex){
            ex.printStackTrace();

        }
    }

    public ObjectNode getTicker(String id){
        System.out.println("id: "+id);
        String result = null;
        ObjectNode resultNode = mapper.createObjectNode();
        try {
            StringBuilder url = new StringBuilder();
            url.append("https://api.alternative.me/v2/ticker/").append(id).append("/");

            System.out.println(" url : "+url.toString());

            HttpHeaders headers = new HttpHeaders();
            HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
            ResponseEntity<String> strResult = restTemplate.exchange(url.toString(), HttpMethod.GET,entity,String.class);
            result = strResult.getBody();

            System.out.println(" result"+result);
            Object responseObj = mapper.readValue(result,Object.class);
            JsonNode responseNode = mapper.valueToTree(responseObj);

            if(responseNode.has("data") && responseNode.get("data") != null){
                JsonNode dataNode = responseNode.get("data").get(id);
                System.out.println(" response: "+responseNode.get("data"));
                resultNode.put("code",dataNode.get("symbol").asText());
                resultNode.put("price",dataNode.get("quotes").get("USD").get("price").asDouble());
                resultNode.put("volume",dataNode.get("quotes").get("USD").get("volume_24h").asDouble());
                resultNode.put("daily_change",dataNode.get("quotes").get("USD").get("percentage_change_24h").asDouble());
                resultNode.put("last_updated",dataNode.get("last_updated").asLong());

                String dataStr = mapper.writeValueAsString(resultNode);
                primaryRedisTemplate.opsForValue().set(id, dataStr,5, TimeUnit.MINUTES);

                String redisResult = primaryRedisTemplate.opsForValue().get(id);
                System.out.println(" redisResult "+redisResult);
            }

        }catch(Exception ex){
            ex.printStackTrace();

        }
        return resultNode;
    }
}
