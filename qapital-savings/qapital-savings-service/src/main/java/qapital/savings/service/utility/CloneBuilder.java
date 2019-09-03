package qapital.savings.service.utility;

import org.apache.commons.lang3.builder.Builder;

/**
 * A clone builder is used to create a deep copy of an object that was previously built
 * using a {@code Builder}.
 *
 * = Usage
 *
 * [source,java]
 * ----
 * public class SomeClass implements CloneBuilder<Builder>{
 *    public static class AClassBuilder implements Builder<SomeClass>{
 *        public SomeClass build(){
 *            ...
 *        }
 *    }
 * }
 * ----
 *
 * @implNote The class implementing a clone build MUST ensure that any attribute
 * it has that implements clone builder also is cloned and not just copied.
 *
 */
public interface CloneBuilder<V extends Builder<?>> {

    /**
     * Create a builder that clones the owning instance before returning.
     *
     * @return A builder that has cloned the owning instance before returning
     */
    V cloneBuilder();
}



