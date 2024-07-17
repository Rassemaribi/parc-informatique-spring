package com.freedomofdev.parcinformatique.controller;

import com.freedomofdev.parcinformatique.entity.Categorie;
import com.freedomofdev.parcinformatique.service.CategorieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "https://parcinformatiquefodservicess.azurewebsites.net", maxAge = 3600, allowCredentials = "true")
@RequestMapping("/api/categories")
public class CategorieController {

    private final CategorieService categorieService;

    @Autowired
    public CategorieController(CategorieService categorieService) {
        this.categorieService = categorieService;
    }

    @PreAuthorize("hasAuthority(@dsiGroupId)")
    @GetMapping
    public ResponseEntity<List<Categorie>> getAllCategories() {
        List<Categorie> categories = categorieService.getAllCategories();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority(@dsiGroupId)")
    @GetMapping("/{id}")
    public ResponseEntity<Categorie> getCategoryById(@PathVariable Long id) {
        Categorie categorie = categorieService.getCategoryById(id);
        return new ResponseEntity<>(categorie, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority(@dsiGroupId)")
    @PostMapping
    public ResponseEntity<Categorie> createCategory(@RequestBody Categorie categorie) {
        Categorie createdCategorie = categorieService.createCategory(categorie);
        return new ResponseEntity<>(createdCategorie, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority(@dsiGroupId)")
    @PutMapping("/{id}")
    public ResponseEntity<Categorie> updateCategory(@PathVariable Long id, @RequestBody Categorie categorie) {
        categorie.setId(id);
        Categorie updatedCategorie = categorieService.updateCategory(categorie);
        return new ResponseEntity<>(updatedCategorie, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority(@dsiGroupId)")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categorieService.deleteCategory(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}