package dev.rodni.ru.tmdbmovieapp.api;

import dev.rodni.ru.tmdbmovieapp.entity.MovieDBResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MoviesDataService {
    @GET("movie/popular")
    Call<MovieDBResponse> getPopularMovies(@Query("api_key") String apiKey);
}
