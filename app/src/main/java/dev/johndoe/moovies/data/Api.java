package dev.johndoe.moovies.data;

import dev.johndoe.moovies.data.services.MovieService;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Api {

    public static final String BASE_THUMBNAIL_IMAGE_URL = "https://image.tmdb.org/t/p/w185";
    public static final String BASE_BACKDROP_IMAGE_URL = "https://image.tmdb.org/t/p/w1280";

    private static final String API_KEY = "f75cc41618a10b86e2933ec0a2cb62a9";
    private static final String LANGUAGE = "pt-BR";

    private static final Api ourInstance = new Api();

    public static Api getInstance() {
        return ourInstance;
    }

    private Api() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(chain -> {
            Request original = chain.request();
            HttpUrl originalHttpUrl = original.url();

            // Add default query parameters
            HttpUrl url = originalHttpUrl.newBuilder()
                    .addQueryParameter("api_key", API_KEY)
                    .addQueryParameter("language", LANGUAGE)
                    .build();

            // Create the new request using default query parameters
            Request.Builder requestBuilder = original.newBuilder().url(url);
            Request request = requestBuilder.build();
            return chain.proceed(request);
        });
        // Build retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .client(httpClient.build())
                .baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        // Build the movie service that will handle all calls to endpoints related to movies
        movieService = retrofit.create(MovieService.class);
    }

    // Non static part
    public MovieService movieService;

}
