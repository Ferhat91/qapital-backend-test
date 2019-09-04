package qapital.broker.kafka.event.subscriber;

import com.qapital.broker.kafka.event.EventWrapperOuterClass;

public interface ConsumerListener {

    void onNext(EventWrapperOuterClass.EventWrapper message);

    void onError(Throwable error);

    void onClose();
}