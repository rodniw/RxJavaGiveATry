package dev.rodni.ru.rxjavamaster;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SkipLastActivity extends AppCompatActivity {

    private static String TAG = "TAG";

    @SuppressLint("CheckResult")
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Observable.just(1, 2, 1, 5, 5, 6, 6)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .skipLast(3)
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
