package com.jyx.core.desensitize.api;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.jyx.core.desensitize.DesensitizeConfiguration;
import com.jyx.core.desensitize.SensitivityEnum;

import java.io.IOException;
import java.util.Objects;

/**
 * @ClassName: SensitivitySerializer
 * @Description: 用于脱敏的json序列化器
 * @Author: jyx
 * @Date: 2024-03-14 16:46
 **/
public class SensitivitySerializer extends JsonSerializer<String> implements ContextualSerializer {

    private SensitivityEnum sensitivityEnum;

    @Override
    public void serialize(String value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeString(DesensitizeConfiguration.apiIsEnable() ? sensitivityEnum.desensitizer().apply(value) : value);
    }

    @Override
    public JsonSerializer<?> createContextual(SerializerProvider prov, BeanProperty property) throws JsonMappingException {
        Sensitivity annotation = property.getAnnotation(Sensitivity.class);
        if (Objects.nonNull(annotation) && Objects.equals(String.class, property.getType().getRawClass())) {
            this.sensitivityEnum = annotation.strategy();
            return this;
        }
        return prov.findValueSerializer(property.getType(), property);
    }

}
