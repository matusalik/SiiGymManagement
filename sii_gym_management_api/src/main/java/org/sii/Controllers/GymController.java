package org.sii.Controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
@Tag(name = Tags.GymsTag, description = "Endpoints for managing gym facilities and financial reports")
public class GymController {
    private final GymService gymService;

    //---------GET----------//

    @Operation(summary = "Get all gyms", description = "Returns a list of all registered gym facilities.")
    @GetMapping
    public ResponseEntity<Iterable<GymResponse>>getGyms(){
        return ResponseEntity.ok(gymService.getGyms());
    }

    @Operation(summary = "Get gym by ID", description = "Retrieves detailed information about a specific gym.")
    @ApiResponse(responseCode = "200", description = "Gym found successfully")
    @ApiResponse(responseCode = "404", description = "Gym with the given ID does not exist")
    @GetMapping("/{id}")
    public ResponseEntity<GymResponse>getGymById(@PathVariable Integer id){
        GymResponse dto = gymService.getGymById(id);
        return ResponseEntity.ok(dto);
    }

    @Operation(summary = "Get global revenue", description = "Calculates and returns the revenue for all gyms based on active memberships.")
    @GetMapping("/revenue")
    public ResponseEntity<Iterable<RevenueResponse>>getRevenue(){
        return ResponseEntity.ok(gymService.getRevenue());
    }

    //---------POST----------//

    @Operation(summary = "Register a new gym", description = "Creates a new gym entry. The name must be unique across the system.")
    @ApiResponse(responseCode = "201", description = "Gym created successfully")
    @ApiResponse(responseCode = "409", description = "Conflict: A gym with this name already exists")
    @PostMapping
    public ResponseEntity<GymResponse>addGym(@RequestBody GymRequest dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(gymService.addGym(dto));
    }
}
