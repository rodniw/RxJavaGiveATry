package dev.rodni.ru.rxjavawithkotlin

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class TimerActivity : AppCompatActivity() {

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //val ints = mutableListOf(1, 3, 5, 15)

        getObservableInterval()
                .subscribeOn(Schedulers.io())
                .subscribe{
                long -> println("received: $long")
        }
    }

    private fun getObservableInterval() : Observable<Long> {
        //delays the emmition
        return Observable.timer(5, TimeUnit.SECONDS)
    }
}
