package com.example.university.services.contract;

import com.example.university.models.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookService {

    Book createBook(Book book);

    Book findBook(Long id);

    Page<Book> getAllBooks(Pageable pageable);

    Book updateBook(Long id, Book book);

    void deleteBook(Long id);

    Page<Book> getBooksByAuthor(String author, Pageable pageable);
}
