package com.denprog.reservationsystem.ui.cuisines;

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
import com.denprog.reservationsystem.databinding.FragmentCuisineListBinding;
import com.denprog.reservationsystem.room.entities.Cuisine;
import com.denprog.reservationsystem.ui.cuisines.placeholder.PlaceholderContent;
import com.denprog.reservationsystem.util.SimpleClickCallback;

public class CuisineFragment extends Fragment {

    FragmentCuisineListBinding binding;
    MyCuisineRecyclerViewAdapter adapter;
    ReservationActivityViewModel viewModel;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCuisineListBinding.inflate(inflater, container, false);
        CuisineFragmentArgs args = CuisineFragmentArgs.fromBundle(getArguments());
        adapter = new MyCuisineRecyclerViewAdapter(new SimpleClickCallback<Cuisine>() {
            @Override
            public void doThing(Cuisine data) {
                NavController navController = Navigation.findNavController(requireActivity(), R.id.reservationAcitvityContainer);
                navController.navigate(CuisineFragmentDirections.actionCuisineFragmentToReservationInfoFragment(data, args.getSelectedRestaurant()));
            }
        });
        adapter.refreshList(args.getCuisinesOFSelectedRestaurant());
        binding.list.setAdapter(adapter);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.viewModel = new ViewModelProvider(requireActivity()).get(ReservationActivityViewModel.class);

        CuisineFragmentArgs args = CuisineFragmentArgs.fromBundle(getArguments());
        viewModel.totalMutableLiveData.setValue(args.getSelectedRestaurant().restaurantPrice);
    }
}