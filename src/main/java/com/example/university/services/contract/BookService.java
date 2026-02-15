package com.example.university.services.contract;

import com.example.university.models.Book;

import java.util.List;

public interface BookService {

    Book createBook(Book book);

    Book findBook(Long id);

    List<Book> getAllBooks();

    Book updateBook(Long id, Book book);

    void deleteBook(Long id);

    List<Book> getBooksByAuthor(String author);
}
