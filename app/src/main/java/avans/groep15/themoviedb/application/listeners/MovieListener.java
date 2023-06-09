package avans.groep15.themoviedb.application.listeners;

import android.view.View;
import android.widget.AdapterView;

import androidx.lifecycle.LifecycleOwner;

import java.util.List;

import avans.groep15.themoviedb.domain.Movie;

public interface MovieListener extends LifecycleOwner {
    void hasLoaded(List<Movie> movies);

    void onItemSelected(AdapterView<?> parent, View view, int position, long id);

    void onNothingSelected(AdapterView<?> parent);
}
