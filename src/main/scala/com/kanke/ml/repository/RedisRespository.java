package com.kanke.ml.repository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

@Repository
public class RedisRespository extends BaseRedisRepository {


	@Autowired
	private RedisTemplate<String, ?> redisTemplate;

	@Override
	protected RedisTemplate<String, ?> getRedisTemplate() {
		return redisTemplate;
	}

}
