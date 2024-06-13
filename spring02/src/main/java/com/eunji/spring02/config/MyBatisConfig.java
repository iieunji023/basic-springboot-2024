package com.eunji.spring02.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
// 해당 패키지에서 Mapper로 인식하라
@MapperScan(basePackages = { "com.eunji.spring02.mapper" })
public class MyBatisConfig {
  
}
