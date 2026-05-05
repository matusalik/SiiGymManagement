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

@Service
@RequiredArgsConstructor
public class GymService {
    private final GymRepository gymRepository;
    private final MembershipRepository membershipRepository;

    //---------GET----------//

    public Iterable<GymResponse>getGyms(){
        return GymMapper.listToDto(gymRepository.findAll());
    }

    public GymResponse getGymById(Integer id){
        Gym gym = gymRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Gym with id: " + id + " not found."));
        return GymMapper.toDto(gym);
    }

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

    public void addGym(GymRequest dto){
        if(gymRepository.existsByNameIgnoreCase(dto.name())){
            throw new GymAlreadyExistsException("Gym with name '" + dto.name() + "' already exists");
        }
        Gym gym = GymMapper.toEntity(dto);
        gymRepository.save(gym);
    }
}
