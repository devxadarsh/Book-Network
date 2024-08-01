package com.devx.book.book;

import com.devx.book.common.PageResponse;
import com.devx.book.history.BookTransactionHistory;
import com.devx.book.history.BookTransactionHistoryRepository;
import com.devx.book.user.User;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class BookService {

    private BookRepository bookRepository;
    private BookMapper bookMapper;
    private BookTransactionHistoryRepository bookTransactionHistoryRepository;

    public Integer save(BookRequest request, Authentication connectedUser) {
        User user = ((User) connectedUser.getPrincipal());
        Book book = bookMapper.toBook(request);
        book.setOwner(user);
        return bookRepository.save(book).getId();
    }

    public BookResponse findById(Integer bookId, Authentication connectedUser) {
        User user = ((User) connectedUser.getPrincipal());
        return bookRepository.findById(bookId)
                .map(bookMapper::toBookResponse)
                .orElseThrow( () -> new EntityNotFoundException("No book found with Id: " + bookId));
    }

    public PageResponse<BookResponse> findAllBooks(int page, int size, Authentication connectedUser) {
        User user = ((User) connectedUser.getPrincipal());
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Page<Book> books = bookRepository.findAllDisplayableBooks(pageable, user.getId());
//        List<BookResponse> bookResponses = books.stream()
//                .map(bookMapper::toBookResponse).toList();
//        return new PageResponse<>(
//                bookResponses,
//                books.getNumber(),
//                books.getSize(),
//                books.getTotalElements(),
//                books.getTotalPages(),
//                books.isFirst(),
//                books.isLast()
//        );

//        return bookResponseMapper(books);

        return getPageResponse(books, bookMapper::toBookResponse);
    }

    public PageResponse<BookResponse> findAllBooksByOwner(int page, int size, Authentication connectedUser) {
        User user = ((User) connectedUser.getPrincipal());
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Page<Book> books = bookRepository.findAll(BookSpecification.withOwnerId(user.getId()),pageable);
//        List<BookResponse> bookResponses = books.stream()
//                .map(bookMapper::toBookResponse).toList();
//        return new PageResponse<>(
//                bookResponses,
//                books.getNumber(),
//                books.getSize(),
//                books.getTotalElements(),
//                books.getTotalPages(),
//                books.isFirst(),
//                books.isLast()
//        );

//        return bookResponseMapper(books);

        return getPageResponse(books, bookMapper::toBookResponse);
    }

    public PageResponse<BorrowedBookResponse> findAllBorrowedBooks(int page, int size, Authentication connectedUser) {

        User user = ((User) connectedUser.getPrincipal());
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdBy").descending());
        Page<BookTransactionHistory> allBorrowedBooks = bookTransactionHistoryRepository.findAllBorrowedBooks(user.getBooks(), pageable);
//        List<BorrowedBookResponse> bookResponses = allBorrowedBooks.stream()
//                .map(bookMapper::toBorrowedBookResponse).toList();
//        return new PageResponse<>(
//                bookResponses,
//                allBorrowedBooks.getNumber(),
//                allBorrowedBooks.getSize(),
//                allBorrowedBooks.getTotalElements(),
//                allBorrowedBooks.getTotalPages(),
//                allBorrowedBooks.isFirst(),
//                allBorrowedBooks.isLast()
//        );

//        return BorrowedBookResponseMapper(allBorrowedBooks);

        return getPageResponse(allBorrowedBooks, bookMapper::toBorrowedBookResponse);

    }

    public PageResponse<BorrowedBookResponse> findAllReturnedBooks(int page, int size, Authentication connectedUser) {
        User user = ((User) connectedUser.getPrincipal());
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdBy").descending());
        Page<BookTransactionHistory> allBorrowedBooks = bookTransactionHistoryRepository.findAllReturnedBooks(user.getBooks(), pageable);
//        List<BorrowedBookResponse> bookResponses = allBorrowedBooks.stream()
//                .map(bookMapper::toBorrowedBookResponse).toList();
//        return new PageResponse<>(
//                bookResponses,
//                allBorrowedBooks.getNumber(),
//                allBorrowedBooks.getSize(),
//                allBorrowedBooks.getTotalElements(),
//                allBorrowedBooks.getTotalPages(),
//                allBorrowedBooks.isFirst(),
//                allBorrowedBooks.isLast()
//        );

//        return BorrowedBookResponseMapper(allBorrowedBooks);

        return getPageResponse(allBorrowedBooks, bookMapper::toBorrowedBookResponse);
    }


//    public PageResponse<BookResponse> bookResponseMapper(Page<Book> books) {
//        List<BookResponse> bookResponses = books.stream()
//                .map(bookMapper::toBookResponse).toList();
//        return new PageResponse<>(
//                bookResponses,
//                books.getNumber(),
//                books.getSize(),
//                books.getTotalElements(),
//                books.getTotalPages(),
//                books.isFirst(),
//                books.isLast()
//        );
//    }
//
//    public PageResponse<BorrowedBookResponse> BorrowedBookResponseMapper(Page<BookTransactionHistory> books) {
//        List<BorrowedBookResponse> bookResponses = books.stream()
//                .map(bookMapper::toBorrowedBookResponse).toList();
//        return new PageResponse<>(
//                bookResponses,
//                books.getNumber(),
//                books.getSize(),
//                books.getTotalElements(),
//                books.getTotalPages(),
//                books.isFirst(),
//                books.isLast()
//        );
//    }

    /**
     * Transforms a Page of elements of type T into a PageResponse of elements of type R using the provided mapper function.
     *
     * @param <T> the type of elements in the input Page
     * @param <R> the type of elements in the output PageResponse
     * @param page the input Page containing elements of type T
     * @param mapper a function that converts an element of type T to an element of type R
     * @return a PageResponse containing the transformed elements of type R along with pagination metadata
     */
    private <T, R> PageResponse<R> getPageResponse(Page<T> page, Function<T, R> mapper) {
        List<R> responses = page.stream()
                .map(mapper)
                .toList();
        return new PageResponse<>(
                responses,
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isFirst(),
                page.isLast()
        );
    }
}
