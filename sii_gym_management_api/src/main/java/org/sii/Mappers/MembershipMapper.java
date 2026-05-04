package org.sii.Mappers;

import org.sii.DTO.Membership.MembershipRequest;
import org.sii.DTO.Membership.MembershipResponse;
import org.sii.Entities.Membership;
import org.sii.Enums.MembershipType;
import org.sii.Model.Price;

import java.util.ArrayList;
import java.util.List;

public class MembershipMapper {
    public static MembershipResponse toDto(Membership membership){
        return new MembershipResponse(
                membership.getMembershipId(),
                membership.getName(),
                membership.getMaxMembers(),
                membership.getMembershipType().toString(),
                membership.getPrice().getAmount(),
                membership.getPrice().getCurrency(),
                membership.getGym().getGymId()
        );
    }

    public static Membership toEntity(MembershipRequest dto){
        Membership membership = new Membership();
        membership.setName(dto.name());
        membership.setMaxMembers(dto.maxMembers());
        membership.setMembershipType(MembershipType.valueOf(dto.membershipType().toUpperCase()));
        membership.setPrice(new Price(dto.amount(), dto.currency()));
        return membership;
    }

    public static Iterable<MembershipResponse>listToDto(Iterable<Membership>memberships){
        List<MembershipResponse>dtos = new ArrayList<>();
        for(Membership m : memberships){
            dtos.add(toDto(m));
        }
        return dtos;
    }
}
