package com.devx.book.history;

import com.devx.book.book.Book;
import com.devx.book.common.BaseEntity;
import com.devx.book.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

// It is an association table between the User and the Book entity. As we now that one user can borrow many books
// and one book can be borrowed by many users. So it will be many-to-many relations, and we can do it directly using the many-to-many annotation.
// Since we need some extra things(columns) so we are using this class to define the relationship between the User and Book entity to keep trac of books;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class BookTransactionHistory extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    private boolean returned;
    private boolean returnApproved;

}
