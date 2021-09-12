package com.bitcoin.assignment.service;

import com.bitcoin.assignment.dto.CoinRequestDTO;
import com.bitcoin.assignment.dto.TickerResponseDTO;
import com.bitcoin.assignment.modal.Users;

import java.util.List;

public interface IBasicService {
    Users createUser(Users inUser);
    Users getUser(int id);
    List<CoinRequestDTO> getCoins(String sort);
    TickerResponseDTO getTicker(String coin_code);
}
