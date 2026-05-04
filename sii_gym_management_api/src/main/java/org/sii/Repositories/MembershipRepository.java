package org.sii.Repositories;

import org.sii.Entities.Membership;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MembershipRepository extends JpaRepository<Membership, Integer> {
    List<Membership>findByGym_GymId(Integer gymId);
}
