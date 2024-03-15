package com.freedomofdev.parcinformatique.service;

import com.freedomofdev.parcinformatique.entity.Categorie;
import com.freedomofdev.parcinformatique.repository.CategorieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CategorieService {

    private final CategorieRepository categorieRepository;

    @Autowired
    public CategorieService(CategorieRepository categorieRepository) {
        this.categorieRepository = categorieRepository;
    }

    @Transactional(readOnly = true)
    public List<Categorie> getAllCategories() {
        return categorieRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Categorie getCategoryById(Long id) {
        Optional<Categorie> optionalCategorie = categorieRepository.findById(id);
        return optionalCategorie.orElse(null);
    }

    @Transactional
    public Categorie createCategory(Categorie categorie) {
        return categorieRepository.save(categorie);
    }

    @Transactional
    public Categorie updateCategory(Categorie categorie) {
        return categorieRepository.save(categorie);
    }

    @Transactional
    public void deleteCategory(Long id) {
        categorieRepository.deleteById(id);
    }
}
