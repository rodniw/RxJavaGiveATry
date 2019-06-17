package dev.rodni.ru.rxjavagiveatry;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import dev.rodni.ru.rxjavagiveatry.data.DataSource;
import dev.rodni.ru.rxjavagiveatry.entity.Task;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Observable";

    //we need compositedisposable object to unsubscribe all our observable inside ondestroy method
    private CompositeDisposable disposables = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //by operator fromIterable() we are getting our data from dataSource
        //subscribeOn() tells us where to do all the work i mean in which thread
        //by using filter we can freeze our work in background thread instead of doing it inside onNext() which is going to freeze ui thread
        //observeOn() tells us there to show the results of our work
        Observable<Task> taskObservable = Observable
                .fromIterable(DataSource.createTasksList())
                .subscribeOn(Schedulers.io())
                .filter(new Predicate<Task>() {
                    @Override
                    public boolean test(Task task) throws Exception {
                        return task.isComplete();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread());

        taskObservable.subscribe(new Observer<Task>() {
            @Override
            public void onSubscribe(Disposable d) {
                //here we added our subscription into comp disp to have possibility to unsubscribe then after
                disposables.add(d);
                Log.d(TAG,"onSubscribe");
            }

            @Override
            public void onNext(Task task) {
                Log.d(TAG, "onNext" + Thread.currentThread().getName());
                Log.d(TAG, "onNext" + task.getDescription());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError: ", e);
            }

            @Override
            public void onComplete() {
                Log.d(TAG,"onComplete");
            }
        });

        disposables.add(taskObservable.subscribe(new Consumer<Task>() {
            @Override
            public void accept(Task task) throws Exception {

            }
        }));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //by clear() method we throw away all our subscriptions from composite disposable
        disposables.clear();
        //but we have one more method - dispose() and by this method we can hardly deprecate every description
    }
}
