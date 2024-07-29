package com.devx.book.book;

import com.devx.book.common.BaseEntity;
import com.devx.book.feedback.Feedback;
import com.devx.book.history.BookTransactionHistory;
import com.devx.book.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
//@EntityListeners(AuditingEntityListener.class) // added in the baseEntity class
public class Book extends BaseEntity {

    private String title;
    private String username;
    private String isbn;
    private String synopsys;
    private String bookCover;
    private boolean archived;
    private boolean sharable;

    @ManyToOne
    @JoinColumn(name = "owner_id") //it is not mandatory in each case,
    // by default, it managed by jpa on the basis of the variable, but here I am using
    private User owner;

    @OneToMany(mappedBy = "book")
    private List<Feedback> feedbacks;

    @OneToMany(mappedBy = "book")
    private List<BookTransactionHistory> histories;

    // Added the code into the baseEntity class because it common in the book and feedback
    // Here we use the inheritance property to use the repetitive codes

//    @CreatedDate
//    @Column(nullable = false, updatable = false)
//    private LocalDateTime createdDate;
//
//    @LastModifiedDate
//    @Column(insertable = false)
//    private LocalDateTime lastModifiedDate;
//
//    @CreatedBy
//    @Column(nullable = false, updatable = false)
//    private String createdBy;
//
//    @LastModifiedBy
//    @Column(insertable = false)
//    private String lastModifiedBy;

}
