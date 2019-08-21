package dev.rodni.ru.rxjavawithkotlin

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class StartWithActivity : AppCompatActivity() {

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //val ints = mutableListOf(1, 3, 5, 15)

        //distinct kills duplicates
        //startWith adds some items before emitting values
        getObservableStartWith()
                .subscribeOn(Schedulers.io())
                .distinct()
                .startWith("Rodion")
                .subscribe{
                name -> println("received: $name")
        }
    }

    private fun getObservableStartWith() : Observable<String> {
        return Observable.just("Vasya", "Petr", "Lexa")
    }
}
