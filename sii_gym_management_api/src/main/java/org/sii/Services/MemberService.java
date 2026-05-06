package org.sii.Services;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.sii.DTO.Member.MemberRequest;
import org.sii.DTO.Member.MemberResponse;
import org.sii.Entities.Member;
import org.sii.Entities.Membership;
import org.sii.Enums.MemberStatus;
import org.sii.Exceptions.CapacityExceededException;
import org.sii.Exceptions.MemberAlreadyActiveException;
import org.sii.Exceptions.MemberAlreadyInactiveException;
import org.sii.Mappers.MemberMapper;
import org.sii.Repositories.MemberRepository;
import org.sii.Repositories.MembershipRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

/**
 * Service layer for managing gym members, including registration,
 * status updates, and capacity validation.
 */
@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final MembershipRepository membershipRepository;

    //---------GET----------//

    /**
     * Retrieves all members from the system.
     *
     * @return a collection of {@link MemberResponse} objects.
     */
    public Iterable<MemberResponse>getMembers(){
        return MemberMapper.listToDto(memberRepository.findAll());
    }

    /**
     * Finds a member by their unique identifier.
     *
     * @param id the unique ID of the member.
     * @return the found member mapped to {@link MemberResponse}.
     * @throws EntityNotFoundException if no member exists with the given ID.
     */
    public MemberResponse getMemberById(Integer id){
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Member with id: " + id + " not found."));
        return MemberMapper.toDto(member);
    }

    //---------POST----------//

    /**
     * Registers a new member and assigns them to a specific membership plan.
     * <p>
     * Logic:
     * 1. Verifies the existence of the membership plan.
     * 2. Validates if the plan has remaining capacity.
     * 3. Sets the membership start date to the current date.
     * 4. Establishes the relationship between the member and the plan.
     * </p>
     *
     * @param dto the registration request data.
     * @return the persisted member as a {@link MemberResponse}.
     * @throws EntityNotFoundException if the chosen membership plan does not exist.
     * @throws CapacityExceededException if the membership plan is already at full capacity.
     */
    public MemberResponse addMember(MemberRequest dto){
        Integer membershipId = dto.membershipId();

        Membership membership = membershipRepository.findById(membershipId)
                .orElseThrow(() -> new EntityNotFoundException("Membership with id: " + membershipId + " not found."));

        validateCapacity(membership);

        Member member = MemberMapper.toEntity(dto);

        member.setMembershipStartDate(LocalDate.now());

        membership.addMember(member);

        return MemberMapper.toDto(memberRepository.save(member));
    }

    //---------PATCH----------//

    /**
     * Updates member status to {@code MemberStatus.CANCELLED}.
     *
     * @param id the unique ID of the member.
     * @throws EntityNotFoundException if the member is not found.
     * @throws MemberAlreadyInactiveException if the member is already in CANCELLED status (409 Conflict).
     */
    public void deactivateMembership(Integer id){
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Member with id: " + id + " not found."));
        if(member.getStatus().equals(MemberStatus.CANCELLED)){
            throw new MemberAlreadyInactiveException("Member with id: " + id + " is already inactive.");
        }
        member.setStatus(MemberStatus.CANCELLED);
        memberRepository.save(member);
    }

    /**
     * Updates member status to {@code MemberStatus.ACTIVE}.
     *
     * @param id the unique ID of the member.
     * @throws EntityNotFoundException if the member is not found.
     * @throws MemberAlreadyActiveException if the member is already in ACTIVE status (409 Conflict).
     */
    public void activateMembership(Integer id){
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Member with id: " + id + " not found."));
        if(member.getStatus().equals(MemberStatus.ACTIVE)){
            throw new MemberAlreadyActiveException("Member with id: " + id + " is already active.");
        }
        member.setStatus(MemberStatus.ACTIVE);
        memberRepository.save(member);
    }

    //--------HELPERS--------//

    /**
     * Validates if the membership plan can accept more members.
     * <p>
     * Compares the current count of active members against the plan's {@code maxMembers} limit.
     * </p>
     *
     * @param membership the membership entity to validate.
     * @throws CapacityExceededException if current member count equals maximum capacity.
     */
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
