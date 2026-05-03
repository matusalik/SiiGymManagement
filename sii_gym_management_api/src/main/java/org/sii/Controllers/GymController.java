package org.sii.Controllers;

import lombok.RequiredArgsConstructor;
import org.sii.DTO.Gym.GymRequest;
import org.sii.DTO.Gym.GymResponse;
import org.sii.Services.GymService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/gym")
public class GymController {
    private final GymService gymService;

    //---------GET----------//

    @GetMapping
    public ResponseEntity<Iterable<GymResponse>>getGyms(){
        return ResponseEntity.ok(gymService.getGyms());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GymResponse>getGymById(@PathVariable Integer id){
        GymResponse dto = gymService.getGymById(id);
        return ResponseEntity.ok(dto);
    }

    //---------POST----------//

    @PostMapping
    public ResponseEntity<Void>addGym(@RequestBody GymRequest dto){
        gymService.addGym(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
