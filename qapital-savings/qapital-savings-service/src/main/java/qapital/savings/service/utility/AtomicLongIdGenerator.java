package qapital.savings.service.utility;

import java.util.concurrent.atomic.AtomicLong;


// As mentioned before, all utility classes should be placed in a utility module.
/**
 * An id generator that uses a atomic long as the backing source and increments by 1 each time.
 *
 */
public class AtomicLongIdGenerator implements IdGenerator {

    private AtomicLong id = new AtomicLong(1);

    private static final AtomicLongIdGenerator INSTANCE = new AtomicLongIdGenerator();


    public AtomicLongIdGenerator(){
    }

    @Override
    public Long generateLong()
    {
        return id.getAndIncrement();
    }


    public static IdGenerator of(){
        return INSTANCE;
    }
}
