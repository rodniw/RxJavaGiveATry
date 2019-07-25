package dev.rodni.ru.rxjavamaster;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class BufferActivity extends AppCompatActivity {

    private static String TAG = "TAG";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Observable.range(1, 10)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .buffer(4)
                .subscribe(
                        (List<Integer> integers) -> printInts(integers),
                        (Throwable e) -> {},
                        () -> {});
                        /*new Observer<List<Integer>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<Integer> integers) {
                        Log.i("TAG", "onNext");
                        for (Integer i: integers) {
                            Log.i("TAG", String.valueOf(i));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                }*///);

    }

    protected void printInts(List<Integer> integers) {
        Log.i("TAG", "onNext");
        for (Integer i: integers) {
            Log.i("TAG", String.valueOf(i));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
