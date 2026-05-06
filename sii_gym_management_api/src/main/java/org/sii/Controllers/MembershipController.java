package org.sii.Controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.sii.Common.Tags;
import org.sii.DTO.Membership.MembershipRequest;
import org.sii.DTO.Membership.MembershipResponse;
import org.sii.Services.MembershipService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/membership")
@Tag(name = Tags.MembershipsTag, description = "Endpoints for defining and retrieving membership plans")
public class MembershipController {
    private final MembershipService membershipService;

    //---------GET----------//

    @Operation(summary = "Get all membership plans", description = "Retrieves a list of all membership plans across all gyms.")
    @GetMapping
    public ResponseEntity<Iterable<MembershipResponse>>getMemberships(){
        return ResponseEntity.ok(membershipService.getMemberships());
    }

    @Operation(summary = "Get plans by Gym ID", description = "Retrieves all membership plans associated with a specific gym facility.")
    @ApiResponse(responseCode = "200", description = "List of plans retrieved")
    @ApiResponse(responseCode = "404", description = "Gym not found")
    @GetMapping("/getAllByGymId/{gymId}")
    public ResponseEntity<Iterable<MembershipResponse>>getAllByGymId(@PathVariable Integer gymId){
        return ResponseEntity.ok(membershipService.getAllByGymId(gymId));
    }

    @Operation(summary = "Get membership plan by ID", description = "Retrieves details of a specific membership plan.")
    @ApiResponse(responseCode = "200", description = "Plan found")
    @ApiResponse(responseCode = "404", description = "Plan not found")
    @GetMapping("/{id}")
    public ResponseEntity<MembershipResponse>getMembershipById(@PathVariable Integer id){
        MembershipResponse dto = membershipService.getMembershipById(id);
        return ResponseEntity.ok(dto);
    }

    //---------POST----------//

    @Operation(summary = "Create a new membership plan", description = "Defines a new membership tier (e.g., Gold, Silver) with a specific price and currency.")
    @ApiResponse(responseCode = "201", description = "Membership plan created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid plan details or unsupported currency")
    @PostMapping
    public ResponseEntity<MembershipResponse>addMembership(@RequestBody MembershipRequest dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(membershipService.addMembership(dto));
    }
}
