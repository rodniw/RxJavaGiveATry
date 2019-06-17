package dev.rodni.ru.rxjavagiveatry.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.reactivestreams.Subscription;

import dev.rodni.ru.rxjavagiveatry.R;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

//-------

//Flowables are a sizeable topic if you include backpressure and the various buffering strategies.
//Flowables were introduced in RxJava2 as an answer to a problem. The problem was related to something called backpressure.

//The property that distinguishes one from the other is backpressure.
//Observables are not backpressure-aware
//Flowables are backpressure-aware

//Backpressure is when in a Flowable processing pipeline can't process the values fast enough and need a way to tell the upstream producer to slow down.

//-------

public class FlowableActivity extends AppCompatActivity {

    private static final String TAG = "Observable";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flowable);

        Flowable.range(0, 1000000)
                .onBackpressureBuffer()
                .observeOn(Schedulers.computation())
                .subscribe(new FlowableSubscriber<Integer>() {
                    @Override
                    public void onSubscribe(Subscription s) {

                    }
                    @Override
                    public void onNext(Integer integer) {
                        Log.d(TAG, "onNext: " + integer);
                    }
                    @Override
                    public void onError(Throwable t) {
                        Log.e(TAG, "onError: ", t);
                    }
                    @Override
                    public void onComplete() {

                    }
                });

        //Observable to Flowable
        Observable<Integer> observable1 = Observable
                .just(1, 2, 3, 4, 5);

        Flowable<Integer> flowable1 = observable1.toFlowable(BackpressureStrategy.BUFFER);

        //------------------------------------------------------------------------------

        //Flowable to Observable
        Observable<Integer> observable2 = Observable
                .just(1, 2, 3, 4, 5);

        Flowable<Integer> flowable2 = observable2.toFlowable(BackpressureStrategy.BUFFER);

        Observable<Integer> backToObservable = flowable2.toObservable();
    }
}
/*
When converting an Observable to a Flowable, you need to specify a buffering strategy. That's pretty much it.

Here are the backpressure strategy constants.

BackpressureStrategy.BUFFER

BackpressureStrategy.DROP

BackpressureStrategy.ERROR

BackpressureStrategy.LATEST

BackpressureStrategy.MISSING
 */
