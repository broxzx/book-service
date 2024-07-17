package com.project.booksocialnetwork.books.services;

import com.project.booksocialnetwork.books.data.Book;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BookRepository extends MongoRepository<Book    , String> {
}
