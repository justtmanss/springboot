package com.example.demo.controller;

import com.example.demo.model.Book;
import com.example.demo.model.Author;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @GetMapping
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        return bookRepository.findById(id)
                .map(book -> ResponseEntity.ok().body(book))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Book createBook(@RequestBody Book book) {
        return bookRepository.save(book);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book bookDetails) {
        return bookRepository.findById(id).map(book -> {
            book.setTitle(bookDetails.getTitle());
            book.setIsbn(bookDetails.getIsbn());
            book.setPubyear(bookDetails.getPubyear());
            book.setCopies(bookDetails.getCopies());

            // Set the author if provided
            if (bookDetails.getAuthor() != null) {
                book.setAuthor(bookDetails.getAuthor());
            }

            // Update author name from the associated author object
            String authorName = bookDetails.getAuthor() != null ? bookDetails.getAuthor().getName() : "Unknown";
            System.out.println("Author name: " + authorName);

            Book updatedBook = bookRepository.save(book);
            return ResponseEntity.ok(updatedBook);
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        return bookRepository.findById(id).map(book -> {
            bookRepository.delete(book);
            return ResponseEntity.ok().<Void>build();
        }).orElse(ResponseEntity.notFound().build());
    }
}
