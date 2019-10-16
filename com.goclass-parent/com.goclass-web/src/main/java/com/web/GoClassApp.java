package com.web;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.goclass.mapper")
public class GoClassApp{
	public static void main(String[] args) {
		SpringApplication.run(GoClassApp.class, args);
	}
}
