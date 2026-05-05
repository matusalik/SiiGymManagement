package org.sii.Repositories;

import org.sii.Entities.Membership;
import org.sii.Enums.MemberStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MembershipRepository extends JpaRepository<Membership, Integer> {
    List<Membership>findByGym_GymId(Integer gymId);

    @Query("SELECT COUNT(m) FROM Member m WHERE m.membership.membershipId = :membershipId AND m.status = :status")
    Integer countActiveMembers(@Param("membershipId") Integer planId, @Param("status") MemberStatus status);
}
