package dev.rodni.ru.rxjavawithkotlin

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.reactivex.Observable

class RangeActivity : AppCompatActivity() {

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //val ints = mutableListOf(1, 3, 5, 15)

        getObservableRange().subscribe{
                range -> println("received: $range")
        }
    }

    private fun getObservableRange() : Observable<Int> {
        //start point is 10 and it will count to 30 because range is 20
        return Observable.range(10, 20).repeat(2)
    }
}
