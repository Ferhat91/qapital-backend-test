package qapital.savings.dao;

import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlite3.SQLitePlugin;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import qapital.savings.domain.event.SavingsEvent;

import javax.sql.DataSource;
import java.util.List;
import java.util.Objects;

public class SavingsRuleDaoImpl implements SavingsRuleDao{

    private static final Logger LOGGER = LoggerFactory.getLogger(SavingsRuleDaoImpl.class);

    private Jdbi jdbi;

    public SavingsRuleDaoImpl(DataSource dataSource) {
        this.jdbi = Objects.requireNonNull(Jdbi.create(dataSource)
                .installPlugin(new SQLitePlugin())
                .installPlugin(new SqlObjectPlugin()), "dataSource");
    }

    @Override
    public List<SavingsEvent> getSavingsEvents() {
        return null;
    }
}
