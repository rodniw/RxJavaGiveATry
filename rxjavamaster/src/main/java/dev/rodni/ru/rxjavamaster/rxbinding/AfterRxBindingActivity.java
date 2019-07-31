package dev.rodni.ru.rxjavamaster.rxbinding;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jakewharton.rxbinding3.view.RxView;
import com.jakewharton.rxbinding3.widget.RxTextView;

import dev.rodni.ru.rxjavamaster.R;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import kotlin.Unit;

public class AfterRxBindingActivity extends AppCompatActivity {

    private TextView textView;
    private EditText editText;
    private Button button;

    private Disposable disposableEdit;
    private Disposable disposableButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_binding);

        textView = findViewById(R.id.text_view_rx_binding);
        editText = findViewById(R.id.edit_text_rx_binding);
        button = findViewById(R.id.button_rx_binding);

        disposableEdit = RxTextView.textChanges(editText)
                .subscribe(charSequence -> {
                    textView.setText(charSequence);
                });

        disposableButton = RxView.clicks(button)
                .subscribe(unit -> {
                    textView.setText("");
                    editText.setText("");
                });
    }
}
