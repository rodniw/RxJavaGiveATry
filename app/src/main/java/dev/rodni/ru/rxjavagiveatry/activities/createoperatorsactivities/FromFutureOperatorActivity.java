package dev.rodni.ru.rxjavagiveatry.activities.createoperatorsactivities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import dev.rodni.ru.rxjavagiveatry.R;

public class FromFutureOperatorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_from_future_operator);

        /*    NOTE
               Input: Future<T>
               Ouput: Observable<T>
         */
    }
}
