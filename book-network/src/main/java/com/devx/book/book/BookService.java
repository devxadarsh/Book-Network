package com.devx.book.book;

import com.devx.book.file.FileStorageService;
import com.devx.book.common.PageResponse;
import com.devx.book.exception.OperationNotPermittedException;
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
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final BookTransactionHistoryRepository bookTransactionHistoryRepository;
    private final FileStorageService fileStorageService;

    public Integer save(BookRequest request, Authentication connectedUser) {
        User user = ((User) connectedUser.getPrincipal());
        Book book = bookMapper.toBook(request);
        book.setOwner(user);
        return bookRepository.save(book).getId();
    }

//    public BookResponse findById(Integer bookId, Authentication connectedUser) {
    public BookResponse findById(Integer bookId) {
//        User user = ((User) connectedUser.getPrincipal());
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
        Page<BookTransactionHistory> allBorrowedBooks = bookTransactionHistoryRepository.findAllBorrowedBooks(user.getId(), pageable);
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
        Page<BookTransactionHistory> allBorrowedBooks = bookTransactionHistoryRepository.findAllReturnedBooks(user.getId(), pageable);
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

    /**
     *
     * @param bookId
     * @param connectedUser
     * @return
     */
    public Map<Book, User> bookUserMapChecker(Integer bookId, Authentication connectedUser) {
        Book book = bookRepository.findById(bookId).orElseThrow(
                () -> new EntityNotFoundException("Book not found with id: " + bookId)
        );
        User user = ((User) connectedUser.getPrincipal());
        Map<Book, User> bookUserMap = new HashMap<>();
        bookUserMap.put(book, user);
        return bookUserMap;
    }



    public Integer updateSharableStatus(Integer bookId, Authentication connectedUser) {
        Book book = bookRepository.findById(bookId).orElseThrow(
                () -> new EntityNotFoundException("Book not found with id: " + bookId)
        );
        User user = ((User) connectedUser.getPrincipal());
        if (!Objects.equals(book.getOwner().getId(), user.getId())){
            throw new OperationNotPermittedException("You can not update the other's book status");
        }
        book.setSharable(!book.isSharable());
        return bookRepository.save(book).getId();
    }

    public Integer updateArchivedStatus(Integer bookId, Authentication connectedUser) {
        Book book = bookRepository.findById(bookId).orElseThrow(
                () -> new EntityNotFoundException("Book not found with id: " + bookId)
        );
        User user = ((User) connectedUser.getPrincipal());
        if (!Objects.equals(book.getOwner().getId(), user.getId())) {
            throw new OperationNotPermittedException("You can not archive other's own book");
        }
        book.setArchived(!book.isArchived());
        return bookRepository.save(book).getId();
    }

    public Integer borrowBook(Integer bookId, Authentication connectedUser) {
        Book book = bookRepository.findById(bookId).orElseThrow(
                () -> new EntityNotFoundException("Book not found with id: " + bookId)
        );
        User user = ((User) connectedUser.getPrincipal());
        if(Objects.equals(book.getOwner().getId(), user.getId())){
            throw new OperationNotPermittedException("You can not borrow your own book");
        }
        final boolean isAlreadyBorrowedByUser = bookTransactionHistoryRepository.isAlreadyBorrowedByUser(bookId, user.getId());
        if (isAlreadyBorrowedByUser) {
            throw new OperationNotPermittedException(("The requested book is already borrowed"));
        }
        BookTransactionHistory bookTransactionHistory = BookTransactionHistory
                .builder()
                .user(user)
                .book(book)
                .returnApproved(false)
                .returned(false)
                .build();
        return bookTransactionHistoryRepository.save(bookTransactionHistory).getId();
    }



    public Integer returnBorrowedBook(Integer bookId, Authentication connectedUser) {
        Book book = bookRepository.findById(bookId).orElseThrow(
                () -> new EntityNotFoundException("Book not found with id: " + bookId)
        );
        User user = ((User) connectedUser.getPrincipal());
        if (book.isArchived() || !book.isSharable()) {
            throw new OperationNotPermittedException("The requested book is archived or not shareable");
        }
        if (Objects.equals(book.getOwner().getId(), user.getId())) {
            throw new OperationNotPermittedException("You can not return your own book");
        }
        BookTransactionHistory bookTransactionHistory = bookTransactionHistoryRepository.findByBookIdAndUserId(bookId, user.getId())
                .orElseThrow(
                        () -> new OperationNotPermittedException("You did not borrowed this book")
                );
        bookTransactionHistory.setReturned(true);
        return bookTransactionHistoryRepository.save(bookTransactionHistory).getId();
    }

    public Integer approveReturnBorrowedBook(Integer bookId, Authentication connectedUser) {
        Book book = bookRepository.findById(bookId).orElseThrow(
                () -> new EntityNotFoundException("Book not found with id: " + bookId)
        );
        User user = ((User) connectedUser.getPrincipal());
        if (!Objects.equals(book.getOwner().getId(), user.getId())) {
            throw new OperationNotPermittedException("You can not approve the return of a book you do not own");
        }
        BookTransactionHistory bookTransactionHistory = bookTransactionHistoryRepository.findByBookIdAndOwnerId(bookId, user.getId())
                .orElseThrow(
                        () -> new OperationNotPermittedException("The book is not returned yet, you can not approve its return")
                );
        bookTransactionHistory.setReturnApproved(true);
        return bookTransactionHistoryRepository.save(bookTransactionHistory).getId();
    }

    public void uploadBookCoverPicture(MultipartFile file, Authentication connectedUser, Integer bookId) {
        Book book = bookRepository.findById(bookId).orElseThrow(
                () -> new EntityNotFoundException("Book not found with id: "+bookId)
        );
        User user = ((User) connectedUser.getPrincipal());
        var profilePicture = fileStorageService.saveFile(file, bookId, user.getId());
        book.setBookCover(profilePicture);
        bookRepository.save(book);
    }
}
