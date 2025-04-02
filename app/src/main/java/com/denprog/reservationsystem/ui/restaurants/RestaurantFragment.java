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
        viewModel.restaurantSearchQuery.observe(getViewLifecycleOwner(), s -> {
            List<Restaurant> filtered = new ArrayList<>();
            List<Restaurant> orig = Restaurant.generateRestaurantList();
            if (s.isEmpty()) {
                adapter.refreshAdapter(orig);
                return;
            }
            orig.forEach(new Consumer<Restaurant>() {
                @Override
                public void accept(Restaurant restaurant) {
                    if (restaurant.restaurantName.contains(s)) {
                        filtered.add(restaurant);
                    }
                }
            });
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