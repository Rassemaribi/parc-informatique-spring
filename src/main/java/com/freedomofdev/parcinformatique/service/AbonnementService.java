package com.freedomofdev.parcinformatique.service;

import com.freedomofdev.parcinformatique.entity.Abonnement;
import com.freedomofdev.parcinformatique.repository.AbonnementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
public class AbonnementService {

    private final AbonnementRepository abonnementRepository;

    @Value("${encryption.secret.key}")
    private String secretKey;

    @Autowired
    public AbonnementService(AbonnementRepository abonnementRepository) {
        this.abonnementRepository = abonnementRepository;
    }

    @Transactional(readOnly = true)
    public List<Abonnement> getAllAbonnements() {
        List<Abonnement> abonnements = abonnementRepository.findAll();
        abonnements.forEach(this::decryptCle);
        return abonnements;
    }

    @Transactional(readOnly = true)
    public Abonnement getAbonnementById(Long id) {
        Abonnement abonnement = abonnementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Abonnement non trouvé avec id: " + id));
        decryptCle(abonnement);
        return abonnement;
    }

    @Transactional
    public Abonnement createAbonnement(Abonnement abonnement) {
        encryptCle(abonnement);
        return abonnementRepository.save(abonnement);
    }

    @Transactional
    public Abonnement updateAbonnement(Abonnement updatedAbonnement) {
        Abonnement existingAbonnement = abonnementRepository.findById(updatedAbonnement.getId())
                .orElseThrow(() -> new RuntimeException("Abonnement non trouvé avec id: " + updatedAbonnement.getId()));
        existingAbonnement.setNom(updatedAbonnement.getNom());
        existingAbonnement.setCle(updatedAbonnement.getCle());
        existingAbonnement.setFournisseur(updatedAbonnement.getFournisseur());
        existingAbonnement.setDateDebut(updatedAbonnement.getDateDebut());
        existingAbonnement.setDateFin(updatedAbonnement.getDateFin());
        existingAbonnement.setAssignedUser(updatedAbonnement.getAssignedUser());
        encryptCle(existingAbonnement);
        return abonnementRepository.save(existingAbonnement);
    }

    @Transactional
    public void deleteAbonnement(Long id) {
        abonnementRepository.deleteById(id);
    }

    private void encryptCle(Abonnement abonnement) {
        try {
            SecretKeySpec keySpec = new SecretKeySpec(secretKey.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, keySpec);
            String cle = abonnement.getCle();
            byte[] encrypted = cipher.doFinal(cle.getBytes());
            String encryptedCle = Base64.getEncoder().encodeToString(encrypted);
            abonnement.setCle(encryptedCle);
        } catch (Exception e) {
            throw new RuntimeException("Error while encrypting cle", e);
        }
    }

    private void decryptCle(Abonnement abonnement) {
        try {
            SecretKeySpec keySpec = new SecretKeySpec(secretKey.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, keySpec);
            String encryptedCle = abonnement.getCle();
            byte[] decoded = Base64.getDecoder().decode(encryptedCle);
            String decryptedCle = new String(cipher.doFinal(decoded));
            abonnement.setCle(decryptedCle);
        } catch (Exception e) {
            throw new RuntimeException("Error while decrypting cle", e);
        }
    }
}