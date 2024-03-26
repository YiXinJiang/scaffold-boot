# Module Description

## jyx-config:

### 此模块用于定义系统内部底层框架配置支持

* 使用 spring-autoconfig 以支持插件化
* 使用范围诸如：日志配置、api接口文档配置
* 生产环境请在配置文件中将以下配置修改为true
    + knife4j.production=true # 是否生产环境
* 使用日志功能时，请注意以下几点：
    + 当前版本log4j不支持控制台彩色日志打印，如需要，请在java启动参数内添加：-Dlog4j.skipJansi=false
    + 请在引用了spring-boot-web组件的模块内排除spring默认日志框架logback：
      ````{r 
      <dependency>
          <groupId>org.springframework.boot</groupId>
          <artifactId>spring-boot-starter-web</artifactId>
          <exclusions>
              <exclusion>
                  <groupId>org.springframework.boot</groupId>
                  <artifactId>spring-boot-starter-logging</artifactId>
              </exclusion>
          </exclusions>
      </dependency>
* 接口文档使用swagger3，注解较之前版本基本不同：
    + ![img.png](img.png)
    + 详情请参考：https://blog.csdn.net/N_007/article/details/131188656