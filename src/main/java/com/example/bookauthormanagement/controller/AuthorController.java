package com.example.demo.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.Author;
import com.example.demo.repository.AuthorRepository;

@RestController
@RequestMapping("/api/authors")
public class AuthorController {

    private static final Logger logger = LoggerFactory.getLogger(AuthorController.class);

    @Autowired
    private AuthorRepository authorRepository;

    // Get all authors
    @GetMapping
    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    // Get a specific author by ID
    @GetMapping("/{id}")
    public ResponseEntity<Author> getAuthorById(@PathVariable Long id) {
        return authorRepository.findById(id)
                .map(author -> ResponseEntity.ok().body(author))
                .orElse(ResponseEntity.notFound().build());
    }

    // Create a new author
    @PostMapping
    public Author createAuthor(@RequestBody Author author) {
        try {
            logger.info("Creating author: {}", author);
            return authorRepository.save(author);
        } catch (Exception e) {
            logger.error("Error creating author", e);
            throw e; // Rethrow the exception after logging
        }
    }

    // Update an existing author
    @PutMapping("/{id}")
    public ResponseEntity<Author> updateAuthor(@PathVariable Long id, @RequestBody Author authorDetails) {
        return authorRepository.findById(id)
                .map(author -> {
                    if (authorDetails.getName() != null && !authorDetails.getName().isEmpty()) {
                        author.setName(authorDetails.getName());
                    }
                    if (authorDetails.getNationality() != null && !authorDetails.getNationality().isEmpty()) {
                        author.setNationality(authorDetails.getNationality());
                    }
                    if (authorDetails.getBirthYear() != null) {
                        author.setBirthYear(authorDetails.getBirthYear());
                    }
                    Author updatedAuthor = authorRepository.save(author);
                    return ResponseEntity.ok().body(updatedAuthor);
                }).orElse(ResponseEntity.notFound().build());
    }

    // Delete an author by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteAuthor(@PathVariable Long id) {
        return authorRepository.findById(id)
                .map(author -> {
                    authorRepository.delete(author);
                    return ResponseEntity.ok().build();
                }).orElse(ResponseEntity.notFound().build());
    }
}