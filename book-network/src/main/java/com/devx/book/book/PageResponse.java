package com.devx.book.book;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageResponse<T> {

    private List<T> content;
    private int number;
    private int size;
    private long totalElement;
    private int totalPage;
    private boolean first;
    private boolean last;
}
