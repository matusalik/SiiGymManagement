package org.sii.Repositories;

import org.sii.Entities.Gym;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GymRepository extends JpaRepository<Gym, Integer> {
    boolean existsByNameIgnoreCase(String name);
}
