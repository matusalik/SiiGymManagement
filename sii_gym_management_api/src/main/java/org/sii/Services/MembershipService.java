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

@Service
@RequiredArgsConstructor
public class MembershipService {
    private final MembershipRepository membershipRepository;
    private final GymRepository gymRepository;
    private final AppConstraints appConstraints;

    //---------GET----------//

    public Iterable<MembershipResponse>getMemberships(){
        return MembershipMapper.listToDto(membershipRepository.findAll());
    }

    public Iterable<MembershipResponse>getAllByGymId(Integer gymId){
        return MembershipMapper.listToDto(membershipRepository.findByGym_GymId(gymId));
    }

    public MembershipResponse getMembershipById(Integer id){
        Membership membership = membershipRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Membership with id: " + id + " not found."));
        return MembershipMapper.toDto(membership);
    }

    //---------POST----------//

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

    private void validateCurrency(String currency) {
        boolean isSupported = appConstraints.getSupportedCurrencies().stream()
                .anyMatch(c -> c.equalsIgnoreCase(currency));

        if (!isSupported) {
            throw new UnsupportedCurrencyException("Currency " + currency + " is not supported. " +
                    "Supported: " + appConstraints.getSupportedCurrencies());
        }
    }

}
