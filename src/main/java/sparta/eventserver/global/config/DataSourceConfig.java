package sparta.eventserver.global.config;

import java.util.Map;
import javax.sql.DataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;

@Configuration
public class DataSourceConfig {

    @Bean
    @ConfigurationProperties("spring.datasource")
    public DataSource masterDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    @ConfigurationProperties("spring.datasource.read")
    public DataSource slaveDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    public DataSource routingDataSource(DataSource masterDataSource, DataSource slaveDataSource) {
        RoutingDataSource routingDataSource = new RoutingDataSource();
        routingDataSource.setTargetDataSources(Map.of(
                RoutingDataSource.MASTER, masterDataSource,
                RoutingDataSource.SLAVE, slaveDataSource
        ));
        routingDataSource.setDefaultTargetDataSource(masterDataSource);
        return routingDataSource;
    }

    /**
     * 실제 쿼리 실행 시점까지 커넥션 획득을 미룬다.
     * 이게 있어야 트랜잭션의 readOnly 플래그가 라우팅에 정상 반영된다.
     */
    @Bean
    @Primary
    public DataSource dataSource(DataSource routingDataSource) {
        return new LazyConnectionDataSourceProxy(routingDataSource);
    }
}
