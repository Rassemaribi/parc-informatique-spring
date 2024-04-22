package com.freedomofdev.parcinformatique.controller;

import com.freedomofdev.parcinformatique.entity.Role;
import com.freedomofdev.parcinformatique.entity.User;
import com.freedomofdev.parcinformatique.enums.AppRole;
import com.freedomofdev.parcinformatique.payload.request.LoginRequest;
import com.freedomofdev.parcinformatique.payload.request.SignupRequest;
import com.freedomofdev.parcinformatique.payload.response.MessageResponse;
import com.freedomofdev.parcinformatique.payload.response.UserInfoResponse;
import com.freedomofdev.parcinformatique.repository.RoleRepository;
import com.freedomofdev.parcinformatique.repository.UserRepository;
import com.freedomofdev.parcinformatique.security.jwt.JwtUtils;
import com.freedomofdev.parcinformatique.security.services.UserDetailsImpl;
import com.freedomofdev.parcinformatique.service.MailService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials = "true")
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    private MailService mailService;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);

        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .body(new UserInfoResponse(userDetails.getId(),
                        userDetails.getUsername(),
                        userDetails.getEmail(),
                        userDetails.getNom(),
                        userDetails.getPrenom(),
                        userDetails.getNumeroTelephone(),
                        roles));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Erreur: username deja utilisé!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Erreur: email deja utilisé!"));
        }

        // Create new user's account
        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()),
                signUpRequest.getNom(),
                signUpRequest.getPrenom(),
                signUpRequest.getNumeroTelephone());

        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role collaborateurRole = roleRepository.findByName(AppRole.ROLE_COLLABORATEUR)
                    .orElseThrow(() -> new RuntimeException("Erreur: Role non trouvé."));
            roles.add(collaborateurRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "DSI":
                        Role DSIRole = roleRepository.findByName(AppRole.ROLE_DSI)
                                .orElseThrow(() -> new RuntimeException("Erreur: Role non trouvé."));
                        roles.add(DSIRole);

                        break;
                    default:
                        Role userRole = roleRepository.findByName(AppRole.ROLE_COLLABORATEUR)
                                .orElseThrow(() -> new RuntimeException("Erreur: Role non trouvé."));
                        roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);
        userRepository.save(user);

        User userwithpassword = user;
        user.setPassword(signUpRequest.getPassword());

        mailService.sendConfirmationEmail(userwithpassword);
        return ResponseEntity.ok(new MessageResponse("Utilisateur enregistré avec succès!"));
    }

    @PostMapping("/signout")
    public ResponseEntity<?> logoutUser() {
        ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(new MessageResponse("Déconnexion réussie!"));
    }
}