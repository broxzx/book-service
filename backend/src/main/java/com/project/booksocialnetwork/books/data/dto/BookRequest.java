package com.project.booksocialnetwork.books.data.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class BookRequest {

    @NotBlank
    private String title;

    @NotBlank
    private String authorName;

    @NotBlank
    private String isbn;

    @NotBlank
    private String synopsis;

    @NotBlank
    private boolean shareable;

}
