package org.sii.DTO.Member;

import jakarta.validation.constraints.NotBlank;

public record MemberRequest(
        @NotBlank String firstName,
        @NotBlank String lastName,
        @NotBlank String email,
        @NotBlank Integer membershipId
) {}
