package com.denprog.reservationsystem.ui.cuisines;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.denprog.reservationsystem.room.entities.Cuisine;
import com.denprog.reservationsystem.ui.cuisines.placeholder.PlaceholderContent.PlaceholderItem;
import com.denprog.reservationsystem.databinding.FragmentCuisineItemBinding;
import com.denprog.reservationsystem.util.SimpleClickCallback;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link PlaceholderItem}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyCuisineRecyclerViewAdapter extends RecyclerView.Adapter<MyCuisineRecyclerViewAdapter.ViewHolder> {

    private final List<Cuisine> mValues = new ArrayList<>();
    private final SimpleClickCallback<Cuisine> cuisineSimpleClickCallback;

    public MyCuisineRecyclerViewAdapter(SimpleClickCallback<Cuisine> cuisineSimpleClickCallback) {
        this.cuisineSimpleClickCallback = cuisineSimpleClickCallback;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(FragmentCuisineItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    public void refreshAdapter(List<Cuisine> cuisines) {
        this.mValues.clear();
        this.mValues.addAll(cuisines);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.binding.cuisineName.setText(holder.mItem.cuisineName);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cuisineSimpleClickCallback.doThing(holder.mItem);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public void refreshList(Cuisine[] cuisinesOFSelectedRestaurant) {
        this.mValues.addAll(Arrays.asList(cuisinesOFSelectedRestaurant));
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView mContentView;
        public Cuisine mItem;
        private FragmentCuisineItemBinding binding;

        public ViewHolder(FragmentCuisineItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            mContentView = binding.content;
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}