package com.freedomofdev.parcinformatique.security.services;

import com.freedomofdev.parcinformatique.entity.Actif;
import com.freedomofdev.parcinformatique.entity.User;
import com.freedomofdev.parcinformatique.enums.Etat;
import com.freedomofdev.parcinformatique.repository.ActifRepository;
import com.freedomofdev.parcinformatique.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    private ActifRepository actifRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

        return UserDetailsImpl.build(user);
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public String getUserNomById(Long id) {
        User user = userRepository.findById(id).orElse(null);
        return user != null ? user.getNom() : null;
    }

    public String getUserPrenomById(Long id) {
        User user = userRepository.findById(id).orElse(null);
        return user != null ? user.getPrenom() : null;
    }

    public String getUserNumeroTelephoneById(Long id) {
        User user = userRepository.findById(id).orElse(null);
        return user != null ? user.getNumeroTelephone() : null;
    }

    public void assignActifToUser(Long userId, Long actifId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        Actif actif = actifRepository.findById(actifId)
                .orElseThrow(() -> new RuntimeException("Actif non trouvé."));

        // Check if the Actif is already assigned to a user
        User oldUser = actif.getAssignedUser();
        if (oldUser != null) {
            oldUser.getAssignedActifs().remove(actif);
            userRepository.save(oldUser);
        }

        user.getAssignedActifs().add(actif);
        actif.setAssignedUser(user);

        actif.setEtat(Etat.ASSIGNED);

        // Save the new user and the Actif to the database
        userRepository.save(user);
        actifRepository.save(actif);
    }

    public void removeActifFromUser(Long userId, Long actifId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Erreur."));
        Actif actif = actifRepository.findById(actifId)
                .orElseThrow(() -> new RuntimeException("Error: Actif is not found."));

        if (!user.getAssignedActifs().contains(actif)) {
            throw new RuntimeException("Error: Actif is not assigned to this user.");
        }

        user.getAssignedActifs().remove(actif);
        actif.setAssignedUser(null);

        userRepository.save(user);
        actifRepository.save(actif);
    }
}