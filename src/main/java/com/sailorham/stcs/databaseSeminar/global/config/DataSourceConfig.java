package com.sailorham.stcs.databaseSeminar.global.config;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class DataSourceConfig {

    @Primary
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.primary")
    public DataSource mainDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Primary
    @Bean
    public JdbcTemplate jdbcTemplate(@Qualifier("mainDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.readonly")
    public DataSource readOnlyDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "readOnlyJdbcTemplate")
    public JdbcTemplate readOnlyJdbcTemplate(
        @Qualifier("readOnlyDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
