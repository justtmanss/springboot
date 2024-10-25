package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Author;
import com.example.demo.repository.AuthorRepository;

@Service
public class AuthorService {
    @Autowired
    private AuthorRepository authorRepository;

    // Retrieve all authors
    public List<Author> findAll() {
        return authorRepository.findAll();
    }

    // Find a specific author by ID
    public Optional<Author> findById(Long id) {
        return authorRepository.findById(id);
    }

    // Save a new author or update an existing one
    public Author save(Author author) {
        return authorRepository.save(author);
    }

    // Delete an author by ID
    public void deleteById(Long id) {
        authorRepository.deleteById(id);
    }
}
