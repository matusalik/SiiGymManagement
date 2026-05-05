package org.sii.Controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.sii.Common.Tags;
import org.sii.DTO.Gym.GymRequest;
import org.sii.DTO.Gym.GymResponse;
import org.sii.DTO.Revenue.RevenueResponse;
import org.sii.Services.GymService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/gym")
@Tag(name = Tags.GymsTag)
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

    @GetMapping("/revenue")
    public ResponseEntity<Iterable<RevenueResponse>>getRevenue(){
        return ResponseEntity.ok(gymService.getRevenue());
    }

    //---------POST----------//

    @PostMapping
    public ResponseEntity<GymResponse>addGym(@RequestBody GymRequest dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(gymService.addGym(dto));
    }
}
