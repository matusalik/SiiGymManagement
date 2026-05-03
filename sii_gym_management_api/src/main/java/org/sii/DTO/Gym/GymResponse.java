package org.sii.DTO.Gym;

import jakarta.validation.constraints.NotBlank;

public record GymResponse(
   @NotBlank Integer gymId,
   @NotBlank String name,
   @NotBlank String address,
   @NotBlank String phoneNumber
) {}
