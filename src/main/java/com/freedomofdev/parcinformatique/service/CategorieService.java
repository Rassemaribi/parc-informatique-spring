package com.freedomofdev.parcinformatique.service;

import com.freedomofdev.parcinformatique.entity.Categorie;
import com.freedomofdev.parcinformatique.exception.BusinessException;
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
        List<Categorie> categories = categorieRepository.findAll();
        if (categories == null || categories.isEmpty()) {
            throw new BusinessException("No Categories found");
        }
        return categories;
    }

    @Transactional(readOnly = true)
    public Categorie getCategoryById(Long id) {
        Optional<Categorie> optionalCategorie = categorieRepository.findById(id);
        return optionalCategorie.orElseThrow(() -> new BusinessException("Category not found with id: " + id));
    }

    @Transactional
    public Categorie createCategory(Categorie categorie) {
        Categorie createdCategorie = categorieRepository.save(categorie);
        if (createdCategorie == null) {
            throw new BusinessException("Failed to create Category");
        }
        return createdCategorie;
    }

    @Transactional
    public Categorie updateCategory(Categorie categorie) {
        Categorie updatedCategorie = categorieRepository.save(categorie);
        if (updatedCategorie == null) {
            throw new BusinessException("Failed to update Category with id: " + categorie.getId());
        }
        return updatedCategorie;
    }

    @Transactional
    public void deleteCategory(Long id) {
        if (!categorieRepository.existsById(id)) {
            throw new BusinessException("Cannot delete. Category not found with id: " + id);
        }
        categorieRepository.deleteById(id);
    }
}