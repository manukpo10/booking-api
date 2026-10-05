package com.manukpo10.booking.professional;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ProfessionalRequest(@NotBlank @Size(max = 100) String name,
                               @NotBlank @Size(max = 100) String specialty) {

}
