package com.example.appdai.service;

import com.example.appdai.model.Artist;
import com.example.appdai.model.Group;
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

    public String getArtistGroup(int artistId) {
        return pcRepository.getArtistGroup(artistId);
    }

    public List<Group> getAllGroups() {
        return pcRepository.getAllGroups();
    }

    public List<Photocard> getPhotocardsByGroup(int groupId) {
        return pcRepository.getPhotocardsByGroup(groupId);
    }

    public List<Artist> getGroupArtists(String groupsName) {
        return pcRepository.getGroupArtists(groupsName);
    }

    public List<Photocard> getUserWishlist(Integer userId) {
        return pcRepository.getUserWishlist(userId);
    }
}
