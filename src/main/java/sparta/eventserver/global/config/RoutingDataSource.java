package sparta.eventserver.global.config;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/**
 * 현재 트랜잭션의 readOnly 여부에 따라 Master / Slave DataSource 를 선택한다.
 * - readOnly = true  -> "slave" (읽기 전용 복제본)
 * - readOnly = false -> "master" (쓰기)
 */
public class RoutingDataSource extends AbstractRoutingDataSource {

    public static final String MASTER = "master";
    public static final String SLAVE = "slave";

    @Override
    protected Object determineCurrentLookupKey() {
        return TransactionSynchronizationManager.isCurrentTransactionReadOnly() ? SLAVE : MASTER;
    }
}
