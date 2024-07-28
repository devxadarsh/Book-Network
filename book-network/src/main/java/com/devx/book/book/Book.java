package com.devx.book.book;

import com.devx.book.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
//@EntityListeners(AuditingEntityListener.class) // added in the baseEntity class
public class Book extends BaseEntity {

    // Added the code into the baseEntity class because it common in the book and feedback
    // Here we use the inheritance property to use the repetitive codes

//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private Integer id;

    private String title;

    private String username;

    private String isbn;

    private String synopsys;

    private String bookCover;

    private boolean archived;

    private boolean sharable;

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
