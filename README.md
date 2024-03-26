

## 

<div style="text-align: center;">
	    <img src="logo.JPG" alt="Description" width="150" height="150">
</div>
<h1  style="margin: 20px 0 20px; font-weight: bold;text-align: center;">jyx v1.0.0</h1>
<h4 style="text-align: center;">基于 Vue/Element UI 和 Spring Boot 前后端分离的服务架构</h4>

[//]: # (<p align="center">)

[//]: # (	<a href="https://gitee.com/y_project/jyx-Cloud/stargazers"><img src="https://gitee.com/y_project/jyx-Cloud/badge/star.svg?theme=dark"></a>)

[//]: # (	<a href="https://gitee.com/y_project/jyx-Cloud"><img src="https://img.shields.io/badge/jyx-v3.6.1-brightgreen.svg"></a>)

[//]: # (	<a href="https://gitee.com/y_project/jyx-Cloud/blob/master/LICENSE"><img src="https://img.shields.io/github/license/mashape/apistatus.svg"></a>)

[//]: # (</p>)

## 平台简介

jyx是一套后台快速开发平台，适合开箱即用。

* 采用前后端分离的模式。
* 后端采用Spring Boot脚手架。
* 权限认证使用Spring Security + JWT + Redis。
* 用户管理：用户是系统操作者，该功能主要完成系统用户配置。
* 接口文档：基于Swagger2+knife4j构建，方便编写API接口文档。
* 代码生成：基于Mybatis-plus-generator（代码自动生成）框架，实现快速开发。
* 文件存储：基于阿里云对象存储（OSS）实现文件上传。
* 短信发送：基于阿里云SMS SDK进行接口二次封装,支持验证码、短信发送功能。
* 网络请求：基于spring-RestTemplate接口二次封装,支持get、post、put、delete等功能




## 系统模块

~~~
com.jyx           
├── jyx-commons                     // 通用组件模块
│       └── jyx-core                // 核心组件
│       └── jyx-authentication      // 登录权限组件
│       └── jyx-config              // 系统内部底层框架配置组件
│       └── jyx-database            // 数据库通信组件
│       └── jyx-httpclient          // 网络请求组件
│       └── jyx-oss                 // 文件存储组件
│       └── jyx-sms                 // 短信发送组件
│       └── jyx-excel               // execl表处理组件
│       └── jyx-cache               // redis缓存组件
├── jyx-business                    // 业务模块 [9200]
├── jyx-system                      // 系统模块 
├──pom.xml                             // 公共依赖
~~~

## 内置功能

1. 用户管理：用户是系统操作者，该功能主要完成系统用户配置。
2. 菜单管理：配置系统菜单，操作权限，按钮权限标识等。
3. 角色管理：角色菜单权限分配、设置角色按机构进行数据范围权限划分。
4. 操作日志：系统正常操作日志记录和查询；系统异常信息日志记录和查询。
5. 登录日志：系统登录日志记录查询包含登录异常。
6. 在线用户：当前系统中活跃用户状态监控。
7. 系统接口：根据业务代码自动生成相关的api接口文档。

## 系统需求

+ JDK >= 11
+ MySQL >= 8.0
+ Maven >= 4.0
+ Node >= 12
+ Redis >= 3


