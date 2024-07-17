package com.project.booksocialnetwork.books.services;

import com.project.booksocialnetwork.books.data.Book;
import com.project.booksocialnetwork.books.data.dto.BookRequest;
import com.project.booksocialnetwork.users.data.User;
import com.project.booksocialnetwork.users.services.UserService;
import com.project.booksocialnetwork.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final UserService userService;
    private final JwtUtils jwtUtils;
    private final ModelMapper modelMapper;


    public Book saveBookToUser(BookRequest bookRequest, String authorizationHeader) {
        User obtainedUser = userService.getUserByEmail(jwtUtils.getEmailFromJwtToken(jwtUtils.extractTokenFromAuthorizationHeader(authorizationHeader)));

        Book mappedBook = modelMapper.map(bookRequest, Book.class);
        mappedBook.setOwnerId(obtainedUser.getId());
        mappedBook.setCreatedBy(obtainedUser.getId());
        mappedBook.setLastModifiedBy(obtainedUser.getId());

        bookRepository.save(mappedBook);

        return mappedBook;
    }
}
