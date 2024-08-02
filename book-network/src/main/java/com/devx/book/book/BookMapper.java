package com.devx.book.book;

import com.devx.book.history.BookTransactionHistory;
import org.springframework.stereotype.Service;

@Service
public class BookMapper {

    public Book toBook( BookRequest request ) {
        return Book.builder()
                .id(request.id)
                .title(request.title)
                .authorName(request.authorName)
                .isbn(request.isbn)
                .synopsys(request.synopsis)
                .sharable(request.sharable)
                .build();
    }

    public BookResponse toBookResponse( Book book ) {
        return BookResponse.builder()
                .id(book.getId())
                .title(book.getTitle())
                .authorName(book.getAuthorName())
                .isbn(book.getIsbn())
                .synopsis(book.getSynopsys())
                .rate(book.getRate())
                .archived(book.isArchived())
                .shareable(book.isSharable())
                .owner(book.getOwner().fullName())
//                .cover(FileUtil.reaFileFromLocation(book.getBookCover()))
                .build();
    }

    public BorrowedBookResponse toBorrowedBookResponse(BookTransactionHistory history) {
        return BorrowedBookResponse.builder()
                .id(history.getBook().getId())
                .title(history.getBook().getTitle())
                .authorName(history.getBook().getAuthorName())
                .isbn(history.getBook().getIsbn())
                .rate(history.getBook().getRate())
                .returned(history.isReturned())
                .returnApproved(history.isReturnApproved())
                .build();
    }
}