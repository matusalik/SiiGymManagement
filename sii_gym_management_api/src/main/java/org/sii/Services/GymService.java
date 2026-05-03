package org.sii.Services;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.sii.DTO.Gym.GymRequest;
import org.sii.DTO.Gym.GymResponse;
import org.sii.Entities.Gym;
import org.sii.Mappers.GymMapper;
import org.sii.Repositories.GymRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GymService {
    private final GymRepository gymRepository;

    public Iterable<GymResponse>getGyms(){
        return GymMapper.listToDto(gymRepository.findAll());
    }

    public GymResponse getGymById(Integer id){
        Gym gym = gymRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Client with id: " + id + " not found."));
        return GymMapper.toDto(gym);
    }

    public void addGym(GymRequest dto){
        Gym gym = GymMapper.toEntity(dto);
        gymRepository.save(gym);
    }
}
