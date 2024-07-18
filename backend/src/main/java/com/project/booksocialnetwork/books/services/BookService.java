package com.project.booksocialnetwork.books.services;

import com.project.booksocialnetwork.books.data.Book;
import com.project.booksocialnetwork.books.data.dto.BookRequest;
import com.project.booksocialnetwork.booktransaction.data.BookTransactionHistory;
import com.project.booksocialnetwork.booktransaction.services.BookTransactionHistoryRepository;
import com.project.booksocialnetwork.users.data.User;
import com.project.booksocialnetwork.users.services.UserService;
import com.project.booksocialnetwork.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final UserService userService;
    private final JwtUtils jwtUtils;
    private final ModelMapper modelMapper;
    private final BookTransactionHistoryRepository transactionHistoryRepository;


    public Book saveBookToUser(BookRequest bookRequest, String authorizationHeader) {
        User obtainedUser = userService.getUserByEmail(jwtUtils.getEmailFromJwtToken(jwtUtils.extractTokenFromAuthorizationHeader(authorizationHeader)));

        Book mappedBook = modelMapper.map(bookRequest, Book.class);
        mappedBook.setOwnerId(obtainedUser.getId());
        mappedBook.setCreatedBy(obtainedUser.getId());
        mappedBook.setLastModifiedBy(obtainedUser.getId());

        bookRepository.save(mappedBook);

        return mappedBook;
    }

    public Book getBookById(String bookId) {
        return bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("book with id %s is not found".formatted(bookId)));
    }

    public Page<Book> getAllUserBooks(int page, int size, String authorizationHeader) {
        User obtainedUser = userService.getUserByEmail(jwtUtils.getEmailFromJwtToken(jwtUtils.extractTokenFromAuthorizationHeader(authorizationHeader)));
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());

        return bookRepository.getDisplayedBooks(pageable);
    }

    public Page<Book> getAllOwnerBooks(int page, int size, String authorizationHeader) {
        User obtainedUser = userService.getUserByEmail(jwtUtils.getEmailFromJwtToken(jwtUtils.extractTokenFromAuthorizationHeader(authorizationHeader)));
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());

        return bookRepository.getAllOwnerBooks(pageable, obtainedUser.getId());
    }

    public Page<Book> getAllBorrowedBooks(int page, int size, String authorizationHeader) {
        User obtainedUser = userService.getUserByEmail(jwtUtils.getEmailFromJwtToken(jwtUtils.extractTokenFromAuthorizationHeader(authorizationHeader)));
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());

        Page<BookTransactionHistory> borrowedBooks = transactionHistoryRepository.findAllBorrowedBooks(pageable, obtainedUser.getId());

        List<String> borrowedBooksIds = borrowedBooks
                .stream()
                .map(BookTransactionHistory::getBookId)
                .toList();

       return bookRepository.findByIdIn(pageable, borrowedBooksIds);
    }
}
