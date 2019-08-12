package dev.rodni.ru.rxjavawithkotlin

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.reactivex.Observable

class FromIterableActivity : AppCompatActivity() {

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val ints = mutableListOf(1, 3, 5, 15)

        getObservableFromIterable(ints).subscribe{
                list -> println("received: $list")
        }
    }

    private fun getObservableFromIterable(ints: MutableList<Int>) : Observable<Int> {
        return Observable.fromIterable(ints)
    }
}
