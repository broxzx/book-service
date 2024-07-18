package com.project.booksocialnetwork.books;

import com.project.booksocialnetwork.books.data.Book;
import com.project.booksocialnetwork.books.data.dto.BookRequest;
import com.project.booksocialnetwork.books.services.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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

    @GetMapping("/getBook/{bookId}")
    public ResponseEntity<Book> getBookById(@PathVariable("bookId") String bookId) {
        return ResponseEntity.ok(bookService.getBookById(bookId));
    }

    @GetMapping("/getAllBooks")
    public ResponseEntity<Page<Book>> getAllBooks(@RequestParam(required = false, defaultValue = "0") int page,
                                                  @RequestParam(required = false, defaultValue = "10") int size,
                                                  @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        return ResponseEntity.ok(bookService.getAllUserBooks(page, size, authorizationHeader));
    }

    @GetMapping("/getAllOwnerBooks")
    public ResponseEntity<Page<Book>> getAllOwnerBooks(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader
    ) {
        return ResponseEntity.ok(bookService.getAllOwnerBooks(page, size, authorizationHeader));
    }

    @GetMapping("/getBorrowedBooks")
    public ResponseEntity<Page<Book>> getAllBorrowedBooks(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader
    ) {
        return ResponseEntity.ok(bookService.getAllBorrowedBooks(page, size, authorizationHeader));
    }

}
