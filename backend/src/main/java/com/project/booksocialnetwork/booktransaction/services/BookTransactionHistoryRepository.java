package com.project.booksocialnetwork.booktransaction.services;

import com.project.booksocialnetwork.booktransaction.data.BookTransactionHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface BookTransactionHistoryRepository extends MongoRepository<BookTransactionHistory, String> {

    @Query("{ $and: [ {ownerId:  ?0}, {returned: false} ] }")
    Page<BookTransactionHistory> findAllBorrowedBooks(Pageable pageable, String id);

}
