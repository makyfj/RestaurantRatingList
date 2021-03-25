package com.example.assignment_4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
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
}