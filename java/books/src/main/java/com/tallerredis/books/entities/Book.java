package com.tallerredis.books.entities;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    private Integer Id;
    private String Name;
    private Integer Year;
    private String Author;
}
