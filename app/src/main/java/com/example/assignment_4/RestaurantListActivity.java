package com.example.assignment_4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class RestaurantListActivity extends AppCompatActivity {

    // Create member variables
    private List<Restaurant> restaurantList = new ArrayList<>();
    private RecyclerView recyclerView;
    private RestaurantAdapter adapter;

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_list);

        // Set up RecyclerView
        recyclerView = findViewById(R.id.recyclerViewRestaurants);
        adapter = new RestaurantAdapter(restaurantList);

        RecyclerView.LayoutManager layoutManager =
                new LinearLayoutManager(getApplicationContext());

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        // Initialize FirebaseFirestore
        db = FirebaseFirestore.getInstance();

        queryDatabase();
    }

    // Menu Item
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       getMenuInflater().inflate(R.menu.app_bar_menu, menu);
       return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.addRestaurant:
                launchAddRestaurant();
                return true;
            case R.id.filterByRating:
                launchGetRatingActivity();
                return true;
            case R.id.clearFilter:
                launchClearFilter();
                return true;
            case R.id.appInfo:
                launchAppInfo();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // AddRestaurant activity
    private void launchAddRestaurant(){
        Intent intent = new Intent(this, AddRestaurant.class);
        startActivityForResult(intent, 1);
    }
    // Get Rating activity
    private void launchGetRatingActivity(){
        Intent intent = new Intent(this, GetRatingActivity.class);
        startActivityForResult(intent, 0);
    }

    // Clear filter
    private void launchClearFilter(){
        // Displays all restaurants because rating is 0
        queryDatabase(0);
    }
    // App Info
    private void launchAppInfo(){
        Intent intent = new Intent(this, AddInfoActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        queryDatabase(0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Return this request code for getRatingActivity
        if(requestCode == 0){
            if(resultCode == RESULT_OK){
                int rating = data.getIntExtra("result", 0);
                queryDatabase(rating);
            }

            if(resultCode == RESULT_CANCELED){
                Log.d("RATING", "There isn't a rating");
            }
        }

        // Return this request code for addRestrictionActivity
        if(requestCode == 1){
            if(resultCode == RESULT_OK){
                int result = data.getIntExtra("result", 0);
                queryDatabase(result);
            }

            if(resultCode == RESULT_CANCELED){
                Log.d("RESULT", "There ins't a result");
            }
        }
    }

    private void queryDatabase(){

        db.collection("Hwk3Restaurants")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        Restaurant r1;
                        String name, location;
                        long ratingLong;
                        int rating;
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot document: task.getResult()){
                                name = document.getString("Name");
                                ratingLong = document.getLong("Rating");
                                location = document.getString("Location");

                                rating = (int)ratingLong;
                                r1 = new Restaurant(name, rating, location);

                                restaurantList.add(r1);
                                adapter.notifyDataSetChanged();
                            }
                        }else{
                            Toast.makeText(getApplicationContext(), "INVLID", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void queryDatabase(int ratingData){

        restaurantList.clear();

        db.collection("Hwk3Restaurants")
                .whereGreaterThanOrEqualTo("Rating", ratingData)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        Restaurant r1;
                        String name, location;
                        long ratingLong;
                        int rating;
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot document: task.getResult()){
                                name = document.getString("Name");
                                ratingLong = document.getLong("Rating");
                                location = document.getString("Location");

                                rating = (int)ratingLong;
                                r1 = new Restaurant(name, rating, location);

                                restaurantList.add(r1);
                                adapter.notifyDataSetChanged();
                            }
                        }else{
                            Toast.makeText(getApplicationContext(), "INVALID", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
}