package dev.rodni.ru.rxjavamaster;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private static String TAG = "TAG";
    private Observable<String> myObservable;
    private DisposableObserver<String> myObserver;
    private CompositeDisposable compDisposable;
    //private Disposable disposable;

    private String helloText = "Hello from RxJava";
    private TextView textField;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //helloText = getString(R.string.hello_rxjava_world);
        textField = findViewById(R.id.hello_rxjava_text);

        compDisposable = new CompositeDisposable();

        //just operator convert smth to the Observer which will emit data
        myObservable = Observable.just(helloText)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        //        .subscribeOn(Schedulers.io())
        //        .observeOn(AndroidSchedulers.mainThread());

        compDisposable.add(
                myObservable
                        .subscribeWith(getObserver())
        );
        //myObservable.subscribe(myObserver);
    }

    protected DisposableObserver getObserver() {
        myObserver = new DisposableObserver<String>() {
            @Override
            public void onNext(String s) {
                Log.i(TAG, "onNext");
                //textField.setText(s);
            }

            @Override
            public void onError(Throwable e) {
                Log.i(TAG, "onError");
            }

            @Override
            public void onComplete() {
                Log.i(TAG, "onComplete");
            }
        };
        return myObserver;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compDisposable.clear();
        //myObserver.dispose();
        //disposable.dispose();
    }
}
