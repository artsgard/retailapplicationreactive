package com.artsgard.retailapplicationreactive;

import javax.sql.DataSource;

import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.ConnectionFactoryOptions;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import static io.r2dbc.pool.PoolingConnectionFactoryProvider.MAX_SIZE;
import static io.r2dbc.spi.ConnectionFactoryOptions.*;
import static io.r2dbc.spi.ConnectionFactoryOptions.DATABASE;

/**
 *
 * @author artsgard
 */
//@Configuration
//@ConfigurationProperties("spring.datasource")
public class DBConfig {
    
    @Profile("prod")
    @Bean(name = "postgresDataSource")
    /*
    public DataSource devDatabaseConnection() {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.url("r2dbc:postgresql://localhost:5432/PRODUCT_PURCHASE_DB");
        dataSourceBuilder.username("postgres");
        dataSourceBuilder.password("postgres");
        return dataSourceBuilder.build();
    }
    */
    public ConnectionFactory connectionFactory() {
        return ConnectionFactories.get(
                ConnectionFactoryOptions.builder()
                        .option(DRIVER, "postgresql")
                        .option(HOST, "localhost")
                        .option(PORT, 5432)
                        .option(USER, "postgres")
                        .option(PASSWORD, "postgres")
                        .option(DATABASE, "PRODUCT_PURCHASE_DB")
                        .option(MAX_SIZE, 40)
                        .build());
    }

    @Profile("test")
    @Bean(name = "h2DataSource")
    public DataSource testDatabaseConnection() {
          DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.url("jdbc:h2:mem:test_db");
        dataSourceBuilder.username("sa");
        dataSourceBuilder.password("sa");
        return dataSourceBuilder.build();
    }

    @Profile("dev")
    @Bean
    public DataSource prodDatabaseConnection() {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.url("r2dbc:mysql://localhost:3306/PRODUCT_PURCHASE_DB"); //?createDatabaseIfNotExist=true&useUnicode=yes&characterEncoding=UTF-8&useLegacyDatetimeCode=false&serverTimezone=UTC");
        dataSourceBuilder.username("root");
        dataSourceBuilder.password("Candita123");
        return dataSourceBuilder.build();
    }
}

