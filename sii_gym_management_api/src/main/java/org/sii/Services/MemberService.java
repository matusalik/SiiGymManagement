package org.sii.Services;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.sii.DTO.Member.MemberRequest;
import org.sii.DTO.Member.MemberResponse;
import org.sii.Entities.Member;
import org.sii.Entities.Membership;
import org.sii.Enums.MemberStatus;
import org.sii.Exceptions.CapacityExceededException;
import org.sii.Mappers.MemberMapper;
import org.sii.Repositories.MemberRepository;
import org.sii.Repositories.MembershipRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final MembershipRepository membershipRepository;

    //---------GET----------//

    public Iterable<MemberResponse>getMembers(){
        return MemberMapper.listToDto(memberRepository.findAll());
    }

    public MemberResponse getMemberById(Integer id){
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Member with id: " + id + " not found."));
        return MemberMapper.toDto(member);
    }

    //---------POST----------//

    public void addMember(MemberRequest dto){
        Integer membershipId = dto.membershipId();

        Membership membership = membershipRepository.findById(membershipId)
                .orElseThrow(() -> new EntityNotFoundException("Membership with id: " + membershipId + " not found."));

        validateCapacity(membership);

        Member member = MemberMapper.toEntity(dto);

        member.setMembershipStartDate(LocalDate.now());

        membership.addMember(member);

        memberRepository.save(member);
    }

    //--------HELPERS--------//

    private void validateCapacity(Membership membership){
        Integer membershipId = membership.getMembershipId();

        Integer count = membershipRepository.countActiveMembers(membershipId, MemberStatus.ACTIVE);

        Integer max = membership.getMaxMembers();

        if(count.equals(max)){
            throw new CapacityExceededException("Membership " + membership.getName() + " is full. Maximum capacity is " +
                    membership.getMaxMembers() + ".");
        }
    }

}
