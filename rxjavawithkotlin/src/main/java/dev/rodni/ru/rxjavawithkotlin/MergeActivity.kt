package dev.rodni.ru.rxjavawithkotlin

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class MergeActivity : AppCompatActivity() {

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //val ints = mutableListOf(1, 3, 5, 15)

        //distinct kills duplicates
        //startWith adds some items before emitting values
        //merge operator connect two streams into the only one stream
        //but it doesnt guarantee that items will have some ordering
        getObservableMergeFirst()
                .subscribeOn(Schedulers.io())
                .distinct()
                .startWith(1)
                .mergeWith(getObservableMergeSecond())
                .subscribe{
                    int -> println("received: $int")
                }
    }

    private fun getObservableMergeFirst() : Observable<Int> {
        return Observable.just(1, 2, 3)
    }

    private fun getObservableMergeSecond() : Observable<Int> {
        return Observable.just(4, 5, 6)
    }
}
