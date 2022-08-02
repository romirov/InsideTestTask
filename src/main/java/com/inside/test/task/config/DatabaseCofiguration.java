package com.inside.test.task.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import javax.sql.DataSource;

@Configuration
public class DatabaseCofiguration {

//  @Bean
//  public JdbcTemplate getJdbcTemplate(DataSource dataSource) {
//    return new JdbcTemplate(dataSource);
//  }
//
//  @Bean
//  public DataSource getDataSource() {
//    final DriverManagerDataSource dataSource = new DriverManagerDataSource();
//    dataSource.setDriverClassName("org.postgresql.Driver");
//    dataSource.setUrl("jdbc:postgresql://localhost:5432/postgres");
//    dataSource.setUsername("postgres");
//    dataSource.setPassword("postgres");
//    return dataSource;
//  }
}
