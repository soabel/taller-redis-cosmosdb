package com.tallerredis.books.util;

import com.tallerredis.books.entities.Book;
import redis.clients.jedis.JedisPubSub;

import java.util.List;

public interface IRedisBook {
    boolean save(Book book);
    Book findById(Integer id);
    List<Book> findAll();
    Book getBook(String key);
    void close();
    void subscribe(JedisPubSub jedisPubSub, String channel);
    void expire(Integer id);
}
