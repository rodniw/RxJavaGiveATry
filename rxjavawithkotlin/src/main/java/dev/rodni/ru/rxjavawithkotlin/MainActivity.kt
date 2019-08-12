package dev.rodni.ru.rxjavawithkotlin

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val dataStream = Observable.just(10,20,30,40)

        val dataObserver = object : Observer<Int> {
            override fun onComplete() {
                println("onComplete")
            }
            override fun onSubscribe(d: Disposable) {
            }
            override fun onNext(t: Int) {
                println("onNext: $t")
            }
            override fun onError(e: Throwable) {
                println("onError: ${e.message}")
            }
        }

        dataStream
                .subscribeOn(Schedulers.io())
                .subscribe(dataObserver)
    }
}
