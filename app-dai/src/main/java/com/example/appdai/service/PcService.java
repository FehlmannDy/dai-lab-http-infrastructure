package com.example.appdai.service;

import com.example.appdai.model.Photocard;
import com.example.appdai.repository.PcRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PcService {

    private final PcRepository pcRepository;

    public PcService(PcRepository pcRepository) {
        this.pcRepository = pcRepository;
    }

    // Exemple de méthode pour récupérer des données via PcRepository
    public String getFirstRecord() {
        return pcRepository.getFirstRecord();
    }

    public List<String> getAllPcs() {
        return pcRepository.getAllPcs();
    }

    public List<Photocard> getAllPcsWithType() {
        return pcRepository.getAllPcsWithType();
    }

}
