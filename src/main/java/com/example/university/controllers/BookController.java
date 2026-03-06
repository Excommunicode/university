package com.example.university.controllers;

import com.example.university.dto.BookCreateDto;
import com.example.university.models.Book;
import com.example.university.services.contract.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @PostMapping
    public ResponseEntity<Book> createBook(@Valid @RequestBody BookCreateDto dto) {
        Book book = new Book();
        book.setTitle(dto.getTitle());
        book.setAuthor(dto.getAuthor());
        book.setYear(dto.getYear());
        return ResponseEntity.status(HttpStatus.CREATED).body(bookService.createBook(book));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> findBook(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.findBook(id));
    }

    @GetMapping
    public ResponseEntity<Page<Book>> getAllBooks(
            @PageableDefault(size = 20, sort = "id") Pageable pageable) {
        return ResponseEntity.ok(bookService.getAllBooks(pageable));
    }

    @GetMapping("/author/{author}")
    public ResponseEntity<Page<Book>> getBooksByAuthor(
            @PathVariable String author,
            @PageableDefault(size = 20, sort = "id") Pageable pageable) {
        return ResponseEntity.ok(bookService.getBooksByAuthor(author, pageable));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id,
                                           @Valid @RequestBody BookCreateDto dto) {
        Book book = new Book();
        book.setTitle(dto.getTitle());
        book.setAuthor(dto.getAuthor());
        book.setYear(dto.getYear());
        return ResponseEntity.ok(bookService.updateBook(id, book));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }
}
