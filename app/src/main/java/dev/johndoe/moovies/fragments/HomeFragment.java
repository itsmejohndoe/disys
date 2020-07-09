package dev.johndoe.moovies.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import dev.johndoe.moovies.R;
import dev.johndoe.moovies.adapters.MoviesAdapter;
import dev.johndoe.moovies.data.Api;
import dev.johndoe.moovies.data.models.Movie;
import dev.johndoe.moovies.data.models.MoviesResult;
import dev.johndoe.moovies.databinding.FragmentHomeBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    private final Api mApi = Api.getInstance();

    private FragmentHomeBinding mFragmentBinding;
    private MoviesAdapter mMoviesAdapter = new MoviesAdapter(new ArrayList<>());

    public HomeFragment() {}

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        mFragmentBinding.recyclerMovies.setHasFixedSize(false);
        mFragmentBinding.recyclerMovies.setLayoutManager(new LinearLayoutManager(inflater.getContext()));
        mFragmentBinding.recyclerMovies.setAdapter(mMoviesAdapter);

        // Build and before returning the root
        mApi.movieService.getMovies().enqueue(new Callback<MoviesResult>() {
            @Override
            public void onResponse(@NonNull Call<MoviesResult> call, @NonNull Response<MoviesResult> response) {
                if (response.isSuccessful() && response.body() != null) {
                    mFragmentBinding.progressBar.setVisibility(View.GONE);
                    // Put items on the recycler view
                    mMoviesAdapter.addItems(response.body().getResults());
                }
            }

            @Override
            public void onFailure(@NonNull Call<MoviesResult> call, @NonNull Throwable t) {
                Log.d("Moovie", "error in here");
            }
        });

        return mFragmentBinding.getRoot();
    }

}