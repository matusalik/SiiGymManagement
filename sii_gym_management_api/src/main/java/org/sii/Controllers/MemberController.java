package org.sii.Controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.sii.Common.Tags;
import org.sii.Services.MemberService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/member")
@Tag(name = Tags.MembersTag)
public class MemberController {
    private final MemberService memberService;
}
