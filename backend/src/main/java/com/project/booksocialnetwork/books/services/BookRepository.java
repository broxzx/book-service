package com.project.booksocialnetwork.books.services;

import com.project.booksocialnetwork.books.data.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface BookRepository extends MongoRepository<Book, String> {

    @Query("{ $and: [ {archived: false}, {shareable:  true}  ] }")
    Page<Book> getDisplayedBooks(Pageable pageable);

    @Query("{ $and: [ {archived: false}, {shareable:  true}, {ownerId: ?0}  ] }")
    Page<Book> getAllOwnerBooks(Pageable pageable, String ownerId);
}
