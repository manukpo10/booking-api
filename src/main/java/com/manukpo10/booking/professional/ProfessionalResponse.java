package com.manukpo10.booking.professional;


public record ProfessionalResponse(Long id, String name, String specialty) {

    public static ProfessionalResponse from(Professional professional) {
        return new ProfessionalResponse(
                professional.getId(),
                professional.getName(),
                professional.getSpecialty()

        );
    }
}
