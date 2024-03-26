# Module Description

## jyx-datebase:

### 此模块用于数据库通信支持，目前仅支持mysql

* 使用 spring-autoconfig 以支持插件化
* 核心依赖为：
    + 数据库驱动：mysql-connector-java
    + 数据库连接池：druid-spring-boot-starter
    + mybatis-plus：mybatis-plus-boot-starter
    + 代码生成器：mybatis-plus-generator
* 代码生成器使用指南：
    + 生成器类路径：com.jyx.database.CodeGenerator
    + 请按以下提示修改配置：
    + ![img.png](img.png)
