package com.taller.redisclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import redis.clients.jedis.DefaultJedisClientConfig;
import redis.clients.jedis.Jedis;

@SpringBootApplication
public class RedisclientApplication {

	public static void main(String[] args) {
		SpringApplication.run(RedisclientApplication.class, args);

		boolean useSsl = true;
		String cacheHostname = "redissession4.redis.cache.windows.net";
		String cachekey = "sgeRAzKidwSlrpIUHnnOADkWE89GlG6V7AzCaKtFnzE=";

		Jedis jedis = new Jedis(cacheHostname, 6380, DefaultJedisClientConfig.builder()
				.password(cachekey)
				.ssl(useSsl)
				.build());

		System.out.println("connection ok");
		
		String pingResponse = jedis.ping();

		System.out.println("pingResponse = " + pingResponse);
		
		String pelicula1 = jedis.get("pelicula1");
		System.out.println("pelicula1 = " + pelicula1);

		String pelicula2 = jedis.set("pelicula2", "Titanic");

		System.out.println("pelicula2 = " + pelicula2);


	}

}
