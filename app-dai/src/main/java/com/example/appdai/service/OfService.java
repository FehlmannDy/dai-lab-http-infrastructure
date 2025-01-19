package com.example.appdai.service;

import com.example.appdai.model.PC_type;
import com.example.appdai.repository.OfRepository;
import org.springframework.stereotype.Service;

@Service
public class OfService {
    private final OfRepository officialSourceRepository;

    public OfService(OfRepository officialSourceRepository) {
        this.officialSourceRepository = officialSourceRepository;
    }

    public void proposeOfficialSource(String title, String versionName, String releaseDate, PC_type type) {
        officialSourceRepository.proposeOfficialSource(title, versionName, releaseDate, type);
    }
}
