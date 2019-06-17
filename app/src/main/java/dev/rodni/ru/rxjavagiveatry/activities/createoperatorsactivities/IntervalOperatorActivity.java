package dev.rodni.ru.rxjavagiveatry.activities.createoperatorsactivities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.concurrent.TimeUnit;

import dev.rodni.ru.rxjavagiveatry.R;
import dev.rodni.ru.rxjavagiveatry.utils.StringSource;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

/*
A pretty common problem we Android programmers run into is 'running a method' or
'executing some kind of process' at a specific time interval or delay.

The Interval operator returns an Observable that emits an infinite sequence of ascending integers,
with a constant interval of time of your choosing between emissions.

I'm using the takeWhile() operator to perform a check to each emitted value.
If the value becomes greater than 5, the observable stops emitting results.
 */
public class IntervalOperatorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interval_operator);

        Observable<Long> intervalObservable = Observable
                .interval(1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .takeWhile(new Predicate<Long>() {
                    @Override
                    public boolean test(Long aLong) throws Exception {
                        return aLong <= 5;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread());

        intervalObservable.subscribe(new Observer<Long>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Long aLong) {
                Log.d(StringSource.getTAG(), "onNext interval: " + aLong);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }
}
