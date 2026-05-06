package org.sii.Controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.sii.Common.Tags;
import org.sii.DTO.Member.MemberRequest;
import org.sii.DTO.Member.MemberResponse;
import org.sii.Services.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/member")
@Tag(name = Tags.MembersTag, description = "Endpoints for managing gym members and their membership statuses")
public class MemberController {
    private final MemberService memberService;

    //---------GET----------//

    @Operation(summary = "Get all members", description = "Retrieves a complete list of all registered members.")
    @GetMapping
    public ResponseEntity<Iterable<MemberResponse>>getMembers(){
        return ResponseEntity.ok(memberService.getMembers());
    }

    @Operation(summary = "Get member by ID", description = "Retrieves detailed information about a specific member using their unique ID.")
    @ApiResponse(responseCode = "200", description = "Member found")
    @ApiResponse(responseCode = "404", description = "Member not found")
    @GetMapping("/{id}")
    public ResponseEntity<MemberResponse>getMemberById(@PathVariable Integer id){
        MemberResponse dto = memberService.getMemberById(id);
        return ResponseEntity.ok(dto);
    }

    //---------POST----------//

    @Operation(summary = "Register a new member", description = "Creates a new member profile and assigns them to a membership plan.")
    @ApiResponse(responseCode = "201", description = "Member successfully registered")
    @ApiResponse(responseCode = "400", description = "Invalid input data provided")
    @PostMapping
    public ResponseEntity<MemberResponse>addMember(@RequestBody MemberRequest dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(memberService.addMember(dto));
    }

    //---------PATCH----------//

    @Operation(summary = "Deactivate membership", description = "Sets the member's status to INACTIVE. Use this when a member cancels or pauses their plan.")
    @ApiResponse(responseCode = "200", description = "Membership successfully deactivated")
    @ApiResponse(responseCode = "404", description = "Member not found")
    @ApiResponse(responseCode = "409", description = "Conflict: Member is already INACTIVE")
    @PatchMapping("/deactivate/{id}")
    public ResponseEntity<Void>deactivateMember(@PathVariable Integer id){
        memberService.deactivateMembership(id);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Activate membership", description = "Sets the member's status to ACTIVE. Use this to reactivate a previously deactivated account.")
    @ApiResponse(responseCode = "200", description = "Membership successfully activated")
    @ApiResponse(responseCode = "404", description = "Member not found")
    @ApiResponse(responseCode = "409", description = "Conflict: Member is already ACTIVE")
    @PatchMapping("/activate/{id}")
    public ResponseEntity<Void>activateMember(@PathVariable Integer id){
        memberService.activateMembership(id);
        return ResponseEntity.ok().build();
    }
}
