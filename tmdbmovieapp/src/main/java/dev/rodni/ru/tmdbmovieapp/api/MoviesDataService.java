package dev.rodni.ru.tmdbmovieapp.api;

import dev.rodni.ru.tmdbmovieapp.entity.MovieDBResponse;
import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MoviesDataService {
    @GET("movie/popular")
    Single<MovieDBResponse> getPopularMovies(@Query("api_key") String apiKey);
}
