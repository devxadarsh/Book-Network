package com.devx.book.feedback;

import com.devx.book.book.Book;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class FeedbackMapper {

    public static Feedback toFeedback(FeedbackRequest request) {

        return Feedback.builder()
                .note(request.note())
                .comment(request.comment())
                .book(Book.builder()
                        .id(request.bookId())
                        .sharable(false) // Not required to write false and has no impact :: just to satisfy lombok
                        .archived(false) // Not required to write false and has no impact :: just to satisfy lombok
                        .build())
                .build();
    }


    public FeedbackResponse toFeedbackResponse(Feedback feedback, Integer userId) {

        return FeedbackResponse.builder()
                .notes(feedback.getNote())
                .comment(feedback.getComment())
                .ownFeedback(Objects.equals(feedback.getCreatedBy(), userId))
                .build();
    }
}
