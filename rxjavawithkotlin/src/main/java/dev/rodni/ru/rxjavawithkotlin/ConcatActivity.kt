package dev.rodni.ru.rxjavawithkotlin

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class ConcatActivity : AppCompatActivity() {

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //val ints = mutableListOf(1, 3, 5, 15)

        //distinct kills duplicates
        //startWith adds some items before emitting values

        //concatWith operator combine two streams into the only one stream
        //instead of mergeWith, concatWith guarantee some ordering
        getObservableConcatFirst()
                .subscribeOn(Schedulers.io())
                .distinct()
                .startWith(1)
                .concatWith(getObservableConcatSecond())
                .subscribe{
                    int -> println("received: $int")
                }
    }

    private fun getObservableConcatFirst() : Observable<Int> {
        return Observable.just(1, 2, 3)
    }

    private fun getObservableConcatSecond() : Observable<Int> {
        return Observable.just(4, 5, 6)
    }
}
