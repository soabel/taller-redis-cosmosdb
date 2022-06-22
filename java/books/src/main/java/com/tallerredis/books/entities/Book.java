package com.tallerredis.books.entities;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Book {
    private Integer Id;
    private String Name;
    private Integer Year;
    private String Author;
}
