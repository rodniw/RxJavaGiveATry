package dev.rodni.ru.rxjavawithretrofit.view;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jakewharton.rxbinding3.view.RxView;

import dev.rodni.ru.rxjavawithretrofit.R;
import dev.rodni.ru.rxjavawithretrofit.entity.User;
import dev.rodni.ru.rxjavawithretrofit.service.PostAppService;
import dev.rodni.ru.rxjavawithretrofit.service.RetrofitInstance;
import io.reactivex.functions.Consumer;
import kotlin.Unit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private TextView resultText;
    private EditText emailInput, passwordInput;
    private Button submitButton;

    private User user, responseUser;

    private PostAppService apiService;

    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resultText = findViewById(R.id.tv_result);
        emailInput = findViewById(R.id.email_field);
        passwordInput = findViewById(R.id.password_field);
        submitButton = findViewById(R.id.submit_btn);

        RxView.clicks(submitButton)
                .subscribe(
                        unit -> {
                            postData();
                        }
                );
    }

    private void postData() {
        user = new User();
        user.setEmail(emailInput.getText().toString());
        user.setPassword(passwordInput.getText().toString());

        Log.i("TAG", "Before id: " + user.getId());

        apiService = RetrofitInstance.getInstance();
        Call<User> call = apiService.getPosts(user);

        emailInput.setText("");
        passwordInput.setText("");

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                responseUser = response.body();
                resultText.setText("Response user id is: " + responseUser.getId());
                Log.i("TAG", "After id: " + responseUser.getId());
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }
}
