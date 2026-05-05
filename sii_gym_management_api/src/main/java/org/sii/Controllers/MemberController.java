package org.sii.Controllers;

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
@Tag(name = Tags.MembersTag)
public class MemberController {
    private final MemberService memberService;

    //---------GET----------//

    @GetMapping
    public ResponseEntity<Iterable<MemberResponse>>getMembers(){
        return ResponseEntity.ok(memberService.getMembers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MemberResponse>getMemberById(@PathVariable Integer id){
        MemberResponse dto = memberService.getMemberById(id);
        return ResponseEntity.ok(dto);
    }

    //---------POST----------//

    @PostMapping
    public ResponseEntity<MemberResponse>addMember(@RequestBody MemberRequest dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(memberService.addMember(dto));
    }

    //---------PATCH----------//

    @PatchMapping("/deactivate/{id}")
    public ResponseEntity<Void>deactivateMember(@PathVariable Integer id){
        memberService.deactivateMembership(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/activate/{id}")
    public ResponseEntity<Void>activateMember(@PathVariable Integer id){
        memberService.activateMembership(id);
        return ResponseEntity.ok().build();
    }
}
