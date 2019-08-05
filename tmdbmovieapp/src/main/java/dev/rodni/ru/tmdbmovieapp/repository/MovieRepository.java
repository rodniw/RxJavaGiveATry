package dev.rodni.ru.tmdbmovieapp.repository;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import dev.rodni.ru.tmdbmovieapp.R;
import dev.rodni.ru.tmdbmovieapp.api.MoviesDataService;
import dev.rodni.ru.tmdbmovieapp.api.RetrofitInstance;
import dev.rodni.ru.tmdbmovieapp.entity.Movie;
import dev.rodni.ru.tmdbmovieapp.entity.MovieDBResponse;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class MovieRepository {
    private Application application;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    private MutableLiveData<List<Movie>> movieLiveData =  new MutableLiveData();
    private ArrayList<Movie> moviesList;
    private Single<MovieDBResponse> moviesResponseObservable;
    private MoviesDataService api;

    public MovieRepository(Application application) {
        this.application = application;
        getPopularMovies();
    }

    private void getPopularMovies() {
        moviesList = new ArrayList<>();
        api = RetrofitInstance.getService();
        moviesResponseObservable = api.getPopularMovies(application.getString(R.string.api_key));

        compositeDisposable.add(
                moviesResponseObservable
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .filter(movieDBResponse -> movieDBResponse.getTotalMovies() > 8)
                        .toSingle()
                        .subscribeWith(new DisposableSingleObserver<MovieDBResponse>() {
                            @Override
                            public void onSuccess(MovieDBResponse movieDBResponse) {
                                moviesList.addAll(movieDBResponse.getMovies());
                                movieLiveData.postValue(moviesList);
                            }

                            @Override
                            public void onError(Throwable e) {

                            }
                        })
        );
    }

    public MutableLiveData<List<Movie>> getMovieLiveData() {
        return movieLiveData;
    }

    public void clear() {
        compositeDisposable.clear();
    }
}
