package com.example.appdai.service;

import com.example.appdai.model.Group;
import com.example.appdai.repository.GroupRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Service class responsible for handling operations related to groups and artists.
 * It acts as a bridge between the controller and the repository layers for retrieving group information.
 */
@Service
public class GroupService {

    private final GroupRepository groupRepository;

    /**
     * Constructs a {@link GroupService} with the provided {@link GroupRepository}.
     *
     * @param groupRepository The {@link GroupRepository} used to interact with the database for group-related operations.
     */
    public GroupService(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    /**
     * Retrieves all group names from the repository.
     *
     * @return A list of {@link Group} objects representing all the groups available.
     */
    public List<Group> getAllGroupNames() {
        return groupRepository.getAllGroups();
    }

    /**
     * Retrieves the artists associated with a specific group, identified by its ID.
     *
     * @param groupId The ID of the group for which the artists are to be retrieved.
     * @return A list of {@link Map} objects, each containing details of an artist associated with the specified group.
     */
    public List<Map<String, Object>> getArtistsByGroupId(int groupId) {
        return groupRepository.getArtistsByGroupId(groupId);
    }


    //TODO A voir si on garde ou pas ou dans quelles conditions
//    public List<Group> getAllGroups() {
//        return groupRepository.getAllGroups();
//    }
//
//    public List<Artist> getGroupArtists(String groupsName) {
//        return groupRepository.getGroupArtists(groupsName);
//    }
}