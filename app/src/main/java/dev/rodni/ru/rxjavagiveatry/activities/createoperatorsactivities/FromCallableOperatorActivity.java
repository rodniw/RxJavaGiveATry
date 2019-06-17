package dev.rodni.ru.rxjavagiveatry.activities.createoperatorsactivities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.List;
import java.util.concurrent.Callable;

import dev.rodni.ru.rxjavagiveatry.R;
import dev.rodni.ru.rxjavagiveatry.entity.Task;
import dev.rodni.ru.rxjavagiveatry.utils.StringSource;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/*
fromCallable() will execute a block of code (usually a method) and return a result.

It will not execute the method immediately. It will only execute the method once a subscriber has subscribed.

When should you use this operator?
To generate a single Obseravle item on demand. Like calling a method to retrieve some objects or a list of objects.

Example:

You need to return a Task object from a local SQLite database cache.
All database operations must be done on a background thread. Then the result is returned to the main thread.

You can use a callable to execute the method on a background thread.
Then return the results to the main thread.

Retrieving a Task object from a local SQLite database.
 */
public class FromCallableOperatorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_from_callable_operator);

        // create Observable (method will not execute yet)
        /*   NOTE
               Input: Callable<T>
               Ouput: T
         */
        Observable<List<Task>> callable = Observable
                .fromCallable(new Callable<List<Task>>() {
                    @Override
                    public List<Task> call() throws Exception {
                        //i return null because i do not have MyDatabase class its just for the example
                        //return MyDatabase.getTask();
                        return null;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        // method will be executed since now something has subscribed
        callable.subscribe(new Observer<List<Task>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(List<Task> tasks) {
                //here we can push our data somewhere where they need them
                Log.d(StringSource.getTAG(), "onNext");
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
