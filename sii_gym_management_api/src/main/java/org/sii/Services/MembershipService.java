package org.sii.Services;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.sii.Common.AppConstraints;
import org.sii.DTO.Membership.MembershipRequest;
import org.sii.DTO.Membership.MembershipResponse;
import org.sii.Entities.Gym;
import org.sii.Entities.Membership;
import org.sii.Exceptions.UnsupportedCurrencyException;
import org.sii.Mappers.MembershipMapper;
import org.sii.Repositories.GymRepository;
import org.sii.Repositories.MembershipRepository;
import org.springframework.stereotype.Service;

/**
 * Service layer responsible for managing membership plans (tiers)
 * and ensuring currency compliance across different gym facilities.
 */
@Service
@RequiredArgsConstructor
public class MembershipService {
    private final MembershipRepository membershipRepository;
    private final GymRepository gymRepository;
    private final AppConstraints appConstraints;

    //---------GET----------//

    /**
     * Retrieves all membership plans available in the system.
     *
     * @return a collection of all {@link MembershipResponse} DTOs.
     */
    public Iterable<MembershipResponse>getMemberships(){
        return MembershipMapper.listToDto(membershipRepository.findAll());
    }

    /**
     * Retrieves all membership plans offered by a specific gym.
     *
     * @param gymId the unique identifier of the gym.
     * @return a list of membership plans associated with the given gym ID.
     */
    public Iterable<MembershipResponse>getAllByGymId(Integer gymId){
        return MembershipMapper.listToDto(membershipRepository.findByGym_GymId(gymId));
    }

    /**
     * Retrieves a specific membership plan by its ID.
     *
     * @param id the unique identifier of the membership plan.
     * @return the found membership data as a {@link MembershipResponse}.
     * @throws EntityNotFoundException if no plan exists with the specified ID.
     */
    public MembershipResponse getMembershipById(Integer id){
        Membership membership = membershipRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Membership with id: " + id + " not found."));
        return MembershipMapper.toDto(membership);
    }

    //---------POST----------//

    /**
     * Creates and assigns a new membership plan to a gym.
     * <p>
     * Validation steps:
     * 1. Checks if the provided currency is supported by the system configuration.
     * 2. Verifies that the target gym exists.
     * 3. Maps the request to an entity and establishes the bidirectional relationship.
     * </p>
     *
     * @param dto the data transfer object containing the plan details.
     * @return the saved membership plan as a {@link MembershipResponse}.
     * @throws UnsupportedCurrencyException if the currency is not in the supported list.
     * @throws EntityNotFoundException if the gym ID provided in the DTO is invalid.
     */
    public MembershipResponse addMembership(MembershipRequest dto){
        validateCurrency(dto.currency());

        Integer gymId = dto.gymId();

        Gym gym = gymRepository.findById(gymId)
                .orElseThrow(() -> new EntityNotFoundException("Gym with id: " + gymId + " not found."));

        Membership membership = MembershipMapper.toEntity(dto);

        gym.addMembership(membership);

        return MembershipMapper.toDto(membershipRepository.save(membership));
    }

    //--------HELPERS--------//

    /**
     * Validates the currency against the system's allowed currency list.
     * <p>
     * This helper method ensures consistency across the financial modules
     * by fetching supported values from {@link AppConstraints}.
     * </p>
     *
     * @param currency the currency code to validate (e.g., "PLN", "USD").
     * @throws UnsupportedCurrencyException if the currency code is not supported (case-insensitive).
     */
    private void validateCurrency(String currency) {
        boolean isSupported = appConstraints.getSupportedCurrencies().stream()
                .anyMatch(c -> c.equalsIgnoreCase(currency));

        if (!isSupported) {
            throw new UnsupportedCurrencyException("Currency " + currency + " is not supported. " +
                    "Supported: " + appConstraints.getSupportedCurrencies());
        }
    }

}
