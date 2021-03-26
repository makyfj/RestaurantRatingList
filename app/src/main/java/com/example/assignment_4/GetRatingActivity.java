package com.example.assignment_4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class GetRatingActivity extends AppCompatActivity {

    private RatingBar setFilterRatingBar;
    private Button setFilterButton;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_rating);

        db = FirebaseFirestore.getInstance();

        setFilterButton = findViewById(R.id.setFilterButton);
        setFilterRatingBar = findViewById(R.id.setFilterRatingBar);

        setFilterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int filterRating = setFilterRatingBar.getNumStars();

                /*
                Query nameQuery = cities.whereGreaterThanOrEqualTo("name", "San Francisco");
                 */
                db.collection("Hwk3Restaurants")
                        .whereGreaterThanOrEqualTo("Rating", filterRating)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if(task.isSuccessful()){
                                    for(QueryDocumentSnapshot document: task.getResult()){
                                        Log.d("Rating", document.getId() + " -> " + document.getData());
                                        launchRestaurantListActivity();
                                    }
                                }else{
                                    Log.d("Rating", "FAILED");
                                }
                            }
                        });

            }
        });
    }

    private void launchRestaurantListActivity(){
        Intent intent = new Intent(this, RestaurantListActivity.class);
        startActivity(intent);
    }
}