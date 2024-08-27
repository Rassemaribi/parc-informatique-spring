package com.freedomofdev.parcinformatique.service;

import com.freedomofdev.parcinformatique.dto.AuthRequest;
import com.freedomofdev.parcinformatique.dto.UserDto;
import com.freedomofdev.parcinformatique.entity.*;
import com.freedomofdev.parcinformatique.enums.Etat;
import com.freedomofdev.parcinformatique.repository.AbonnementRepository;
import com.freedomofdev.parcinformatique.repository.ActifRepository;
import com.freedomofdev.parcinformatique.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private AbonnementRepository abonnementRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private ActifRepository actifRepository;
    @Transactional
    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Transactional
    public User findOrCreateUser(AuthRequest authRequest) {
        String email = authRequest.getEmail();
        List<String> groups = authRequest.getGroups();

        String[] nameParts = authRequest.getName().split(" ", 2);
        String prenom = nameParts[0];
        String nom = nameParts.length > 1 ? nameParts[1] : "";

        UserDto userDto = this.findByEmail(email);
        User user;
        if (userDto == null) {
            user = new User();
            user.setEmail(email);
            user.setPrenom(prenom);
            user.setNom(nom);
            user.setUserGroups(groups);
        } else {
            user = convertToEntity(userDto);
            user.setUserGroups(groups); // Update the groups if the user already exists

            // Update nom and prenom if they're null or empty
            if (user.getNom() == null || user.getNom().isEmpty()) {
                user.setNom(nom);
            }
            if (user.getPrenom() == null || user.getPrenom().isEmpty()) {
                user.setPrenom(prenom);
            }
        }
        userRepository.save(user); // Save the user after the groups are added
        return user;
    }

    public User convertToEntity(UserDto dto) {
        User user = new User();
        user.setId(dto.getId());
        user.setEmail(dto.getEmail());
        user.setNom(dto.getNom());
        user.setPrenom(dto.getPrenom());
        user.setActive(dto.isActive());
        user.setPhoneNumber(dto.getPhoneNumber());
        user.setAssignedActifs(dto.getAssignedActifs());
        user.setCreatedActifs(dto.getCreatedActifs());
        user.setDemandesAcquisitionCollaborateur(dto.getDemandesAcquisitionCollaborateur());
        user.setDemandesAcquisitionDSI(dto.getDemandesAcquisitionDSI());
        user.setDemandesReparationCollaborateur(dto.getDemandesReparationCollaborateur());
        user.setDemandesReparationDSI(dto.getDemandesReparationDSI());
        user.setUserGroups(dto.getUserGroups());
        return user;
    }

    @Transactional
    public UserDto findByEmail(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        User user;
        if (userOptional.isPresent()) {
            user = userOptional.get();
        } else {
            // Create a new user if a user with the provided email does not exist
            user = new User();
            user.setEmail(email);
            userRepository.save(user);
        }

        // Fetch the collections separately in different transactions
        user.setAssignedActifs(getAssignedActifs(user.getId()));
        user.setCreatedActifs(getCreatedActifs(user.getId()));
        user.setDemandesAcquisitionCollaborateur(getDemandesAcquisitionCollaborateur(user.getId()));
        user.setDemandesAcquisitionDSI(getDemandesAcquisitionDSI(user.getId()));
        user.setDemandesReparationCollaborateur(getDemandesReparationCollaborateur(user.getId()));
        user.setDemandesReparationDSI(getDemandesReparationDSI(user.getId()));

        return convertToDto(user);
    }

    @Transactional
    public List<Actif> getAssignedActifs(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("Error: User is not found."));
        return user.getAssignedActifs() != null ? new ArrayList<>(user.getAssignedActifs()) : new ArrayList<>();
    }

    @Transactional
    public List<Actif> getCreatedActifs(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("Error: User is not found."));
        return user.getCreatedActifs() != null ? new ArrayList<>(user.getCreatedActifs()) : new ArrayList<>();
    }

    @Transactional
    public List<DemandeAcquisition> getDemandesAcquisitionCollaborateur(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("Error: User is not found."));
        return user.getDemandesAcquisitionCollaborateur() != null ? new ArrayList<>(user.getDemandesAcquisitionCollaborateur()) : new ArrayList<>();
    }

    @Transactional
    public List<DemandeAcquisition> getDemandesAcquisitionDSI(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("Error: User is not found."));
        return user.getDemandesAcquisitionDSI() != null ? new ArrayList<>(user.getDemandesAcquisitionDSI()) : new ArrayList<>();
    }

    @Transactional
    public List<DemandeReparation> getDemandesReparationCollaborateur(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("Error: User is not found."));
        return user.getDemandesReparationCollaborateur() != null ? new ArrayList<>(user.getDemandesReparationCollaborateur()) : new ArrayList<>();
    }

    @Transactional
    public List<DemandeReparation> getDemandesReparationDSI(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("Error: User is not found."));
        return user.getDemandesReparationDSI() != null ? new ArrayList<>(user.getDemandesReparationDSI()) : new ArrayList<>();
    }

    @Transactional
    public User save(User user) {
        return userRepository.save(user);
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
        List<User> users = userRepository.findByActiveTrue();
        return users.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    private UserDto convertToDto(User user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setNom(user.getNom());
        dto.setPrenom(user.getPrenom());
        dto.setActive(user.isActive());
        dto.setPhoneNumber(user.getPhoneNumber());
        dto.setAssignedActifs(new ArrayList<>(user.getAssignedActifs()));
        dto.setCreatedActifs(new ArrayList<>(user.getCreatedActifs()));
        dto.setDemandesAcquisitionCollaborateur(new ArrayList<>(user.getDemandesAcquisitionCollaborateur()));
        dto.setDemandesAcquisitionDSI(new ArrayList<>(user.getDemandesAcquisitionDSI()));
        dto.setDemandesReparationCollaborateur(new ArrayList<>(user.getDemandesReparationCollaborateur()));
        dto.setDemandesReparationDSI(new ArrayList<>(user.getDemandesReparationDSI()));
        dto.setUserGroups(user.getUserGroups());
        return dto;
    }

    @Transactional
    public void deactivateUsers(List<Long> ids) {
        userRepository.deactivateUsers(ids);
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
        userRepository.delete(user);
    }

    @Transactional
    public void assignAbonnementToUser(Long userId, Long abonnementId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        Abonnement abonnement = abonnementRepository.findById(abonnementId)
                .orElseThrow(() -> new RuntimeException("Abonnement non trouvé."));

        // Check if the Abonnement is already assigned to a user
        User oldUser = abonnement.getAssignedUser();
        if (oldUser != null) {
            oldUser.getAssignedAbonnements().remove(abonnement);
            userRepository.save(oldUser);
        }

        user.getAssignedAbonnements().add(abonnement);
        abonnement.setAssignedUser(user);

        // Save the new user and the Abonnement to the database
        userRepository.save(user);
        abonnementRepository.save(abonnement);
    }

    @Transactional
    public void removeAbonnementFromUser(Long userId, Long abonnementId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Erreur."));
        Abonnement abonnement = abonnementRepository.findById(abonnementId)
                .orElseThrow(() -> new RuntimeException("Error: Abonnement is not found."));

        if (!user.getAssignedAbonnements().contains(abonnement)) {
            throw new RuntimeException("Error: Abonnement is not assigned to this user.");
        }

        user.getAssignedAbonnements().remove(abonnement);
        abonnement.setAssignedUser(null);

        userRepository.save(user);
        abonnementRepository.save(abonnement);
    }
}