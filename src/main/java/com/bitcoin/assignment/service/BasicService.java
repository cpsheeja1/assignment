package com.bitcoin.assignment.service;

import com.bitcoin.assignment.dto.CoinRequestDTO;
import com.bitcoin.assignment.dto.TickerResponseDTO;
import com.bitcoin.assignment.exception.CustomBusinessException;
import com.bitcoin.assignment.exception.CustomBusinessValidationException;
import com.bitcoin.assignment.modal.Users;
import com.bitcoin.assignment.repository.UserRepository;
import com.bitcoin.assignment.util.ExternalApiCacheUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class BasicService implements IBasicService{

    @Autowired
    UserRepository userRepository;

    @Autowired
    StringRedisTemplate primaryRedisTemplate;

    @Autowired
    ObjectMapper mapper;

    @Autowired
    ExternalApiCacheUtil externalApiCacheUtil;

    @Override
    public Users createUser(Users inUser) {
        Users user = userRepository.save(inUser);
        return user;
    }

    @Override
    public Users getUser(int id) {
        Optional<Users> user = userRepository.findById(id);
        if(user.isPresent())
            return user.get();
        else
            return null;
    }

    @Override
    public List<CoinRequestDTO> getCoins(String sort) {

        String coinStr = primaryRedisTemplate.opsForValue().get("coins");
        List<CoinRequestDTO> responseArr = new ArrayList<>();
        try {
            Object responseObj = mapper.readValue(coinStr, Object.class);
            if (coinStr != null) {
                ArrayNode node = mapper.valueToTree(responseObj);
                for (JsonNode inNode : node) {
                    CoinRequestDTO dto = new CoinRequestDTO();
                    dto.setCode(inNode.get("symbol").asText());
                    dto.setName(inNode.get("name").asText());
                    responseArr.add(dto);
                }
            } else {
                throw new CustomBusinessException(" Coin detail unavailable ");
            }
        }catch (Exception ex){

        }

        if(sort.equals("ASC")){
            responseArr.sort(Comparator.comparing(object -> object.getName().toUpperCase(Locale.ROOT)));
        }else{
            responseArr.sort((object2, object1) ->
                    object1.getName().toUpperCase(Locale.ROOT).compareTo(object2.getName().
                            toUpperCase(Locale.ROOT)));
        }

        return responseArr;
    }

    @Override
    public TickerResponseDTO getTicker(String coin_code) {
        TickerResponseDTO responseDTO = new TickerResponseDTO();

        // get id for the code from redis
        // check if the response present in redis, if not
            // pass id and get the response from (https://api.alternative.me/v2/ticker/{id}
            // store response in redis for 5 mins
        // form the response dto and return
        try{
            String coinStr = primaryRedisTemplate.opsForValue().get("coins");
            List<CoinRequestDTO> responseArr = new ArrayList<>();
            Object coinObj = mapper.readValue(coinStr,Object.class);
            ArrayNode coinArr = mapper.valueToTree(coinObj);
            AtomicReference<String> id = new AtomicReference<String>();
            String actualId = null;
            JsonNode tickerNode = null;
            coinArr.forEach(node -> {
                if(coin_code.equals(node.get("symbol").asText())){
                    id.set(node.get("id").asText());
                }
            });
            if(id == null){
                throw new CustomBusinessValidationException(" Id not found for the coin"+coin_code);
            }

            actualId = id.get();

            String tickerFromRedis = primaryRedisTemplate.opsForValue().get(actualId);
            if(tickerFromRedis == null){
                tickerNode = externalApiCacheUtil.getTicker(actualId);

            }else{
                Object tickerFromRedisObj = mapper.readValue(tickerFromRedis,Object.class);
                tickerNode = mapper.valueToTree(tickerFromRedisObj);
            }
            responseDTO = mapper.convertValue(tickerNode,TickerResponseDTO.class);

        }catch(Exception ex){
            ex.printStackTrace();
        }
        return responseDTO;
    }
}
