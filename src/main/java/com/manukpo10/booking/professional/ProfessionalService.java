package com.manukpo10.booking.professional;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProfessionalService {

    private final ProfessionalRepository professionalRepository;

    public ProfessionalService(ProfessionalRepository professionalRepository) {
        this.professionalRepository = professionalRepository;
    }

    public List<ProfessionalResponse> findAll() {
        List<ProfessionalResponse> responses = new ArrayList<>();
        for (Professional professional : professionalRepository.findAll()) {
            responses.add(ProfessionalResponse.from(professional));
        }
        return responses;
    }


    public ProfessionalResponse findById(Long id) {
        return ProfessionalResponse.from(findEntityById(id));
    }


    public Professional findEntityById(Long id) {
        Optional<Professional> optionalProfessional = professionalRepository.findById(id);
        if (optionalProfessional.isEmpty()) {
            throw new ProfessionalNotFoundException(id);
        }
        return optionalProfessional.get();
    }

    public ProfessionalResponse create(ProfessionalRequest request) {
        Professional professional = new Professional(null, request.name(), request.specialty());
        Professional saved = professionalRepository.save(professional);
        return ProfessionalResponse.from(saved);
    }

    public ProfessionalResponse update(Long id, ProfessionalRequest request) {

        Professional professional = findEntityById(id);


        professional.setName(request.name());
        professional.setSpecialty(request.specialty());


        Professional saved = professionalRepository.save(professional);
        return ProfessionalResponse.from(saved);
    }

    public void delete(Long id) {

        if (!professionalRepository.existsById(id)) {
            throw new ProfessionalNotFoundException(id);
        }

        professionalRepository.deleteById(id);
    }
}