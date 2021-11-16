package io.ilya.libraryapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookCreationRequest {
    private String title;
    private String author;
    private Integer year;
    private Integer pagesAmount;
    private Long publisherId;
}

