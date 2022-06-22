package com.tallerredis.books;

import com.tallerredis.books.util.IRedisBook;
import com.tallerredis.books.util.RedisBookHelper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import redis.clients.jedis.JedisPubSub;

import java.util.Date;

@SpringBootApplication
public class BooksApplication {


	public static void main(String[] args) {


		SpringApplication.run(BooksApplication.class, args);

//		IRedisBook client = new RedisBookHelper();
//		client.subscribe(new JedisPubSub() {
//			@Override
//			public void onMessage(String channel, String message) {
//				System.out.println("time = " + java.time.LocalTime.now());
//				System.out.println("channel = " + channel);
//				System.out.println("message = " + message);
//			}
//		}, "bookChannel");
	}


}
