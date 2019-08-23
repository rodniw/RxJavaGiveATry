package dev.rodni.ru.rxjavawithkotlin

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class MapActivity : AppCompatActivity() {

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //map operator transforms observable's item with some rule which we specify inside the map operator.
        getObservableZipFirst()
                .subscribeOn(Schedulers.io())
                .distinct()
                .map{ it * 2 }
                .subscribe( {
                    number -> println("received: $number")
                }, {
                    throwable ->
                    println(throwable.message.toString())
                }
                )
    }

    private fun getObservableZipFirst() : Observable<Int> {
        return Observable.just(1, 2, 3, 4, 5, 6, 7, 8, 9)
    }

}
