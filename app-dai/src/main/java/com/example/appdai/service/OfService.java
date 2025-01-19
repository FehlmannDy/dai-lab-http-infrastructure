package com.example.appdai.service;

import com.example.appdai.model.OfficialSource;
import com.example.appdai.repository.OfRepository;
import org.springframework.stereotype.Service;

/**
 * Service class responsible for handling operations related to official sources.
 * It acts as an intermediary between the controller and the repository layers for official source-related operations.
 */
@Service
public class OfService {
    private final OfRepository officialSourceRepository;

    /**
     * Constructs a {@link OfService} with the provided {@link OfRepository}.
     *
     * @param officialSourceRepository The {@link OfRepository} used to interact with the database for official source-related operations.
     */
    public OfService(OfRepository officialSourceRepository) {
        this.officialSourceRepository = officialSourceRepository;
    }

    /**
     * Proposes a new official source to the repository.
     *
     * @param officialSource The {@link OfficialSource} object to be proposed.
     * @throws IllegalArgumentException If the official source is invalid or missing required fields.
     */
    public void proposeOfficialSource(OfficialSource officialSource) {
        officialSourceRepository.proposeOfficialSource(officialSource);
    }
}
