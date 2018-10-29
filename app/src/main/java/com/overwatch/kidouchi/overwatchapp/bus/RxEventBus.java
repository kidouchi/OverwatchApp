package com.overwatch.kidouchi.overwatchapp.bus;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

public class RxEventBus {

    private static RxEventBus INSTANCE;

    private PublishSubject<Object> subject = PublishSubject.create();

    public static RxEventBus getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new RxEventBus();
        }

        return INSTANCE;
    }

    public void setEvent(Object object) {
        subject.onNext(object);
    }

    public Observable<Object> getEvents() {
        return subject;
    }
}
