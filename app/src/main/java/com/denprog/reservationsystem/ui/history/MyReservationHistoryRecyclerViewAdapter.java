package com.denprog.reservationsystem.ui.history;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.denprog.reservationsystem.room.entities.ReservationInfo;
import com.denprog.reservationsystem.ui.history.placeholder.PlaceholderContent.PlaceholderItem;
import com.denprog.reservationsystem.databinding.FragmentReservationBinding;
import com.denprog.reservationsystem.util.SimpleClickCallback;

import java.util.ArrayList;
import java.util.List;
public class MyReservationHistoryRecyclerViewAdapter extends RecyclerView.Adapter<MyReservationHistoryRecyclerViewAdapter.ViewHolder> {

    private final List<ReservationInfo> mValues = new ArrayList<>();
    private final SimpleClickCallback<ReservationInfo> simpleClickCallback;

    public MyReservationHistoryRecyclerViewAdapter(SimpleClickCallback<ReservationInfo> simpleClickCallback) {
        this.simpleClickCallback = simpleClickCallback;
    }

    public void refreshList(List<ReservationInfo> mValues) {
        this.mValues.clear();
        this.mValues.addAll(mValues);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(FragmentReservationBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                simpleClickCallback.doThing(holder.mItem);
            }
        });
        holder.binding.totalDisplayReservationItem.setText(holder.mItem.reservationPrice + " Pesos");
        holder.binding.reservationCode.setText("Reservation at " + holder.mItem.restaurantName);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        FragmentReservationBinding binding;
        public ReservationInfo mItem;

        public ViewHolder(FragmentReservationBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mItem.reservationInfo + "'";
        }
    }
}