package com.jyx.cache.redis;

import com.jyx.cache.CacheService;
import com.jyx.cache.redisson.RedissonLock;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName: CacheServiceImpl
 * @Description:
 * @Author: jyx
 * @Date: 2024-03-05 14:20
 **/
@Component
public class RedisCacheService implements CacheService {

    public final RedisTemplate<String, Object> redisTemplate;
    private final RedissonLock redissonLock;

    public RedisCacheService(RedisTemplate<String, Object> redisTemplate, RedissonLock redissonLock
    ) {
        this.redisTemplate = redisTemplate;
        this.redissonLock = redissonLock;
    }

    @Override
    public <T> void set(final String key, final T value) {
        redisTemplate.opsForValue().set(key, value);
    }

    @Override
    public <T> void set(final String key, final T value, Long timeout, TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key, value, timeout, timeUnit);
    }

    @Override
    public Boolean exist(String key) {
        return redisTemplate.hasKey(key);
    }

    @Override
    public Object get(String key) {
        ValueOperations<String, Object> operation = redisTemplate.opsForValue();
        return operation.get(key);
    }

    @Override
    public boolean remove(String key) {
        return Boolean.TRUE.equals(redisTemplate.delete(key));
    }

    @Override
    public boolean remove(Collection<String> collection) {
        return Boolean.TRUE.equals(redisTemplate.delete(String.valueOf(collection)));
    }

    @Override
    public <T> long setList(String key, List<T> dataList) {
        Long count = redisTemplate.opsForList().rightPushAll(key, dataList);
        return Optional.ofNullable(count).orElse(0L);
    }

    @Override
    public List<Object> getList(String key) {
        return redisTemplate.opsForList().range(key, 0, -1);
    }

    @Override
    public List<Object> getList(String key, int start, int end) {
        return redisTemplate.opsForList().range(key, start, end);
    }

    @Override
    public <T> void setMap(String key, Map<String, T> dataMap) {
        Optional.ofNullable(dataMap).ifPresent(data -> redisTemplate.opsForHash().putAll(key, dataMap));
    }

    @Override
    public Map<Object, Object> getMap(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    @Override
    public <T> void setMapValue(String key, String hKey, T value) {
        redisTemplate.opsForHash().put(key, hKey, value);
    }

    @Override
    public <T> T getMapValue(String key, String hKey) {
        HashOperations<String, String, T> opsForHash = redisTemplate.opsForHash();
        return opsForHash.get(key, hKey);
    }

    @Override
    public Collection<Object> getMultiMapValue(String key, Collection<Object> hKeys) {
        return redisTemplate.opsForHash().multiGet(key, hKeys);
    }

    @Override
    public boolean deleteMapValue(String key, String hKey) {
        return redisTemplate.opsForHash().delete(key, hKey) > 0;
    }

    @Override
    public Collection<String> keys(String pattern) {
        return redisTemplate.keys(pattern);
    }

    @Override
    public Long increment(String key) {
        return redisTemplate.opsForValue().increment(key);
    }

    @Override
    public Long increment(String key, long delta) {
        return redisTemplate.opsForValue().increment(key, delta);
    }

    @Override
    public Boolean tryLock(String lockKey) {
        return redissonLock.tryLock(lockKey, 30);
    }

    @Override
    public Boolean renewalLock(String lockKey) {
        return redissonLock.tryLock(lockKey, -1);
    }

    @Override
    public void unLock(String lockKey) {
        redissonLock.unLock(lockKey);
    }
}
