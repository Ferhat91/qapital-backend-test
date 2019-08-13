package qapital.transactions.dao.mapper;

import org.jdbi.v3.core.statement.SqlStatement;
import org.jdbi.v3.sqlobject.customizer.SqlStatementCustomizerFactory;
import org.jdbi.v3.sqlobject.customizer.SqlStatementCustomizingAnnotation;
import org.jdbi.v3.sqlobject.customizer.SqlStatementParameterCustomizer;
import qapital.transactions.domain.Transaction;
import java.lang.annotation.*;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;

@SqlStatementCustomizingAnnotation(BindTransaction.AccountBinderFactory.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER})
public @interface BindTransaction {

    String value();

    class AccountBinderFactory implements SqlStatementCustomizerFactory {

        private void bind(SqlStatement<?> q, BindTransaction bind, String field, Object value, int type) {
            if (value == null) {
                q.bindNull(bind.value() + "." + field, type);
            } else {
                q.bind(bind.value() + "." + field, value);
            }
        }

        @Override
        public SqlStatementParameterCustomizer createForParameter(Annotation annotation,
                                                                  Class<?> sqlObjectType,
                                                                  Method method,
                                                                  Parameter param,
                                                                  int index,
                                                                  Type type) {
            BindTransaction bind = (BindTransaction) annotation;
            return (SqlStatement<?> q, Object bean) -> {
                Transaction transaction = (Transaction) bean;
                q.bind(bind.value() + ".id", transaction.getId());
                q.bind(bind.value() + ".user_id", transaction.getUserId());
                q.bind(bind.value() + ".amount", transaction.getAmount());
                q.bind(bind.value() + ".purchase_description", transaction.getPurchaseDescription());
                q.bind(bind.value() + ".execution_time", transaction.getExecutionTime());
            };
        }
    }
}
