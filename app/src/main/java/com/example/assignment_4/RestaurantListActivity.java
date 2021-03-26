package com.example.assignment_4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
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

        // Add test data hardcode
        //Restaurant r;
        //r = new Restaurant("The Shed", 3, "Sayville");
        //restaurantList.add(r);

        // Initialize FirebaseFirestore
        db = FirebaseFirestore.getInstance();

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
                            }
                            adapter.notifyDataSetChanged();
                        }else{
                            Toast.makeText(getApplicationContext(), "INVLID", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        //adapter.notifyDataSetChanged();
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
                Toast.makeText(getApplicationContext(),"Item 2 Selected",Toast.LENGTH_LONG).show();
                return true;
            case R.id.clearFilter:
                Toast.makeText(getApplicationContext(),"Item 3 Selected",Toast.LENGTH_LONG).show();
                return true;
            case R.id.appInfo:
                Toast.makeText(getApplicationContext(),"Item 4 Selected",Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // Methods for menu items activities

    // AddRestaurant activity
    private void launchAddRestaurant(){
        Intent intent = new Intent(this, AddRestaurant.class);
        startActivity(intent);
    }
    // Filter
}