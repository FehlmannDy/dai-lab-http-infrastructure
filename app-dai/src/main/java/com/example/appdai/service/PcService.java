package com.example.appdai.service;

import com.example.appdai.model.Photocard;
import com.example.appdai.repository.PcRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;


/**
 * Service class responsible for handling operations related to photocards.
 * It interacts with the repository to manage photocards and provide the necessary data to the controller layer.
 */
@Service
public class PcService {

    private final PcRepository pcRepository;

    /**
     * Constructs a {@link PcService} with the provided {@link PcRepository}.
     *
     * @param pcRepository The {@link PcRepository} used to interact with the database for photocard-related operations.
     */
    public PcService(PcRepository pcRepository) {
        this.pcRepository = pcRepository;
    }

    /**
     * Retrieves all proposed photocards.
     *
     * @return A list of proposed {@link Photocard} objects.
     */
    public List<Photocard> getProposedPhotocards() {
        return pcRepository.getProposedPhotocards();
    }

    /**
     * Retrieves all photocards as a list of their names.
     *
     * @return A list of strings representing the names of all photocards.
     */
    public List<String> getAllPcs() {
        return pcRepository.getAllPcs();
    }

    /**
     * Retrieves all photocards with their types.
     *
     * @return A list of {@link Photocard} objects containing their types.
     */
    public List<Photocard> getAllPcsWithType() {
        return pcRepository.getAllPcsWithType();
    }

    /**
     * Retrieves the group of an artist based on the artist's ID.
     *
     * @param artistId The ID of the artist whose group is to be retrieved.
     * @return The name of the artist's group.
     */
    public String getArtistGroup(int artistId) {
        return pcRepository.getArtistGroup(artistId);
    }

    /**
     * Retrieves photocards associated with a specific group.
     *
     * @param groupId The ID of the group for which photocards are to be retrieved.
     * @return A list of {@link Photocard} objects associated with the specified group.
     */
    public List<Photocard> getPhotocardsByGroup(int groupId) {
        return pcRepository.getPhotocardsByGroup(groupId);
    }

    /**
     * Retrieves photocards in a paginated format.
     *
     * @param groupId The ID of the group whose photocards are to be retrieved.
     * @param page The page number for pagination.
     * @param size The number of items per page.
     * @return A list of maps containing paginated photocards' data.
     */
    public List<Map<String, Object>> getPaginatedPcs(Integer groupId, int page, int size) {
        int offset = (page - 1) * size;
        return pcRepository.getPaginatedPcs(groupId, size, offset);
    }

}
