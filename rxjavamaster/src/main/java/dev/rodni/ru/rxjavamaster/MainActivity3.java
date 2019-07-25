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

//fromArray, fromCallable, fromFuture, fromIterable, fromPublisher
public class MainActivity3 extends AppCompatActivity {

    private static String TAG = "TAG";
    private Observable<String> myObservable;
    private DisposableObserver<String> myObserver;
    private CompositeDisposable compDisposable;

    private String[] helloArray = {"Hello A", "Hello B", "Hello C"};

    private TextView textField;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textField = findViewById(R.id.hello_rxjava_text);

        compDisposable = new CompositeDisposable();

        //for example the difference from just here is that
        //just will emit the only one observable but from array many
        myObservable = Observable.fromArray(helloArray)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        compDisposable.add(
                myObservable
                        .subscribeWith(getObserver())
        );
    }

    protected DisposableObserver getObserver() {
        myObserver = new DisposableObserver<String>() {
            @Override
            public void onNext(String s) {
                Log.i(TAG, "onNext" + s);
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
    }
}
