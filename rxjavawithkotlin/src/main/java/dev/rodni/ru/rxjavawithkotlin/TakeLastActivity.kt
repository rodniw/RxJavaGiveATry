package dev.rodni.ru.rxjavawithkotlin

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class TakeLastActivity : AppCompatActivity() {

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val ints = mutableListOf(1, 3, 5, 15, 16, 4, 2, 8)

        //taking last(n) values by takeLast operator
        getObservableFromIterable(ints)
                .subscribeOn(Schedulers.io())
                .filter{ x -> x > 3 }
                .takeLast(4)
                .subscribe{
                list -> println("received: $list")
        }
    }

    //delaying emmition by using delay operator
    private fun getObservableFromIterable(ints: MutableList<Int>) : Observable<Int> {
        return Observable.fromIterable(ints).delay(3, TimeUnit.SECONDS)
    }
}
