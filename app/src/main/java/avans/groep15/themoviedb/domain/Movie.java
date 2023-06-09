package avans.groep15.themoviedb.domain;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity(tableName = "movie")
public class Movie implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int idid;
    private int id;
    private String original_title;
    private String overview;
    private boolean adult;
    private double vote_average;
    private String original_language;

    @Ignore
    private Date release_date;

    private String poster_path;
    @Ignore
    private List<Integer> genre_ids;

    @Ignore
    private List<String> actors;
    private String status; //Enum / boolean

    public Movie(int id, String original_title, double vote_average, Date release_date, String poster_path, List<Integer> genres) {
        this.id = id;
        this.original_title = original_title;
        this.vote_average = vote_average;
        this.release_date = release_date;
        this.poster_path = poster_path;
        this.genre_ids = genres;
    }


    public Movie(String original_title, double vote_average) {
        this.original_title = original_title;
        this.vote_average = vote_average;
    }

    public List<Integer> getGenres() {
        return genre_ids;
    }


    public void setGenres(List<Integer> genres) {
        this.genre_ids = genres;
    }

    public List<String> getActors() {
        return actors;
    }

    public void setActors(List<String> actors) {
        this.actors = actors;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public double getVote_average() {
        return vote_average;
    }

    public void setVote_average(double vote_average) {
        this.vote_average = vote_average;
    }

    public Date getRelease_date() {
        return release_date;
    }

    public void setRelease_date(Date release_date) {
        this.release_date = release_date;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }


    @NonNull
    @Override
    public String toString() {
        return this.original_title;
    }



    //Hardcoded genre from id
    public List<String> getGenreString() {
        List<String> genreNames = new ArrayList<>();
        if (genre_ids != null) {

        for (int id : genre_ids) {
            String name;
            switch (id) {
                case 28:
                    name = "Action";
                    break;
                case 12:
                    name = "Adventure";
                    break;
                case 16:
                    name = "Animation";
                    break;
                case 35:
                    name = "Comedy";
                    break;
                case 80:
                    name = "Crime";
                    break;
                case 99:
                    name = "Documentary";
                    break;
                case 18:
                    name = "Drama";
                    break;
                case 10751:
                    name = "Family";
                    break;
                case 14:
                    name = "Fantasy";
                    break;
                case 36:
                    name = "History";
                    break;
                case 27:
                    name = "Horror";
                    break;
                case 10402:
                    name = "Music";
                    break;
                case 9648:
                    name = "Mystery";
                    break;
                case 10749:
                    name = "Romance";
                    break;
                case 878:
                    name = "Science Fiction";
                    break;
                case 10770:
                    name = "TV Movie";
                    break;
                case 53:
                    name = "Thriller";
                    break;
                case 10752:
                    name = "War";
                    break;
                case 37:
                    name = "Western";
                    break;
                default:
                    name = "Unknown";
            }
            genreNames.add(name);
        }
        }
        return genreNames;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public void setOriginal_language(String original_language) {
        this.original_language = original_language;
    }

    public List<Integer> getGenre_ids() {
        return genre_ids;
    }

    public void setGenre_ids(List<Integer> genre_ids) {
        this.genre_ids = genre_ids;
    }


    public int getIdid() {
        return idid;
    }

    public void setIdid(int idid) {
        this.idid = idid;
    }
}



