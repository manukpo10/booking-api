package com.manukpo10.booking.appointment;

import com.manukpo10.booking.common.ConflictException;
import com.manukpo10.booking.customer.Customer;
import com.manukpo10.booking.customer.CustomerResponse;
import com.manukpo10.booking.customer.CustomerService;
import com.manukpo10.booking.professional.Professional;
import com.manukpo10.booking.professional.ProfessionalNotFoundException;
import com.manukpo10.booking.professional.ProfessionalService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final CustomerService customerService;
    private final ProfessionalService professionalService;


    public AppointmentService(AppointmentRepository appointmentRepository, CustomerService customerService, ProfessionalService professionalService) {
        this.appointmentRepository = appointmentRepository;
        this.customerService = customerService;
        this.professionalService = professionalService;
    }

    @Transactional
    public AppointmentResponse create(AppointmentRequest request) {

        Customer customer = customerService.findEntityById(request.customerId());
        Professional professional = professionalService.findEntityById(request.professionalId());

        LocalDateTime start = request.startTime();
        LocalDateTime end = start.plusMinutes(request.durationMinutes());


        boolean overlaps = appointmentRepository
                .existsByProfessionalIdAndStatusAndStartTimeLessThanAndEndTimeGreaterThan(
                        professional.getId(), AppointmentStatus.BOOKED, end, start);
        if (overlaps) {
            throw new ConflictException("Professional already has an appointment in that time slot");
        }

        Appointment appointment = new Appointment(customer, professional, start, end);
        Appointment saved = appointmentRepository.save(appointment);
        return AppointmentResponse.from(saved);
    }

    @Transactional(readOnly = true)
    public List<AppointmentResponse> findAll() {
        List<AppointmentResponse> responses = new ArrayList<>();
        for (Appointment appointment : appointmentRepository.findAll()) {
            responses.add(AppointmentResponse.from(appointment));
        }
        return responses;
    }

    @Transactional(readOnly = true)
    public AppointmentResponse findById(Long id) {
        return AppointmentResponse.from(findEntityById(id));
    }


    private Appointment findEntityById(Long id) {
        Optional<Appointment> optionalAppointment = appointmentRepository.findById(id);
        if (optionalAppointment.isEmpty()) {
            throw new AppointmentNotFoundException(id);
        }
        return optionalAppointment.get();
    }

    @Transactional
    public AppointmentResponse cancel(Long id){
        Appointment appointment = findEntityById(id);
        appointment.cancel();
        Appointment saved = appointmentRepository.save(appointment);
        return AppointmentResponse.from(saved);
    }
}