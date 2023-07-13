package com.kanke.ml.repository;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public abstract class BaseRedisRepository {

    protected abstract RedisTemplate<String, ?> getRedisTemplate();

    public boolean set(final String key, final String value) {
        RedisTemplate<String, ?> redisTemplate = getRedisTemplate();
        boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                connection.set(serializer.serialize(key), serializer.serialize(value));
                return true;
            }
        });
        return result;
    }

    public boolean set(final String key, final String value,final int seconds) {
        RedisTemplate<String, ?> redisTemplate = getRedisTemplate();
        boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                byte[] bytes = serializer.serialize(key);
                connection.set(bytes, serializer.serialize(value));
                connection.expire(bytes,seconds);
                return true;
            }
        });
        return result;
    }

    public Set<String> keys(final String key){
        RedisTemplate<String, ?> redisTemplate = getRedisTemplate();
        Set<String> result = redisTemplate.execute(new RedisCallback<Set<String>>() {
            @Override
            public Set<String> doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                Set<byte[]> keys = connection.keys(serializer.serialize(key));
                Set<String> keyArr = new HashSet<String>();
                for(byte[] key :keys){
                    String newKey = serializer.deserialize(key);
                    keyArr.add(newKey);
                }
                return keyArr;
            }
        });
        return result;
    }


    public boolean has(final String key) {
        RedisTemplate<String, ?> redisTemplate = getRedisTemplate();
        return redisTemplate.hasKey(key);
    }

    public String get(final String key){

        RedisTemplate<String, ?> redisTemplate = getRedisTemplate();
        String result = redisTemplate.execute(new RedisCallback<String>() {
            @Override
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                byte[] value =  connection.get(serializer.serialize(key));
                return serializer.deserialize(value);
            }
        });
        return result;
    }

    public String getH(final String key,final String field){

        RedisTemplate<String, ?> redisTemplate = getRedisTemplate();
        String result = redisTemplate.execute(new RedisCallback<String>() {
            @Override
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                byte[] value =  connection.hGet(serializer.serialize(key), serializer.serialize(field));
                return serializer.deserialize(value);
            }
        });
        return result;
    }

    public String delH(final String key,final String field){
        RedisTemplate<String, ?> redisTemplate = getRedisTemplate();
        String result = redisTemplate.execute(new RedisCallback<String>() {
            @Override
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                Long aLong = connection.hDel(serializer.serialize(key), serializer.serialize(field));
                return String.valueOf(aLong);
            }
        });
        return result;
    }

    public String del(final String key){
        RedisTemplate<String, ?> redisTemplate = getRedisTemplate();
        String result = redisTemplate.execute(new RedisCallback<String>() {
            @Override
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                connection.del(serializer.serialize(key));
                return String.valueOf("");
            }
        });
        return result;
    }



    public boolean setH(final String key,final String field,String value1){

        RedisTemplate<String, ?> redisTemplate = getRedisTemplate();
        boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                Boolean value =  connection.hSet(serializer.serialize(key), serializer.serialize(field), serializer.serialize(value1));
                return value;
            }
        });
        return result;
    }

    public boolean setHM(final String key, final Map<String, String> mapvalue) {
        RedisTemplate<String, ?> redisTemplate = getRedisTemplate();
        return redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<String> stringSerializer = redisTemplate.getStringSerializer();
                Map<byte[], byte[]> data = new HashMap<>();
                mapvalue.forEach((k, v) -> {
                    data.put(stringSerializer.serialize(k), stringSerializer.serialize(v));

                });
                connection.hMSet(stringSerializer.serialize(key), data);
                return true;
            }
        });
    }

    public boolean addS(final String key,String value1){
        RedisTemplate<String, ?> redisTemplate = getRedisTemplate();
        boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                Long l = connection.sAdd(serializer.serialize(key), serializer.serialize(value1));
                return l>0;
            }
        });
        return result;
    }


    public Set<String> zRangeByScore(String key,double min,double max) {
        RedisTemplate<String, ?> redisTemplate = getRedisTemplate();
        Set<String> result = redisTemplate.execute((RedisCallback<Set<String>>) connection -> {
            RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
            Set<byte[]> bytes = connection.zRangeByScore(serializer.serialize(key), min, max);
            return SerializationUtils.deserialize(bytes, serializer);
        });
        return result;
    }

    public boolean zRemRangeByScore(String key,double min,double max){
        RedisTemplate<String, ?> redisTemplate = getRedisTemplate();
        return redisTemplate.execute((RedisCallback<Boolean>) connection -> {
            RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
            connection.zRemRangeByScore(serializer.serialize(key), min, max);
            return true;
        });
    }

    public boolean hIncrBy(final String key,final String field,int incr){
        RedisTemplate<String, ?> redisTemplate = getRedisTemplate();
        return redisTemplate.execute((RedisCallback<Boolean>) connection -> {
            RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
            connection.hIncrBy(serializer.serialize(key), serializer.serialize(field), incr);
            return true;
        });
    }

    public Map<String, String> hScan(final String key){
        RedisTemplate<String, ?> redisTemplate = getRedisTemplate();
        return redisTemplate.execute((RedisCallback<Map<String, String>>) connection -> {
            RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
            Cursor<Map.Entry<byte[], byte[]>> entryCursor = connection.hScan(serializer.serialize(key), ScanOptions.scanOptions().count(100).match("*").build());
            HashMap<String, String> map = new HashMap<>();
            while (entryCursor.hasNext()) {
                Map.Entry<byte[], byte[]> next = entryCursor.next();
                map.put(serializer.deserialize(next.getKey()),serializer.deserialize(next.getValue()));
            }
            return map;
        });
    }

    public boolean lPush(final String key,final String value){
        RedisTemplate<String,?> redisTemplate =this.getRedisTemplate();
        return redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                connection.lPush(serializer.serialize(key),serializer.serialize(value));
                return true;
            }
        });
    }

    public long lLen(final String key){
        RedisTemplate<String,?> redisTemplate =this.getRedisTemplate();
        return redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                return connection.lLen(serializer.serialize(key));
            }
        });
    }

    public String rPop(final String key){
        RedisTemplate<String,?> redisTemplate =this.getRedisTemplate();
        return redisTemplate.execute(new RedisCallback<String>() {
            @Override
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                byte[] bytes = connection.rPop(serializer.serialize(key));
                return serializer.deserialize(bytes);
            }
        });
    }
}
