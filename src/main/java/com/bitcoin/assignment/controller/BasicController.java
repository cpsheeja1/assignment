package com.bitcoin.assignment.controller;

import com.bitcoin.assignment.dto.CoinRequestDTO;
import com.bitcoin.assignment.dto.TickerResponseDTO;
import com.bitcoin.assignment.modal.Users;
import com.bitcoin.assignment.response.APIResponse;
import com.bitcoin.assignment.service.IBasicService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@Api
@RestController
@RequestMapping("/api/v1")
public class BasicController {

    @Autowired
    IBasicService iBasicService;

    @GetMapping("/user")
    public APIResponse getUser(@RequestParam int id){
        Users user = this.iBasicService.getUser(id);
        return new APIResponse(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")),
                HttpStatus.OK.value(), "User data received successfully ", user);
    }

    @PostMapping("/user")
    public APIResponse createUser(@RequestBody Users inUser){
        Users user = null;
        try {
            user = this.iBasicService.createUser(inUser);
        }catch(Exception ex){
            ex.printStackTrace();
        }

        if(user != null)
            return new APIResponse(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")),
                HttpStatus.OK.value(), "Registration successful ", user);
        else
            return new APIResponse(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")),
                    HttpStatus.INTERNAL_SERVER_ERROR.value(), "Unknown error");// TODO: handle in excepion handler
    }

    @GetMapping("/coins")
    public APIResponse getCoin(@RequestParam (defaultValue = "ASC") String sort){

        List<CoinRequestDTO> response = this.iBasicService.getCoins(sort);
        return new APIResponse(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")),
                HttpStatus.OK.value(), "Coin data received successfully ",response);
    }

    @GetMapping("/ticker/{coin_code}")
    public APIResponse getTicker(@PathVariable String coin_code){

        TickerResponseDTO response = this.iBasicService.getTicker(coin_code);
        return new APIResponse(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")),
                HttpStatus.OK.value(), "Ticker data received successfully ",response);
    }
}
