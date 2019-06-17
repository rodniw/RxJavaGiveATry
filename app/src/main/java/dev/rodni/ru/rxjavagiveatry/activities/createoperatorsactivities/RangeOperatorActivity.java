package dev.rodni.ru.rxjavagiveatry.activities.createoperatorsactivities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import dev.rodni.ru.rxjavagiveatry.R;
import dev.rodni.ru.rxjavagiveatry.entity.Task;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

/*
range() is very simple and intuitive.
It emits a range of objects. As input, you pass a min value and a max value.
It will emit all the values in the range (Inclusive on the min but exclusive on the max).
 */
public class RangeOperatorActivity extends AppCompatActivity {

    private static final String TAG = "TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_range_operator);

        /* INTRODUCTION
                Input: [x, x+1, ..., x + y]
                Output: Observable<Integer>
         */
        Observable<Integer> observable = Observable
                .range(1, 9)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        observable.subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Integer integer) {
                Log.d(TAG, "onNext: " + integer);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });

        //More complicated example
        Observable<Task> taskObservable = Observable
                .range(0, 9)
                .subscribeOn(Schedulers.io())
                .map(new Function<Integer, Task>() {
                    @Override
                    public Task apply(Integer integer) throws Exception {
                        Log.d(TAG, "apply : " + Thread.currentThread().getName());
                        return new Task("Priority is: " + String.valueOf(integer), false, integer);
                    }
                })
                .takeWhile(new Predicate<Task>() {
                    @Override
                    public boolean test(Task task) throws Exception {
                        return task.getPriority() < 9;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread());

        taskObservable.subscribe(new Observer<Task>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Task task) {
                Log.d(TAG, "onNext: " + task.getPriority());
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
