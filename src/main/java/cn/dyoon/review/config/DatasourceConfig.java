package cn.dyoon.review.config;

import cn.dyoon.review.util.AESUtil;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * cn.dyoon.review.config
 *
 * @author majhdk
 * @date 2020/2/8
 */
@Configuration
@EnableConfigurationProperties(DataSourceProperties.class)
public class DatasourceConfig {

    @Bean
    public DataSource getDataSource(DataSourceProperties properties) {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(properties.getUrl());
        try {
            dataSource.setUsername(AESUtil.decrypt(properties.getUsername()));
            dataSource.setPassword(AESUtil.decrypt(properties.getPassword()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataSource;
    }

}
