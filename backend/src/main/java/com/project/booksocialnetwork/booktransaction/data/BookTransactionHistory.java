package com.project.booksocialnetwork.booktransaction.data;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "bookstransactions")
public class BookTransactionHistory {

    private String id;

    private String ownerId;

    private String bookId;

    private boolean returned;

    private boolean returnedApproved;

}
