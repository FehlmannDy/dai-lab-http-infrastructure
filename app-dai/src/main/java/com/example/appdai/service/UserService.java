package com.example.appdai.service;

import com.example.appdai.model.Photocard;
import com.example.appdai.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class responsible for managing user-related operations.
 * It interacts with the {@link UserRepository} to perform CRUD operations for photocards in user collections and wishlist.
 */
@Service
public class UserService {

    private final UserRepository userRepository;

    /**
     * Constructs a {@link UserService} with the provided {@link UserRepository}.
     *
     * @param userRepository The {@link UserRepository} used to interact with the database for user-related operations.
     */
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // ------------------- USER -------------------

    /**
     * Adds or updates a photocard in the user's collection or wishlist.
     *
     * @param userId The ID of the user.
     * @param photocardId The ID of the photocard.
     * @param have A boolean indicating if the photocard is in the collection (true) or the wishlist (false).
     */
    public void addOrUpdatePhotocard(int userId, int photocardId, boolean have) {
        userRepository.addOrUpdatePhotocard(userId, photocardId, have);
    }

    /**
     * Removes a photocard from the user's collection or wishlist.
     *
     * @param userId The ID of the user.
     * @param photocardId The ID of the photocard to be removed.
     */
    public void removeFromUserlist(int userId, int photocardId) {
        userRepository.deletePcFromUserList(userId, photocardId);
    }

    /**
     * Retrieves the wishlist of a specific user (photocards the user does not have).
     *
     * @param userId The ID of the user whose wishlist is to be retrieved.
     * @return A list of {@link Photocard} objects representing the user's wishlist.
     */
    public List<Photocard> getUserWishlist(int userId) {
        return userRepository.getUserCollection(userId,false);
    }

    /**
     * Retrieves the collection of a specific user (photocards the user has).
     *
     * @param userId The ID of the user whose collection is to be retrieved.
     * @return A list of {@link Photocard} objects representing the user's collection.
     */
    public List<Photocard> getUserCollection(int userId) {
        return userRepository.getUserCollection(userId,true);
    }

    /**
     * Proposes a new photocard.
     *
     * @param photocard The {@link Photocard} to be proposed.
     */
    public void proposePhotocard(Photocard photocard) {
        userRepository.proposePhotocard(photocard);
    }

    // ------------------- ADMIN -------------------

    /**
     * Accepts proposed photocards and makes them official.
     *
     * @param photocardIds A list of photocard IDs to be accepted.
     */
    public void acceptProposedPhotocard(List<Integer> photocardIds) {
        userRepository.acceptProposedPhotocard(photocardIds);
    }

    /**
     * Rejects proposed photocards and removes them from the system.
     *
     * @param photocardIds A list of photocard IDs to be rejected.
     */
    public void rejectProposedPhotocard(List<Integer> photocardIds) {
        userRepository.rejectProposedPhotocard(photocardIds);
    }
}
