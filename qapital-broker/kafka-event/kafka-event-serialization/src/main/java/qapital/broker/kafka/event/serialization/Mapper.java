package kafka.event.serialization;

import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;


// SHOULD BE PUT IN A SEPARATE UTILITY COMMON module and not here in service!!

/**
 * A mapper class that maps between two instance, the source instance and the target instance.
 *
 */
public final class Mapper<SourceType, TargetType> {

    protected SourceType source;

    protected TargetType target;

    protected Mapper(SourceType source, Supplier<TargetType> targetConstructor) {
        this.source = Objects.requireNonNull(source, "source");
        this.target = targetConstructor.get();
    }

    /**
     * Execute the final build for the mapping by delegating to a build function. If the target is the
     * actual thing to build the default build method should be used instead.
     *
     * @param buildFunction
     * @param <BuildType>
     * @return
     */
    public <BuildType> BuildType build(Function<TargetType, BuildType> buildFunction) {
        if (target != null) {
            return buildFunction.apply(target);
        }
        return null;
    }


    /**
     * The default build method returns the target as it is after mapping has occurred.
     *
     * @return
     */
    public TargetType build() {
        return target;
    }


    public static <SourceType, TargetType> Mapper<SourceType, TargetType> of(SourceType source, Supplier<TargetType> targetSupplier) {
        return new Mapper<>(source, targetSupplier);
    }

    public <ValueType> Mapper<SourceType, TargetType> map(Function<SourceType, ValueType> producer,
                                                          BiConsumer<TargetType, ValueType> consumer) {
        return map(producer, value -> value, consumer);
    }

    /**
     * Performs mapping with conversion.
     *
     * @param producer        the producer of the value to consume
     * @param converter
     * @param consumer        the consumer of the value that was produced
     * @param <ValueType>
     * @param <ConvertedType>
     * @return 'this' to support method chaining
     */
    public <ValueType, ConvertedType> Mapper<SourceType, TargetType> map(
            Function<SourceType, ValueType> producer,
            Function<ValueType, ConvertedType> converter,
            BiConsumer<TargetType, ConvertedType> consumer) {
        if (source != null && target != null) {
            ValueType value = producer.apply(source);
            if (value != null) {
                ConvertedType converted = converter.apply(value);
                if (converted != null) {
                    consumer.accept(target, converted);
                }
            }
        }
        return this;
    }
}
