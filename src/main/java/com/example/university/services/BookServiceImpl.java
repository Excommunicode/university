package com.example.university.services;

import com.example.university.exception.ResourceNotFoundException;
import com.example.university.models.Book;
import com.example.university.repositories.BookRepository;
import com.example.university.services.contract.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Override
    @Transactional
    public Book createBook(Book book) {
        log.info("createBook called: title='{}', author='{}'", book.getTitle(), book.getAuthor());
        return bookRepository.save(book);
    }

    @Override
    public Book findBook(Long id) {
        log.info("findBook called: id={}", id);
        return bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));
    }

    @Override
    public Page<Book> getAllBooks(Pageable pageable) {
        log.info("getAllBooks called: page={}, size={}", pageable.getPageNumber(), pageable.getPageSize());
        return bookRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public Book updateBook(Long id, Book book) {
        log.info("updateBook called: id={}, title='{}'", id, book.getTitle());
        Book existing = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));
        existing.setTitle(book.getTitle());
        existing.setAuthor(book.getAuthor());
        existing.setYear(book.getYear());
        return bookRepository.save(existing);
    }

    @Override
    @Transactional
    public void deleteBook(Long id) {
        log.info("deleteBook called: id={}", id);
        if (!bookRepository.existsById(id)) {
            throw new ResourceNotFoundException("Book not found with id: " + id);
        }
        bookRepository.deleteById(id);
    }

    @Override
    public Page<Book> getBooksByAuthor(String author, Pageable pageable) {
        log.info("getBooksByAuthor called: author='{}', page={}, size={}", author, pageable.getPageNumber(), pageable.getPageSize());
        return bookRepository.findByAuthor(author, pageable);
    }
}
