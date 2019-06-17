package dev.rodni.ru.rxjavagiveatry.activities.createoperatorsactivities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import dev.rodni.ru.rxjavagiveatry.R;
import dev.rodni.ru.rxjavagiveatry.data.DataSource;
import dev.rodni.ru.rxjavagiveatry.entity.Task;
import dev.rodni.ru.rxjavagiveatry.utils.StringSource;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/*
This will take an iterable of objects as input and output an Observable. Types of iterables include: List, ArrayList, Set, etc...

It will not execute the method immediately. It will only execute the method once a subscriber has subscribed.

When should you use this operator?
To emit an arbitrary number of items that are known upfront. Same as the fromArray() operator but it's an iterable.
 */
public class FromIterableOperatorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_from_iterable_operator);

        /*    INTRO
                   Input: List<T>, ArrayList<T>, Set<T>, etc...
                   Ouput: Observable<T>
         */
        Observable<Task> taskObservable = Observable
                .fromIterable(DataSource.createTasksList())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        taskObservable.subscribe(new Observer<Task>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Task task) {
                Log.d(StringSource.getTAG(), "onNext: : " + task.getDescription());
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
