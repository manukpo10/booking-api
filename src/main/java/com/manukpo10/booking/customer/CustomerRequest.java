package com.manukpo10.booking.customer;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CustomerRequest(@NotBlank @Size(max = 100) String name, @Email @NotBlank @Size(max = 255) String email,
                              @Size(max = 30) String phone) {


}
