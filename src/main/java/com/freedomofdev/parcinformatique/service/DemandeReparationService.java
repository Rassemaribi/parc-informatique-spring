package com.freedomofdev.parcinformatique.service;

import com.freedomofdev.parcinformatique.entity.DemandeReparation;
import com.freedomofdev.parcinformatique.entity.User;
import com.freedomofdev.parcinformatique.repository.DemandeReparationRepository;
import com.freedomofdev.parcinformatique.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DemandeReparationService {
    @Autowired
    private DemandeReparationRepository demandeReparationRepository;
    @Autowired
    private UserRepository userRepository;


    public DemandeReparation createDemandeReparation(DemandeReparation demandeReparation, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with id: " + userId));

        demandeReparation.setReparationRequestedBy(user);

        return demandeReparationRepository.save(demandeReparation);
    }

    public DemandeReparation updateDemandeReparation(DemandeReparation demandeReparation) {
        return demandeReparationRepository.save(demandeReparation);
    }

    public List<DemandeReparation> getDemandesReparationsById(Long userId) {
        return demandeReparationRepository.findByReparationRequestedBy_Id(userId);
    }
}
