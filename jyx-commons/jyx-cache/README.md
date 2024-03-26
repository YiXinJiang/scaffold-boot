# Module Description

## jyx-cache:

### 此模块用于缓存支持

* 使用 Facade Pattern 抽象缓存核心接口(CacheService)
    + 基于 Redis 实现了以下类型的缓存
        + 基本类型
        + 字符串
        + 对象
        + 嵌套对象，并支持嵌套对象的反序列化
        + Collection
        + Map
    + 基于 Redisson 实现了分布式锁
* 模块核心依赖：
    + spring-redis：spring-boot-starter-data-redis
    + redis-connect-pool：lettuce、commons-pool2
    + redisson：redisson-spring-boot-starter
* 因 lettuce 使用的 netty 对 java9 以上版本做了 io.netty.tryReflectionSetAccessible 是否为true的校验，导致应用启动时会抛出不影响运行的异常栈帧
    + 如需解决，在 java启动命令中 添加以下参数：
      ````{r 
      --add-opens java.base/jdk.internal.misc=ALL-UNNAMED
      --illegal-access=warn
      -Dio.netty.tryReflectionSetAccessible=true

