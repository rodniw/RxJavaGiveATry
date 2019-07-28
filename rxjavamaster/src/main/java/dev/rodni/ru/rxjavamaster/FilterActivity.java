package dev.rodni.ru.rxjavamaster;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.List;

import dev.rodni.ru.rxjavamaster.R;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

public class FilterActivity extends AppCompatActivity {

    private static String TAG = "TAG";

    @SuppressLint("CheckResult")
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Observable.range(1, 20)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(integer -> integer%3 == 0)
                .subscribe(
                        this::printInt,
                        e -> {},
                        () -> {});
    }

    protected void printInt(int a) {
        Log.i("TAG", "onNext" + a);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
