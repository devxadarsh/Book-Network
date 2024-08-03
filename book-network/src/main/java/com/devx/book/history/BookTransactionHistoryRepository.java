package com.devx.book.history;

import com.devx.book.book.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookTransactionHistoryRepository extends JpaRepository<BookTransactionHistory, Integer> {

    @Query("""
            select history
            form BookTransactionHistory history
            where history.id = :userId
            
            """)
    Page<BookTransactionHistory> findAllBorrowedBooks(List<Book> books, Pageable pageable);

    @Query("""
            select history
            form BookTransactionHistory history
            where history.id = :userId
            
            """)
    Page<BookTransactionHistory> findAllReturnedBooks(List<Book> books, Pageable pageable);

    @Query("""
            select (count (*) > 0) as isBorrowed
            from BookTransactionHistory history
            where history.book.id :bookId
            and history.user.id :userId
            and history.returnedApproved :false
            """)
    boolean isAlreadyBorrowedByUser(Integer bookId, Integer id);

    BookTransactionHistory findByBookIdAndUserId(Integer bookId, Integer id);
}
