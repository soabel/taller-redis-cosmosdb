package com.tallerredis.books.util;

import com.tallerredis.books.entities.Book;
import org.springframework.beans.factory.annotation.Value;
import redis.clients.jedis.DefaultJedisClientConfig;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

import java.util.ArrayList;
import java.util.List;

public class RedisBookHelper implements IRedisBook {

    private String resource="book";
    private String  channel=resource + "Channel";
    private boolean useSsl = true;
    private String cacheHostname= "redissession4.redis.cache.windows.net";
    private String cachekey = "so49HnTEuD0pBA9UzxETTIZXhcgEVQIzMAzCaFwQdow=";
    private Integer port=6380;

    private Jedis redisClient;

    public RedisBookHelper(){
        System.out.println("cacheHostname = " + cacheHostname);
        this.redisClient = new Jedis(cacheHostname, this.port, DefaultJedisClientConfig.builder()
                .password(cachekey)
                .ssl(useSsl)
                .build());
    }

    @Override
    public boolean save(Book book){
        try{
            String key=this.resource + ":" + book.getId();
            redisClient.hset(key, "id",book.getId().toString());
            redisClient.hset(key, "name",book.getName());
            redisClient.hset(key, "year",book.getYear().toString());
            redisClient.hset(key, "author",book.getAuthor());

//            redisClient.publish(this.channel,"save " + key );

            return true;
        }
        catch(Exception e){

        }
        return false;
    }

    @Override
    public Book findById(Integer id){
        try{
            String key=this.resource + ":" + id.toString();
//            redisClient.publish(this.channel,"findById " + key );
            return this.getBook(key);
        }
        catch(Exception e){

        }
        return null;
    }

    public Book getBook(String key) {
        var hash = redisClient.hgetAll(key);
        Book book = new Book();
        book.setId(Integer.parseInt(hash.get("id")));
        book.setName(hash.get("name"));
        book.setAuthor(hash.get("author"));
        book.setYear(Integer.parseInt(hash.get("year")));
        return book;
    }

    @Override
    public List<Book> findAll(){
        try{
            List<Book> books = new ArrayList();
            var bookKeys = redisClient.keys(this.resource+":*");
           bookKeys.forEach(key ->{
              books.add(this.getBook(key));
           });

//            redisClient.publish(this.channel,"findAll Keys ");

           return books;
        }
        catch(Exception e){

        }
        return null;
    }

    @Override
    public void close(){
        this.redisClient.close();
    }

    public void subscribe(JedisPubSub jedisPubSub, String channel){
        this.redisClient.subscribe(jedisPubSub, channel);
    }
}
