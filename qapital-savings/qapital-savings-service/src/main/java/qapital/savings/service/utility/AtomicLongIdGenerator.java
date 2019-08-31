package qapital.savings.service.event;

import java.util.concurrent.atomic.AtomicLong;

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
