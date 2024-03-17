package com.freedomofdev.parcinformatique.controller;

import com.freedomofdev.parcinformatique.entity.Categorie;
import com.freedomofdev.parcinformatique.service.CategorieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials="true")
@RequestMapping("/api/categories")
public class CategorieController {

    private final CategorieService categorieService;

    @Autowired
    public CategorieController(CategorieService categorieService) {
        this.categorieService = categorieService;
    }

    @GetMapping
    public ResponseEntity<List<Categorie>> getAllCategories() {
        List<Categorie> categories = categorieService.getAllCategories();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Categorie> getCategoryById(@PathVariable Long id) {
        Categorie categorie = categorieService.getCategoryById(id);
        if (categorie != null) {
            return new ResponseEntity<>(categorie, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Categorie> createCategory(@RequestBody Categorie categorie) {
        Categorie createdCategorie = categorieService.createCategory(categorie);
        return new ResponseEntity<>(createdCategorie, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Categorie> updateCategory(@PathVariable Long id, @RequestBody Categorie categorie) {
        categorie.setId(id);
        Categorie updatedCategorie = categorieService.updateCategory(categorie);
        if (updatedCategorie != null) {
            return new ResponseEntity<>(updatedCategorie, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categorieService.deleteCategory(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

