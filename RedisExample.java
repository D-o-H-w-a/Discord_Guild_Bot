package org.example;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Connection;

public class RedisExample {

    public static Jedis jedis;
    public static void connectJedisDB() {
        // Redis 서버 정보로 Jedis 클라이언트를 초기화
        jedis = new Jedis("redis://default:hpbGlQEhslDjsiasyyDoUB1NyE8VfwkR@redis-13991.c241.us-east-1-4.ec2.cloud.redislabs.com:13991"); // 호스트 및 포트를 실제 서버 설정에 맞게 수정

        Connection connection = jedis.getConnection();
    }

    public static void saveGameInformaition(String key, String value)
    {
        jedis.set(key, value);
    }

    public static String loadGameInformation(String key) {
        String data = jedis.get(key);
        if (data != null) {
            return data;
        }
        return "데이터가 존재하지 않는다냥";
    }

    public static String removeGameInformation(String key){
        String data = jedis.get(key);
        if(data != null){
            jedis.del(key);
            return "삭제가 잘 되었다냥";
        }
        else{
            return "삭제 할 데이터가 없다냥";
        }
    }
}
