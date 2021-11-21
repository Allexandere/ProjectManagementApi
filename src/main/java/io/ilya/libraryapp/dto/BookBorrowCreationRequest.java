package io.ilya.libraryapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookBorrowCreationRequest {
    private Long borrowedBookId;
    private Long readerId;
    private Date expiryDate;
}

