package com.taller.redisclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import redis.clients.jedis.DefaultJedisClientConfig;
import redis.clients.jedis.Jedis;

import java.util.Map;

@SpringBootApplication
public class RedisclientApplication {

	public static void main(String[] args) {
		SpringApplication.run(RedisclientApplication.class, args);

		boolean useSsl = true;
		String cacheHostname = "redissession4.redis.cache.windows.net";
		String cachekey = "Rn884914KFOkgv0h7wNIGOhbzbEphVRilAzCaAGgHvM=";

		Jedis jedis = new Jedis(cacheHostname, 6380, DefaultJedisClientConfig.builder()
				.password(cachekey)
				.ssl(useSsl)
				.build());

		System.out.println("connection ok");
		
		String pingResponse = jedis.ping();

		System.out.println("pingResponse = " + pingResponse);

		System.out.println("STRINGS - Peliculas");

		String pelicula1 = jedis.get("pelicula1");
		System.out.println("pelicula1 = " + pelicula1);
		String pelicula2 = jedis.set("pelicula2", "Titanic");
		System.out.println("pelicula2 = " + pelicula2);

		System.out.println("LISTS - CREAR LISTA DE CIUDADES");
		jedis.lpush("lista-ciudades","Lima", "Piura", "Trujillo");
		var result = jedis.lrange("lista-ciudades",0,-1);
		System.out.println("result = " + result);

		System.out.println("HASHES - CREAR UN OBJETO BOOK");
		String key="book:1";
		jedis.hset(key, "id","1");
		jedis.hset(key, "name","Mobidick");

		Map<String, String> bookMap = Map.of("id","2","name","La ciudad y los perros");
		jedis.hmset("book:2", bookMap);

	}

}
