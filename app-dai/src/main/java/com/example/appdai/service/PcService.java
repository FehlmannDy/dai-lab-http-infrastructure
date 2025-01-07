package com.example.appdai.service;

import com.example.appdai.model.Pc;
import com.example.appdai.repository.PcRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

@Service
public class PcService {

    private final PcRepository pcRepository;

    public PcService(PcRepository pcRepository) {
        this.pcRepository = pcRepository;
    }

    public List<Map<String, Object>> getAllGroups() {
        return pcRepository.getAllGroups();
    }

    public List<Map<String, Object>> getArtistsByGroupName(String groupName) {
        return pcRepository.getArtistsByGroupName(groupName);
    }

    public String getFirstRecord() {
        return pcRepository.getFirstRecord();
    }

    public List<String> getAllPcs() {
        return pcRepository.getAllPcs();
    }

    public List<Pc> getAllPcsWithType() {
        return pcRepository.getAllPcsWithType();
    }

}
