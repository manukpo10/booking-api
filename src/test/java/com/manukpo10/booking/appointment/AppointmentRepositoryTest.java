package com.manukpo10.booking.appointment;

import com.manukpo10.booking.customer.Customer;
import com.manukpo10.booking.customer.CustomerRepository;
import com.manukpo10.booking.professional.Professional;
import com.manukpo10.booking.professional.ProfessionalRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;
import org.testcontainers.utility.TestcontainersConfiguration;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(TestcontainersConfiguration.class)
class AppointmentRepositoryTest {

    @Autowired
    AppointmentRepository appointmentRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    ProfessionalRepository professionalRepository;

    private Professional professional;
    private final LocalDateTime ten = LocalDateTime.of(2030, 1, 15, 10, 0);

    @BeforeEach
    void setUp() {
        Customer customer = customerRepository.save(new Customer(null, "Ana", "ana@mail.com", "221 555-0000"));
        professional = professionalRepository.save(new Professional(null, "Laura", "Corte"));
        appointmentRepository.save(new Appointment(customer, professional, ten, ten.plusHours(1)));  // 10:00 a 11:00
    }

    @Test
    void exists_whenNewSlotOverlaps_returnsTrue() {
        boolean exists = appointmentRepository
                .existsByProfessionalIdAndStatusAndStartTimeLessThanAndEndTimeGreaterThan(
                        professional.getId(), AppointmentStatus.BOOKED,
                        ten.plusMinutes(90),    // fin del nuevo:    11:30
                        ten.plusMinutes(30));   // inicio del nuevo: 10:30

        assertThat(exists).isTrue();
    }

    @Test
    void exists_whenNewSlotStartsWhenExistingEnds_returnsFalse() {
        boolean exists = appointmentRepository
                .existsByProfessionalIdAndStatusAndStartTimeLessThanAndEndTimeGreaterThan(
                        professional.getId(), AppointmentStatus.BOOKED,
                        ten.plusHours(2),  //   fin del nuevo:    12:00
                        ten.plusHours(1));   // inicio del nuevo: 11:00

        assertThat(exists).isFalse();
    }

    @Test
    void exists_whenNewSlotEndsWhenExistingStarts_returnsFalse() {
        boolean exists = appointmentRepository
                .existsByProfessionalIdAndStatusAndStartTimeLessThanAndEndTimeGreaterThan(
                        professional.getId(), AppointmentStatus.BOOKED,
                        ten,  //   fin del nuevo:    10:00
                        ten.minusHours(1));   // inicio del nuevo: 09:00

        assertThat(exists).isFalse();
    }




}