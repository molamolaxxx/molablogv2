package com.mola.molablogv2;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.mola.molablogv2.repository")
public class Molablogv2Application {

	public static void main(String[] args) {
		SpringApplication.run(Molablogv2Application.class, args);
	}

}
