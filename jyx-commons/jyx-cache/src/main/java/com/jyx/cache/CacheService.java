package com.jyx.cache;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @InterfaceName: CacheService
 * @Description: 缓存管理接口
 * @Author: jyx
 * @Date: 2024-03-05 14:10
 **/
public interface CacheService {

    /**
     * 缓存基本的对象，Integer、String、实体类(支持对象嵌套)等
     *
     * @param key   缓存的键值
     * @param value 缓存的值
     */
    <T> void set(final String key, final T value);

    /**
     * 缓存基本的对象，Integer、String、实体类等
     *
     * @param key      缓存的键值
     * @param value    缓存的值
     * @param timeout  时间
     * @param timeUnit 时间颗粒度
     */
    <T> void set(final String key, final T value, final Long timeout, final TimeUnit timeUnit);

    /**
     * 判断 key是否存在
     *
     * @param key 键
     * @return true 存在 false不存在
     */
    Boolean exist(String key);

    /**
     * 获得缓存的基本对象。
     *
     * @param key 缓存键值
     * @return 缓存键值对应的数据
     */
    <T> T get(final String key);

    /**
     * 删除单个对象
     *
     * @param key
     */
    boolean remove(final String key);

    /**
     * 删除key列表
     *
     * @param collection 多个对象
     * @return
     */
    boolean remove(final Collection<String> collection);

    /**
     * 缓存List数据
     *
     * @param key      缓存的键值
     * @param dataList 待缓存的List数据
     * @return 缓存的对象
     */
    <T> long setList(final String key, final List<T> dataList);

    /**
     * 获得缓存的list对象
     *
     * @param key 缓存的键值
     * @return 缓存键值对应的数据
     */
    <T> List<T> getList(final String key);

    /**
     * 分页获得缓存的list对象
     *
     * @param key 缓存的键值
     * @return 缓存键值对应的数据
     */
    <T> List<T> getList(final String key, int start, int end);

    /**
     * 缓存Map
     *
     * @param key
     * @param dataMap
     */
    <T> void setMap(final String key, final Map<String, T> dataMap);

    /**
     * 获得缓存的Map
     *
     * @param key
     * @return
     */
    <T> Map<Object, T> getMap(final String key);

    /**
     * 往Hash中存入数据
     *
     * @param key   Redis键
     * @param hKey  Hash键
     * @param value 值
     */
    <T> void setMapValue(final String key, final String hKey, final T value);

    /**
     * 获取Hash中的数据
     *
     * @param key  Redis键
     * @param hKey Hash键
     * @return Hash中的对象
     */
    <T> T getMapValue(final String key, final String hKey);

    /**
     * 获取多个Hash中的数据
     *
     * @param key   Redis键
     * @param hKeys Hash键集合
     * @return Hash对象集合
     */
    Collection<Object> getMultiMapValue(final String key, final Collection<Object> hKeys);

    /**
     * 删除Hash中的某条数据
     *
     * @param key  Redis键
     * @param hKey Hash键
     * @return 是否成功
     */
    boolean deleteMapValue(final String key, final String hKey);

    /**
     * 获得缓存的基本对象列表
     *
     * @param pattern 字符串前缀
     * @return 对象列表
     */
    Collection<String> keys(final String pattern);

    /**
     * 获取一个增量为1的Long
     *
     * @param key
     * @return
     */
    Long increment(String key);

    /**
     * 获取一个指定增量的Long
     *
     * @param key
     * @param delta 增量
     * @return
     */
    Long increment(String key, long delta);

    /**
     * 分布式锁
     *
     * @param lockKey
     * @return
     */
    Boolean tryLock(String lockKey);

    /**
     * 分布式续期锁
     *
     * @param lockKey
     * @return
     */
    Boolean renewalLock(String lockKey);

    /**
     * 释放锁
     *
     * @param lockKey
     */
    void unLock(String lockKey);

}
