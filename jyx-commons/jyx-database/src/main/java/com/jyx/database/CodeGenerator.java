package com.jyx.database;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;
import com.baomidou.mybatisplus.generator.fill.Column;

import java.io.File;
import java.util.Collections;
import java.util.Objects;

/**
 * @ClassName: CodeGenaretor
 * @Description:
 * @Author: jyx
 * @Date: 2024-03-04 11:45
 **/
public class CodeGenerator {

    /*
     * * * * * * * * * * * * * * * * * * * * * * * * *
     * 请在此处键入你的配置
     * * * * * * * * * * * * * * * * * * * * * * * * *
     */
    static String url = "jdbc:mysql://localhost:3306/chargepile-dev";
    static String username = "root";
    static String password = "123456";
    static String author = "tgb";
    static String moduleName = "jyx-system"; // 模块名
    static String packageName = "com.jyx.system"; // 模块内包路径
    static String[] tables = {"sys_logininfor"}; // 需要生成的表


    public static void main(String[] args) {
        File file = new File(Objects.requireNonNull(CodeGenerator.class.getResource("/")).getPath());
        String path = file.getPath();
        String replace = path.replace("\\jyx-commons", "").replace("\\jyx-database", "");
        String sourcePath = replace.substring(0, replace.indexOf("\\target"));
        String classOutPath = sourcePath + "/" + moduleName + "/src/main/java";
        String xmlOutPath = sourcePath + "/" + moduleName + "/src/main/resources/mapper";
        System.out.println("----- code will be out to " + classOutPath + " -----");
        String[] parts = packageName.split("\\.", 2);
        //1、配置数据源
        FastAutoGenerator.create(url, username, password)
                //2、全局配置
                .globalConfig(builder -> {
                    builder.author(author) // 设置作者名
                            .outputDir(classOutPath)   //设置输出路径
                            .commentDate("yyyy-MM-dd hh:mm:ss")   //注释日期
                            .dateType(DateType.TIME_PACK)   //定义生成的实体类中日期的类型 TIME_PACK=LocalDateTime;ONLY_DATE=Date;
                            // .fileOverride()   //覆盖之前的文件
                            // .enableSwagger()   //开启 swagger 模式
                            .disableOpenDir();   //禁止打开输出目录，默认打开
                })
                //3、包配置
                .packageConfig(builder -> {
                    builder.parent(parts[0]) // 设置父包名
                            .moduleName(parts[1])   //设置模块包名
                            .entity("domain")   //pojo 实体类包名
                            .service("service") //Service 包名
                            .serviceImpl("serviceImpl") // ***ServiceImpl 包名
                            .mapper("mapper")   //Mapper 包名
                            .xml("mapper")  //Mapper XML 包名
                            .controller("controller") //Controller 包名
                            .other("utils") //自定义文件包名
                            .pathInfo(Collections.singletonMap(OutputFile.mapperXml, xmlOutPath));    //配置 **Mapper.xml 路径信息：项目的 resources 目录的 Mapper 目录下
                })
                //4、策略配置
                .strategyConfig(builder -> {
                    builder.addInclude(tables) // 设置需要生成的数据表名
                            .addTablePrefix("t_", "tb_", "c_", "sys_") // 设置过滤表前缀

                            //4.1、Mapper策略配置
                            .mapperBuilder()
                            .superClass(BaseMapper.class)   //设置父类
                            .formatMapperFileName("%sMapper")   //格式化 mapper 文件名称
                            .enableMapperAnnotation()       //开启 @Mapper 注解
                            .formatXmlFileName("%sMapper") //格式化 Xml 文件名称

                            //4.2、service 策略配置
                            .serviceBuilder()
                            .formatServiceFileName("%sService") //格式化 service 接口文件名称，%s进行匹配表名，如 UserService
                            .formatServiceImplFileName("%sServiceImpl") //格式化 service 实现类文件名称，%s进行匹配表名，如 UserServiceImpl

                            //4.3、实体类策略配置
                            .entityBuilder()
                            .enableLombok() //开启 Lombok
                            // .disableSerialVersionUID()  //不实现 Serializable 接口，不生产 SerialVersionUID
                            .logicDeleteColumnName("del_flag")   //逻辑删除字段名
                            .naming(NamingStrategy.underline_to_camel)  //数据库表映射到实体的命名策略：下划线转驼峰命
                            .columnNaming(NamingStrategy.underline_to_camel)    //数据库表字段映射到实体的命名策略：下划线转驼峰命
                            .addTableFills(
                                    new Column("create_time", FieldFill.INSERT),
                                    new Column("update_time", FieldFill.INSERT_UPDATE),
                                    new Column("create_by", FieldFill.INSERT),
                                    new Column("update_by", FieldFill.INSERT_UPDATE),
                                    new Column("del_flag", FieldFill.INSERT)
                            )   //添加表字段填充，"create_time"字段自动填充为插入时间，"modify_time"字段自动填充为插入修改时间
                            .enableTableFieldAnnotation()       // 开启生成实体时生成字段注解

                            //4.4、Controller策略配置
                            .controllerBuilder()
                            .formatFileName("%sController") //格式化 Controller 类文件名称，%s进行匹配表名，如 UserController
                            .enableRestStyle();  //开启生成 @RestController 控制器
                })
                //5、模板引擎
                .templateEngine(new VelocityTemplateEngine())   //默认
                // .templateEngine(new FreemarkerTemplateEngine())
                // .templateEngine(new BeetlTemplateEngine())
                //6、执行
                .execute();

    }

}
