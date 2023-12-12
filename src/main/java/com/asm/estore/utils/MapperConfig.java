package com.asm.estore.utils;

import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class MapperConfig {
    @Bean
    public ModelMapper mapper() {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration()
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(Configuration.AccessLevel.PRIVATE);

        return mapper;
    }
}
