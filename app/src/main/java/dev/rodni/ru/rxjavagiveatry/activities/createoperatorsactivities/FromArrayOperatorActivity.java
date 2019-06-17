package dev.rodni.ru.rxjavagiveatry.activities.createoperatorsactivities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import dev.rodni.ru.rxjavagiveatry.R;
import dev.rodni.ru.rxjavagiveatry.entity.Task;
import dev.rodni.ru.rxjavagiveatry.utils.StringSource;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/*
This will take an array of objects as input and output an Observable.

It will not execute the method immediately. It will only execute the method once a subscriber has subscribed.

When should you use this operator?
To emit an arbitrary number of items that are known upfront.
 */
public class FromArrayOperatorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_from_array_operator);

        Task[] list = new Task[5];
        list[0] = (new Task("Take out the trash", true, 3));
        list[1] = (new Task("Walk the dog", false, 2));
        list[2] = (new Task("Make my bed", true, 1));
        list[3] = (new Task("Unload the dishwasher", false, 0));
        list[4] = (new Task("Make dinner", true, 5));

        /*   INTRO
                 Input: T[]
                 Ouput: Observable<T>
         */
        Observable<Task> taskObservable = Observable
                .fromArray(list)
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
