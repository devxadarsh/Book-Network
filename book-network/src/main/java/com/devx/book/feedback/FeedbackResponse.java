package com.devx.book.feedback;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FeedbackResponse {

    private double notes;
    private String comment;
    private boolean ownFeedback;
}
