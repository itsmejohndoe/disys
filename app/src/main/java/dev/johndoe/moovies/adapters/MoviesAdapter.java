package dev.johndoe.moovies.adapters;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import dev.johndoe.moovies.R;
import dev.johndoe.moovies.data.Api;
import dev.johndoe.moovies.data.models.Movie;
import dev.johndoe.moovies.databinding.ViewMovieBinding;
import dev.johndoe.moovies.fragments.MovieDetailFragment;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder> {
    private List<Movie> mMovies;

    public MoviesAdapter(List<Movie> movies) {
        this.mMovies = movies;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View movieView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_movie, parent, false);
        return new MovieViewHolder(movieView);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        final Movie movie = mMovies.get(position);
        final String ratingText = holder.itemView.getResources().getString(R.string.s_movie_rating, movie.getVoteCount(), String.valueOf(movie.getVoteAverage()));
        final String date = holder.itemView.getResources().getString(R.string.s_release_date, movie.getReleaseDateInBr());
        // Update content
        holder.movie = movie;
        holder.mViewHolderBinding.title.setText(movie.getTitle());
        holder.mViewHolderBinding.overview.setText(movie.getOverview().isEmpty() ? "Descrição não disponível" : movie.getOverview());
        holder.mViewHolderBinding.releaseDate.setText(date);
        holder.mViewHolderBinding.rating.setText(ratingText);
        Glide.with(holder.itemView).load(Api.BASE_THUMBNAIL_IMAGE_URL.concat(movie.getPosterImagePath()))
                .into(holder.mViewHolderBinding.image);
        // Update the margin top only for the first item
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) holder.mViewHolderBinding.card.getLayoutParams();
        params.topMargin = position == 0 ? holder.itemView.getContext().getResources().getDimensionPixelSize(R.dimen.default_margin) : 0;
        holder.mViewHolderBinding.card.setLayoutParams(params);
    }

    @Override
    public int getItemCount() {
        return mMovies.size();
    }

    public void addItems(List<Movie> newMovies) {
        final int lastItemCount = mMovies.size();
        this.mMovies.addAll(newMovies);
        notifyItemRangeInserted(lastItemCount, newMovies.size());
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ViewMovieBinding mViewHolderBinding;
        Movie movie;
        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            mViewHolderBinding = DataBindingUtil.bind(itemView);
            assert mViewHolderBinding != null;
            mViewHolderBinding.card.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Bundle args = new Bundle();
            args.putParcelable(MovieDetailFragment.ARG_MOVIE, movie);
            Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_movieDetailFragment, args);
        }
    }

}
