package org.sii.DTO.Membership;

import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;

public record MembershipResponse(
        @NotBlank Integer membershipId,
        @NotBlank String name,
        @NotBlank Integer maxMembers,
        @NotBlank String membershipType,
        @NotBlank BigDecimal amount,
        @NotBlank String currency,
        @NotBlank Integer gymId
        ) { }