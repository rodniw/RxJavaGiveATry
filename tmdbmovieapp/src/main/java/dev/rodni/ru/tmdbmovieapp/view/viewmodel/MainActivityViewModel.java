package dev.rodni.ru.tmdbmovieapp.view.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

import dev.rodni.ru.tmdbmovieapp.entity.Movie;
import dev.rodni.ru.tmdbmovieapp.repository.MovieRepository;

public class MainActivityViewModel extends AndroidViewModel {
    private MovieRepository repository;

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        repository = new MovieRepository(application);
    }

    public LiveData<List<Movie>> getPopularMovies() {
        return repository.getMovieLiveData();
    }

    public void clear() {
        repository.clear();
    }
}
