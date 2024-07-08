package com.freedomofdev.parcinformatique.service;

import com.freedomofdev.parcinformatique.dto.UserDto;
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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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

    @Transactional
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
        actif.setDateAssignation(new Date());


        // Save the new user and the Actif to the database
        userRepository.save(user);
        actifRepository.save(actif);
    }

    @Transactional
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

        // Set the state of the Actif to EN_STOCK
        actif.setEtat(Etat.EN_STOCK);

        userRepository.save(user);
        actifRepository.save(actif);
    }

    @Transactional
    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    private UserDto convertToDto(User user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setNom(user.getNom());
        dto.setPrenom(user.getPrenom());
        dto.setNumeroTelephone(user.getNumeroTelephone());
        dto.setAssignedActifs(new ArrayList<>(user.getAssignedActifs()));
        dto.setRoles(user.getRoles().stream().map(role -> role.getName().name()).collect(Collectors.toList())); // map roles
        return dto;
    }

    @Transactional
    public void deleteUserAndUnassignActifs(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé avec Id: " + userId));

        for (Actif actif : user.getAssignedActifs()) {
            actif.setAssignedUser(null);
            actif.setEtat(Etat.EN_STOCK);
            actifRepository.save(actif);
        }
        user.getRoles().clear();
        userRepository.delete(user);
    }
}