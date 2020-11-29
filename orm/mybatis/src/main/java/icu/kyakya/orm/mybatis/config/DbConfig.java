package icu.kyakya.orm.mybatis.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.testcontainers.containers.MySQLContainer;

import javax.sql.DataSource;

@Configuration
@Slf4j
public class DbConfig {

    @Profile({"h2"})
    @Bean(name = "dataSource")
    public static DataSource h2DataSource() {
        log.info("[kyakya] 初始化H2内存数据库");
        return new EmbeddedDatabaseBuilder()
                .setName("testdb")
                .setType(EmbeddedDatabaseType.H2)
                .addScripts("/mvn/db/h2/code_master.sql", "/mvn/db/h2/others_schemas.sql")
                .build();
    }

    @Profile({"mysql"})
    @Bean(name = "dataSource")
    public DataSource dataSource() {
        log.info("[kyakya] 初始化MySQL数据库");
        MySQLContainer<?> container = MySQLBuilder.buildMySQLContainer();
        return DataSourceBuilder.create()
                .url(container.getJdbcUrl())
                .username(container.getUsername())
                .password(container.getPassword())
                .build();
    }
}
