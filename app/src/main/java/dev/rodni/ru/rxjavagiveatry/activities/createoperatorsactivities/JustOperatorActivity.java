package dev.rodni.ru.rxjavagiveatry.activities.createoperatorsactivities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import dev.rodni.ru.rxjavagiveatry.R;
import dev.rodni.ru.rxjavagiveatry.entity.Task;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/*
The 'just()' and 'create()' operators should be used if you want to create a single Observable.
The Just() operator has the ability to accept a list of up to 10 entries.
However, I don't see a point in passing it a list because there's other operators
that also accepts lists, and they aren't limited to 10 entries.
So I recommend only using it to create a single observable.

range() and repeat() are great for replacing loops or any iterative processes / methods.
You can do the work on a background thread and observe the results on the main thread.
 */
public class JustOperatorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_just_operator);

        final Task task = new Task("description", false, 3);

        /*      INTRODUCTION
               Input: T... (Optional Array[10])
               Output: Observable<T>
         */
        //NOTE: You can only pass a maximum of 10 objects to the just() operator. Not that you'll ever use it.
        Observable<Task> taskObservable = Observable
                .just(task)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        taskObservable.subscribe(new Observer<Task>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Task task) {

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
