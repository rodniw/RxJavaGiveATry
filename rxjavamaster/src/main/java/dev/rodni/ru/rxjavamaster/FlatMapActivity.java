package dev.rodni.ru.rxjavamaster;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;

import dev.rodni.ru.rxjavamaster.pojo.Human;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

//in the real world instead of array list of humans its gonna be some
//data for example from the database or from the rest api
public class FlatMapActivity extends AppCompatActivity {

    private static String TAG = "TAG";
    private Observable<Human> myObservable;
    private DisposableObserver<Human> myObserver;
    private CompositeDisposable compDisposable;

    private ArrayList<Human> humans;

    private TextView textField;
    private Human human1;
    private Human human2;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textField = findViewById(R.id.hello_rxjava_text);

        compDisposable = new CompositeDisposable();

        myObservable = Observable.create(emitter -> {
            humans = getListOfHumans();

            for (Human human: humans) {
                emitter.onNext(human);
            }
            emitter.onComplete();
        });

        compDisposable.add(
                myObservable
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .flatMap((Function<Human, Observable<Human>>) human -> {
                            human1 = new Human(15, "Vasya", "Mechanic");
                            human2 = new Human(15, "Mark", "Worker");
                            return Observable.just(human, human1, human2);
                        }
                        )
                        .subscribeWith(getObserver())
        );
    }

    protected DisposableObserver getObserver() {
        myObserver = new DisposableObserver<Human>() {
            @Override
            public void onNext(Human human) {
                Log.i(TAG, "onNext" + human.getName());
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

    private ArrayList<Human> getListOfHumans() {
        ArrayList<Human> humans = new ArrayList<>();

        humans.add(new Human(25, "Serg", "Programmer"));
        humans.add(new Human(15, "Artem", "Journalist"));
        humans.add(new Human(25, "Danil", "Sportsman"));

        return humans;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compDisposable.clear();
    }
}
