package org.sii.Controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.sii.Common.Tags;
import org.sii.DTO.Member.MemberResponse;
import org.sii.Services.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
