package com.example.appdai.service;

import com.example.appdai.model.Pc;
import com.example.appdai.repository.PcRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PcService {

    private final PcRepository cardRepository;

    public PcService(PcRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    public List<Pc> getAllCards() {
        return cardRepository.findAll();
    }

    public void createCard(Pc card) {
        cardRepository.save(card);
    }
}