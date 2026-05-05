package org.sii.Mappers;

import org.sii.DTO.Member.MemberRequest;
import org.sii.DTO.Member.MemberResponse;
import org.sii.Entities.Member;
import org.sii.Enums.MemberStatus;

import java.util.ArrayList;
import java.util.List;

public class MemberMapper {
    public static MemberResponse toDto(Member member){
        return new MemberResponse(
                member.getFirstName(),
                member.getLastName(),
                member.getStatus().toString(),
                member.getMembershipStartDate(),
                member.getMembership().getName(),
                member.getMembership().getGym().getName()
        );
    }

    public static Member toEntity(MemberRequest dto){
        Member member = new Member();
        member.setFirstName(dto.firstName());
        member.setLastName(dto.lastName());
        member.setEmail(dto.email());
        member.setStatus(MemberStatus.ACTIVE);
        return member;
    }

    public static Iterable<MemberResponse>listToDto(Iterable<Member>members){
        List<MemberResponse> dtos = new ArrayList<>();
        for(Member m : members){
            dtos.add(toDto(m));
        }
        return dtos;
    }
}
