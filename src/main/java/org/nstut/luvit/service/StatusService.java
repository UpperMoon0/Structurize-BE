package org.nstut.luvit.service;

import org.nstut.luvit.repository.IStatusRepository;
import org.nstut.luvit.entity.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StatusService {
    private final IStatusRepository statusRepository;

    @Autowired
    public StatusService(IStatusRepository statusRepository) {
        this.statusRepository = statusRepository;
    }

    public Status createStatus(Status status) {
        return statusRepository.save(status);
    }

    public Optional<Status> getStatus(Long id) {
        return statusRepository.findById(id);
    }

    public Status updateStatus(Status status) {
        return statusRepository.save(status);
    }

    public void deleteStatus(Long id) {
        statusRepository.deleteById(id);
    }

    public List<Status> getAllStatuses() {
        return statusRepository.findAll();
    }

}
