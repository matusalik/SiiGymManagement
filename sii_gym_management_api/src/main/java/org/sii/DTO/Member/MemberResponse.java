package org.sii.DTO.Member;

import jakarta.validation.constraints.NotBlank;

public record MemberResponse(
        @NotBlank String firstName,
        @NotBlank String lastName,
        @NotBlank String status,
        @NotBlank String membershipName,
        @NotBlank String gymName
) {}
