package dev.rodni.ru.rxjavamaster.subjects;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import dev.rodni.ru.rxjavamaster.R;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.AsyncSubject;

public class AsyncSubjectActivity extends AppCompatActivity {

    private static String TAG = "TAG";

    @SuppressLint("CheckResult")
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AsyncSubject<String> asyncSubject = AsyncSubject.create();

        Observable.just("Java", "Kotlin", "Json")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(asyncSubject);

        asyncSubject.subscribe(getFirstObservable());
        asyncSubject.subscribe(getSecondObservable());
    }

    protected Observer<String> getFirstObservable() {
        Observer<String> firstObservable = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
            }
            @Override
            public void onNext(String s) {
                Log.i("tag", "firstObservable" + s);
            }
            @Override
            public void onError(Throwable e) {
            }
            @Override
            public void onComplete() {
            }
        };

        return firstObservable;
    }

    protected Observer<String> getSecondObservable() {
        Observer<String> secondObservable = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
            }
            @Override
            public void onNext(String s) {
                Log.i("tag", "secondObservable" + s);
            }
            @Override
            public void onError(Throwable e) {
            }
            @Override
            public void onComplete() {
            }
        };

        return secondObservable;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
