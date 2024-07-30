package com.devx.book.book;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class BookRequest {

    Integer id;
    @NotNull(message = "100")
    @NotEmpty(message = "100")
    String title;
    @NotNull(message = "102")
    @NotEmpty(message = "102")
    String authorName;
    @NotNull(message = "103")
    @NotEmpty(message = "103")
    String isbn;
    @NotNull(message = "103")
    @NotEmpty(message = "103")
    String synopsis;
    boolean sharable;
}
