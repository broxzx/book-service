package com.project.booksocialnetwork.books.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "books")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Book {

    private String id;

    private String title;

    private String authorName;

    private String isbn;

    private String synopsis;

    private String bookCover;

    @Builder.Default
    private boolean archived = false;

    @Builder.Default
    private boolean shareable = false;

    @Builder.Default
    private LocalDateTime createdDate = LocalDateTime.now();

    @Builder.Default
    private LocalDateTime lastModifiedDate = LocalDateTime.now();

    private String createdBy;

    private String lastModifiedBy;

    private String ownerId;

}
