package qapital.savings.dao.rule;

import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlite3.SQLitePlugin;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import qapital.savings.domain.rule.SavingsRule;
import javax.sql.DataSource;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class SavingsRuleDaoImpl implements SavingsRuleDao{

    private static final Logger LOGGER = LoggerFactory.getLogger(SavingsRuleDaoImpl.class);

    private Jdbi jdbi;

    public SavingsRuleDaoImpl(DataSource dataSource) {
        this.jdbi = Objects.requireNonNull(Jdbi.create(dataSource)
                .installPlugin(new SQLitePlugin())
                .installPlugin(new SqlObjectPlugin()), "dataSource");
    }

    @Override
    public List<SavingsRule> getSavingsRules(Long userId) {
        List<SavingsRule> savingsRule = jdbi.withExtension(SavingsRuleDao.class, dao -> dao.getSavingsRules(userId));
        LOGGER.info("Successfully fetched {} savingRule(s) for userId: {}", savingsRule.size(), userId);
        return savingsRule;
    }

    @Override
    public List<SavingsRule> getSavingsRules() {
        List<SavingsRule> savingsRule = jdbi.withExtension(SavingsRuleDao.class, dao -> dao.getSavingsRules());
        LOGGER.info("Successfully fetched {} savingRule(s)", savingsRule.size());
        return savingsRule;
    }

    @Override
    public Optional<SavingsRule> getSavingsRule(Long userId, Long id) {
        Optional<SavingsRule> savingsRule = jdbi.withExtension(SavingsRuleDao.class, dao -> dao.getSavingsRule(userId, id));
        LOGGER.info("Successfully fetched savingRule {} for userId: {}", id, userId);
        return savingsRule;
    }

    @Override
    public void persistSavingsRule(SavingsRule savingsRule) {
        LOGGER.info("Storing savingsRule {} for userId {}", savingsRule.getId(), savingsRule.getUserId());
        jdbi.open().attach(SavingsRuleDao.class).persistSavingsRule(savingsRule);
    }
}
