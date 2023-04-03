package avans.groep15.themoviedb.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import avans.groep15.themoviedb.R;
import avans.groep15.themoviedb.application.asynctasks.LogOutTask;
import avans.groep15.themoviedb.application.listeners.LogOutListener;
import avans.groep15.themoviedb.datastorage.AccountRepository;

public class AccountActivity extends AppCompatActivity implements LogOutListener {

    private TextView usernameLoggedIn;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.logged_in_account_activity);

        this.usernameLoggedIn = findViewById(R.id.usernameLoggedIn);
        String username = this.usernameLoggedIn.getText().toString();
        username += ": " + AccountRepository.getInstance().getUsernameObservable().getValue();
        this.usernameLoggedIn.setText(username);
    }

    public void logOut(View view) {
        new LogOutTask(this).execute();
        Toast.makeText(this, "Logging Out...", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void hasLoggedOut() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
