package org.sii.Services;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.sii.DTO.Member.MemberResponse;
import org.sii.Entities.Member;
import org.sii.Mappers.MemberMapper;
import org.sii.Repositories.MemberRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    //---------GET----------//

    public Iterable<MemberResponse>getMembers(){
        return MemberMapper.listToDto(memberRepository.findAll());
    }

    public MemberResponse getMemberById(Integer id){
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Member with id: " + id + " not found."));
        return MemberMapper.toDto(member);
    }

}
