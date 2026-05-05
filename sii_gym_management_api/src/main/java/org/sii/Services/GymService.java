package org.sii.Services;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.sii.DTO.Gym.GymRequest;
import org.sii.DTO.Gym.GymResponse;
import org.sii.Entities.Gym;
import org.sii.Exceptions.GymAlreadyExistsException;
import org.sii.Mappers.GymMapper;
import org.sii.Repositories.GymRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GymService {
    private final GymRepository gymRepository;

    //---------GET----------//

    public Iterable<GymResponse>getGyms(){
        return GymMapper.listToDto(gymRepository.findAll());
    }

    public GymResponse getGymById(Integer id){
        Gym gym = gymRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Gym with id: " + id + " not found."));
        return GymMapper.toDto(gym);
    }

    //---------POST----------//

    public void addGym(GymRequest dto){
        if(gymRepository.existsByName(dto.name())){
            throw new GymAlreadyExistsException("Gym with name '" + dto.name() + "' already exists");
        }
        Gym gym = GymMapper.toEntity(dto);
        gymRepository.save(gym);
    }
}
