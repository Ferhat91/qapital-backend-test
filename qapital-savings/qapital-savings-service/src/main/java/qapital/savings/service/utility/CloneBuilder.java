package com.so4it.common.builder;

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
 *
 * @author Magnus poromaa {@literal <mailto:magnus.poromaa@so4it.com/>}
 */
public interface CloneBuilder<V extends Builder<?>> {

    /**
     * Create a builder that clones the owning instance before returning.
     *
     * @return A builder that has cloned the owning instance before returning
     */
    V cloneBuilder();
}



