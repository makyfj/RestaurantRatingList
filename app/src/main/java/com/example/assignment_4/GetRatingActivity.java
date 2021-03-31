package com.example.assignment_4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
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

        // get intent result

        setFilterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                float resultRatingFloat = setFilterRatingBar.getRating();
                int resultRatingInt = (int)resultRatingFloat;

                Intent ratingResult = new Intent();
                ratingResult.putExtra("ratingResult", resultRatingInt);

                setResult(RESULT_OK, ratingResult);
                finish();
            }
        });
    }
}