# 支撑类工具库
## 是系统最底层模块，禁止依赖其他系统模块！
### 放置各类Util，系统常量，枚举，注解等
+ #### 注解类
+ #### 网络请求返回实体定义以及code定义
+ #### 常用静态常量
+ #### 常用枚举类型
+ #### 常用异常类型定义
+ #### 常用工具类如：文件操作工具类，字符串和类型转换工具类等
+ #### AES加解密工具类和RSA加解密工具类
+ #### jackson解析
+ #### 对象转换工具ModelMapper
  - 需要使用时直接依赖注入`ModelMapper` 即可
+ #### 信息脱敏
  - 目前只支持四种脱敏策略，用户名、手机号、身份证、住址
  - 接口脱敏
    - http接口返回的pojo中，在需要脱敏的字段上添加注解： `@Sensitivity`
    - 通过继承jackson中`JsonSerializer`实现，核心实现类： `SensitivitySerializer`
  - 日志脱敏
    - 把需要脱敏的字段名称写入对应的配置中
    - 通过自定义log4j日志输出组件实现，核心实现类：`CustomPatternLayout`
  - 配置文件：
    - application-core.yml
      - desensitize.enable.log-enable: 是否开启日志脱敏 true/false
      - desensitize.enable.log-api: 是否开启接口脱敏 true/false
      - desensitize.log-property.id-card: 日志中身份证的字段名
      - desensitize.log-property.username: 日志中用户名的字段名
      - desensitize.log-property.phone: 日志中手机号码的字段名
      - desensitize.log-property.address: 日志中地址的字段名