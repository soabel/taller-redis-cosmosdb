package com.tallerredis.books.util;

import com.tallerredis.books.entities.Book;
import org.springframework.beans.factory.annotation.Value;
import redis.clients.jedis.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RedisBookHelper implements IRedisBook {

    private String resource="book";
    private String  channel=resource + "Channel";
    private boolean useSsl = true;
    private String cacheHostname= "redis6premium.redis.cache.windows.net";
    private String cachekey = "Ylmpl0D6txRmRHoegbKAh9qa74zqyr3g4AzCaBp8Plw=";
    private Integer port=6380;

//    private Jedis redisClient;
    private JedisCluster redisClient;
    public final static int CONNECT_TIMEOUT_MILLS = 5000;
    public final static int OPERATION_TIMEOUT_MILLS = 1000;
    public final static int POOL_MAX_TOTAL = 200;
    public final static int POOL_MAX_IDLE = 100;
    public final static int POOL_MIN_IDLE = 50;
    public final static boolean POOL_BLOCK_WHEN_EXHAUSTED = true;
    public final static int POOL_MAX_WAIT_MILLIS = OPERATION_TIMEOUT_MILLS;
    public final static int RECONNECT_MAX_ATTEMPTS = 3;

    public RedisBookHelper(){
        System.out.println("cacheHostname = " + cacheHostname);
        this.redisClient = new Jedis(cacheHostname, this.port, DefaultJedisClientConfig.builder()
                .password(cachekey)
                .ssl(useSsl)
                .build());

//        HostAndPort hostAndPort = new HostAndPort(cacheHostname, this.port);
//        var poolConfig = createPoolConfig();
////        public JedisCluster(HostAndPort node, int connectionTimeout, int soTimeout, int maxAttempts, String password, GenericObjectPoolConfig<Connection> poolConfig) {
//
//
//        this.redisClient = new JedisCluster(hostAndPort,CONNECT_TIMEOUT_MILLS,OPERATION_TIMEOUT_MILLS,RECONNECT_MAX_ATTEMPTS,cachekey, poolConfig);

    }

    private static JedisPoolConfig createPoolConfig(){
        JedisPoolConfig poolConfig = new JedisPoolConfig();

        poolConfig.setMaxTotal(POOL_MAX_TOTAL);
        poolConfig.setMaxIdle(POOL_MAX_IDLE);
        poolConfig.setBlockWhenExhausted(POOL_BLOCK_WHEN_EXHAUSTED);
        poolConfig.setMaxWaitMillis(POOL_MAX_WAIT_MILLIS);
        poolConfig.setMinIdle(POOL_MIN_IDLE);

        return poolConfig;
    }

    public void expire(Integer id){
        String key=this.resource + ":" + id;
        redisClient.expire(key,1);
        redisClient.publish("bookExpire","expire " + key);
    }
    private void expire(String key, Integer seconds){
        redisClient.expire(key,seconds);
    }

    @Override
    public boolean save(Book book){
        try{
            String key=this.resource + ":" + book.getId();
            redisClient.hset(key, "id",book.getId().toString());
            redisClient.hset(key, "name",book.getName());
            redisClient.hset(key, "year",book.getYear().toString());
            redisClient.hset(key, "author",book.getAuthor());

            redisClient.publish(this.channel,"save " + book.toString() );

            return true;
        }
        catch(Exception e){
            throw e;
        }
    }

    @Override
    public Book findById(Integer id){
        try{
            String key=this.resource + ":" + id.toString();
            redisClient.publish(this.channel,"findById = " + key );
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

//            Map<String, JedisPool> clusterNodes = this.redisClient.getClusterNodes();
//            var bookKeys = clusterNodes.keySet().stream().filter(x-> x.contains(this.resource)).toList();
//
//            bookKeys.forEach(key ->{
//                books.add(this.getBook(key));
//            });

            redisClient.publish(this.channel,"findAll Keys ");

           return books;
        }
        catch(Exception e){

        }
        return null;
    }

    @Override
    public void close(){
//        this.redisClient.close();
    }

    public void subscribe(JedisPubSub jedisPubSub, String channel){
        this.redisClient.subscribe(jedisPubSub, channel);
    }
}
