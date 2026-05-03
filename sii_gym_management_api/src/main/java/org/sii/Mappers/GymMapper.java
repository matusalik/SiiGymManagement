package org.sii.Mappers;

import org.sii.DTO.Gym.GymRequest;
import org.sii.DTO.Gym.GymResponse;
import org.sii.Entities.Gym;

import java.util.ArrayList;
import java.util.List;

public class GymMapper {
    public static GymResponse toDto(Gym gym){
        return new GymResponse(
                gym.getGymId(),
                gym.getName(),
                gym.getAddress(),
                gym.getPhoneNumber()
        );
    }

    public static Gym toEntity(GymRequest dto){
        Gym gym = new Gym();
        gym.setName(dto.name());
        gym.setAddress(dto.address());
        gym.setPhoneNumber(dto.phoneNumber());
        return gym;
    }

    public static Iterable<GymResponse>listToDto(Iterable<Gym>gyms){
        List<GymResponse>dtos = new ArrayList<>();
        for(Gym g : gyms){
            dtos.add(toDto(g));
        }
        return dtos;
    }
}
