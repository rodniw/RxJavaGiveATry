package dev.rodni.ru.rxjavawithkotlin

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class DistinctActivity : AppCompatActivity() {

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //val ints = mutableListOf(1, 3, 5, 15)

        //distinct kills duplicates
        getObservableDistinct()
                .subscribeOn(Schedulers.io())
                .distinct()
                .subscribe{
                int -> println("received: $int")
        }
    }

    private fun getObservableDistinct() : Observable<Int> {
        return Observable.just(1, 2, 2, 2, 4, 6, 6)
    }
}
