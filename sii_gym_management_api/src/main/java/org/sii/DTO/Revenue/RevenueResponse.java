package org.sii.DTO.Revenue;

import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;

public record RevenueResponse(
        @NotBlank String gymName,
        @NotBlank BigDecimal amount,
        @NotBlank String currency
        ) {}