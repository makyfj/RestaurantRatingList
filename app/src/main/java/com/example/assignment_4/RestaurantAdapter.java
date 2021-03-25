package com.example.assignment_4;

import android.content.pm.ResolveInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.MyViewHolder> {

    private List<Restaurant> restaurantList = new ArrayList<>();

    public class MyViewHolder extends RecyclerView.ViewHolder {

        // Declare items layout
        public TextView nameTextView;
        public TextView locationTextView;
        public RatingBar ratingRatingBar;

        public MyViewHolder(View view){
            super(view);

            // Initialize items layout
            nameTextView = view.findViewById(R.id.nameTextView);
            locationTextView = view.findViewById(R.id.locationTextView);
            ratingRatingBar = view.findViewById(R.id.ratingRatingBar);
        }
    }

    // Adapter Constructor
    public RestaurantAdapter(List<Restaurant> restaurantList) {
        this.restaurantList = restaurantList;
    }

    // Instances of ViewHolder
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        // Initiate the individual item layout
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.restaurants_recyclerview_items, parent, false);

        // Crate a new instance of View Holder with the Inflated Individual
        return new MyViewHolder(itemView);
    }

    // Copies data from the underlying collection to the individual item
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position){

        Restaurant restaurant = restaurantList.get(position);

        // Get data to copy into the layout
        String name = restaurant.getName();
        String location = restaurant.getLocation();
        int rating = restaurant.getRating();

        // Set data inside the view holder
        holder.nameTextView.setText(name);
        holder.locationTextView.setText(location);
        holder.ratingRatingBar.setRating(rating);
    }

    // Return the number of items in the underlying collection
    @Override
    public int getItemCount() {
        return restaurantList.size();
    }

    // Change the underlying data collection(not an override)
    public void setData(List<Restaurant> restaurantList){
        // set to the new collection
        this.restaurantList = restaurantList;

        // Notify the recycler view that the underlying data set has changed
        notifyDataSetChanged();
    }
}
