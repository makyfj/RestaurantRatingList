package com.example.assignment_4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

public class AddRestaurant extends AppCompatActivity {

    private FirebaseFirestore db;
    private EditText nameEditText;
    private EditText locationEditText;
    private RatingBar ratingRatingBar;
    private Button addRestaurantButton;
    private RestaurantListActivity list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_restaurant);

        // initialize firebase firestore
        db = FirebaseFirestore.getInstance();

        // initialize edit text and button
        nameEditText = findViewById(R.id.nameRestaurantEditText);
        locationEditText = findViewById(R.id.locationRestaurantEditText);
        ratingRatingBar = findViewById(R.id.ratingRestarurantRatingBar);
        addRestaurantButton = findViewById(R.id.addRestaurantButton);

        // on a click Add Restaurant
        addRestaurantButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = nameEditText.getText().toString();
                String location = locationEditText.getText().toString();
                float ratingFloat = ratingRatingBar.getRating();
                int ratingInt = (int)ratingFloat;

                Map<String, Object> addRestaurantMap = new HashMap<>();
                addRestaurantMap.put("Location", location);
                addRestaurantMap.put("Name", name);
                addRestaurantMap.put("Rating", ratingInt);

                String path = "Hwk3Restaurants";

                db.collection(path).document()
                        .set(addRestaurantMap, SetOptions.merge())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(getApplicationContext(), "Restaurant Added", Toast.LENGTH_SHORT).show();
                                }else{
                                    Log.d("Data", "Failed");
                                }
                            }
                        });

                int ratingResult = 0;
                Intent result = new Intent();
                result.putExtra("result", ratingResult);

                setResult(RESULT_OK, result);
                finish();

            }
        });
    }
}