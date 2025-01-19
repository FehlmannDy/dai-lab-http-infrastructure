package com.example.appdai.service;

import com.example.appdai.model.Artist;
import com.example.appdai.model.Group;
import com.example.appdai.model.Photocard;
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

    public List<Photocard> getProposedPhotocards() {
        return pcRepository.getProposedPhotocards();
    }

    public List<String> getAllPcs() {
        return pcRepository.getAllPcs();
    }

    public List<Photocard> getAllPcsWithType() {
        return pcRepository.getAllPcsWithType();
    }

    public String getArtistGroup(int artistId) {
        return pcRepository.getArtistGroup(artistId);
    }

    public List<Photocard> getPhotocardsByGroup(int groupId) {
        return pcRepository.getPhotocardsByGroup(groupId);
    }

    public List<Map<String, Object>> getPaginatedPcs(Integer groupId, int page, int size) {
        int offset = (page - 1) * size;
        return pcRepository.getPaginatedPcs(groupId, page, offset);
    }


}
