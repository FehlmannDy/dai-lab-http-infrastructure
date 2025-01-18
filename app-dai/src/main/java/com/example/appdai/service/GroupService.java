package com.example.appdai.service;

import com.example.appdai.model.Group;
import com.example.appdai.model.Photocard;
import com.example.appdai.repository.GroupRepository;
import com.example.appdai.repository.PcRepository;
import org.springframework.stereotype.Service;

import com.example.appdai.model.Artist;
import com.example.appdai.model.Group;
import com.example.appdai.model.Photocard;
import com.example.appdai.repository.PcRepository;
import org.springframework.stereotype.Service;
import java.util.List;

import java.util.List;
import java.util.Map;

@Service
public class GroupService {

    private final GroupRepository groupRepository;

    public GroupService(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    public List<Map<String, Object>> getAllGroupNames() {
        return groupRepository.getAllGroupNames();
    }

    public List<Map<String, Object>> getArtistsByGroupId(int groupId) {
        return groupRepository.getArtistsByGroupId(groupId);
    }


    // A voir si on garde ou pas ou dans quelles conditions
//    public List<Group> getAllGroups() {
//        return groupRepository.getAllGroups();
//    }
//
//    public List<Artist> getGroupArtists(String groupsName) {
//        return groupRepository.getGroupArtists(groupsName);
//    }
}