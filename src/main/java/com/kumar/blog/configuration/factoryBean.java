package com.kumar.blog.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class factoryBean {

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
}
