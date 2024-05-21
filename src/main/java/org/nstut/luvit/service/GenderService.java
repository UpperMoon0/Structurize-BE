package org.nstut.luvit.service;

import org.nstut.luvit.entity.Gender;
import org.nstut.luvit.repository.IGenderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GenderService {
    private final IGenderRepository genderRepository;

    @Autowired
    public GenderService (IGenderRepository genderRepository) {
        this.genderRepository = genderRepository;
    }

    public Gender createGender(Gender gender) {
        return genderRepository.save(gender);
    }

    public Optional<Gender> getGender(Byte id) {
        return genderRepository.findById(id);
    }

    public Gender updateGender(Gender gender) {
        return genderRepository.save(gender);
    }

    public void deleteGender(Byte id) {
        genderRepository.deleteById(id);
    }

    public List<Gender> getAllGenders() {
        return genderRepository.findAll();
    }
}
