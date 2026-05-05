package org.sii.DTO.Member;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public record MemberResponse(
        @NotBlank String firstName,
        @NotBlank String lastName,
        @NotBlank String status,
        @NotBlank LocalDate membershipStartDate,
        @NotBlank String membershipName,
        @NotBlank String gymName
) {}
