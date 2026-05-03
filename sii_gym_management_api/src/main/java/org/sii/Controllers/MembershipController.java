package org.sii.Controllers;

import lombok.RequiredArgsConstructor;
import org.sii.Services.MembershipService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/membership")
public class MembershipController {
    private final MembershipService membershipService;
}
