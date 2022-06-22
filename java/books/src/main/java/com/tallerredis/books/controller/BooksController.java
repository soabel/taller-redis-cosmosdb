package com.tallerredis.books.controller;

import com.tallerredis.books.entities.Book;
import com.tallerredis.books.util.IRedisBook;
import com.tallerredis.books.util.RedisBookHelper;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("books")
public class BooksController {


    @GetMapping
    public List<Book> findAll(){
        IRedisBook redisHelper= new RedisBookHelper();
        return redisHelper.findAll();
    }

    @PostMapping
    public void save(@RequestBody Book book){
        System.out.println("call save");
        IRedisBook redisHelper= new RedisBookHelper();
        redisHelper.save(book);
    }

    @GetMapping("{id}")
    public Book findById(@PathVariable Integer id){
       return new Book();
    }
}
