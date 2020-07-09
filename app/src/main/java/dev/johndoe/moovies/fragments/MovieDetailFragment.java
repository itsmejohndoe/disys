package dev.johndoe.moovies.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.Navigator;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.google.android.material.chip.Chip;

import dev.johndoe.moovies.R;
import dev.johndoe.moovies.data.Api;
import dev.johndoe.moovies.data.models.Genre;
import dev.johndoe.moovies.data.models.Movie;
import dev.johndoe.moovies.databinding.FragmentMovieDetailBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MovieDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MovieDetailFragment extends Fragment {

    public static final String ARG_MOVIE = "mMovie";
    private final Api mApi = Api.getInstance();
    private Movie mMovie;
    private FragmentMovieDetailBinding mFragmentBinding;

    public MovieDetailFragment() {}

    public static MovieDetailFragment newInstance(int movieId) {
        MovieDetailFragment fragment = new MovieDetailFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_MOVIE, movieId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mMovie = getArguments().getParcelable(ARG_MOVIE);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_movie_detail, container, false);
        mFragmentBinding.toolbar.setNavigationIcon(R.drawable.baseline_arrow_back_24);
        mFragmentBinding.toolbar.setNavigationOnClickListener(view -> {
            Navigation.findNavController(mFragmentBinding.getRoot()).navigateUp();
        });

        // Build UI before returning the root view
        Glide.with(mFragmentBinding.getRoot()).load(Api.BASE_BACKDROP_IMAGE_URL.concat(mMovie.getPosterImagePath())).into(mFragmentBinding.movieParallaxImage);
        mFragmentBinding.collapsingToolbar.setTitle(mMovie.getTitle());
        // Load detailed data for the movie
        mApi.movieService.getMovie(mMovie.getId()).enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(@NonNull Call<Movie> call, @NonNull Response<Movie> response) {
                if (response.isSuccessful() && response.body() != null) {
                    mMovie = response.body();
                    // Start updating UI to reflect the loaded movie with more complementary info
                    mFragmentBinding.progressBar.setVisibility(View.GONE);
                    mFragmentBinding.content.setVisibility(View.VISIBLE);
                    mFragmentBinding.majority.setVisibility(mMovie.isAdultOnly() ? View.VISIBLE : View.GONE);
                    mFragmentBinding.overview.setText(mMovie.getOverview());
                    mFragmentBinding.runtime.setText(getString(R.string.s_duration_text, mMovie.getRuntime()));
                    mFragmentBinding.rating.setText(getString(R.string.s_movie_only_rating, String.valueOf(mMovie.getVoteAverage())));
                    mFragmentBinding.votes.setText(getString(R.string.s_votes_text, mMovie.getVoteCount()));
                    mFragmentBinding.popularity.setText(String.valueOf(mMovie.getPopularity()));
                    mFragmentBinding.releaseDate.setText(getString(R.string.s_release_date, mMovie.getReleaseDateInBr()));
                    mFragmentBinding.originalTitle.setText(getString(R.string.s_original_title, mMovie.getOriginalTitle()));
                    // Build genres view
                    for (Genre genre : mMovie.getGenres()) {
                        Chip chip = (Chip) getLayoutInflater().inflate(R.layout.view_chip, container, false);
                        chip.setText(genre.getName());

                        mFragmentBinding.genresGroup.addView(chip);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<Movie> call, @NonNull Throwable t) {

            }
        });

        return mFragmentBinding.getRoot();
    }
}