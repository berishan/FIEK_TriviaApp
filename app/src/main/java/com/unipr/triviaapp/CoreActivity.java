package com.unipr.triviaapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.unipr.triviaapp.db.DatabaseHelper;
import com.unipr.triviaapp.db.Queries;
import com.unipr.triviaapp.entities.User;
import com.unipr.triviaapp.helpers.ExtrasHelper;

import static android.content.ContentValues.TAG;

public class CoreActivity extends AppCompatActivity {


    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    DatabaseReference reference;
    String userId;


    RelativeLayout coreLayout;

    HistoryFragment historyFragment = new HistoryFragment();

    String email;
    String name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_core);

        coreLayout = findViewById(R.id.relativeLayoutCore);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userId = firebaseUser.getUid();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);

        name = "User";
        email = "email";

        new AsyncCoreActivity().execute();
    }

    class AsyncCoreActivity extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            reference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    User user = snapshot.getValue(User.class);
                    if (user != null) {
                        name = user.getName() + " " + user.getLastName();
                        email = user.getEmail();
                        Bundle bundle = new Bundle();
                        bundle.putString(ExtrasHelper.EMAIL, email);
                        bundle.putString(ExtrasHelper.FULL_NAME, name);

                        HomeFragment fragment = new HomeFragment();
                        fragment.setArguments(bundle);
                        historyFragment.setArguments(bundle);
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, fragment).commit();
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(CoreActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                }
            });
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            return;
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;
                    switch (item.getItemId()) {
                        case R.id.nav_home:
                            selectedFragment = new HomeFragment();
                            Bundle bundle = new Bundle();
                            bundle.putString(ExtrasHelper.EMAIL, email);
                            bundle.putString(ExtrasHelper.FULL_NAME, name);
                            selectedFragment.setArguments(bundle);
                            break;
                        case R.id.nav_history:
                            selectedFragment = new HistoryFragment();
                            Bundle bundle1 = new Bundle();
                            bundle1.putString(ExtrasHelper.EMAIL, email);
                            bundle1.putString(ExtrasHelper.FULL_NAME, name);
                            selectedFragment.setArguments(bundle1);
                            break;
                        case R.id.nav_leaderboard:
                            Bundle bundle2 = new Bundle();
                            selectedFragment = new LeaderboardFragment();
                            bundle2.putString(ExtrasHelper.EMAIL, email);
                            bundle2.putString(ExtrasHelper.FULL_NAME, name);
                            selectedFragment.setArguments(bundle2);
                            break;
                        case R.id.nav_quiz:
                            Bundle bundle3 = new Bundle();
                            selectedFragment = new CreateQuizFragment();
                            bundle3.putString(ExtrasHelper.EMAIL, email);
                            bundle3.putString(ExtrasHelper.FULL_NAME, name);
                            selectedFragment.setArguments(bundle3);
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,
                            selectedFragment).commit();
                    return true;
                }
            };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.switch_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_logout:
                SharedPreferences sharedPreferences = getSharedPreferences("checkbox", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("remember", "false");
                editor.apply();
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(this.getApplicationContext(), LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

                break;
                // TODO create quiz later
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
         // mos me t bo logout me back
    }


}

