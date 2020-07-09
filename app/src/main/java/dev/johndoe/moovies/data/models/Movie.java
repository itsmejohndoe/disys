package dev.johndoe.moovies.data.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import dev.johndoe.moovies.R;

public class Movie implements Parcelable {

    public Movie() {}

    private int id;
    private String title;
    private String overview;
    @SerializedName("backdrop_path")
    private String backdropImagePath;
    @SerializedName("poster_path")
    private String posterImagePath;
    @SerializedName("release_date")
    private String releaseDate;
    @SerializedName("original_language")
    private String originalLanguage;
    @SerializedName("original_title")
    private String originalTitle;
    private double popularity;
    @SerializedName("vote_count")
    private int voteCount;
    @SerializedName("vote_average")
    private double voteAverage;
    @SerializedName("video")
    private boolean isVideo;
    @SerializedName("adult")
    private boolean isAdultOnly;

    // Complementary info loaded by the detail endpoint
    private List<Genre> genres;
    @SerializedName("production_countries")
    private List<Country> productionCountries;
    @SerializedName("production_companies")
    private List<Company> productionCompanies;
    private int runtime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getBackdropImagePath() {
        return backdropImagePath;
    }

    public void setBackdropImagePath(String backdropImagePath) {
        this.backdropImagePath = backdropImagePath;
    }

    public String getPosterImagePath() {
        return posterImagePath;
    }

    public void setPosterImagePath(String posterImagePath) {
        this.posterImagePath = posterImagePath;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public boolean isVideo() {
        return isVideo;
    }

    public void setVideo(boolean video) {
        isVideo = video;
    }

    public boolean isAdultOnly() {
        return isAdultOnly;
    }

    public void setAdultOnly(boolean adultOnly) {
        isAdultOnly = adultOnly;
    }

    public String getReleaseDateInBr() {
        String[] dateInArray = releaseDate.split("-");
        return dateInArray[2] + "/" + dateInArray[1] + "/" + dateInArray[0];
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.title);
        dest.writeString(this.overview);
        dest.writeString(this.backdropImagePath);
        dest.writeString(this.posterImagePath);
        dest.writeString(this.releaseDate);
        dest.writeString(this.originalLanguage);
        dest.writeString(this.originalTitle);
        dest.writeDouble(this.popularity);
        dest.writeInt(this.voteCount);
        dest.writeDouble(this.voteAverage);
        dest.writeByte(this.isVideo ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isAdultOnly ? (byte) 1 : (byte) 0);
    }

    protected Movie(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.overview = in.readString();
        this.backdropImagePath = in.readString();
        this.posterImagePath = in.readString();
        this.releaseDate = in.readString();
        this.originalLanguage = in.readString();
        this.originalTitle = in.readString();
        this.popularity = in.readDouble();
        this.voteCount = in.readInt();
        this.voteAverage = in.readDouble();
        this.isVideo = in.readByte() != 0;
        this.isAdultOnly = in.readByte() != 0;
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public List<Country> getProductionCountries() {
        return productionCountries;
    }

    public void setProductionCountries(List<Country> productionCountries) {
        this.productionCountries = productionCountries;
    }

    public int getRuntime() {
        return runtime;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    public List<Company> getProductionCompanies() {
        return productionCompanies;
    }

    public void setProductionCompanies(List<Company> productionCompanies) {
        this.productionCompanies = productionCompanies;
    }
}
