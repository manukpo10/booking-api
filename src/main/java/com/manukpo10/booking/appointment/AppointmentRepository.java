package com.manukpo10.booking.appointment;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    boolean existsByProfessionalIdAndStatusAndStartTimeLessThanAndEndTimeGreaterThan(
            Long professionalId, AppointmentStatus status,
            LocalDateTime newEnd, LocalDateTime newStart);
}
