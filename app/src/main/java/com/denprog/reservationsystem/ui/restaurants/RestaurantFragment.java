package com.denprog.reservationsystem.ui.restaurants;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.denprog.reservationsystem.R;
import com.denprog.reservationsystem.ReservationActivityViewModel;
import com.denprog.reservationsystem.databinding.FragmentRestaurantListBinding;
import com.denprog.reservationsystem.room.entities.Cuisine;
import com.denprog.reservationsystem.room.entities.Restaurant;
import com.denprog.reservationsystem.ui.restaurants.placeholder.PlaceholderContent;
import com.denprog.reservationsystem.util.SimpleClickCallback;

import java.util.Arrays;

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
    }
}