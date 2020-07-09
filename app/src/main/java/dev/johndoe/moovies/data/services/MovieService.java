package dev.johndoe.moovies.data.services;

import dev.johndoe.moovies.data.models.Movie;
import dev.johndoe.moovies.data.models.MoviesResult;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface MovieService {

    @GET("movie/upcoming")
    Call<MoviesResult> getMovies();

    @GET("movie/{id}")
    Call<Movie> getMovie(@Path("id") int movieId);

}
