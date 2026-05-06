package org.sii.Services;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.sii.DTO.Gym.GymRequest;
import org.sii.DTO.Gym.GymResponse;
import org.sii.DTO.Revenue.RevenueResponse;
import org.sii.Entities.Gym;
import org.sii.Entities.Membership;
import org.sii.Enums.MemberStatus;
import org.sii.Exceptions.GymAlreadyExistsException;
import org.sii.Mappers.GymMapper;
import org.sii.Repositories.GymRepository;
import org.sii.Repositories.MembershipRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Service layer responsible for handling business logic related to Gym facilities.
 * This includes registration, retrieval, and financial reporting.
 */
@Service
@RequiredArgsConstructor
public class GymService {
    private final GymRepository gymRepository;
    private final MembershipRepository membershipRepository;

    //---------GET----------//

    /**
     * Retrieves all registered gyms in the system.
     *
     * @return a collection of {@link GymResponse} containing basic gym information.
     */
    public Iterable<GymResponse>getGyms(){
        return GymMapper.listToDto(gymRepository.findAll());
    }


    /**
     * Retrieves detailed information for a specific gym facility.
     *
     * @param id the unique identifier of the gym.
     * @return the found gym data mapped to {@link GymResponse}.
     * @throws EntityNotFoundException if no gym exists with the specified ID.
     */
    public GymResponse getGymById(Integer id){
        Gym gym = gymRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Gym with id: " + id + " not found."));
        return GymMapper.toDto(gym);
    }

    /**
     * Generates a global revenue report by iterating through all membership plans.
     * <p>
     * Calculation logic:
     * 1. Iterates over every membership plan.
     * 2. Filters for members with {@code MemberStatus.ACTIVE}.
     * 3. Multiplies the number of active members by the plan's price.
     * 4. Groups results by gym name and currency.
     * </p>
     *
     * @return a sorted list of {@link RevenueResponse} objects representing earnings per plan/gym.
     */
    public Iterable<RevenueResponse>getRevenue(){
        return membershipRepository.findAll().stream()
                .map(plan -> {
                    long activeCount = plan.getMembers().stream()
                            .filter(m -> m.getStatus() == MemberStatus.ACTIVE)
                            .count();

                    BigDecimal totalAmount = plan.getPrice().getAmount()
                            .multiply(BigDecimal.valueOf(activeCount));

                    return new RevenueResponse(
                            plan.getGym().getName(),
                            totalAmount,
                            plan.getPrice().getCurrency()
                    );
                })
                .sorted(Comparator.comparing(RevenueResponse::gymName)
                        .thenComparing(RevenueResponse::currency))
                .toList();
    }

    //---------POST----------//

    /**
     * Registers a new gym facility.
     * <p>
     * Validation: Ensures that the gym name is unique (case-insensitive) before persistence.
     * </p>
     *
     * @param dto the gym request data.
     * @return the successfully persisted gym as a {@link GymResponse}.
     * @throws GymAlreadyExistsException if a gym with the same name is already present.
     */
    public GymResponse addGym(GymRequest dto){
        if(gymRepository.existsByNameIgnoreCase(dto.name())){
            throw new GymAlreadyExistsException("Gym with name '" + dto.name() + "' already exists");
        }
        Gym gym = GymMapper.toEntity(dto);
        return GymMapper.toDto(gymRepository.save(gym));
    }
}
