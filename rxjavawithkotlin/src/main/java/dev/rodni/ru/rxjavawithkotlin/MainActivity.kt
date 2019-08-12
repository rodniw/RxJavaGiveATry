package dev.rodni.ru.rxjavawithkotlin

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val ints = arrayOf(1, 3, 5, 15)

        val dataStream = getObservable(ints)

        val dataObserver = getObserver()

        dataStream
                .subscribeOn(Schedulers.io())
                .subscribe(dataObserver)
    }

    private fun getObservable(ints: Array<Int>) : Observable<Array<Int>> {
        return Observable.fromArray(ints)
    }

    private fun getObserver() : Observer<Array<Int>>{
        return object : Observer<Array<Int>> {
            override fun onComplete() {
                println("onComplete")
            }

            override fun onSubscribe(d: Disposable) {
                println("onSubscribe")
            }

            override fun onNext(t: Array<Int>) {
                println("onNext ${Arrays.toString(t)}")
            }

            override fun onError(e: Throwable) {
                println("onError ${e.message}")
            }
        }
    }
}
