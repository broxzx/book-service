package com.project.booksocialnetwork.books;

import com.project.booksocialnetwork.books.data.Book;
import com.project.booksocialnetwork.books.data.dto.BookRequest;
import com.project.booksocialnetwork.books.services.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @PostMapping("/saveBook")
    public ResponseEntity<Book> saveBookToUser(@RequestBody BookRequest bookRequest,
                                               @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        return ResponseEntity.ok(bookService.saveBookToUser(bookRequest, authorizationHeader));
    }

}
