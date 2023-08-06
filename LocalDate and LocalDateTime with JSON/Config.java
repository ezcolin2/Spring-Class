package com.formatting.jsonlocaldate;

import java.time.format.DateTimeFormatter;

import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
@Configuration
public class Config {

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {

        return builder -> {
            // LocalDate 타입 직렬화, 역직렬화 패턴
            DateTimeFormatter localDateSerializeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
            DateTimeFormatter localDateDeserializeFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");

            // LocalDateTime 타입 직렬화, 역직렬화 패턴
            DateTimeFormatter localDateTimeSerializeFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss.SS");
            DateTimeFormatter localDateTimeDeserializeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss.SS");

            // 등록
            builder.serializers(new LocalDateSerializer(localDateSerializeFormatter));
            builder.deserializers(new LocalDateDeserializer(localDateDeserializeFormatter));

            builder.serializers(new LocalDateTimeSerializer(localDateTimeSerializeFormatter));
            builder.deserializers(new LocalDateTimeDeserializer(localDateTimeDeserializeFormatter));

        };
    }
}