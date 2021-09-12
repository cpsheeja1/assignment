package com.bitcoin.assignment;

import com.bitcoin.assignment.dto.CoinRequestDTO;
import com.bitcoin.assignment.modal.Users;
import com.bitcoin.assignment.repository.UserRepository;
import com.bitcoin.assignment.service.BasicService;
import com.bitcoin.assignment.util.ExternalApiCacheUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.List;

public class BasicServiceTest {

    @Spy
    @InjectMocks
    BasicService basicService;

    @Mock
    UserRepository userRepository;

    @Mock
    StringRedisTemplate primaryRedisTemplate;

    @Mock
    ObjectMapper mapper;

    @Mock
    ExternalApiCacheUtil externalApiCacheUtil;

    @Before
    public void init() throws JsonProcessingException {
        initialize();
        MockitoAnnotations.initMocks(this);
    }

    Users user = null;
    String coinStr = null;
    JsonNode node = null;

    @Test
    public void TestGetUser_200response() throws Exception {
        Mockito.when(userRepository.findById(1)).
                thenReturn(java.util.Optional.of(user));
        basicService.getUser(1);
        Assert.assertEquals(1, user.getId());
    }

    @Test
    public void TestGetCoins_200response() throws Exception {
        Mockito.when(primaryRedisTemplate.opsForValue().get("coins")).
                thenReturn(coinStr);
        List<CoinRequestDTO> response = basicService.getCoins("ASE");
        Assert.assertEquals(true, response != null);
    }


    void initialize() throws JsonProcessingException {
        user = new Users();
        user.setId(1);
        user.setName("Test");
        user.setEmail("Test@gmail.com");
        user.setPassword("123");

        ObjectMapper objectMapper = new ObjectMapper();
        String coinStr = "{ \"data\": [ { \"id\": \"1\", \"name\": \"Bitcoin\", \"symbol\": \"BTC\", \"website_slug\": \"bitcoin\" }, { \"id\": \"2\", \"name\": \"Litecoin\", \"symbol\": \"LTC\",\"website_slug\": \"litecoin\" }], \"metadata\": { \"timestamp\": 1537430627, \"num_cryptocurrencies\": 935, \"error\": null } }";
        node = objectMapper.readTree(coinStr);
    }
}
