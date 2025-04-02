package com.denprog.reservationsystem.ui.restaurants;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.denprog.reservationsystem.R;
import com.denprog.reservationsystem.ReservationActivityViewModel;
import com.denprog.reservationsystem.databinding.FragmentRestaurantListBinding;
import com.denprog.reservationsystem.room.entities.Restaurant;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class RestaurantFragment extends Fragment {
    private FragmentRestaurantListBinding binding;
    private MyRestaurantRecyclerViewAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRestaurantListBinding.inflate(inflater, container, false);
        this.adapter = new MyRestaurantRecyclerViewAdapter(data -> {
            NavController navController = Navigation.findNavController(requireActivity(), R.id.reservationAcitvityContainer);
            navController.navigate(RestaurantFragmentDirections.actionRestaurantFragmentToCuisineFragment(data.restaurantCuisineList, data));});
        binding.list.setAdapter(adapter);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ReservationActivityViewModel viewModel = new ViewModelProvider(requireActivity()).get(ReservationActivityViewModel.class);
        viewModel.showPrevBtn.setValue(false);
        viewModel.restaurantSearchQuery.observe(getViewLifecycleOwner(), queryAndRange -> {
            List<Restaurant> filtered = new ArrayList<>();
            List<Restaurant> orig = Restaurant.generateRestaurantList();

            if ((queryAndRange.searchQuery == null || queryAndRange.searchQuery.isEmpty()) && queryAndRange.endRange == 0 && queryAndRange.startRange == 0) {
                adapter.refreshAdapter(orig);
                return;
            } else {

                if ((queryAndRange.searchQuery != null && !queryAndRange.searchQuery.isEmpty()) && queryAndRange.endRange != 0) {
                    orig.forEach(restaurant -> {
                        if (restaurant.restaurantName.contains(queryAndRange.searchQuery) && restaurant.restaurantPrice >= queryAndRange.startRange && restaurant.restaurantPrice <= queryAndRange.endRange) {
                            filtered.add(restaurant);
                        }
                    });
                } else if (queryAndRange.startRange == 0 && queryAndRange.endRange == 0) {
                    orig.forEach(restaurant -> {
                        if (restaurant.restaurantName.contains(queryAndRange.searchQuery) ) {
                            filtered.add(restaurant);
                        }
                    });
                } else {
                    orig.forEach(restaurant -> {
                        if ( restaurant.restaurantPrice >= queryAndRange.startRange && restaurant.restaurantPrice <= queryAndRange.endRange) {
                            filtered.add(restaurant);
                        }
                    });
                }

                adapter.refreshAdapter(filtered);
            }
            adapter.refreshAdapter(filtered);
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        ReservationActivityViewModel viewModel = new ViewModelProvider(requireActivity()).get(ReservationActivityViewModel.class);
        viewModel.showPrevBtn.setValue(false);
    }
}