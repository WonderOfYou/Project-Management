package avans.groep15.themoviedb.presentation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import avans.groep15.themoviedb.R;
import avans.groep15.themoviedb.application.asynctasks.GetMovieTask;
import avans.groep15.themoviedb.application.listeners.MovieListener;
import avans.groep15.themoviedb.datastorage.AccountRepository;
import avans.groep15.themoviedb.domain.Movie;
import avans.groep15.themoviedb.datastorage.database.MovieDatabase;

public class MainActivity extends AppCompatActivity implements MovieListener {

    private final static String TAG = MainActivity.class.getSimpleName();

    private SearchView searchView;
    private ArrayList<Movie> movies;
    private RecyclerView recyclerView;
    private MovieAdapter movieAdapter;
    private AccountRepository accountRepository = AccountRepository.getInstance();
    private String selectedCategory = "";


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:
                // Handle Home click
                return true;
            case R.id.account:
                Intent accountIntent = new Intent(this, LoginActivity.class);
                startActivity(accountIntent);
                return true;
            case R.id.lists:
                // Start ListActivity
                if (accountRepository.getSessionIdObservable().getValue() == null) {
                    Toast.makeText(this, "You need to be logged in first", Toast.LENGTH_SHORT).show();
                    return true;
                }
                Intent listIntent = new Intent(this, ListActivity.class);
                startActivity(listIntent);
                return true;
            case R.id.settings:
                Intent settingsIntent = new Intent(this, SettingsActivity.class);
                startActivity(settingsIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        movies = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerView);
        movieAdapter = new MovieAdapter(this, movies);
        recyclerView.setAdapter(movieAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        if (isConnected()) {
            //IF INTERNET --> API
            Log.i("Internet", "Internet connection found");
            new GetMovieTask(this, this).execute();
        } else {
            //NO INTERNET --> DATABASE
            Log.i("Internet", "No internet connection found");
            new Thread(() -> {

                List<Movie> localMovies = new ArrayList<>();
                localMovies = getMoviesFromDatabase();
                List<Movie> finalLocalMovies = localMovies;
                runOnUiThread(() -> {
                    if (finalLocalMovies.isEmpty()) {
                        Toast.makeText(this, "No movies found", Toast.LENGTH_SHORT).show();
                    } else {
                        Log.i("InsertingDatabase", "Inserting movies from database");
                        // If there are movies in the database, show them in the RecyclerView
                        movies.addAll(finalLocalMovies);
                        movieAdapter.notifyDataSetChanged();
                    }
                });
            }).start();
        }
    }

    // This method checks if there is internet connectivity
    private boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

        boolean connected = (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED);
        return connected;
    }

    private List<Movie> getMoviesFromDatabase() {
        List<Movie> movies = new ArrayList<>();
        // Retrieve movies from the database
        try {
            movies = MovieDatabase.getInstance(this).movieDao().getAllMovies();
            Log.i(TAG, "DATABASE: " + movies);
        } catch (Exception e) {
            Log.e(TAG, "Error retrieving movies from database", e);
        }
        return movies;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public void hasLoaded(List<Movie> movies) {
        for (Movie movie : movies) {
            Log.d("movie", movie.getOriginal_title());
        }
        recyclerView = findViewById(R.id.recyclerView);
        movieAdapter = new MovieAdapter(this, movies);
        recyclerView.setAdapter(movieAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));


        searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Update your adapter's data with the new search query
                movieAdapter.filter(newText);
                return false;
            }
        });

        Spinner spinner = findViewById(R.id.genreSpinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedCategory = parent.getItemAtPosition(position).toString();
                movieAdapter.filterByCategory(selectedCategory);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedCategory = "";
                movieAdapter.filterByCategory(selectedCategory);
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}


