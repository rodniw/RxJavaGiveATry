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
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Movie> movies;
    private RecyclerView recyclerView;
    private MovieAdapter movieAdapter;
    private SwipeRefreshLayout swipeContainer;

    private MoviesDataService api;
    //private MovieDBResponse dbResponse;
    //private Call<MovieDBResponse> call;
    private Single<MovieDBResponse> singleCall;
    private CompositeDisposable compositeDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        compositeDisposable = new CompositeDisposable();
        getSupportActionBar().setTitle(getString(R.string.app_title));
        getPopularMovies();

        swipeContainer = findViewById(R.id.swipe_layout);
        swipeContainer.setColorSchemeResources(R.color.colorPrimary);
        swipeContainer.setOnRefreshListener(() -> getPopularMovies());
    }

    private void getPopularMovies() {
        movies = new ArrayList<>();

        api = RetrofitInstance.getService();

        singleCall = api.getPopularMovies(getString(R.string.api_key));

        compositeDisposable.add(
                singleCall
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<MovieDBResponse>() {
                            @Override
                            public void onSuccess(MovieDBResponse movieDBResponse) {
                                movies.addAll(movieDBResponse.getMovies());
                                init();
                                hideSwipe();
                            }

                            @Override
                            public void onError(Throwable e) {

                            }
                        })
        );
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

    private void hideSwipe() {
        swipeContainer.setRefreshing(false);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }
}
