package com.manukpo10.booking.professional;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/professionals")
public class ProfessionalController {


    private final ProfessionalService professionalService;

    public ProfessionalController(ProfessionalService professionalService) {
        this.professionalService = professionalService;
    }


    @GetMapping
    public List<ProfessionalResponse> findAll() {
        return professionalService.findAll();
    }

    @GetMapping("/{id}")
    public ProfessionalResponse findById(@PathVariable Long id) {
        return professionalService.findById(id);
    }

    @PostMapping
    public ResponseEntity<ProfessionalResponse> create(@Valid @RequestBody ProfessionalRequest request) {
        ProfessionalResponse created = professionalService.create(request);
        return ResponseEntity
                .created(URI.create("/api/professionals/" + created.id()))
                .body(created);
    }

    @PutMapping("/{id}")
    public ProfessionalResponse update(@PathVariable Long id, @Valid @RequestBody ProfessionalRequest request) {
        return professionalService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        professionalService.delete(id);
        return ResponseEntity.noContent().build();
    }
}