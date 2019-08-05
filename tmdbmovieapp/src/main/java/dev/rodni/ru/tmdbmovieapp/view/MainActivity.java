package dev.rodni.ru.tmdbmovieapp.view;

import android.arch.lifecycle.ViewModelProviders;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import dev.rodni.ru.tmdbmovieapp.R;
import dev.rodni.ru.tmdbmovieapp.entity.Movie;
import dev.rodni.ru.tmdbmovieapp.view.adapter.MovieAdapter;
import dev.rodni.ru.tmdbmovieapp.view.viewmodel.MainActivityViewModel;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Movie> movies;
    private RecyclerView recyclerView;
    private MovieAdapter movieAdapter;
    private SwipeRefreshLayout swipeContainer;
    private MainActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);

        getSupportActionBar().setTitle(getString(R.string.app_title));

        swipeContainer = findViewById(R.id.swipe_layout);
        swipeContainer.setColorSchemeResources(R.color.colorPrimary);
        swipeContainer.setOnRefreshListener(() -> getPopularMovies());

        getPopularMovies();
    }

    private void getPopularMovies() {
        viewModel.getPopularMovies().observe(this, moviesList -> {
            movies = (ArrayList<Movie>) moviesList;
            init();
        });
        hideSwipe();
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
        viewModel.clear();
    }
}
