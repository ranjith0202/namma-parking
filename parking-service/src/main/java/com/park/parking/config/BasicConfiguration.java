package com.park.parking.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BasicConfiguration {

	

	@Bean
	ModelMapper modelMapper(){
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration()
        .setSkipNullEnabled(true);        // 🚀 Prevent null overriding

        return mapper;
	}
	
	
}
