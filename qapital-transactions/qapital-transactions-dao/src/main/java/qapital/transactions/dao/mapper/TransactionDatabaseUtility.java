package qapital.transactions.dao.mapper;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import static java.util.Objects.isNull;

public class TransactionDatabaseUtility {

    private static final SimpleDateFormat COMMON_DATE_TIME_FORMATTER = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static String formatTimestampIfPresent(Timestamp timestamp){
        return !isNull(timestamp) ? COMMON_DATE_TIME_FORMATTER.format(timestamp) : null;
    }

    public static final String AMOUNT = "amount";

    public static final String CURRENCY = "currency";

    public static final String EXECUTION_TIME = "execution_time";

    public static final String ID = "id";

    public static final String PURCHASE_DESCRIPTION = "purchase_description";

    public static final String USER_ID = "user_id";

}
