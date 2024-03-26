package com.jyx.cache.redisson;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName: DistributedLock
 * @Description: 分布式锁
 * @Author: pengmingming
 * @Date: 2023-12-21 15:27
 * @Version: 1.0
 **/
@Slf4j
@Component
public class RedissonLock {
    private static final long DISTRIBUTED_LOCK_WAIT_TIME = 1;
    @Resource
    private RedissonClient redissonClient;

    public boolean tryLock(String lockName, long leaseTime) {
        try {
            RLock rLock = redissonClient.getLock(lockName);
            return rLock.tryLock(DISTRIBUTED_LOCK_WAIT_TIME, leaseTime, TimeUnit.SECONDS);
        } catch (Exception e) {
            log.error("DistributedLock: {}", e.getMessage(), e);
            return false;
        }
    }

    public void unLock(String lockName) {
        RLock lock = redissonClient.getLock(lockName);
        if (lock.isLocked()) {
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }
}
