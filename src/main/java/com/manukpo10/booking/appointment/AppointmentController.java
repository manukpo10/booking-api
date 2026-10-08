package com.manukpo10.booking.appointment;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {


    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }


    @GetMapping
    public List<AppointmentResponse> findAll() {
        return appointmentService.findAll();
    }

    @GetMapping("/{id}")
    public AppointmentResponse findById(@PathVariable Long id) {
        return appointmentService.findById(id);
    }

    @PostMapping
    public ResponseEntity<AppointmentResponse> create(@Valid @RequestBody AppointmentRequest request) {
        AppointmentResponse created = appointmentService.create(request);
        return ResponseEntity
                .created(URI.create("/api/appointments/" + created.id()))
                .body(created);
    }

    @PostMapping("/{id}/cancel")
    public AppointmentResponse cancel(@PathVariable Long id) {
        return appointmentService.cancel(id);
    }


}