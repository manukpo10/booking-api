package com.manukpo10.booking.appointment;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    boolean existsByProfessionalIdAndStatusAndStartTimeLessThanAndEndTimeGreaterThan(
            Long professionalId, AppointmentStatus status,
            LocalDateTime newEnd, LocalDateTime newStart);

    @Override
    @EntityGraph(attributePaths = {"customer", "professional"})
    List<Appointment> findAll();

}
