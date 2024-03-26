package com.jyx.database;

import com.jyx.database.config.DBMetaObjectHandler;
import com.jyx.database.config.MybatisPlusPageConfig;
import org.springframework.context.annotation.Import;

/**
 * @ClassName: ConfigImport
 * @Description:
 * @Author: jyx
 * @Date: 2024-03-04 15:34
 **/
@Import({DBMetaObjectHandler.class, MybatisPlusPageConfig.class})
public class ConfigImport {
}
