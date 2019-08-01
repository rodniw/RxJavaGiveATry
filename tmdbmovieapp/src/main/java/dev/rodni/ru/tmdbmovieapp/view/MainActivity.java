package dev.rodni.ru.tmdbmovieapp.view;

import android.content.res.Configuration;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.net.SocketTimeoutException;
import java.util.ArrayList;

import dev.rodni.ru.tmdbmovieapp.R;
import dev.rodni.ru.tmdbmovieapp.api.MoviesDataService;
import dev.rodni.ru.tmdbmovieapp.api.RetrofitInstance;
import dev.rodni.ru.tmdbmovieapp.entity.Movie;
import dev.rodni.ru.tmdbmovieapp.entity.MovieDBResponse;
import dev.rodni.ru.tmdbmovieapp.view.adapter.MovieAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Movie> movies;
    private RecyclerView recyclerView;
    private MovieAdapter movieAdapter;
    private SwipeRefreshLayout swipeContainer;

    private MoviesDataService api;
    private MovieDBResponse dbResponse;
    private Call<MovieDBResponse> call;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle(getString(R.string.app_title));
        getPopularMovies();

        swipeContainer = findViewById(R.id.swipe_layout);
        swipeContainer.setColorSchemeResources(R.color.colorPrimary);
        swipeContainer.setOnRefreshListener(() -> getPopularMovies());
    }

    private void getPopularMovies() {
        movies = new ArrayList<>();

        api = RetrofitInstance.getService();

        call = api.getPopularMovies(getString(R.string.api_key));

        call.enqueue(new Callback<MovieDBResponse>() {
            @Override
            public void onResponse(Call<MovieDBResponse> call, Response<MovieDBResponse> response) {
                dbResponse = response.body();
                if (dbResponse != null && dbResponse.getMovies() != null) {
                    movies = (ArrayList<Movie>) dbResponse.getMovies();
                    init();
                    swipeContainer.getProgressViewEndOffset();
                }
            }
            @Override
            public void onFailure(Call<MovieDBResponse> call, Throwable t) {
                if (t instanceof SocketTimeoutException) {
                    Toast.makeText(
                            getApplicationContext(),
                            "Socket Time out.",
                            Toast.LENGTH_LONG)
                            .show();
                }
            }
        });
    }

    public void init() {
        recyclerView = findViewById(R.id.rvMovies);
        movieAdapter = new MovieAdapter(this, movies);

        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        }

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(movieAdapter);
        movieAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (call != null) {
            if (call.isExecuted()) {
                call.cancel();
            }
        }
    }
}
