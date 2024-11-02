package com.ti.estoque.services;

import com.ti.estoque.models.Movement;
import com.ti.estoque.repository.MovementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovementService {

    @Autowired
    private MovementRepository movementRepository;

    public Movement createMovement(Movement movement) {
        return movementRepository.save(movement);
    }

    public List<Movement> getAllMovements() {
        return movementRepository.findAll();
    }

    public Optional<Movement> getMovementById(Long id) {
        return movementRepository.findById(id);
    }

    public void deleteMovement(Long id) {
        movementRepository.deleteById(id);
    }
}
