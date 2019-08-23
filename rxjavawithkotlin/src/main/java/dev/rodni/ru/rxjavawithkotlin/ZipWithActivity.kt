package dev.rodni.ru.rxjavawithkotlin

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class ZipWithActivity : AppCompatActivity() {

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //val ints = mutableListOf(1, 3, 5, 15)

        //distinct kills duplicates

        //zipWith concat items from firest observable with the items from the second one
        //NOTE that its needed to specify type of the zipWith especially when we have BiFunction inside it
        getObservableZipFirst()
                .subscribeOn(Schedulers.io())
                .distinct()
                .zipWith<String, String>(getObservableZipSecond(), BiFunction { firstName, lastName ->
                    "$firstName $lastName"
                })
                .subscribe{
                    fullname -> println("received: $fullname")
                }
    }

    private fun getObservableZipFirst() : Observable<String> {
        return Observable.just("Vasya", "Andrew", "Jack")
    }

    private fun getObservableZipSecond() : Observable<String> {
        return Observable.just("Pupkin", "Serov", "Ivanov")
    }
}
