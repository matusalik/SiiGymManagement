package org.sii.Controllers;

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
@Tag(name = Tags.MembershipsTag)
public class MembershipController {
    private final MembershipService membershipService;

    //---------GET----------//

    @GetMapping
    public ResponseEntity<Iterable<MembershipResponse>>getMemberships(){
        return ResponseEntity.ok(membershipService.getMemberships());
    }

    @GetMapping("/getAllByGymId/{gymId}")
    public ResponseEntity<Iterable<MembershipResponse>>getAllByGymId(@PathVariable Integer gymId){
        return ResponseEntity.ok(membershipService.getAllByGymId(gymId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MembershipResponse>getMembershipById(@PathVariable Integer id){
        MembershipResponse dto = membershipService.getMembershipById(id);
        return ResponseEntity.ok(dto);
    }

    //---------POST----------//

    @PostMapping
    public ResponseEntity<Void>addMembership(@RequestBody MembershipRequest dto){
        membershipService.addMembership(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
