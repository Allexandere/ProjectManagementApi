package io.ilya.libraryapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookCopyCreationRequest {
    private String ISBN;
    private String position;
    private Long originalId;
}

