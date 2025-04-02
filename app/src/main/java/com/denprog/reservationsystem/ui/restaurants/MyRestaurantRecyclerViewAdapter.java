package com.denprog.reservationsystem.ui.restaurants;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.denprog.reservationsystem.room.entities.Restaurant;
import com.denprog.reservationsystem.ui.restaurants.placeholder.PlaceholderContent.PlaceholderItem;
import com.denprog.reservationsystem.databinding.FragmentRestaurantItemBinding;
import com.denprog.reservationsystem.util.SimpleClickCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link PlaceholderItem}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyRestaurantRecyclerViewAdapter extends RecyclerView.Adapter<MyRestaurantRecyclerViewAdapter.ViewHolder> {

    private final List<Restaurant> mValues = new ArrayList<Restaurant>();
    private final SimpleClickCallback<Restaurant> restaurantSimpleClickCallback;

    public MyRestaurantRecyclerViewAdapter(SimpleClickCallback<Restaurant> restaurantSimpleClickCallback) {
        this.restaurantSimpleClickCallback = restaurantSimpleClickCallback;
        mValues.addAll(Restaurant.generateRestaurantList());
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(FragmentRestaurantItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.binding.restaurantName.setText(mValues.get(position).restaurantName);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                restaurantSimpleClickCallback.doThing(holder.mItem);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView mContentView;
        public Restaurant mItem;

        FragmentRestaurantItemBinding binding;
        public ViewHolder(FragmentRestaurantItemBinding binding) {
            super(binding.getRoot());
            this.binding =binding;
            mContentView = binding.content;
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}