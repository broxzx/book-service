package com.project.booksocialnetwork.booktransaction.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "bookstransactions")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class BookTransactionHistory {

    private String id;

    private String ownerId;

    private String bookId;

    private boolean returned;

    private boolean returnedApproved;

}
