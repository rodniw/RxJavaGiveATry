package dev.rodni.ru.rxjavagiveatry;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

import dev.rodni.ru.rxjavagiveatry.data.DataSource;
import dev.rodni.ru.rxjavagiveatry.entity.Task;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
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
public class FirstOperatorsActivity extends AppCompatActivity {

    private static final String TAG = "Observable";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_operators);

        //final Task task = new Task("description", false, 3);
        final List<Task> taskList = DataSource.createTasksList();

        /* INTRODUCTION
             Input:T
             Output:Observable<T>
         */
        //The Create() operator is obviously used to create Observables.
        //It's the most basic but it's also probably the most flexible.
        //If you want to create an Observable and none of the other operators fit your needs, consider the Create() operator.
        Observable<Task> taskObservable = Observable
                .create(new ObservableOnSubscribe<Task>() {
                    @Override
                    public void subscribe(ObservableEmitter<Task> emitter) throws Exception {

                        for (Task task : DataSource.createTasksList()) {
                            if (!emitter.isDisposed()) {
                                emitter.onNext(task);
                            }
                        }

                        if (!emitter.isDisposed()) {
                            emitter.onComplete();
                        }
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        taskObservable.subscribe(new Observer<Task>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Task task) {
                Log.d(TAG, "onNext: " + task.getDescription());
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
